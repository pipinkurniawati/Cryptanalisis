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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class OccurenceCounter {
    private String infile = new String ("C:\\in2.txt");
    private String ciphertext;
    Vector<Integer> positions = new Vector<Integer>(), distances = new Vector<Integer>();
    
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
    
    public void addPositions() {
        for(int i=0; i<ciphertext.length()-2; i++) {
            if(ciphertext.subSequence(i, i+3).equals("HWI")) {
                positions.add(i);
            }
        }
    }
    
    public void countDistances() {
        for(int i=0; i<positions.size()-1; i++) {
            distances.add(positions.get(i+1)-positions.get(i));
            System.out.println(positions.get(i+1)-positions.get(i));
        }
    }
    
    /*public static void main(String[] args) {
        OccurenceCounter oc = new OccurenceCounter();
        oc.readBytes();
        oc.addPositions();
        oc.countDistances();
    }*/
}
