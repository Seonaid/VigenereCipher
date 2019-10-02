import edu.duke.*;
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
    
    public void testTryKeyLength(){
        System.out.println("\nTesting tryKeyLength");
        // Case sliceString("abcdefghijklm", 0, 3) should return "adgjm"
        VigenereBreaker vb = new VigenereBreaker();

        
        // sliceString("abcdefghijklm", 0, 3) should return "adgjm"
        
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        int[] key = vb.tryKeyLength(encrypted, 4, 'e');
        for (int i=0; i < 4; i++) {
            System.out.print(key[i] + " ");
        }

        System.out.println("\nAll tests run");        
    }
}
