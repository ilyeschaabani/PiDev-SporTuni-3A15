package Controller;

import Entity.User;
import Utiils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DataSource;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import Service.UserService;


public class LoginController {

    @FXML
    private TextField tfemail;
    @FXML
    private PasswordField tf_pwd;
    @FXML
    private Button loginbtn;
    private Connection connexion;

    @FXML
    private Hyperlink changepwd;
    private Statement ste;
    private PreparedStatement pst;

    public void initialize() {


    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void textfieldDesign(){
        if (tfemail.isFocused()){
            tfemail.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
            tf_pwd.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");


        }else if (tf_pwd.isFocused()){
            tfemail.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            tfemail.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
        }
    }




    public boolean ValidationEmail(){
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
        Matcher match = pattern.matcher(tfemail.getText());

        if(match.find() && match.group().equals(tfemail.getText()))
        {
            return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Email");
            alert.showAndWait();

            return false;
        }
    }
    private String generateOTP() {
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(rnd.nextInt(characters.length())));
        }
        return sb.toString();
    }

    private void sendEmailWithOTP(String toEmail, String otp) {
        final String username = "waves.esprit@gmail.com";
        final String password = "tgao tbqg wudl aluo";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("waves.esprit@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("2FA Authentication");
            message.setText("Dear user,\n\nYour 2FA code is: " + otp);
            Transport.send(message);
            System.out.println("OTP email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        if(tfemail.getText().equals("admin") && tf_pwd.getText().equals("admin") )
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("SporTuni:: Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenu Admin");
            alert.showAndWait();
            Parent signInParent = null;
            signInParent = FXMLLoader.load(getClass().getResource("/Menu.fxml"));
            Scene signInScene = new Scene(signInParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(signInScene);
            window.show();
        }else {

            String query2="select * from user where email=?  and mdp=?";
            connexion= DataSource.getInstance().getCnx();
            try{
                PreparedStatement smt = connexion.prepareStatement(query2);

                smt.setString(1,tfemail.getText());
                smt.setString(2,tf_pwd.getText());
                ResultSet rs= smt.executeQuery();
                User p;
                if(rs.next()){
                    p=new User( rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("mdp"), rs.getString("role"), rs.getInt("numero"), rs.getString("adress"));
                    User.setCurrent_User(p);
                    SessionManager.getInstace(rs.getInt("id"),rs.getString("nom"),rs.getString("Prenom"),rs.getInt("numero"),rs.getString("email"),rs.getString("adress"),rs.getString("role"));
                    System.out.println(User.Current_User.getEmail());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("SporTuni :: Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Vous etes connecté");
                    alert.showAndWait();
                   loginbtn.getScene().getWindow().hide();
                    Parent signInParent = null;
                    signInParent = FXMLLoader.load(getClass().getResource("/MenuUser.fxml"));
                    Scene signInScene = new Scene(signInParent);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(signInScene);
                    window.show();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SporTuni :: Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Email/Password !!");
                    alert.showAndWait();
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
   void Signup(ActionEvent event) throws IOException {
        Parent signInParent = FXMLLoader.load(getClass().getResource("/UseerSignup.fxml"));
        Scene signInScene = new Scene(signInParent);

        // Get the stage from the event that triggered the method call
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene on the stage
        window.setScene(signInScene);
        window.show();
    }
    @FXML
    void changePassword(ActionEvent event) {
        TextInputDialog emailDialog = new TextInputDialog();
        emailDialog.setTitle("réinitialiser le mot de passe");
        emailDialog.setHeaderText("vérifier votre email");
        emailDialog.setContentText("Veuillez entrer votre Email:");
        Optional<String> emailResult = emailDialog.showAndWait();
        emailResult.ifPresent(email -> {
            UserService UserS = new UserService();
            if (UserS.checkUserExists(email)) {
                String otp = generateOTP();
                sendEmailWithOTP(email, otp);
                TextInputDialog otpDialog = new TextInputDialog();
                otpDialog.setTitle("OTP Verification");
                otpDialog.setHeaderText("OTP for Password Reset");
                otpDialog.setContentText("Veuillez entrer le mot de passe à usage unique envoyé à votre Email:");
                Optional<String> otpResult = otpDialog.showAndWait();
                if (otpResult.isPresent() && otpResult.get().equals(otp)) {
                    TextInputDialog newPasswordDialog = new TextInputDialog();
                    newPasswordDialog.setTitle("Change Password");
                    newPasswordDialog.setHeaderText("Enter New Password");
                    newPasswordDialog.setContentText("New Password:");
                    Optional<String> newPasswordResult = newPasswordDialog.showAndWait();
                    newPasswordResult.ifPresent(newPassword -> {
                        boolean updateSuccess = UserS.updatePassword(email, newPassword);
                        if (updateSuccess) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Votre mot de passe a été modifié avec succès");
                            alert.showAndWait();
                        } else {
                            showAlert("Erreur", "Un problème est survenu lors de la modification de votre mot de passe. Veuillez réessayer.");
                        }
                    });
                } else {
                    showAlert("Incorrect OTP", "Le mot de passe que vous avez entré est incorrect. Veuillez réessayer.");
                }
            } else {
                showAlert("Email Invalide", "L’email que vous avez saisi n’existe pas.");
            }
        });

    }

}

