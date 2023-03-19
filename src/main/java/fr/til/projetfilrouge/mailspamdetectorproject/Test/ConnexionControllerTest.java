package fr.til.projetfilrouge.mailspamdetectorproject.Test;

import fr.til.projetfilrouge.mailspamdetectorproject.Controller.ConnexionController;
import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;
import org.junit.Test;

import javax.mail.MessagingException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        int b = connexionController.readNbMailSent();
        boolean a = connexionController.isSubjectInInbox("test "+ " of : " + connexionController.readNbMailSent());
        System.out.println("test + "+ a);

        assertTrue(a);
    }
}
