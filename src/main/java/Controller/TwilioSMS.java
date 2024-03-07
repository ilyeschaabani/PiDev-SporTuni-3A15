package Controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSMS {
    // Vos identifiants Twilio
    public static final String ACCOUNT_SID = "AC69cd5f7563bdda1f3e4154b4a8b1beea";
    public static final String AUTH_TOKEN = "374cd60e8d4afa24763e8b3ed5055b16";

    // Le numéro de téléphone Twilio
    public static final String TWILIO_NUMBER = "+19283230397";

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
