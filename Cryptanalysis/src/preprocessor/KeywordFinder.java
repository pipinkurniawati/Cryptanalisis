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
    private StringBuilder keyword = new StringBuilder("--------");
    
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
    
    public void findKeyword() {
        keyword.setCharAt(3, (char)((('H' - 'T' + 26) % 26) + 'A'));
        keyword.setCharAt(4, (char)((('W' - 'H') % 26) + 'A'));
        keyword.setCharAt(5, (char)((('I' - 'E') % 26) + 'A'));
        System.out.println(keyword);
    }
    
    public static void main(String[] args) {
        KeywordFinder kf = new KeywordFinder();
        kf.readBytes();
        kf.classify();
        kf.findKeyword();
    }
}
