package controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSMS {
    // Vos identifiants Twilio
    public static final String ACCOUNT_SID = "AC9e058d7c39ea1735e2a5e869b18e2a19";
    public static final String AUTH_TOKEN = "d277ba755587490a74bdc265800fd7e9";

    // Le numéro de téléphone Twilio
    public static final String TWILIO_NUMBER = "+17178644509";

    // Méthode pour envoyer un SMS
    public static void sendSMS(String toPhoneNumber, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message twilioMessage = Message.creator(
                        new PhoneNumber(toPhoneNumber), // Le numéro de téléphone du destinataire (avec le code du pays)
                        new PhoneNumber(TWILIO_NUMBER), // Votre numéro Twilio
                        message) // Le message à envoyer
                .create();

        System.out.println("SMS envoyé avec SID : " + twilioMessage.getSid());
    }

    public static void main(String[] args) {
        // Utilisation de l'exemple de méthode pour envoyer un SMS
        // Exemple de numéro tunisien
        String numeroTunisien = "+216XXXXXXXX"; // Remplacez XXXXXXXX par le numéro tunisien
        sendSMS(numeroTunisien, "Ceci est un message de test depuis Twilio vers un numéro tunisien!");
    }
}
