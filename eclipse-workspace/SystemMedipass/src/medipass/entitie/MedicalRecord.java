package medipass.entitie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class MedicalRecord {

    
	private String patientId;
    private List<MedicalHistory> antecedents = new ArrayList<>();
    private List<Consultation> consultations = new ArrayList<>();
    private List<Prescription> prescriptions = new ArrayList<>();
    private List<MedicalExam> examens = new ArrayList<>(); // NOUVEAU
    private boolean archive = false;

    public MedicalRecord(String patientId) {
        this.patientId = patientId;
    }

    // Méthodes d'ajout (Agrégation)
    public void ajouterAntecedent(MedicalHistory antecedent) { this.antecedents.add(antecedent); }
    public void ajouterConsultation(Consultation consultation) { this.consultations.add(consultation); }
    public void ajouterPrescription(Prescription prescription) { this.prescriptions.add(prescription); }
    public void ajouterExamen(MedicalExam examen) { this.examens.add(examen); } // NOUVEAU

    // Getters pour les listes
    public List<Consultation> getConsultations() { return consultations; }
    public List<MedicalExam> getExamens() { return examens; } // NOUVEAU
    public boolean isArchive() { return archive; }

    // Logique métier pour la catégorisation
    public String getSpecialiteDominante() {
        Map<String, Integer> compteur = new HashMap<>();

        for (Consultation c : consultations) {
            String specialite = c.getProfessionnel().getSpecialite();
            compteur.put(specialite, compteur.getOrDefault(specialite, 0) + 1);
        }
        for (MedicalExam e : examens) {
            String specialite = e.getDemandeur().getSpecialite();
            compteur.put(specialite, compteur.getOrDefault(specialite, 0) + 1);
        }

        if (compteur.isEmpty()) {
            return "Non classé";
        }

        return compteur.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .get().getKey();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nDossier Médical pour le patient ID: ").append(patientId).append("\n");
        sb.append("----------------------------------------------------\n");
        sb.append("Statut: ").append(archive ? "Archivé" : "Actif").append("\n");
        sb.append("Spécialité dominante: ").append(getSpecialiteDominante()).append("\n\n");

        sb.append("--- Antécédents ---\n");
        antecedents.forEach(a -> sb.append("- ").append(a).append("\n"));

        sb.append("\n--- Consultations ---\n");
        consultations.forEach(c -> sb.append("- ").append(c).append("\n"));

        sb.append("\n--- Prescriptions ---\n");
        prescriptions.forEach(p -> sb.append("- ").append(p).append("\n"));

        sb.append("\n--- Examens Médicaux ---\n");
        examens.forEach(e -> sb.append("- ").append(e).append("\n"));

        sb.append("----------------------------------------------------\n");
        return sb.toString();
    }
}

