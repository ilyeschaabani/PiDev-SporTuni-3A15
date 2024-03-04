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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DataSource;



public class LoginController {

    @FXML
    private TextField tfemail;
    @FXML
    private PasswordField tf_pwd;
    @FXML
    private Button loginbtn;
    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;

    public void initialize() {


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
    void PasswordFieldKeyTaped(KeyEvent event) {

    }

}

