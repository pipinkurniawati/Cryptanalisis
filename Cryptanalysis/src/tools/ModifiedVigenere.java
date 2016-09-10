
/**
 *
 * @file ModifiedVigenere.java
 */
public class ModifiedVigenere {
    private static final int MAX_KEY_LENGTH = 25;
    private static final int MOD = 256;
    private String key, text;
    
    /**
     * 
     * @param text
     * @param key
     * constructor
     */
    public ModifiedVigenere(String text, String key) {
        this.text = new String(text);
        this.key = new String (key);
    }
    
    /**
     * 
     * @return string
     * encrypt text based on input key
     */
    public String encrypt() {
        String result = new String();
        String plaintext = new String(text);
        int offset = 0, period = 0, j = 0, move = 0;
        for (int i=0; i<plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            result += (char) (((int)c + (int)key.charAt(j) + move) % MOD);
            j += period + 1;
            if (j >= key.length()) {
                offset++;
                if (offset > period) {
                    period = (period + 1) % key.length();
                    offset = 0;
                    move = (move + 1) % MOD;
                }
                j = offset;
            }
        }
        return result;
    }
    
    /**
     * 
     * @return string
     * dectypt text based on input key
     */
    public String decrypt() {
        String result = new String();
        String ciphertext = new String(text);
        int offset = 0, period = 0, j = 0, move = 0;
        for (int i=0; i<ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            result += (char) (((int)c - (int)key.charAt(j) + MOD + move) % MOD);
            j += period + 1;
            if (j >= key.length()) {
                offset++;
                if (offset > period) {
                    period = (period + 1) % key.length();
                    offset = 0;
                    move = (move + MOD - 1) % MOD;
                }
                j = offset;
            }
        }
        return result;
    }
}
