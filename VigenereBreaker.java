import java.util.*;
import java.io.*;
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
        
        FileResource frMessage = new FileResource();
        String encrypted = frMessage.asString();
        
        HashMap<String, HashSet<String>> languages = new HashMap<String, HashSet<String>>();
        
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()) {
            FileResource frDictionary = new FileResource(f);
            HashSet<String> dictionary = readDictionary(frDictionary);
            languages.put(f.getName(), dictionary);
        }
        
        breakForAllLangs(encrypted, languages);
        //String message = breakForLanguage(encrypted, dictionary);
        
        //System.out.println(message.substring(0, 80));
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
        char testChar = mostCommonCharIn(dictionary);
        for (int k = 1; k < 101; k++) {
            int[] currentKey = tryKeyLength(encrypted, k, testChar);
            VigenereCipher vc = new VigenereCipher(currentKey);
            String message = vc.decrypt(encrypted);
            int numWords = countWords(message, dictionary);
            if (numWords > mostWords) {
                mostWords = numWords;
                keyLength = k;
                key = currentKey;
            }
        }
        
        System.out.println("Key length = " + keyLength);
        System.out.println("Number of words = " + mostWords);
        System.out.println("Most common character = " + testChar);
        VigenereCipher vc = new VigenereCipher(key);
        String message = vc.decrypt(encrypted);
        return message;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary) {
        HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
        
        for (String word : dictionary) {
            for (char c : word.toLowerCase().toCharArray()) {
                if (hm.keySet().contains(c)) {
                    hm.put(c, hm.get(c)+1);
                } else {
                    hm.put(c, 1);
                }
            } 
        }

        char key = Collections.max(hm.entrySet(), Map.Entry.comparingByValue()).getKey();
        //System.out.println("Most common character is: " + key);
        return key;
    }
    
    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {
        int mostWords = 0;
        String messageLanguage = "";
        
        for (String language: languages.keySet()) {
            HashSet<String> dictionary = languages.get(language);
            String tryMessage = breakForLanguage(encrypted, dictionary);
            int numWords = countWords(tryMessage, dictionary);
            if (numWords > mostWords) {
                messageLanguage = language;
                mostWords = numWords;
            }
        }
        
        System.out.println("Message is in " + messageLanguage);
        System.out.println("First line(s): ");
        String message = breakForLanguage(encrypted, languages.get(messageLanguage));
        System.out.println(message.substring(0, 80));
    }
}
