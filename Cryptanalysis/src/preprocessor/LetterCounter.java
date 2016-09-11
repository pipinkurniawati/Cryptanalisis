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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pipin
 */
public class LetterCounter {
    private String infile = new String ("C:\\in.txt"), outfile = new String ("C:\\out.txt");
    private int[] counter = new int[26];
    private String ciphertext;
    private Map<String, Integer> bigram = new HashMap<String, Integer>();
    private Map<String, Integer> trigram = new HashMap<String, Integer>();
    
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
    }
    
    public void countLetter() {
        int i=0;
        while (i < ciphertext.length()) {
            counter[ciphertext.charAt(i)-'a'] += 1;
            i++;  
        }
    }
    
    public String clearSymbols(String text) {
        String result = new String();
        for (int i=0; i<text.length(); i++) {
            if (text.charAt(i) < 'a' || text.charAt(i) > 'z') {
                continue;
            } else {
                result += (char) (text.charAt(i));
            }
        } 
        return result;
    }
    
    public void countTrigram() {
        int i=0;
        String substring;
        while (i < ciphertext.length()-2) {
            substring = ciphertext.substring(i, i+3);
            if (trigram.containsKey(substring)) {
                trigram.put(substring, trigram.get(substring)+1);
            } else {
                trigram.put(substring, 1);
            }
            i++;  
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
            i++;  
        }
    }
    
    public void saveCounter() throws IOException{
        BufferedWriter out = new BufferedWriter(new FileWriter(outfile));
        for (int i = 0; i < 26; i++) {
            out.write((char)(i+'a') + " " + counter[i] + "  \n");
        }
        out.write("\n\n");
        for (Map.Entry<String,Integer> entry : bigram.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (value >= 44) {
                out.write(key + " " + value + "  \n");
            }
        }
        out.write("\n\n");
        for (Map.Entry<String,Integer> entry : trigram.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (value >= 14) {
                out.write(key + " " + value + "  \n");
            }
        }
        out.close();
    }
    
    public static void main(String[] args) {
        LetterCounter counter = new LetterCounter();
        counter.readBytes();
        counter.countLetter();
        counter.countBigram();
        counter.countTrigram();
        try {
            counter.saveCounter();
        } catch (IOException ex) {
            Logger.getLogger(LetterCounter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
