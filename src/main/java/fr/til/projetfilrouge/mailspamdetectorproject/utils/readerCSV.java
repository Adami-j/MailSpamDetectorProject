package fr.til.projetfilrouge.mailspamdetectorproject.utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class readerCSV {
    public static HashMap<String, String> readCSV(String filePath) {
        HashMap<String, String> wordCountMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                for (String word : words) {
                    word = word.trim();
                    wordCountMap.put(word, word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordCountMap;
    }
}
