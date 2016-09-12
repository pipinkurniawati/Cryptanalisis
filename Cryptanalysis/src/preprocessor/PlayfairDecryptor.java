/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pipin
 */
public class PlayfairDecryptor {
    private String infile = new String ("C:\\in3.txt"), outfile = new String ("C:\\out3.txt");
    private Map<String, Integer> bigram = new HashMap<String, Integer>();
    char key[] = new String("ABCDEFGHIKLMNOPQRSTUVWXYZ").toCharArray();
    Random rand = new Random();
    private String ciphertext;
    
    public String clearSymbols(String text) {
        String result = new String();
        for (int i=0; i<text.length(); i++) {
            if (text.charAt(i) < 'A' || text.charAt(i) > 'Z') {
                continue;
            } else {
                result += (char) (text.charAt(i));
            }
        } 
        return result;
    }
    
    public void readBytes() {
        Path path = Paths.get(infile);
        String text = new String();
        try {
            byte[] bytes = Files.readAllBytes(path);
            text = new String(bytes);
        } catch (IOException ex) {
            Logger.getLogger(LetterCounter.class.getName()).log(Level.SEVERE, null, ex);
        }
        ciphertext = clearSymbols(text);
        System.out.println(ciphertext);
    }
    
    public void swapLetters(char[] key){
        int i = rand.nextInt()%25;
        int j = rand.nextInt()%25;
        char temp = key[i];
        key[i]= key[j];
        key[j] = temp;
    }
    
    public void swapColumns(char[] key){
        int i = rand.nextInt()%5;
        int j = rand.nextInt()%5;
        char temp;
        int k;
        for(k=0;k<5;k++){
            temp = key[i + k*5];
            key[i + k*5] = key[j + k*5];
            key[j + k*5] = temp;
        }
    }
    
    public void swapRows(char[] key){
        int i = rand.nextInt()%5;
        int j = rand.nextInt()%5;
        char temp;
        int k;
        for(k=0; k<5; k++){
            temp = key[i*5 + k];
            key[i*5 + k] = key[j*5 + k];
            key[j*5 + k] = temp;
        }
    }
    
    public void generateNewKey(char[] newKey, char[] oldKey){
        int k,j,i = rand.nextInt()%50;
        switch(i){
            case 0: 
                newKey = Arrays.copyOf(oldKey, oldKey.length);
                swapRows(newKey);
                break;
            case 1:
                newKey = Arrays.copyOf(oldKey, oldKey.length);
                swapColumns(newKey);
                break;       
            case 2: 
                for(k=0; k<25; k++) {
                    newKey[k] = oldKey[24-k];
                } 
                newKey[25] = '\0'; 
                break;
            case 3: 
                for(k=0; k<5; k++) {
                    for(j=0; j<5; j++) {
                        newKey[k*5 + j] = oldKey[(4-k)*5+j];
                    }
                }
                newKey[25] = '\0';
                break;
            case 4: 
                for(k=0; k<5; k++) {
                    for(j=0; j<5; j++) {
                        newKey[j*5 + k] = oldKey[(4-j)*5+k];
                    }
                }
                newKey[25] = '\0';
                break;
            default:
                newKey = Arrays.copyOf(oldKey, oldKey.length); 
                swapLetters(newKey);
        }
    }
    
    public void countBigram() {
        int i=0;
        String substring;
        while (i < ciphertext.length()-1) {
            substring = ciphertext.substring(i, i+2);
            if (bigram.containsKey(substring)) {
                bigram.put(substring, bigram.get(substring)+1);
            } else {
                bigram.put(substring, 1);
            }
            i+=2;  
        }
    }
    
    public void saveBigram() throws IOException{
        BufferedWriter out = new BufferedWriter(new FileWriter(outfile));
        for (Map.Entry<String,Integer> entry : bigram.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            //if (value >= 44) {
            out.write(key + " " + value + "  \n");
            //}
        }
        out.close();
    }
    
    public static void main(String[] args) {
        PlayfairDecryptor pd = new PlayfairDecryptor();
        pd.readBytes();
        pd.countBigram();
        try {
            pd.saveBigram();
        } catch (IOException ex) {
            Logger.getLogger(LetterCounter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
