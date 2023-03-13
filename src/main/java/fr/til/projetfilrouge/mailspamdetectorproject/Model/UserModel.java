package fr.til.projetfilrouge.mailspamdetectorproject.Model;

public class UserModel {
    private static UserModel userInstance = null;
    private String login;
    private String password;


    private UserModel(){

    }

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
