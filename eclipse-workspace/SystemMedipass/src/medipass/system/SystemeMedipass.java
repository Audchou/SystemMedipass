package medipass.system;

import medipass.entitie.*;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;




	public class SystemeMedipass {
	    // Singleton
	    private static SystemeMedipass instance;

	    // Collections
	    private List<Patient> patients;
	    private List<User> utilisateurs;
	    private User utilisateurConnecte;
	    private List<MedicalRecord> dossiers = new ArrayList<>();

	    private SystemeMedipass() {
	        this.patients = new ArrayList<>();
	        this.utilisateurs = new ArrayList<>();
	        initialiserDonneesTest();
	    }

	    public static SystemeMedipass getInstance() {
	        if (instance == null) {
	            instance = new SystemeMedipass();
	        }
	        return instance;
	    }
	    
	    public User getUtilisateurConnecte() {
	        return utilisateurConnecte;
	    }
	    
	    // Ajouter un dossier à la liste
	    public void ajouterDossier(MedicalRecord dossier) {
	        dossiers.add(dossier);
	    }
	    
	    // Méthode pour récupérer un dossier à partir de son ID
	    public MedicalRecord getDossierByPatientId(String patientId) {
	        for (MedicalRecord dossier : dossiers) {
	            if (dossier.getPatientId().equals(patientId)) {
	                return dossier; // dossier trouvé+-
	            }
	        }
	        return null; // si le dossier est introuvable
	    }
	
	
	
	 private void initialiserDonneesTest() {
	        // Utilisateurs
	        utilisateurs.add(new Admin("U001", "Dupont", "Jean", LocalDate.of(1980, 1, 1), "admin", "adminpass"));
	        utilisateurs.add(new HealthPro("U002", "Martin", "Sophie", LocalDate.of(1985, 5, 15), "sophie.martin", "medpass", "MEDECIN", "Généraliste"));
	        utilisateurs.add(new HealthPro("U003", "Lefevre", "Paul", LocalDate.of(1978, 11, 20), "paul.lefevre", "medpass", "MEDECIN", "Cardiologie"));

	        // Patients
	        Patient p1 = new Patient("P001", "Durand", "Marie", LocalDate.of(1975, 3, 10));
	        Patient p2 = new Patient("P002", "Petit", "Luc", LocalDate.of(2000, 7, 25));
	        
	        // Ajout de données de test pour la catégorisation
	        HealthPro drSophie = (HealthPro) utilisateurs.get(1);
	        HealthPro drPaul = (HealthPro) utilisateurs.get(2);

	        // P1: 2 Consultations Généraliste, 1 Examen Cardiologie
	        p1.getDossier().ajouterConsultation(new Consultation("C001", LocalDateTime.now().minusDays(50), "Fièvre", "Repos", drSophie, p1));
	        p1.getDossier().ajouterConsultation(new Consultation("C002", LocalDateTime.now().minusDays(30), "Contrôle", "RAS", drSophie, p1));
	        p1.getDossier().ajouterExamen(new MedicalExam("E001", LocalDateTime.now().minusDays(10), "ECG", "Anomalie mineure", drPaul));
	        
	        // P2: 1 Consultation Cardiologie
	        p2.getDossier().ajouterConsultation(new Consultation("C003", LocalDateTime.now().minusDays(5), "Douleur poitrine", "Hospitalisation", drPaul, p2));
	        
	        patients.add(p1);
	        patients.add(p2);
	 }
	
	
	
	public boolean archiverDossier(String patientId) {
	    // Vérifier que l'utilisateur connecté est un médecin
	    if (!(utilisateurConnecte instanceof HealthPro) || 
	         !((HealthPro) utilisateurConnecte).getRole().equalsIgnoreCase("Médecin")) {
	        System.out.println("Action non autorisée : seul un médecin peut archiver un dossier.");
	        return false;
	    }

	    MedicalRecord dossier = getDossierByPatientId(patientId);
	    if (dossier != null && !dossier.isArchive()) {
	        dossier.archiver();
	        return true;
	    }
	    return false;
	}
	
	
	public boolean desarchiverDossier(String patientId) {
	    if (!(utilisateurConnecte instanceof HealthPro) || 
	         !((HealthPro) utilisateurConnecte).getRole().equalsIgnoreCase("Médecin")) {
	        System.out.println("Action non autorisée : seul un médecin peut désarchiver un dossier.");
	        return false;
	    }

	    MedicalRecord dossier = getDossierByPatientId(patientId);
	    if (dossier != null && dossier.isArchive()) {
	        dossier.desarchiver();
	        return true;
	    }
	    return false;
	}
	
	
	
	public void afficherDossiersArchives() {
	    System.out.println("\n--- Liste des dossiers archivés ---");
	    boolean aucun = true;

	    for (MedicalRecord dossier : dossiers) {
	        if (dossier.isArchive()) {
	            System.out.println(dossier); // utilise toString() de MedicalRecord
	            aucun = false;
	        }
	    }

	    if (aucun) {
	        System.out.println("Aucun dossier n'est actuellement archivé.");
	    }
	}

}





	
