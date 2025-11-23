package medipass.entitie;

import java.time.LocalDate;



public abstract class User extends Personne {
    private String login;
    private String motDePasse;
    private String role; // Ex: "ADMIN", "MEDECIN", "INFIRMIER", "PHARMACIEN"

    public User(String id, String nom, String prenom, LocalDate dateNaissance, String login, String motDePasse, String role) {
        super(id, nom, prenom, dateNaissance);
        this.login = login;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    // Getters
    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }

    // Méthode pour vérifier le mot de passe (simplifiée pour l'exercice)
    public boolean verifierMotDePasse(String motDePasse) {
        return this.motDePasse.equals(motDePasse);
    }

    // Setters
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    // Getter pour mot de passe (nécessaire pour la modification de rôle)
    public String getMotDePasse() {
        return motDePasse;
    }

    @Override
    public String toString() {
        return super.toString() + ", Rôle: " + role + ", Login: " + login;
    }
}
