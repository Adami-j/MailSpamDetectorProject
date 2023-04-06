package fr.til.projetfilrouge.mailspamdetectorproject.Test;

import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;
import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModelInterface;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class UserModelTests {

    private String coucou ="Coucou";

    private  UserModelInterface userModel;
    @Before
    public void setUp(){
        userModel = UserModelInterface.getInstance();
    }

    @Test
    public void testInstanceNonNulle(){
       userModel = UserModelInterface.getInstance();
       assertNotEquals(userModel,null);
    }


    /**
     * Ce test permet de vérifier la connexion avec un
     */
    @Test
    public void testLoginCorrect(){
        userModel = UserModelInterface.getInstance();
        userModel.setLogin(coucou);
        assertEquals(userModel.getLogin(),coucou);
    }

    @Test
    public void testLoginVide(){
         userModel = UserModelInterface.getInstance();
        userModel.setLogin("");
        assertEquals(userModel.getLogin(),"");
    }

    @Test
    public void testPasswordCorrect()  {
         userModel = UserModelInterface.getInstance();
        userModel.setPassword(coucou);
        assertEquals(userModel.getPassword(),coucou);

    }


}
