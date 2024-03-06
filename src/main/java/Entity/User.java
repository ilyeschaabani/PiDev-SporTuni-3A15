package Entity;

public class User {
    private int id_user;
    private String nom;
    private String prenom;
    private int Numero;
    private String Adresse;
    private String email;
    private String password;
    private String role;
    public static User Current_User;

    public User(int id_user, String nom, String prenom, String email, String password, String role , int Numero , String Adresse) {
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.Numero = Numero;
        this.Adresse = Adresse;
    }
    public User( String nom, String prenom, String email, String password, String role , int Numero, String Adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.Numero = Numero;
        this.Adresse = Adresse;
    }
    public User( String nom, String prenom, String email, String role , int Numero, String Adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
        this.Numero = Numero;
        this.Adresse = Adresse;
    }
    public User(int id_user, String nom, String prenom, String email, String role , int Numero, String Adresse) {
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
        this.Numero = Numero;
        this.Adresse = Adresse;
    }
    public int getId_user() {
        return id_user;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    public int getNumero() {
        return Numero;
    }
    public String getAdresse() {
        return Adresse;
    }

    public static User getCurrent_User() {
        return Current_User;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setNumero(int Numero) {
        this.Numero = Numero;
    }
    public void setAdresse(String Adresse) {
        this.Adresse = Adresse;
    }
    public static void setCurrent_User(User Current_User) {
        User.Current_User = Current_User;
    }
    public static User getCurentUser(){
        return Current_User;
    }
    @Override
    public String toString() {
        return "User{" + "id_user=" + id_user + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", password=" + password + ", role=" + role + ", Numero=" + Numero + ", Adresse=" + Adresse + '}';
    }

}
