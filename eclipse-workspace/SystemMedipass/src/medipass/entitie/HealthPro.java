package medipass.entitie;

import java.time.LocalDate;



public class HealthPro  extends User {
    
    private String specialite;

	    public HealthPro(String id, String nom, String prenom, LocalDate dateNaissance, String login, String motDePasse, String role, String specialite) {
	        super(id, nom, prenom, dateNaissance, login, motDePasse, role);
	        this.specialite = specialite;
	    }

	    public String getSpecialite() {
	        return specialite;
	    }

	    public void setSpecialite(String specialite) {
	        this.specialite = specialite;
	    }
	    

	    // Logique métier: un professionnel peut prescrire
	    public void prescrire(Patient patient, Prescription prescription) {
	        if (patient != null && patient.getDossier() != null) {
	            patient.getDossier().ajouterPrescription(prescription);
	        }
	    }
    
 
   
 



}
