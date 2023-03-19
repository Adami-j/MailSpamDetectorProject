    package fr.til.projetfilrouge.mailspamdetectorproject.Controller;

    import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;

    import java.io.*;
    import java.util.Properties;
    import javax.mail.*;
    import javax.mail.internet.InternetAddress;
    import javax.mail.internet.MimeMessage;


    public class ConnexionController {

        private static ConnexionController connexionInstance = null;
        private UserModel userModel;
        private Session session;
        private Store store;

        private static int counter ;
        /**
         *Creation d'un type properties, ajout des propriétés suivantes : protocole : imap
         * l'hote outlook
         * le port : 933
         * et l'activation du ssl
         * Pour utiliser la classe Session
         * @author Tanou81
         * @param userModel utilisateur qui souhaite se connecter
         */
        private ConnexionController(UserModel userModel) throws MessagingException {

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
            store = session.getStore();
            store.connect(userModel.getLogin(), userModel.getPassword());


            //récupération des emails courrants
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();
            for (Message m : messages){
                Object content = null;
                try {
                    content = m.getContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (content instanceof String) {
                    System.out.println("Body: " + (String) content);
                } else if (content instanceof Multipart) {
                    Multipart multipart = (Multipart) content;
                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart bodyPart = multipart.getBodyPart(i);
                        if (bodyPart.isMimeType("text/plain")) {
                            try {
                                System.out.println("Body is : " + bodyPart.getContent());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            }
            // Fermeture de la boîte de réception et de la connexion


        }

        /**
         * Méthode getInstance pour appliquer le pattern Singleton
         * et garder une seule et même connexion tout le long de l'utilisation de l'application
         * permet d'éviter l'appel au constructeur
         * @author tanou81
         * @param userModel
         * @return ConnexionController l'instance synchronized
         */
        public static synchronized ConnexionController getInstance(UserModel userModel) throws MessagingException {
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
         * @author Tanou81
         * @return
         * @throws MessagingException
         */
        public Message[] getMessageInbox() throws MessagingException {
            //récupération du storage provider
            Message[] messages = new Message[0];
            try {
                //récupération des emails courrants
                Folder inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_ONLY);
                messages = inbox.getMessages();
            }catch(Exception e){
                System.out.println("error "+ e );
            }
            // Fermeture de la boîte de réception et de la connexion


            return messages;
        }

        public void setUserModel(UserModel userModel) {
            this.userModel = userModel;
        }
        /**
         * Envoie un mail en utilisant JavaMail.
         *
         * @param from l'adresse e-mail de l'expéditeur
         * @param to l'adresse e-mail du destinataire
         * @param subject le sujet du mail
         * @param content le contenu du mail
         * @throws MessagingException en cas d'erreur lors de l'envoi du mail
         */
        public void sendMail(String from, String to, String subject, String content) throws MessagingException, IOException {
            // Configuration des propriétés de la session
            Properties properties = new Properties();
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.host", "outlook.office365.com");
            properties.setProperty("mail.smtp.port", "587");
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.starttls.enable", "true");

            // Création de la session
            Session session = Session.getDefaultInstance(properties);

            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject + " of : " + this.readNbMailSent());
            message.setText(content+ " of : " + this.readNbMailSent());

            // Envoi du message
            Transport transport = session.getTransport();
            transport.connect(userModel.getLogin(), userModel.getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            incrementNbMailSent();
        }

        private static final String FILENAME = "src/main/java/fr/til/projetfilrouge/mailspamdetectorproject/Controller/nbMailSend.txt";
        /**
         * Increments the number of mails sent by one
         */
        public static void incrementNbMailSent() throws IOException {
            // Read current count
            int currentCount = readNbMailSent();

            // Increment count and write to file
            writeNbMailSent(currentCount + 1);
        }

        /**
         * Decrements the number of mails sent by one
         */
        public static void decrementNbMailSent() throws IOException {
            // Read current count
            int currentCount = readNbMailSent();

            // Decrement count and write to file
            writeNbMailSent(currentCount - 1);

        }

        /**
         * Reads the current number of mails sent from the file
         */
        public  static int readNbMailSent() throws IOException {
            System.out.println("read ");
            File file = new File(FILENAME);
            if (!file.exists()) {
                // If the file does not exist, assume that the number of mails sent is zero
                return 0;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                if (line != null) {
                    int r = Integer.parseInt(line);
                    counter = r;
                    System.out.println("read "+counter);
                    return r;

                } else {
                    // If the file is empty, assume that the number of mails sent is zero
                    return 0;
                }
            }
        }

        /**
         * Writes the given number of mails sent to the file
         */
        private static void writeNbMailSent(int count) throws IOException {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
                bw.write(Integer.toString(count));
                System.out.println("write "+ count );
                bw.close();
            }
        }


        public  boolean isSubjectInInbox(String subject) {
            try {
                // Récupération des messages de la boîte de réception
                Message[] messages = getMessageInbox();

                // Vérification de la présence du sujet dans chaque message
                for (Message message : messages) {
                    if (message.getSubject().equals(subject)) {
                        return true;
                    }
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return false;
        }


        public static int getCounter() throws IOException {

                readNbMailSent();
            return counter;
        }
        public void setCounter(int counter){
            this.counter = counter;
        }
    }