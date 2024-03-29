package fr.til.projetfilrouge.mailspamdetectorproject.Controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerBayesian {
    private Map<String, Double> spamProbabilities = new HashMap<>();
    private Map<String, Double> nonSpamProbabilities = new HashMap<>();
    private Map<String, Integer> spamWordCounts = new HashMap<>();
    private Map<String, Integer> nonSpamWordCounts = new HashMap<>();
    private double spamProbability;
    private double nonSpamProbability;
    private int spamCount;

    private int nonSpamCount;


    /**
     * @Author Mateo Isabey
     * permet l'apprentissage automatique
     * @param spamFolder
     * @param noSpamFolder
     * @throws IOException
     */
    public void train(String spamFolder, String noSpamFolder) throws IOException {

        List<File> spamFiles = Files.walk(Paths.get(spamFolder))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        List<File> noSpamFiles = Files.walk(Paths.get(noSpamFolder))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        spamCount = spamFiles.size();
        nonSpamCount = noSpamFiles.size();

        for (File file : spamFiles) {
            List<String> lines = null;
            try {
                lines = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                continue;
            }

            for (String line : lines) {
                String[] words = line.split("\\s+");
                countWord(words, spamWordCounts);
            }
        }

        for (File file : noSpamFiles) {
            List<String> lines = null;
            try {
                lines = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                continue;
            }

            for (String line : lines) {
                String[] words = line.split("\\s+");
                countWord(words, nonSpamWordCounts);
            }
        }



        for (String word : spamWordCounts.keySet()) {
            spamProbabilities.put(word, (double)(spamWordCounts.get(word) +1 ) / (spamCount));
        }

        for (String word : nonSpamWordCounts.keySet()) {
            nonSpamProbabilities.put(word, (double)(nonSpamWordCounts.get(word) +1 ) / (nonSpamCount));
        }

        spamProbability = (double) spamCount / (spamCount + nonSpamCount);
        nonSpamProbability = (double) nonSpamCount / (spamCount + nonSpamCount);
    }

    /**
     * @AAuthor Mateo Isabey
     * Verification si le mail est un spam
     * @param message
     * @return
     */
    public boolean isSpam(String message) {
        double spamScore = Math.log(spamProbability);
        double hamScore = Math.log(nonSpamProbability);
        String[] words = message.split(" ");
        for (String word : words) {
            if (spamProbabilities.containsKey(word)) {
                spamScore += Math.log(spamProbabilities.get(word));
            } else {
                spamScore += Math.log(1.0 / (spamWordCounts.size() + spamCount));
            }
            if (nonSpamProbabilities.containsKey(word)) {
                hamScore += Math.log(nonSpamProbabilities.get(word));
            } else {
                hamScore += Math.log(1.0 / (nonSpamWordCounts.size() + nonSpamCount));
            }
        }
        return spamScore > hamScore;
    }

    public Map<String, Double> getspamProbabilities() {
        return spamProbabilities;
    }

    public int getNonSpamCount() {
        return nonSpamCount;
    }

    public Map<String, Double> getNonSpamProbabilities() {
        return nonSpamProbabilities;
    }


    /**
     * @Autors Mateo Isabey
     * Methode pour ajouter les mots un par un par un dans la map Ham ou Spam
     * @param words
     * @param WordCounts
     */
    private void countWord(String[] words, Map<String, Integer> WordCounts) {
        for (String word : words) {
            if (WordCounts.containsKey(word)) {
                WordCounts.put(word, WordCounts.get(word) + 1);
            } else {
                WordCounts.put(word, 1);
            }
        }
    }

}