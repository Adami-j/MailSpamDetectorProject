package fr.til.projetfilrouge.mailspamdetectorproject.Test;

import fr.til.projetfilrouge.mailspamdetectorproject.Controller.ControllerBayesian;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ControllerBayesianTest {

    private ControllerBayesian controller;

    @Before
    public void setup() {
        controller = new ControllerBayesian();
    }

    @Test
    public void testTrain() throws IOException {
        String spamFolder = "src/main/resources/file/SpamDirectory";
        String noSpamFolder = "src/main/resources/file/nonSpamDirectory";
        controller.train(spamFolder, noSpamFolder);

        assertTrue(controller.isSpam("Buy Viagra now!"));
        assertFalse(controller.isSpam("Hello, how are you?"));
    }

}