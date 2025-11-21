package medipass.entitie;

import java.time.LocalDate;


public class Admin extends User {

    public Admin(String id, String nom, String prenom, LocalDate dateNaissance, String login, String motDePasse) {
        super(id, nom, prenom, dateNaissance, login, motDePasse, "ADMIN");
    }

    // L'administrateur aura des méthodes spécifiques dans SystemeMedipass
    // pour la gestion des utilisateurs.

    @Override
    public String toString() {
        return "Administrateur - " + super.toString();
    }
}

	  
	   