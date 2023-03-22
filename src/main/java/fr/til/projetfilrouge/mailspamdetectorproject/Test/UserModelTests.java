package fr.til.projetfilrouge.mailspamdetectorproject.Test;

import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;
import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModelInterface;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class UserModelTests {

    @Test
    public void testInstanceNonNulle(){
       UserModelInterface userModel = UserModelInterface.getInstance();
       assertNotEquals(userModel,null);
    }

    @Test
    public void testInstanceNulle(){
        UserModelInterface userModel = null;
        userModel = UserModelInterface.getInstance();
        assertNotEquals(userModel,null);
    }

    @Test
    public void testInstanceNulleNonNulle(){
        UserModelInterface userModel = null;
        userModel = UserModelInterface.getInstance();
        assertNotEquals(userModel,null);
    }

    @Test
    public void testLoginCorrect(){
        UserModelInterface userModel = UserModelInterface.getInstance();
        userModel.setLogin("Coucou");
        assertEquals(userModel.getLogin(),"Coucou");
    }

    @Test
    public void testLoginVide(){
        UserModelInterface userModel = UserModelInterface.getInstance();
        userModel.setLogin("");
        assertEquals(userModel.getLogin(),"");
    }

    @Test
    public void testPasswordCorrect()  {
        UserModelInterface userModel = UserModelInterface.getInstance();
        userModel.setPassword("Coucou");
        assertEquals(userModel.getPassword(),"Coucou");

    }


}
