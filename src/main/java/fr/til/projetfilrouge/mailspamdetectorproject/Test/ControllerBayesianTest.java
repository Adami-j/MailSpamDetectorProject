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
        String spamFolder = "src/main/resources/file/SPAM";
        String noSpamFolder = "src/main/resources/file/HAM";
        controller.train(spamFolder, noSpamFolder);

        assertTrue(controller.isSpam("Buy Viagra now!"));
        assertFalse(controller.isSpam("Hello, how are you?"));
    }

    @Test
    public void testContreExemple() throws IOException {
        String spamFolder = "src/main/resources/file/SPAM";
        String noSpamFolder = "src/main/resources/file/HAM";
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

}