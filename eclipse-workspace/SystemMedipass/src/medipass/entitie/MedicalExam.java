package medipass.entitie;

import java.time.LocalDateTime;


public class MedicalExam {
	
	 private String id;
	    private LocalDateTime date;
	    private String type; // Ex: Radiographie, Analyse de sang
	    private String resultat;
	    private HealthPro demandeur; // Association

	    public MedicalExam(String id, LocalDateTime date, String type, String resultat, HealthPro demandeur) {
	        this.id = id;
	        this.date = date;
	        this.type = type;
	        this.resultat = resultat;
	        this.demandeur = demandeur;
	    }

	    // Getters
	    public String getId() { return id; }
	    public LocalDateTime getDate() { return date; }
	    public String getType() { return type; }
	    public String getResultat() { return resultat; }
	    public HealthPro getDemandeur() { return demandeur; }

	    @Override
	    public String toString() {
	        return String.format("ID: %s | Date: %s | Type: %s | Résultat: %s | Demandeur: Dr. %s (%s)",
	            id, date.toLocalDate(), type, resultat, demandeur.getNom(), demandeur.getSpecialite());
	    }

}
