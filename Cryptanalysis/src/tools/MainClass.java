
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pipin
 */
public class MainClass {
    
    public String clearSpace(String cipher) {
        return cipher.replaceAll("\\s+","");
    }
    
    public String divideIntoN(String cipher, int n) {
        cipher = clearSpace(cipher);
        for (int i=0, j=0; i<cipher.length(); i++) {
            if (i == j+((j+1)*n)) {
                cipher = new StringBuffer(cipher).insert(i, " ").toString();
                j++;
            }
        }
        return cipher;
    }
    
    public String readBytes(String filePath)
    {
        Path path = Paths.get(filePath);
        String text = new String();
        try {
            byte[] bytes = Files.readAllBytes(path);
            text = new String(bytes);
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return text;
    }
    
    public void saveResult(String output, String filePath) throws IOException {
        byte data[] = output.getBytes();
        File someFile = new File(filePath);
        try {
            FileOutputStream fos = new FileOutputStream(someFile);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void printAlgoOption() {
        System.out.println("=======================================");
        System.out.println("                WELCOME                ");
        System.out.println("=======================================");
        System.out.println("********** CHOOSE ALGORITHM ***********");
        System.out.println("1. Standard Vigenere Cipher");
        System.out.println("2. Extended Vigenere Cipher");
        System.out.println("3. Modified Vigenere Cipher");
        System.out.println("4. Playfair Cipher");
        System.out.print("Your Input: ");
    }
    
    public void printTypeOption() {
        System.out.println("********** CHOOSE TYPE **********");
        System.out.println("1. Encryption");
        System.out.println("2. Decryption");
        System.out.print("Your Input: ");
    }
    
    public void printInputOption() {
        System.out.println("********** CHOOSE INPUT **********");
        System.out.println("1. Write to Console");
        System.out.println("2. Read from File");
        System.out.print("Your Input: ");
    }
    
    public void printOption() throws IOException {
        int algo = -1, type = -1, input = -1;
        String text = "", key = "", result = "";
        String path;
        Scanner in = new Scanner(System.in);
        printAlgoOption();
        algo = Integer.parseInt(in.nextLine());
        printTypeOption();
        type = Integer.parseInt(in.nextLine());
        printInputOption();
        input = Integer.parseInt(in.nextLine());
        if (type == 1) {
            switch(input) {
                case 1:
                    System.out.print("Type the plaintext you want to encrypt: ");
                    text = in.nextLine();
                    System.out.print("Type the encryption key: ");
                    key = in.nextLine();
                    break;
                case 2: 
                    System.out.print("Type the file path you want to encrypt: ");
                    text = readBytes(in.nextLine());
                    System.out.print("Type the unique encryption key without J: \n");
                    key = in.nextLine();
                    break;
            }
        }
        else {
            switch(input) {
                case 1:
                    System.out.print("Type the plaintext you want to decrypt: ");
                    text = in.nextLine();
                    System.out.print("Type the decryption key: ");
                    key = in.nextLine();
                    break;
                case 2:
                    System.out.print("Type the file path you want to decrypt: ");
                    text = readBytes(in.nextLine());
                    System.out.print("Type the decryption key: ");
                    key = in.nextLine();
                    break;
            }
        }
        switch (algo) {
            case 1 : // Standard Vigenere Cipher
                StandardVigenere stdVigenere = new StandardVigenere(text, key);
                if (type == 1) {
                    result = stdVigenere.encrypt();
                }
                else {
                    result = stdVigenere.decrypt();
                }
                break;
            case 2 : // Extended Vigenere Cipher
                ExtendedVigenere extVigenere = new ExtendedVigenere(text, key);
                if (type == 1) {
                    result = extVigenere.encrypt();
                }
                else {
                    result = extVigenere.decrypt();
                }
                break;
            case 3 : // Modified Vigenere Cipher
                ModifiedVigenere modVigenere = new ModifiedVigenere(text, key);
                if (type == 1) {
                    result = modVigenere.encrypt();
                }
                else {
                    result = modVigenere.decrypt();
                }
                break;
            case 4 : // Playfair Cipher
                PlayfairCipher playfair = new PlayfairCipher(text, key);
                if (type == 1) {
                    result = playfair.encrypt();
                }
                else {
                    result = playfair.decrypt();
                }
        }
        if (type == 1) {
            System.out.println("\n==================== RESULT ====================");
            System.out.println("Your Plaintext                                  : " + text);
            System.out.println("Your Ciphertext in Original format              : " + result);
            System.out.println("Your Ciphertext without Whitespace              : " + clearSpace(result));
            System.out.println("Your Ciphertext with Space Every 5 Characters   : " + divideIntoN(result, 5) +"\n\n");
        }
        else {
            System.out.println("\n==================== RESULT ====================");
            System.out.println("Your Chipertext                                : " + text);
            System.out.println("Your Plaintext in Original format              : " + result);
            System.out.println("Your Plaintext without Whitespace              : " + clearSpace(result));
            System.out.println("Your Plaintext with Space Every 5 Characters   : " + divideIntoN(result, 5) +"\n\n");
        }
        System.out.print("Saving to file. Type the file path: ");
        path = in.nextLine();
        saveResult(result, path);
        System.out.println("The result has been saved to " + path + "\n\n");
    }
    
    public static void main(String[] args) {
        try {
            MainClass main = new MainClass();
            main.printOption();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}