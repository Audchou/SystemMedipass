package medipass.entitie;

import java.time.LocalDate;

public class Prescription {
	 private static int compteur = 1;

	private String id;
    private String medicament;
    private String posologie;
    private int dureeJours;
    private LocalDate dateEmission;

    public Prescription(String id, String medicament, String posologie, int dureeJours) {
    	 this.id = "PR" + (compteur++);   
    	this.medicament = medicament;
        this.posologie = posologie;
        this.dureeJours = dureeJours;
        this.dateEmission = LocalDate.now();
    }

    // Getters
    public String getMedicament() {
        return medicament;
    }
    
    public String getId() {
        return id;
    }
   
    public void setId(String id) {
        this.id = id;
    }


    public String getPosologie() {
        return posologie;
    }

    public int getDureeJours() {
        return dureeJours;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    @Override
    public String toString() {
        return "Médicament: " + medicament + ", Posologie: " + posologie + ", Durée: " + dureeJours + " jours (Émis le: " + dateEmission + ")";
    }

    // Définit le nom du médicament
    public void setMedicament(String medicament2) {
        this.medicament = medicament2;
    }

    // Définit la posologie
    public void setPosologie(String posologie2) {
        this.posologie = posologie2;
    }

    // Définit la durée en jours
    public void setDuree(int dureeJours2) {
        this.dureeJours = dureeJours2;
        // Si vous souhaitez lever une exception, décommentez la ligne suivante :
        // throw new UnsupportedOperationException("Méthode 'setDuree' non implémentée");
    }
}
