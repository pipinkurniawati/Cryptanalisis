/**
 *
 * @file PlayfairCipher.java
 */
import java.util.Vector;

public class PlayfairCipher {
    private static final int MAX_KEY_LENGTH = 25;
    private static final char EXCLUDE_CHAR = 'J';
    private static final char CHANGE_CHAR = 'I';
    private static final char EXTRA_CHAR = 'Z';
    private char[][] key;
    private String text;
    
    /**
     * 
     * @param text
     * @param keyInput 
     * ctor
     */
    public PlayfairCipher(String text, Vector<String> keyInput) {
        this.text = new String(text.toUpperCase());
        this.key = new char[5][5];
        
        for (int i=0; i<5; i++) {
            String line = keyInput.get(i).replaceAll("\\s+","");
            char[] splited = line.toUpperCase().toCharArray();
            for (int j=0; j<5; j++) {
                key[i][j] = splited[j];
            }
        }
    }

    /**
     * 
     * @param text
     * @param keyInput 
     * ctor
     */
    public PlayfairCipher(String text, String keyInput) {
        this.text = new String(text.toUpperCase());
        this.key = new char[5][5];
        String strKey = new String(keyInput.toUpperCase());
        boolean[] occurs = new boolean[256];
        int j = 0;
        for (int i = 0; i < strKey.length(); i++) {
            char c = strKey.charAt(i);
            if ('A' <= c && c <= 'Z' && !occurs[c] && c != EXCLUDE_CHAR) {
                occurs[c] = true;
                key[j/5][j % 5] = c;
                j++;
            }
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != EXCLUDE_CHAR && !occurs[c]) {
                key[j / 5][j % 5] = c;
                j++;
            }
        }
    }
    
    /**
     * 
     * @param text
     * @return string
     * return string alphabet only
     */
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
    /**
     * 
     * @return string
     * encryption function
     */
    public String encrypt() {
        String ciphertext = new String();
        text = clearSymbols(text);
        text = text.replaceAll("" + EXCLUDE_CHAR, "" + CHANGE_CHAR);
        
        //replace second character with Z
        for (int i=0; i<text.length()-1; i+=2) {
          if (text.charAt(i)==text.charAt(i+1)) {
              text = new StringBuffer(text).insert(i+1, EXTRA_CHAR).toString();
          }
        } 
        if (text.length() % 2 != 0) {
            text = new StringBuffer(text).insert(text.length(), EXTRA_CHAR).toString();
        }
        
        for (int i=0; i<text.length()-1; i+=2) {  
            int col1=0, row1=0, col2=0, row2=0;
            for (int j=0; j<key.length; j++) {
                for (int k=0; k<key[j].length; k++) {
                    if (key[j][k] == text.charAt(i)) {
                        row1=j; col1 = k;
                    }
                    if (key[j][k] == text.charAt(i+1)) {
                        row2=j; col2 = k;
                    }
                }
            }
            
            //substitution
            if (row1==row2) {
                ciphertext += key[row1][(col1+1)%5];
                ciphertext += key[row1][(col2+1)%5];
            } else if (col1==col2) {
                ciphertext += key[(row1+1)%5][col1];
                ciphertext += key[(row2+1)%5][col1];
            } else {
                ciphertext += key[row1][col2];
                ciphertext += key[row2][col1];
            }
        }
        return ciphertext;
    }
    
    //decryption function
    public String decrypt() {
        String plaintext = new String();
        text = clearSymbols(text);
        
        for (int i=0; i<text.length()-1; i+=2) {    
            int col1=0, row1=0, col2=0, row2=0;
            for (int j=0; j<key.length; j++) {
                for (int k=0; k<key[j].length; k++) {
                    if (key[j][k] == text.charAt(i)) {
                        row1=j; col1 = k;
                    }
                    if (key[j][k] == text.charAt(i+1)) {
                        row2=j; col2 = k;
                    }
                }
            }
            
            if (row1==row2) {
                if (col1==0) {
                    col1=5;
                } else if (col2==0) {
                    col2=5;
                }
                plaintext += key[row1][col1-1];
                plaintext += key[row1][col2-1];
            } else if (col1==col2) {
                if (row1==0) {
                    row1=4;
                } else if (row2==0) {
                    row2=4;
                }
                plaintext += key[row1-1][col1];
                plaintext += key[row2-1][col1];
            } else {
                plaintext += key[row1][col2];
                plaintext += key[row2][col1];
            }
        }
        return plaintext;
    }
}
