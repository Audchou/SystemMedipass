package medipass.entitie;

import java.time.LocalDate;

public class Patient extends Personne {
    private MedicalRecord dossier;

    public Patient(String id, String nom, String prenom, LocalDate dateNaissance) {
        super(id, nom, prenom, dateNaissance);
        this.dossier = new MedicalRecord(id); // Chaque patient a un dossier unique
    }

    // Getter
    public MedicalRecord getDossier() {
        return dossier;
    }

    // Setter
    public void setDossier(MedicalRecord dossier) {
        this.dossier = dossier;
    }

    @Override
    public String toString() {
        return "Patient - " + super.toString();
    }
}