package fr.til.projetfilrouge.mailspamdetectorproject.Tests;

import fr.til.projetfilrouge.mailspamdetectorproject.Controller.ConnexionController;
import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;
import org.junit.Test;

import javax.mail.NoSuchProviderException;

import static org.junit.Assert.assertEquals;


public class ConnexionControllerTest {

    @Test
    public void testConnexionControllerTest() throws NoSuchProviderException {
       assertEquals(1,1);
        UserModel userModel = UserModel.getInstance();
        userModel.setLogin("");
        userModel.setPassword("");
       ConnexionController connexionController = ConnexionController.getInstance(userModel);
       System.out.println( connexionController.isConnected());

    }


}
