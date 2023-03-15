package fr.til.projetfilrouge.mailspamdetectorproject.Test;


import fr.til.projetfilrouge.mailspamdetectorproject.Exceptions.CSVException;
import org.junit.Test;

import java.util.HashMap;

import fr.til.projetfilrouge.mailspamdetectorproject.Util.readerCSV;

import static org.junit.Assert.assertEquals;


public class readersCSVTest {

    @Test
    public void testReadCSV() throws CSVException {
        HashMap<String, String> wordCountMap = readerCSV.readCSV("src/main/resources/file/test.csv");
        assertEquals("porno", wordCountMap.get("porno"));
        assertEquals("argent", wordCountMap.get("argent"));
        assertEquals("casino", wordCountMap.get("casino"));

    }


}
