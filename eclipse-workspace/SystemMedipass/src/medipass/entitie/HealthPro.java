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
    
 
   
	 // Contrôles d'accès
	    public boolean peutCreerConsultation() {
	        return getRole().equalsIgnoreCase("MEDECIN");
	    }

	    public boolean peutModifierConsultation() {
	        return getRole().equalsIgnoreCase("MEDECIN");
	    }

	    public boolean peutVoirConsultationsParSpecialite() {
	        return getRole().equalsIgnoreCase("MEDECIN");
	    }

	    public boolean peutAjouterAntecedent() {
	        return getRole().equalsIgnoreCase("MEDECIN");
	    }

	    public boolean peutModifierPrescription() {
	        return getRole().equalsIgnoreCase("MEDECIN") || getRole().equalsIgnoreCase("PHARMACIEN");
	    }

	    public boolean peutCreerPrescription() {
	        return getRole().equalsIgnoreCase("MEDECIN") || getRole().equalsIgnoreCase("PHARMACIEN");
	    }

	    public boolean peutConsulterDossierMedical() {
	        return true; // Tous les professionnels peuvent consulter
	    }
	    
	    public boolean peutAjouterDossier() {
	        return this.getRole().equalsIgnoreCase("MEDECIN") || getRole().equalsIgnoreCase("INFIRMIER");
	    }
	    
	    public boolean peutSupprimerDossier() {
	        return this.getRole().equalsIgnoreCase("MEDECIN");
	    }

	    public boolean voirSpecialiteDominante() {
	        return this.getRole().equalsIgnoreCase("MEDECIN");
	    }
	    public boolean peutArchiverDossier() {
	        return getRole().equalsIgnoreCase("MEDECIN") || getRole().equalsIgnoreCase("INFIRMIER");
	    }

	    public boolean peutDesarchiverDossier() {
	        return getRole().equalsIgnoreCase("MEDECIN") || getRole().equalsIgnoreCase("INFIRMIER");
	    }

	    public boolean peutVoirListeDossiersArchives() {
	        return getRole().equalsIgnoreCase("MEDECIN") || getRole().equalsIgnoreCase("INFIRMIER");
	    }
	    
	    public boolean peutAjouterExamenMedical() {
	        return getRole().equalsIgnoreCase("MEDECIN");
	    }
	}


