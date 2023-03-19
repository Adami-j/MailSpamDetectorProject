package fr.til.projetfilrouge.mailspamdetectorproject.Test;

import fr.til.projetfilrouge.mailspamdetectorproject.Controller.ConnexionController;
import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;
import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ConnexionControllerTest {

    @Test
    public void testConnexionControllerTest() throws MessagingException {
       assertEquals(1,1);
        UserModel userModel = UserModel.getInstance();
        userModel.setLogin("equipedetectorspmtest@outlook.fr");
        userModel.setPassword("SpamTestBaiern12..");
        System.out.println(userModel.getLogin()+userModel.getPassword());

        ConnexionController connexionController = ConnexionController.getInstance(userModel);
    }

    @Test
    public void testReceiveMessage() throws MessagingException, IOException {
        UserModel userModel = UserModel.getInstance();
        userModel.setLogin("equipedetectorspmtest@outlook.fr");
        userModel.setPassword("SpamTestBaiern12..");
        System.out.println(userModel.getLogin()+userModel.getPassword());
        ConnexionController connexionController = ConnexionController.getInstance(userModel);

        connexionController.sendMail("equipedetectorspmtest@outlook.fr", "equipedetectorspmtest@outlook.fr", "test ", "test ");
        int b = connexionController.readNbMailSent() -1;
        System.out.println("wait ");
        try {
            TimeUnit.SECONDS.sleep(5); // Pause de 5 secondes
        } catch (InterruptedException e) {
            // Gestion de l'exception
        }
        System.out.println("is 5 ? ");
        //connexionController.decrementNbMailSent();
        boolean a = connexionController.isSubjectInInbox("test "+ " of : " + b);
        System.out.println("test + "+ a + " "+ b);

        assertTrue(a);
    }


    public static ConnexionController connexionController;

    @Before
    public  void setUp() throws Exception {
        UserModel userModel = UserModel.getInstance();
        userModel.setLogin("equipedetectorspmtest@outlook.fr");
        userModel.setPassword("SpamTestBaiern12..");
        connexionController = ConnexionController.getInstance(userModel);
    }

    @Test
    public void testGetInstance() throws MessagingException {
        ConnexionController connexionController2 = ConnexionController.getInstance(connexionController.getUserModel());
        assertEquals(connexionController, connexionController2);
    }

    @Test
    public void testIsConnected() throws Exception {
        assertTrue(connexionController.isConnected());
    }

    @Test
    public void testGetMessageInbox() throws Exception {
        assertNotNull(connexionController.getMessageInbox());
    }
}
