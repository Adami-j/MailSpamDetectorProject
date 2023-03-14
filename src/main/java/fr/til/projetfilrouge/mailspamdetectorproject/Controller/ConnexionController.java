package fr.til.projetfilrouge.mailspamdetectorproject.Controller;

import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;

import java.util.Properties;
import javax.mail.*;


public class ConnexionController {

    private static ConnexionController connexionInstance = null;
    private UserModel userModel;
    private Session session;


    /**
     *Creation d'un type properties, ajout des propriétés suivantes : protocole : imap
     * l'hote outlook
     * le port : 933
     * et l'activation du ssl
     * Pour utiliser la classe Session
     * @author Julien ADAMI
     * @param userModel utilisateur qui souhaite se connecter
     */
    private ConnexionController(UserModel userModel) {

        Properties properties = new Properties();
        this.userModel = userModel;
        properties.setProperty("mail.store.protocol","imap");
        properties.setProperty("mail.imap.host", "outlook.office365.com");
        properties.setProperty("mail.imap.port", "993");
        properties.setProperty("mail.imap.ssl.enable", "true");

        //création de l'authentification, en utilisant les paramètres de l'utilisateur
        Authenticator authentificator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userModel.getLogin(), userModel.getPassword());
            }
        };
        session = Session.getInstance(properties,authentificator);

    }

    /**
     * Méthode getInstance pour appliquer le pattern Singleton
     * et garder une seule et même connexion tout le long de l'utilisation de l'application
     * permet d'éviter l'appel au constructeur
     * @author Julien ADAMI
     * @param userModel
     * @return ConnexionController l'instance synchronized
     */
    public static synchronized ConnexionController getInstance(UserModel userModel) {
        if(connexionInstance==null){
            connexionInstance = new ConnexionController(userModel);
        }
        return connexionInstance;
    }


    public UserModel getUserModel() {
        return userModel;
    }

    /**
     * Permet de savoir si on est connecté
     * @return
     * @throws NoSuchProviderException
     */
    public Boolean isConnected() throws NoSuchProviderException {
        return session.getStore().isConnected();
    }

    /**
     *Méthode qui permet d'aller récupérer les messages de la boite mail connectée
     * @author Julien ADAMI
     * @return
     * @throws MessagingException
     */
    public Message[] getMessageInbox() throws MessagingException {
        //récupération du storage provider
        Store store = session.getStore();

        //récupération des emails courrants
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();

        // Fermeture de la boîte de réception et de la connexion
        inbox.close(false);
        store.close();

        return messages;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
    

}