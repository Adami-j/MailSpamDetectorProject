package fr.til.projetfilrouge.mailspamdetectorproject.Tests;

import fr.til.projetfilrouge.mailspamdetectorproject.Exceptions.UserExceptions;
import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class UserModelTests {

    @Test
    public void testInstanceNonNulle(){
       UserModel userModel = UserModel.getInstance();
       assertNotEquals(userModel,null);
    }

    @Test
    public void testInstanceNulle(){
        UserModel userModel = null;
        assertEquals(userModel,null);
    }

    @Test
    public void testInstanceNulleNonNulle(){
        UserModel userModel = null;
        userModel = UserModel.getInstance();
        assertNotEquals(userModel,null);
    }

    @Test
    public void testLoginCorrect(){
        UserModel userModel = UserModel.getInstance();
        userModel.setLogin("Coucou");
        assertEquals(userModel.getLogin(),"Coucou");
    }

    @Test
    public void testLoginVide(){
        UserModel userModel = UserModel.getInstance();
        userModel.setLogin("");
        assertEquals(userModel.getLogin(),"");
    }

    @Test
    public void testPasswordCorrect()  {
        UserModel userModel = UserModel.getInstance();
        userModel.setPassword("Coucou");
        assertEquals(userModel.getPassword(),"Coucou");

    }


}
