package fr.til.projetfilrouge.mailspamdetectorproject.Model;

public class UserModel {
    /**
     * Paramètre initialisé à null lors de la création de l'instance  de la classe UserModel
     */
    private static UserModel userInstance = null;

    private String login;
    private String password;


    private UserModel(){

    }

    /**
     * Méthode qui retourne l'instance synchronisée d'un user
     * Permet de s'affranchir de la déclaration d'un constructeur
     * lors de la déclaration de la classe
     * @author Julien ADAMI
     * @return UserModel
     */
    public static synchronized UserModel getInstance() {
        if(userInstance==null){
            userInstance = new UserModel();
        }
        return userInstance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
