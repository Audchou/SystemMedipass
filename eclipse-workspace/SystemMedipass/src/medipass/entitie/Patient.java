package medipass.entitie;

import java.time.LocalDate;
import java.util.List;

public class Patient extends Personne {
    private MedicalRecord dossier;

    public Patient(String id, String nom, String prenom, LocalDate dateNaissance) {
        super(id, nom, prenom, dateNaissance);
        this.dossier = new MedicalRecord(this); // Chaque patient a un dossier unique
    }

    // Getter
    public MedicalRecord getDossier() {
        return dossier;
    }

    // Setter
    public void setDossier(MedicalRecord dossier) {
        this.dossier = dossier;
    }

    public List<MedicalExam> getMedicalExams() {
        return dossier.getExamens();
    }

    @Override
    public String toString() {
        return "Patient - " + super.toString();
    }
}