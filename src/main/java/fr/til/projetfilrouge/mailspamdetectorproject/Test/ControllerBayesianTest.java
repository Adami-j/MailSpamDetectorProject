package fr.til.projetfilrouge.mailspamdetectorproject.Test;

import fr.til.projetfilrouge.mailspamdetectorproject.Controller.ControllerBayesian;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ControllerBayesianTest {

    private ControllerBayesian controller;
    private String spamFolder = "src/main/resources/file/SPAM";
    private String noSpamFolder = "src/main/resources/file/HAM";

    @Before
    public void setup() {
        controller = new ControllerBayesian();
        try {
            controller.train(spamFolder, noSpamFolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests sur des spams hams simples
     * @throws IOException
     */
    @Test
    public void testTrain() throws IOException {

        controller.train(spamFolder, noSpamFolder);

        assertTrue(controller.isSpam("Buy Viagra now!"));
        assertFalse(controller.isSpam("Hello, how are you?"));
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void testContreExemple() throws IOException {

        controller.train(spamFolder, noSpamFolder);

        assertTrue(controller.isSpam("Félicitations ! Vous avez été sélectionné pour participer à notre tirage au sort exclusif et tenter de gagner un iPhone 13 tout neuf ! Pour participer, il vous suffit de cliquer sur ce lien : www.participez-au-tirage.com"));
        assertTrue(controller.isSpam("Offre spéciale pour une durée limitée ! Profitez de notre incroyable réduction de 50 % sur tous nos produits en visitant notre site web : www.promotion-a-ne-pas-manquer.com"));
        // contre exemple 1 ce n'est pas un spam
        assertTrue(controller.isSpam("Bonjour, je suis Julien ADAMI, expert en cybersécurité, je souhaitais vous soumettre mon cv ci join, en attente de votre réponse, cordialement"));
        //contre exemple 2 c'est un spam non détecté
        assertFalse(controller.isSpam("Chèr(e) ab0nné, nous avons détëcté un légèr pr0blème sëcurité sür votre c0mpte. Afin de rés0udre cette situäti0n, n0us vous invi+ons à vérifier v0s inf0rmati0ns pers0nnelles en suiv@nt ce li3n sécurisé : h++ps://verif-compte-securise.xyz"));
        // contre exemple 3 ce n'est pas un spam alors qu'il est classé tel quel
        assertTrue(controller.isSpam("Cher ami, après avoir gagné à la loterie, j'ai décidé de faire don d'une partie de mes gains à des personnes dans le besoin. Je vous ai choisi pour recevoir une somme de 10 000 €. Pour recevoir cet argent, veuillez simplement me fournir votre nom complet, adresse e-mail et numéro de téléphone, afin que je puisse vous contacter et organiser le transfert. Cordialement, John"));
    }


    @Test
    public void testTrain_withEmptyFolders_allTimeSup0() throws IOException {
        String spamFolderVide = "";
        String noSpamFolderVide = "";
        controller.train(spamFolderVide, noSpamFolderVide);

        assertFalse(controller.getspamProbabilities().isEmpty());
        assertFalse(controller.getNonSpamProbabilities().isEmpty());
    }

    @Test
    public void testIsSpam_withEmptyMessage() {
        String message = "";
        assertFalse(controller.isSpam(message));
    }

    @Test
    public void testIsSpam_withWordsNotInSpamOrNonSpamFolders() {
        String message = "xyz abc def";
        assertFalse(controller.isSpam(message));
    }

    @Test
    public void testIsSpam_withOnlySpamWords() {
        String message = "Buy Viagra now!";
        assertTrue(controller.isSpam(message));
    }

    @Test
    public void testIsSpam_withOnlyNonSpamWords() {
        String message = "Hello, how are you?";
        assertFalse(controller.isSpam(message));
    }

    @Test
    public void testIsSpam_withCommonSpamAndNonSpamWords() {
        String message = "Viagra is a popular drug, but it's not for everyone.";
        assertTrue(controller.isSpam(message));
    }

    @Test
    public void testTrain_withEmptySpamAndNonSpamFolders() throws IOException {
        String spamFolderVide = "";
        String noSpamFolderVide = "";
        controller.train(spamFolderVide, noSpamFolderVide);
        // no exception should be thrown
    }

    @Test
    public void testIsSpam_withSpecialCharacters() {
        String message ="Viagra is a popular drug ? @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@";
        assertTrue(controller.isSpam(message));
    }

}