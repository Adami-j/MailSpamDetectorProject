package fr.til.projetfilrouge.mailspamdetectorproject.Controller;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ControllerBayesien {
    private Map<String, Double> spamProba = new HashMap<>();
    private Map<String, Double> nonSpamProba = new HashMap<>();
    private Map<String, Integer> spamCount = new HashMap<>();
    private Map<String, Integer> nonSpamCount = new HashMap<>();
    private double spamProbabilite;
    private double nonSpamProbabilite;
    private int spamCountIndicator;
    private int nonSpamCountIndicator;

    public void SpamClassifier() {

    }

    public void train(String spamFolder, String hamFolder) throws IOException {

    }

    public boolean isSpam() {

        return false;
    }

}





}