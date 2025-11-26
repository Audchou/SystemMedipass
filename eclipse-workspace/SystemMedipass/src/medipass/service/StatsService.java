package medipass.service;

import java.util.List;
import medipass.entitie.Patient;
import medipass.entitie.HealthPro;

public class StatsService {
    public static void afficherStatistiques(List<Patient> patients, List<HealthPro> pros) {
        int nbPatients = patients.size();
        int nbPros = pros.size();
        int nbConsultations = 0;
        for (Patient patient : patients) {
            nbConsultations += patient.getDossier().getConsultations().size();
        }
        System.out.println("Nombre de patients : " + nbPatients);
        System.out.println("Nombre de professionnels : " + nbPros);
        System.out.println("Nombre total de consultations : " + nbConsultations);
    }
}