package fr.til.projetfilrouge.mailspamdetectorproject.Util;
import fr.til.projetfilrouge.mailspamdetectorproject.Exceptions.CSVException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class readerCSV {

    /**
     * @author MateoIsabey
     * récupere la fonction un HashMap de tout les mots du CSV qui contient les termes pour la classification bayésienne
     * @param filePath
     * @return HashMap de String
     */
    public static HashMap<String, String> readCSV(String filePath) throws CSVException {
        HashMap<String, String> wordCountMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isEmpty = true;
            while ((line = reader.readLine()) != null) {
                isEmpty = false;
                String[] words = line.split(",");//récupère le mot, chaque "mot" est séparé par une virgule
                for (String word : words) {
                    word = word.trim(); //enlever tous les espaces qui peuvent poser un problème
                    wordCountMap.put(word, word);//ajout du mot dans le HashMap
                }
            }
            if (isEmpty) {
                throw new CSVException("Le fichier CSV est vide");
            }
        } catch (IOException e) {
            throw new CSVException("Erreur lors de la lecture du fichier CSV", e);
        }

        return wordCountMap;
    }
}
