package Utiils;

public class SessionManager {

    private static SessionManager instance;

    private static int id_user;
    private static String nom;
    private static String prenom;
    private static int numero;
    private static String Adresse;
    private static String email;
    private static String role;
    private SessionManager(int id_user , String nom , String prenom , int numero , String email ,String address,String role ) {
        SessionManager.id_user=id_user;
        SessionManager.nom=nom;
        SessionManager.prenom=prenom;
        SessionManager.numero=numero;
        SessionManager.email=email;
        SessionManager.Adresse=address;
        SessionManager.role=role;
    }
    public static SessionManager getInstace(int id_user , String nom , String prenom , int numero , String email ,String adress, String role) {
        if(instance == null) {
            instance = new SessionManager( id_user, nom ,  prenom ,  numero ,  email ,adress, role);
        }
        return instance;
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public static int getId_user() {
        return id_user;
    }

    public static String getNom() {
        return nom;
    }

    public static String getPrenom() {
        return prenom;
    }

    public static int getNumero() {
        return numero;
    }

    public static String getAdresse() {
        return Adresse;
    }

    public static String getEmail() {
        return email;
    }

    public static String getRole() {
        return role;
    }

    public static void setInstance(SessionManager instance) {
        SessionManager.instance = instance;
    }

    public static void setId_user(int id_user) {
        SessionManager.id_user = id_user;
    }

    public static void setNom(String nom) {
        SessionManager.nom = nom;
    }

    public static void setPrenom(String prenom) {
        SessionManager.prenom = prenom;
    }

    public static void setNumero(int numero) {
        SessionManager.numero = numero;
    }

    public static void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public static void setEmail(String email) {
        SessionManager.email = email;
    }

    public static void setRole(String role) {
        SessionManager.role = role;
    }
    public static void cleanUserSession() {
        id_user=0;
        nom="";
        prenom="";
        numero=0;
        email="";
        Adresse="";
        role="";
    }

}
