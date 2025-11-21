package medipass.entitie;

import java.time.LocalDate;

public class Prescription {
    private String medicament;
    private String posologie;
    private int dureeJours;
    private LocalDate dateEmission;

    public Prescription(String medicament, String posologie, int dureeJours) {
        this.medicament = medicament;
        this.posologie = posologie;
        this.dureeJours = dureeJours;
        this.dateEmission = LocalDate.now();
    }

    // Getters
    public String getMedicament() {
        return medicament;
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
}
