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
                //System.err.println("Error reading file: " + file.getName() + ". Ignoring this file.");
                continue;
            }

            for (String line : lines) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (spamWordCounts.containsKey(word)) {
                        spamWordCounts.put(word, spamWordCounts.get(word) + 1);
                    } else {
                        spamWordCounts.put(word, 1);
                    }
                }
            }
        }

        for (File file : noSpamFiles) {
            List<String> lines = null;
            try {
                lines = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                //System.err.println("Error reading file: " + file.getName() + ". Ignoring this file.");
                continue;
            }

            for (String line : lines) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (nonSpamWordCounts.containsKey(word)) {
                        nonSpamWordCounts.put(word, nonSpamWordCounts.get(word) + 1);
                    } else {
                        nonSpamWordCounts.put(word, 1);
                    }
                }
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
}