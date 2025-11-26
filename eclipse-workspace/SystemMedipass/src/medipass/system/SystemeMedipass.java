package medipass.system;

import medipass.entitie.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
	    private List<Consultation> consultations = new ArrayList<>();

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
	
	
		
	
	    // creerConsultation
	    public void creerConsultation(String id, LocalDateTime date, String motif,
	                                          String observations, HealthPro professionnel, Patient patient) {
	    	 if (!(utilisateurConnecte instanceof HealthPro)) {
	    		 throw new IllegalArgumentException("Erreur: Seul un professionnel de santé peut créer une consultation.");
	    	 }
	             
	    	 HealthPro pro = (HealthPro) utilisateurConnecte;
	             String consultId = "C" + (patient.getDossier().getConsultations().size() + 1);

	        boolean occupe = consultations.stream()
	                .anyMatch(c -> c.getProfessionnel().getId().equals(professionnel.getId())
	                        && c.getDate().equals(date));

	        if (occupe) {
	            throw new IllegalArgumentException("Le professionnel n'est pas disponible à cette date.");
	        }

	        Consultation c = new Consultation(consultId, date, motif, observations, pro, patient);
	        consultations.add(c);
	        patient.getDossier().ajouterConsultation(c);
	        System.out.println("Consultation créée pour le patient " + patient.getNom() + " par Dr. " + pro.getNom());
	    }
	    	    

	    // modifierConsultation()
	    public void modifierConsultation(String id, LocalDateTime nouvelleDate,
	                                             String nouveauMotif, String nouvellesObservations) {
	    	 // On cherche la consultation à modifier
	        Consultation ancienne = consultations.stream()
	                .filter(c -> c.getId().equals(id))
	                .findFirst()
	                .orElse(null);

	        if (ancienne == null) {
	        	System.out.println("Aucune conultation trouvée");
	        }
	     // On vérifie si la nouvelle date est occupée par le même professionnel
	        if (nouvelleDate != null && !nouvelleDate.equals(ancienne.getDate())) {
	            boolean occupe = consultations.stream()
	            		 .anyMatch(c ->
	                     c.getProfessionnel().equals(ancienne.getProfessionnel()) &&
	                     c.getDate().equals(nouvelleDate) &&
	                     !c.getId().equals(id)
	             );

	            if (occupe) {
	                throw new IllegalArgumentException("Le professionnel n'est pas disponible à la nouvelle date.");
	            }
	        }
	     // On crée la consultation modifiée
	        Consultation cModifiee = new Consultation(
	                ancienne.getId(),
	                nouvelleDate != null ? nouvelleDate : ancienne.getDate(),
	                nouveauMotif != null ? nouveauMotif : ancienne.getMotif(),
	                nouvellesObservations != null ? nouvellesObservations : ancienne.getObservations(),
	                ancienne.getProfessionnel(),
	                ancienne.getPatient()
	        );

	        int index = consultations.indexOf(ancienne);
	        consultations.set(index, cModifiee);
	        
	        
	        // Mettre à jour la liste du dossier médical du patient
	        Patient patient = ancienne.getPatient();
	        List<Consultation> dossierConsults = patient.getDossier().getConsultations();
	        int indexDossier = dossierConsults.indexOf(ancienne);
	        if (indexDossier != -1) {
	            dossierConsults.set(indexDossier, cModifiee);
	        }

	        System.out.println("Consultation modifiée avec succès !");
	         
	    }

	    // consultationParSpecialite()
	    public List<Consultation> consultationParSpecialite(String specialite) {
	        return consultations.stream()
	                .filter(c -> c.getProfessionnel().getSpecialite() != null &&
	                		c.getProfessionnel().getSpecialite().equalsIgnoreCase(specialite))
	                .collect(Collectors.toList());
	    }
	

	public Optional<Patient> rechercherPatient(String id) {
        return patients.stream().filter(p -> p.getId().equals(id)).findFirst();
        }
	
	
	    public User seConnecter(String login, String motDePasse) {
	        for (User u : this.utilisateurs) {
	            if (u.getLogin().equals(login) && u.verifierMotDePasse(motDePasse)) {
	                this.utilisateurConnecte = u;
	                return u;
	            }
	        }
	        return null;
	    }

	    public void seDeconnecter() {
	        this.utilisateurConnecte = null;
	    }
	    
	    public void ajouterUtilisateur(User utilisateur) {
	        if (utilisateurConnecte instanceof Admin) {
	            this.utilisateurs.add(utilisateur);
	            System.out.println("Utilisateur " + utilisateur.getNom() + " ajouté.");
	        } else {
	            System.out.println("Erreur: Seul un administrateur peut ajouter des utilisateurs.");
	        }
	}
	    
	    public boolean modifierMotDePasse(String login, String nouveauMotDePasse) {
	        for (User u : this.utilisateurs) {
	            if (u.getLogin().equals(login)) {
	                u.setMotDePasse(nouveauMotDePasse);
	                return true;
	            }
	        }
	        return false;
	    }
	    
	    public boolean modifierSpecialite(String login, String nouvelleSpecialite) {
	        for (User u : this.utilisateurs) {
	            if (u.getLogin().equals(login)) {
	                if (u instanceof HealthPro) {
	                    HealthPro healthPro = (HealthPro) u;
	                    healthPro.setSpecialite(nouvelleSpecialite);
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
	    
	    public boolean modifierRole(String login, String nouveauRole, String nouvelleSpecialite) {
	        User utilisateurAModifier = null;

	        // Chercher l'utilisateur
	        for (User u : this.utilisateurs) {
	            if (u.getLogin().equals(login)) {
	                utilisateurAModifier = u;
	                break;
	            }
	        }

	        if (utilisateurAModifier == null) {
	            return false; // Utilisateur non trouvé
	        }

	        // Récupérer le mot de passe actuel
	        String motDePasseActuel = utilisateurAModifier.getMotDePasse();

	        // Supprimer l'ancien utilisateur de la liste
	        this.utilisateurs.remove(utilisateurAModifier);

	        User nouveauUtilisateur = null;

	        // Créer un nouvel utilisateur selon le rôle
	        if (nouveauRole.equalsIgnoreCase("ADMIN")) {
	            nouveauUtilisateur = new Admin(
	                utilisateurAModifier.getId(),
	                utilisateurAModifier.getNom(),
	                utilisateurAModifier.getPrenom(),
	                utilisateurAModifier.getDateNaissance(),
	                login,
	                motDePasseActuel
	            );
	        } else if (nouveauRole.equalsIgnoreCase("MEDECIN") || 
	                   nouveauRole.equalsIgnoreCase("INFIRMIER") || 
	                   nouveauRole.equalsIgnoreCase("PHARMACIEN")) {
	            nouveauUtilisateur = new HealthPro(
	                utilisateurAModifier.getId(),
	                utilisateurAModifier.getNom(),
	                utilisateurAModifier.getPrenom(),
	                utilisateurAModifier.getDateNaissance(),
	                login,
	                motDePasseActuel,
	                nouveauRole.toUpperCase(),
	                nouvelleSpecialite != null ? nouvelleSpecialite : ""
	            );
	        }

	        if (nouveauUtilisateur != null) {
	            this.utilisateurs.add(nouveauUtilisateur);
	            return true; // Rôle modifié avec succès
	        }

	        return false; // Rôle invalide ou échec
	    }
	    

	    public boolean supprimerUtilisateur(String login) {
	        User utilisateurASupprimer = null;

	        for (User u : this.utilisateurs) {
	            if (u.getLogin().equals(login)) {
	                utilisateurASupprimer = u;
	                break;
	            }
	        }

	        if (utilisateurASupprimer == null) return false; // Utilisateur non trouvé
	        if (utilisateurASupprimer.equals(utilisateurConnecte)) return false; // Ne peut pas supprimer soi-même

	        this.utilisateurs.remove(utilisateurASupprimer);
	        return true;
	    }

		// Retourne la liste des patients
		public List<Patient> getPatients() {
			return new ArrayList<>(patients);
		}

		// Retourne la liste des professionnels de santé
		public List<HealthPro> getHealthPros() {
			List<HealthPro> healthPros = new ArrayList<>();
			for (User user : utilisateurs) {
				if (user instanceof HealthPro) {
					healthPros.add((HealthPro) user);
				}
			}
			return healthPros;
		}

        public void exporterDonneesCSV() {
            throw new UnsupportedOperationException("Unimplemented method 'exporterDonneesCSV'");
        }

        public void ajouterExamen(Patient patient, String type, String resultat) {
			
            throw new UnsupportedOperationException("Unimplemented method 'ajouterExamen'");
        }




}







