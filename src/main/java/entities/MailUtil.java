package entities;


import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class MailUtil {
        public static void sendMailAcceptation(String mail) throws Exception{

            System.out.println("Preparing to send email");

            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            String myAccountEmail = "Ibtihelab9@gmail.com";
            String password = "Esprit12345";

            Session session = Session.getInstance(properties, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, password);
                }
            });

            Message message = prepareMessage(session, myAccountEmail, myAccountEmail,"Votre réservation à été bien confirmé","Confirmation Réservation");
            Transport.send(message);
            System.out.println("Message sent successfully");
        }

    public static void sendMailRefus(String mail) throws Exception{

        System.out.println("Preparing to send email");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "ghaith.oueslati@esprit.tn";
        String password = "Ghassen002";

        Session session = Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, myAccountEmail,"Votre réservation été réjeté tant que il n'ya plus des places possibles.","Réservation réjeté");
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient,String messageText,String messageSubject) {
            Message message = new MimeMessage(session);

            try {
                message.setFrom(new InternetAddress(myAccountEmail));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
                message.setSubject(messageSubject);
                message.setText(messageText);
            } catch (Exception e) {
                Logger.getLogger(MailUtil.class.getName(), e.getMessage());
            }
            return message;
        }


    }
