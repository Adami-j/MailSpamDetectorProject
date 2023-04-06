package fr.til.projetfilrouge.mailspamdetectorproject.Test;

import fr.til.projetfilrouge.mailspamdetectorproject.Controller.ConnexionController;
import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;
import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModelInterface;
import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ConnexionControllerTest {

    private static Properties getProperties(){
        Properties properties = new Properties();
        try (
                FileInputStream fis = new FileInputStream("src/configuration.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }



    @Before
    public void setUp() throws  MessagingException {
        Properties properties = getProperties();
        UserModelInterface userModel = UserModelInterface.getInstance();
        userModel.setLogin(properties.getProperty("email"));
        userModel.setPassword(properties.getProperty("password"));

        connexionController = ConnexionController.getInstance((UserModel) userModel);

    }

    /**
     * Test qui permet d'initialiser la connexion
     * en récupérant le fichier de configuration
     * @throws MessagingException
     */
    @Test
    public void testConnexionControllerTest() throws MessagingException {
       assertEquals(1,1);
        UserModelInterface userModel = UserModelInterface.getInstance();

         connexionController = ConnexionController.getInstance((UserModel) userModel);
    }

    /**
     * Test qui check l'envoie et la réception
     * d'un mail
     * @throws MessagingException
     */
    @Test
    public void testReceiveMessage() throws MessagingException, IOException {
        UserModelInterface userModel = UserModelInterface.getInstance();
        String test = "test";
        connexionController.sendMail(userModel.getPassword(), userModel.getLogin(), test, test);
        int b = connexionController.readNbMailSent() -1;

        try {
            TimeUnit.SECONDS.sleep(5); // Pause de 5 secondes
        } catch (InterruptedException e) {
            // Gestion de l'exception
        }


        boolean a = connexionController.isSubjectInInbox(test+ " of : " + b);


        assertTrue(a);
    }


    private static ConnexionController connexionController;

    /**
     * Tests de récupération d'une instance de connexion controller
     * @throws MessagingException
     */
    @Test
    public void testGetInstance() throws MessagingException {
        ConnexionController connexionController2 = ConnexionController.getInstance(connexionController.getUserModel());
        assertEquals(connexionController, connexionController2);
    }

    /**
     * Teste si la liste des messanges dans la messagerie n'est pas nulle
     * @throws MessagingException
     */
    @Test
    public void testGetMessageInbox() throws MessagingException {
        assertNotNull(connexionController.getMessageInbox());
    }

    /**
     * Test de l'incrémentation du compteur de mails envoyés
     * en gérant l'exception
     * @throws IOException
     */
    @Test
    public void testIncrementCounter() throws IOException {
        int counter = connexionController.getCounter();
        connexionController.incrementNbMailSent();
        try {
            TimeUnit.SECONDS.sleep(5); // Pause de 5 secondes
        } catch (InterruptedException e) {
            // Gestion de l'exception
        }
        int counterNow = connexionController.getCounter();
        counter++;
        assertEquals(counter, counterNow);
        connexionController.decrementNbMailSent();
    }


    @Test
    public void testDecrementCounter() throws IOException {
        int counter = connexionController.getCounter();
        connexionController.decrementNbMailSent();
        try {
            TimeUnit.SECONDS.sleep(5); // Pause de 5 secondes
        } catch (InterruptedException e) {
            // Gestion de l'exception

        }
        counter--;
        assertEquals(counter, connexionController.getCounter());
        connexionController.incrementNbMailSent();
    }
}
