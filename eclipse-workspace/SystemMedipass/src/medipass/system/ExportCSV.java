package medipass.system;


import medipass.entitie.Patient;
import medipass.entitie.User;
import java.io.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportCSV {
    private static final String PATIENTS_FILE = "patients.csv";
    private static final String USERS_FILE = "users.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    // --- Exportation des Patients ---
    public static void exporterPatients(List<Patient> patients) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PATIENTS_FILE))) {
            // En-tête CSV
            pw.println("ID;Nom;Prenom;DateNaissance");

            for (Patient p : patients) {
                pw.printf("%s;%s;%s;%s\n",
                    p.getId(),
                    p.getNom(),
                    p.getPrenom(),
                    p.getDateNaissance().format(DATE_FORMATTER)
                );
            }
            System.out.println("Exportation des patients vers " + PATIENTS_FILE + " réussie.");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'exportation des patients: " + e.getMessage());
        }
    }

    // --- Exportation des Utilisateurs (Simplifiée) ---
    public static void exporterUtilisateurs(List<User> utilisateurs) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE))) {
            // En-tête CSV
            pw.println("ID;Nom;Prenom;DateNaissance;Login;Role");

            for (User u : utilisateurs) {
                pw.printf("%s;%s;%s;%s;%s;%s\n",
                    u.getId(),
                    u.getNom(),
                    u.getPrenom(),
                    u.getDateNaissance().format(DATE_FORMATTER),
                    u.getLogin(),
                    u.getRole()
                );
            }
            System.out.println("Exportation des utilisateurs vers " + USERS_FILE + " réussie.");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'exportation des utilisateurs: " + e.getMessage());
        }
    }

   
}

