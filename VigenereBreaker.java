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
        
        FileResource fr2 = new FileResource();
        HashSet<String> dictionary = readDictionary(fr2);
        
        //int[] key = tryKeyLength(encrypted, 4, 'e');
        
        // VigenereCipher vc = new VigenereCipher(key);
        // String message = vc.decrypt(encrypted);
        String message = breakForLanguage(encrypted, dictionary);
        
        System.out.println(message.substring(0, 80));
    }
    
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> dictionary = new HashSet<String>();
        
        for (String line: fr.lines()) {
            dictionary.add(line.toLowerCase());
        }
        
        //System.out.println(dictionary.size());
        return dictionary;
    }
    
    public int countWords(String message, HashSet<String> dictionary){
        int numWords = 0;
        
        for (String word: message.split("\\W")) {
            if (dictionary.contains(word.toLowerCase())) {
                numWords += 1;
            }
        }
        
        return numWords;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
        int mostWords = 0;
        int keyLength = 1;
        int[] key = new int[1];
        for (int k = 1; k < 101; k++) {
            int[] currentKey = tryKeyLength(encrypted, k, 'e');
            VigenereCipher vc = new VigenereCipher(currentKey);
            String message = vc.decrypt(encrypted);
            int numWords = countWords(message, dictionary);
            if (numWords > mostWords) {
                mostWords = numWords;
                keyLength = k;
                key = currentKey;
            }
        }
        
        // key = tryKeyLength(encrypted, 5, 'e');
        // VigenereCipher vc = new VigenereCipher(key);
        // String message = vc.decrypt(encrypted);        
        // mostWords = countWords(message, dictionary);
        
        System.out.println("Key length = " + keyLength);
        System.out.println("Number of words = " + mostWords);
        VigenereCipher vc = new VigenereCipher(key);
        String message = vc.decrypt(encrypted);
        return message;
    }
}
