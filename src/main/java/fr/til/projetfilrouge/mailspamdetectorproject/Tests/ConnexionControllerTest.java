package fr.til.projetfilrouge.mailspamdetectorproject.Tests;

import fr.til.projetfilrouge.mailspamdetectorproject.Controller.ConnexionController;
import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;
import org.junit.Test;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

import static org.junit.Assert.assertEquals;

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
}
