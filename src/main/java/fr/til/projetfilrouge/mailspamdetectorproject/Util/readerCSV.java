package fr.til.projetfilrouge.mailspamdetectorproject.Util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class readerCSV {

    /**
     * récupere la fonction un HashMap de tout les mots du CSV qui contient les termes pour la classification bayésienne
     * @param filePath
     * @return HashMap de String
     */
    public static HashMap<String, String> readCSV(String filePath) {
        HashMap<String, String> wordCountMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");//récupère le mot, chaque "mot" est séparé par une virgule
                for (String word : words) {
                    word = word.trim(); //enlever tous les espaces qui peuvent poser un problème
                    wordCountMap.put(word, word);//ajout du mot dans le HashMap
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordCountMap;
    }
}
