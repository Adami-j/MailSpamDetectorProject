package fr.til.projetfilrouge.mailspamdetectorproject.Tests;

import fr.til.projetfilrouge.mailspamdetectorproject.Controller.ConnexionController;
import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConnexionControllerTest {

    @Test
    public void testConnexionControllerTest(){
       assertEquals(1,1);
        UserModel userModel = UserModel.getInstance();

       ConnexionController connexionController = ConnexionController.getInstance(userModel);

    }


}
