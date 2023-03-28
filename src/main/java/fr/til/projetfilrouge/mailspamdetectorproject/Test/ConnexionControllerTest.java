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

    private Properties getProperties(){
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
    public void setUp() throws IOException, MessagingException {
        Properties properties = getProperties();
        UserModelInterface userModel = UserModelInterface.getInstance();
        userModel.setLogin(properties.getProperty("email"));
        userModel.setPassword(properties.getProperty("password"));

        connexionController = ConnexionController.getInstance((UserModel) userModel);

    }


    @Test
    public void testConnexionControllerTest() throws MessagingException {
       assertEquals(1,1);
        UserModelInterface userModel = UserModelInterface.getInstance();

        ConnexionController connexionController = ConnexionController.getInstance((UserModel) userModel);
    }

    @Test
    public void testReceiveMessage() throws MessagingException, IOException {
        UserModelInterface userModel = UserModelInterface.getInstance();


        ConnexionController connexionController = ConnexionController.getInstance((UserModel) userModel);

        connexionController.sendMail("equipedetectorspmtest@outlook.fr", "equipedetectorspmtest@outlook.fr", "test ", "test ");
        int b = connexionController.readNbMailSent() -1;

        try {
            TimeUnit.SECONDS.sleep(5); // Pause de 5 secondes
        } catch (InterruptedException e) {
            // Gestion de l'exception
        }

        //connexionController.decrementNbMailSent();
        boolean a = connexionController.isSubjectInInbox("test "+ " of : " + b);


        assertTrue(a);
    }


    public static ConnexionController connexionController;

    @Test
    public void testGetInstance() throws MessagingException {
        ConnexionController connexionController2 = ConnexionController.getInstance(connexionController.getUserModel());
        assertEquals(connexionController, connexionController2);
    }

    @Test
    public void testGetMessageInbox() throws Exception {
        assertNotNull(connexionController.getMessageInbox());
    }

    @Test
    public void testIncrementCounter() throws MessagingException, IOException {
        int counter = connexionController.getCounter();
        connexionController.incrementNbMailSent();
        try {
            TimeUnit.SECONDS.sleep(5); // Pause de 5 secondes
        } catch (InterruptedException e) {
            // Gestion de l'exception
        }
        int counterNow = connexionController.getCounter();

        assertEquals(counter + 1, counterNow);
        connexionController.decrementNbMailSent();
    }

    @Test
    public void testDecrementCounter() throws MessagingException, IOException {
        int counter = connexionController.getCounter();
        connexionController.decrementNbMailSent();
        try {
            TimeUnit.SECONDS.sleep(5); // Pause de 5 secondes
        } catch (InterruptedException e) {
            // Gestion de l'exception
        }
        assertEquals(counter -1, connexionController.getCounter());
        connexionController.incrementNbMailSent();
    }
}
