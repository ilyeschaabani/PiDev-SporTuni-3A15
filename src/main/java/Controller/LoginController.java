package Controller;

import Entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DataSource;



public class LoginController {

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfmdp;
    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;




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
        if(tfemail.getText().equals("admin") && tfmdp.getText().equals("admin") )
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Travel Me :: Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenu Admin");
            alert.showAndWait();

            Parent signInParent = null;

            signInParent = FXMLLoader.load(getClass().getResource("/UserDashbord.fxml"));

            Scene signInScene = new Scene(signInParent);

            // Get the stage from the event that triggered the method call
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene on the stage
            window.setScene(signInScene);
            window.show();
        }else {

            String query2="select * from user where email=?  and password=?";
            connexion= DataSource.getInstance().getCnx();
            try{
                PreparedStatement smt = connexion.prepareStatement(query2);

                smt.setString(1,tfemail.getText());
                smt.setString(2,tfmdp.getText());
                ResultSet rs= smt.executeQuery();
                User p;
                if(rs.next()){
//                    p=new User(rs.getInt("id_user"),rs.getString("email"),rs.getString("password"),rs.getString("role"));
//                    User.setCurrent_User(p);
//                    SessionManager.getInstace(rs.getInt("id"),rs.getInt("cin"),rs.getString("user_name"),rs.getInt("numero"),rs.getString("email"),rs.getString("adresse"),rs.getString("roles"));
//                    System.out.println(User.Current_User.getEmail());
//                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                    alert.setTitle("Travel Me :: Success Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Vous etes connecté");
//                    alert.showAndWait();
//                    loginbtn.getScene().getWindow().hide();
//                    Parent root = FXMLLoader.load(getClass().getResource("/Gui/MenuDynamicDevelopers.fxml"));
//                    Scene scene;
//                    scene = new Scene(root);
//                    Stage stage = new Stage();
//                    stage.initStyle(StageStyle.TRANSPARENT);
//                    stage.setScene(scene);
//
//                    stage.show();


                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Travel Me :: Error Message");
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

}

