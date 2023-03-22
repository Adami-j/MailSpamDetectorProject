package fr.til.projetfilrouge.mailspamdetectorproject.Model;

public class UserModel implements UserModelInterface {
    /**
     * Paramètre initialisé à null lors de la création de l'instance  de la classe UserModel
     *@author Nicolas PAIVA
     *@author Julien ADAMI
     */
    static UserModel userInstance = null;

    private String login;
    private String password;


    UserModel(){

    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
