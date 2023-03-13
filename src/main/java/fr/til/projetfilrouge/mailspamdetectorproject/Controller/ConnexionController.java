package fr.til.projetfilrouge.mailspamdetectorproject.Controller;

import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;

public class ConnexionController {
    private static ConnexionController connexionInstance = null;

    private ConnexionController(UserModel userModel) {
    }

    public static synchronized ConnexionController getInstance(UserModel userModel) {
        if(connexionInstance==null){
            connexionInstance = new ConnexionController(userModel);
        }
        return connexionInstance;
    }


}
