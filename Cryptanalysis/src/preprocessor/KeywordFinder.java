/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class KeywordFinder {
    private String infile = new String ("C:\\in2.txt");
    private String ciphertext;
    private String[] classifier = new String[8];
    private int[] maxCounter;
    
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
    }
    
    public void classify() {
        for(int i=0; i<ciphertext.length(); i++) {
            classifier[i%8] += (char) (ciphertext.charAt(i));
        }
        for (int i=0; i<8; i++) {
            classifier[i] = classifier[i].replaceAll("null", "");
            System.out.println(classifier[i]);
        }
    }
    
    public int getMaxIndex(int[] array) {
        int max = 0, idx = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
              max = array[i];
              idx = i;
            }
        }
        return idx;
    }
    
    public void maxOccurence() {
        for(int i=0; i<8; i++) {
            maxCounter = new int[26];
            for(int j=0; j<classifier[i].length(); j++) {
                maxCounter[classifier[i].charAt(j)-'A'] = maxCounter[classifier[i].charAt(j)-'A']+1;
            }
            classifier[i] = "" + getMaxIndex(maxCounter);
            System.out.println(getMaxIndex(maxCounter));
        }
    }
    
    public static void main(String[] args) {
        KeywordFinder kf = new KeywordFinder();
        kf.readBytes();
        kf.classify();
        kf.maxOccurence();
    }
}
