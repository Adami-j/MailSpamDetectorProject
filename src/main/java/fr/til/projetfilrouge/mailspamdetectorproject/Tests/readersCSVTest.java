package fr.til.projetfilrouge.mailspamdetectorproject.Tests;


import org.junit.Test;

import java.util.HashMap;
import java.util.Optional;

import fr.til.projetfilrouge.mailspamdetectorproject.utils.readerCSV;

import static org.junit.Assert.assertEquals;


public class readersCSVTest {

    @Test
    public void testReadCSV() {
        HashMap<String, String> wordCountMap = readerCSV.readCSV("src/main/resources/file/test.csv");
        assertEquals("porno", wordCountMap.get("porno"));
        assertEquals("argent", wordCountMap.get("argent"));
        assertEquals("casino", wordCountMap.get("casino"));

    }


}
