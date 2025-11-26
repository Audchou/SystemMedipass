package medipass.service;

import java.util.ArrayList;
import java.util.List;
import medipass.entitie.Patient;
import medipass.entitie.MedicalExam;

public class PatientService {
    private List<Patient> patients = new ArrayList<>();
// pour ajouter patient(Patient patient)
    public void ajouterPatient(Patient patient) {
        patients.add(patient);
    }
//pour supprimer patient()
    public boolean supprimerPatient(Patient patient) {
        return patients.remove(patient);
    }
//  pour ajouter examen

    public void ajouterExamen(Patient patient, MedicalExam examen) {
        if (patients.contains(patient)) {
            patient.getMedicalExams().add(examen);
        }
    }

}