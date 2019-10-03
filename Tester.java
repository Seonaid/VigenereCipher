import edu.duke.*;
import java.util.*;

/**
 * Write a description of Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tester {
    public void testCeasarCipher(){
        System.out.println("\nTesting CaesarCipher");
        
        // Case: a transform to e with key = 4
        CaesarCipher cc4 = new CaesarCipher(4);
        
        String oneCharacter = "a";
        if (!cc4.encrypt(oneCharacter).equals("e")){
            System.out.println(cc4.encrypt(oneCharacter));
        }
        
        // Case: can encrypt and decrypt a small file "asString"
        FileResource fr = new FileResource();
        String message = fr.asString();
        String encryptedMessage = cc4.encrypt(message);
        String decryptedMessage = cc4.decrypt(encryptedMessage);
        if (! decryptedMessage.equals(message)){
            System.out.println("Error in encrypt-decrypt multiple lines from file");
        }
       
        System.out.println("All tests run");
    }
    
    public void testCaesarCracker(){
        System.out.println("\nTesting CaesarCracker");
        
        CaesarCracker cc = new CaesarCracker();
        
        // Case: English
        FileResource fr = new FileResource();
        String message = fr.asString();
        String decryptedMessage = cc.decrypt(message);
        
        System.out.println(decryptedMessage);
        
        // Case: Portuguese (most common letter = 'a')
        CaesarCracker ccp = new CaesarCracker('a');
        FileResource frp = new FileResource();
        decryptedMessage = ccp.decrypt((frp.asString()));
        System.out.println(decryptedMessage);
        
        System.out.println("All tests run");        
    }
    
    public void testVigenereCipher(){
        System.out.println("\nTesting VigenereCipher");
        int[] key = {17, 14, 12, 4};
        VigenereCipher vc = new VigenereCipher(key);
        
        FileResource fr = new FileResource();
        String message = fr.asString();
        String encryptedMessage = vc.encrypt(message);
        System.out.println(encryptedMessage);
        //String decryptedMessage = vc.decrypt(encryptedMessage);
        System.out.println("Key is: " + vc.toString());
        
        System.out.println("All tests run");           
    }
    
    public void testSliceString(){
        System.out.println("\nTesting sliceString");
        // Case sliceString("abcdefghijklm", 0, 3) should return "adgjm"
        VigenereBreaker vb = new VigenereBreaker();
        
        if (!vb.sliceString("abcdefghijklm", 0, 3).equals("adgjm")) {
            System.out.println("error with 0,3");
        }
        
        if (!vb.sliceString("abcdefghijklm", 1, 3).equals("behk")) {
            System.out.println("error with 1,3");
        }        
        
        // Case sliceString("abcdefghijklm", 2, 3) should return "cfil"
        if (!vb.sliceString("abcdefghijklm", 2, 3).equals("cfil")) {
            System.out.println("error with 2, 3");
        } 
        
        // Case sliceString("abcdefghijklm", 4, 5) should return "ej"
        if (!vb.sliceString("abcdefghijklm", 4, 5).equals("ej")) {
            System.out.println("error with 4, 5");
        }         
        System.out.println("All tests run");
    }
    
    public void testTryKeyLength(int length){
        System.out.println("\nTesting tryKeyLength");
        // Case sliceString("abcdefghijklm", 0, 3) should return "adgjm"
        VigenereBreaker vb = new VigenereBreaker();

        
        // sliceString("abcdefghijklm", 0, 3) should return "adgjm"
        
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        int[] key = vb.tryKeyLength(encrypted, length, 'e');
        for (int i=0; i < length; i++) {
            System.out.print(key[i] + " ");
        }
        
        VigenereCipher vc = new VigenereCipher(key);
        String message = vc.decrypt(encrypted);
        FileResource fr2 = new FileResource();
        HashSet<String> dictionary = vb.readDictionary(fr2);
        
        System.out.println(vb.countWords(message, dictionary));
        
        System.out.println(message.substring(0, 80));
        
        System.out.println("\nAll tests run");        
    }
    
    public void testParameterKeyLength() {
        testTryKeyLength(38);
    }
    
    public void testReadDictionary(){
        FileResource fr = new FileResource();
        VigenereBreaker vb = new VigenereBreaker();
        
        vb.readDictionary(fr);
    }
    
    public void testCountWords(){
        System.out.println("\nTesting countWords");
        VigenereBreaker vb = new VigenereBreaker();
        

        FileResource fr = new FileResource();       
        HashSet<String> dictionary = vb.readDictionary(fr);
        
        String message = "hello";
        
        if (vb.countWords(message, dictionary) != 1) {
            System.out.println("Cannot count solitary word");
        }
        
        System.out.println("\nAll tests run");            
    }
    
    public void testMostCommonCharIn() {
        System.out.println("\nTesting mostCommonCharIn");
        VigenereBreaker vb = new VigenereBreaker();         
        
        // Case: English dictionary
        System.out.println("Counting English");
        FileResource fr = new FileResource("dictionaries/English");
        HashSet<String> dictionary = vb.readDictionary(fr);
        if (vb.mostCommonCharIn(dictionary) != 'e') {
            System.out.println("Error in English");
        }
        
        // Case: Portuguese should give 'a'
        System.out.println("Counting Portuguese");        
        fr = new FileResource("dictionaries/Portuguese");
        dictionary = vb.readDictionary(fr);        
        if (vb.mostCommonCharIn(dictionary) != 'a') {
            System.out.println("Error in Portuguese");
        }        
        
        // Case: Italian should give 'a'
        System.out.println("Counting Italian");        
        fr = new FileResource("dictionaries/Italian");
        dictionary = vb.readDictionary(fr);        
        if (vb.mostCommonCharIn(dictionary) != 'a') {
            System.out.println("Error in Italian");
        }  
        
        
        // Case: German should give 'e'... non-Latin letters do not crash
        System.out.println("Counting German");        
        fr = new FileResource("dictionaries/German");
        dictionary = vb.readDictionary(fr);        
        if (vb.mostCommonCharIn(dictionary) != 'e') {
            System.out.println("Error in German");
        }           
        
        System.out.println("\nAll tests run");    
    }
}
