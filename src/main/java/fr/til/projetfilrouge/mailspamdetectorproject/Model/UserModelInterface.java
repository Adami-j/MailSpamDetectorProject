package fr.til.projetfilrouge.mailspamdetectorproject.Model;

public interface UserModelInterface {
     static UserModel userInstance = null;
    /**
     * Méthode qui retourne l'instance synchronisée d'un user
     * Permet de s'affranchir de la déclaration d'un constructeur
     * lors de la déclaration de la classe
     *
     * @return UserModel
     * @author Julien ADAMI
     */
    static UserModel getInstance() {
        if (userInstance == null) {
            UserModel.userInstance = new UserModel();
        }
        return UserModel.userInstance;
    }

    String getLogin();

    void setLogin(String login);

    String getPassword();

    void setPassword(String password);
}
