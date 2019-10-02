import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //REPLACE WITH YOUR CODE
        StringBuilder msg = new StringBuilder(message);
        StringBuilder sb = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i+= totalSlices){
            sb.append(msg.charAt(i));
        }
        
        return sb.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
        //WRITE YOUR CODE HERE
        for (int k=0; k < klength; k++){
            // get klength slices starting at points from 0 to klength - 1
            String ss = sliceString(encrypted, k, klength);
            key[k] = cc.getKey(ss);
            //System.out.println(key[k]);
        }
        
        return key;
    }

    public void breakVigenere () {
        //WRITE YOUR CODE HERE
        
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        int[] key = tryKeyLength(encrypted, 4, 'e');
        
        VigenereCipher vc = new VigenereCipher(key);
        String message = vc.decrypt(encrypted);
        
        System.out.println(message.substring(0, 80));
    }
    
}
