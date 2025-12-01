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
	    
	    
	    /**
	     * Constructeur privé du système.
	     * Initialise les listes de patients et d'utilisateurs.
	     * Remplit également le système avec des données de test pour le démarrage.
	     */
	    private SystemeMedipass() {
	        this.patients = new ArrayList<>();
	        this.utilisateurs = new ArrayList<>();
	        initialiserDonneesTest();
	    }
	    
	    
	    
	    /**
	     * Retourne l'instance unique du système (singleton).
	     * 
	     * Ici, le mot instance fait référence à l'unique objet de type SystemeMedipass
	     * qui existe pour toute l'application. Cela permet de centraliser toutes
	     * les données (patients, utilisateurs, consultations, etc.) dans un seul
	     * endroit, garantissant que toutes les opérations utilisent le même état
	     * du système.
	     *
	     * Le pattern Singleton est utilisé ici :
	     * - Si l'instance n'existe pas encore, elle est créée.
	     * - Sinon, l'instance existante est retournée.
	     */
	    public static SystemeMedipass getInstance() {
	        if (instance == null) {
	            instance = new SystemeMedipass();
	        }
	        return instance;
	    }
	    
	    //Retourne l'utilisateur actuellement connecté
	    public User getUtilisateurConnecte() {
	        return utilisateurConnecte;
	    }
	    
	    //Retourne la liste complète des consultations enregistrées dans le système.
	    public List<Consultation> getConsultations() {
	        return consultations;
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

	    
	    // Ajoute un dossier à la liste
	    public void ajouterDossier(MedicalRecord dossier) {
	        dossiers.add(dossier);
	    }
	    
	    // Méthode pour récupérer un dossier à partir de son ID
	    public MedicalRecord getDossierByPatientId(String patientId) {
	        return dossiers.stream()
	                .filter(d -> d.getPatientId().equalsIgnoreCase(patientId))
	                .findFirst()
	                .orElse(null);
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
	 
	 
	                        //Méthodes GESTION PATIENTS
	 
	 
	// Ajoute un patient à la liste du système et crée son dossier médical
	 public void ajouterPatient(Patient patient) {
		    this.patients.add(patient);              
		    ajouterDossier(patient.getDossier());    
		}

	
	 public boolean supprimerPatient(Patient patient) {
		    return this.patients.remove(patient);
		}
	 
	// Cherche un patient par son ID et retourne un Optional pour gérer le cas où il n'existe pas
	 public Optional<Patient> rechercherPatient(String id) {
	        return patients.stream().filter(p -> p.getId().equals(id)).findFirst();
	        }
	 
	 
	       

	
	                       //Méthodes GESTION CONSULTATIONS
	
	// Crée une nouvelle consultation pour un patient donné par un professionnel de santé
	// Vérifie que l'utilisateur est bien un HealthPro et qu'il est disponible à la date proposée
	    public void creerConsultation(String id, LocalDateTime date, String motif,
	                                          String observations, HealthPro professionnel, Patient patient) {
	    	 if (!(utilisateurConnecte instanceof HealthPro)) {
	    		 throw new IllegalArgumentException("Erreur: Seul un professionnel de santé peut créer une consultation.");
	    	 }
	             
	    	 HealthPro pro = (HealthPro) utilisateurConnecte;
	    	 

	    	 // Vérifie si le professionnel a déjà une consultation à cette date
	        boolean occupe = consultations.stream()
	                .anyMatch(c -> c.getProfessionnel().getId().equals(professionnel.getId())
	                        && c.getDate().equals(date));

	        if (occupe) {
	            throw new IllegalArgumentException("Le professionnel n'est pas disponible à cette date.");
	        }
	        // Création et ajout de la consultation au dossier med
	        Consultation c = new Consultation(id, date, motif, observations, pro, patient);
	        consultations.add(c);
	        patient.getDossier().ajouterConsultation(c);
	        System.out.println("Consultation créée pour le patient " + patient.getNom() + " par Dr. " + pro.getNom());
	    }
	    	    

	 // Modifie les informations d'une consultation existante
	 // On peut changer la date, le motif ou les observations
	    public void modifierConsultation(String id, LocalDateTime nouvelleDate,
                String nouveauMotif, String nouvellesObservations) {
       
	    	// On cherche par id la consultation à modifier 
      Consultation ancienne = consultations.stream()
                   .filter(c -> c.getId().equals(id))
                   .findFirst()
                   .orElse(null);

          if (ancienne == null) {
                 System.out.println("Aucune consultation trouvée pour l'ID : " + id);
                  return; // On renvoi ce msg si consultation introuvable
 }

       // Vérifie si la nouvelle date est libre pour le même professionnel
         if (nouvelleDate != null && !nouvelleDate.equals(ancienne.getDate())) {
     boolean occupe = consultations.stream()
              .anyMatch(c ->
              c.getProfessionnel().equals(ancienne.getProfessionnel()) &&
              c.getDate().equals(nouvelleDate) &&
             !c.getId().equals(id));

        if (occupe) {
                System.out.println("Le professionnel n'est pas disponible à la nouvelle date.");
                  return; 
                  }
 }
         // Mise à jour des informations
         if (nouvelleDate != null) ancienne.setDate(nouvelleDate);
         if (nouveauMotif != null && !nouveauMotif.isBlank()) ancienne.setMotif(nouveauMotif);
         if (nouvellesObservations != null && !nouvellesObservations.isBlank()) ancienne.setObservations(nouvellesObservations);

       System.out.println("Consultation modifiée avec succès !");
 }


	    // Retourne la liste des consultations d'une spécialité donnée(BONUS)
	    public List<Consultation> consultationParSpecialite(String specialite) {
	        return consultations.stream()
	                .filter(c -> c.getProfessionnel().getSpecialite() != null &&
	                		c.getProfessionnel().getSpecialite().equalsIgnoreCase(specialite))
	                .collect(Collectors.toList());
	    }
	

	
	              //Ajout au dossier médical
    
	public boolean ajouterAntecedent(Patient patient, String type, String description) {
	    if (patient == null) return false;
	    MedicalRecord dossier = patient.getDossier();
	    if (dossier == null) return false;
	    MedicalHistory antecedent = new MedicalHistory(type, description);
	    dossier.ajouterAntecedent(antecedent);
	    return true;
	}
	

	                     //Méthodes d'AUTHENTIFICATION
	
	    //Connexion, vérification login et mot de passe
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
	    
	    
	                          //Méthodes GESTION UTILISATEURS
	    
	 // Ajoute un nouvel utilisateur au système
	 // Seul un administrateur peut ajouter un utilisateur
	 // Vérifie que l'ID n'existe pas déjà 
	    public boolean ajouterUtilisateur(User utilisateur) {
	    	 if(utilisateur == null) return false;
	    	 boolean idExiste = utilisateurs.stream()
                     .anyMatch(u -> u.getId().equals(utilisateur.getId()));

            if(idExiste) {
                System.out.println("Erreur : un utilisateur avec cet ID existe déjà !");
                   return false;
}
	        if (utilisateurConnecte instanceof Admin) {
	            this.utilisateurs.add(utilisateur);
	            System.out.println("Utilisateur " + utilisateur.getNom() + " ajouté.");
	        } else {
	            System.out.println("Erreur: Seul un administrateur peut ajouter des utilisateurs.");
	            return false;
	        }
	        return true;
	}
	    
	 // Affiche les informations d'un utilisateur
	 // Affiche rôle et spécialité si c'est un professionnel de santé
	    public void afficherUtilisateur(User u) {
	        if (u == null) {
	            System.out.println("Utilisateur introuvable !");
	            return;
	        }
	        System.out.println("ID: " + u.getId());
	        System.out.println("Nom: " + u.getNom());
	        System.out.println("Prénom: " + u.getPrenom());
	        System.out.println("Date de naissance: " + u.getDateNaissance());
	        System.out.println("Login: " + u.getLogin());

	        if (u instanceof HealthPro hp) {
	            System.out.println("Rôle: " + hp.getRole());
	            System.out.println("Spécialité: " + hp.getSpecialite());
	        } else if (u instanceof Admin) {
	            System.out.println("Rôle: ADMIN");
	        }
	    }
	    
	   // Supprime un utilisateur à partir de son login (Il doit exister d'abord)
	   // Ne permet pas de supprimer l'utilisateur connecté
	    public boolean supprimerUtilisateur(String login) {
	        User utilisateurASupprimer = null;

	        for (User u : this.utilisateurs) {
	        	if (u.getLogin().equalsIgnoreCase(login.trim())) {
	                utilisateurASupprimer = u;
	                break;
	            }
	        }

	        if (utilisateurASupprimer == null) return false; 
	        if (utilisateurASupprimer.equals(utilisateurConnecte)) return false; 

	        this.utilisateurs.remove(utilisateurASupprimer);
	        return true;
	    }
	    
	    // Cherche un utilisateur par ID et l'affiche si trouvé
	    public User rechercherUtilisateur(String id) {
	        for (User u : utilisateurs) {
	            if (u.getId().equals(id)) {
	                return u;
	            }
	        }
	        return null; 
	    }
	    
	                     // MODIFIER UTILISATEURS
	    
	    
	    // Modifie le mot de passe d'un utilisateur à partir de son login
	    public boolean modifierMotDePasse(String login, String nouveauMotDePasse) {
	        for (User u : this.utilisateurs) {
	        	if (u.getLogin().equalsIgnoreCase(login.trim())) {
	                u.setMotDePasse(nouveauMotDePasse);
	                return true;
	            }
	        }
	        return false;
	    }
	  
	    // Modifie la spécialité d'un professionnel de santé
	    public boolean modifierSpecialite(String login, String nouvelleSpecialite) {
	        for (User u : this.utilisateurs) {
	        	if (u.getLogin().equalsIgnoreCase(login.trim())) {
	                if (u instanceof HealthPro) {
	                    HealthPro healthPro = (HealthPro) u;
	                    healthPro.setSpecialite(nouvelleSpecialite);
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
	  
	    // Change le rôle d'un utilisateur (Admin ou HealthPro)
	    public boolean modifierRole(String login, String nouveauRole, String nouvelleSpecialite) {
	        User utilisateurAModifier = null;

	        // recherche de l'utilisateur
	        for (User u : this.utilisateurs) {
	        	if (u.getLogin().equalsIgnoreCase(login.trim())) {
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
	            else {
	                return false; 
	            }
	            this.utilisateurs.add(nouveauUtilisateur);
	            return true;
	            }
	    
	    
	    
	                           //Méthodes pour vérifier l'UNICITE des ID
	    
	    public boolean idExiste(String id) {
	        return utilisateurs.stream()
	                           .anyMatch(u -> u.getId().equals(id));
	    }
	    
	    public boolean idExistePatient(String id) {
	        return patients.stream()
	                       .anyMatch(p -> p.getId().equals(id));
	    }
	    
	    public boolean idConsultationExiste(String id) {
	        return consultations.stream().anyMatch(c -> c.getId().equals(id));
	    }
	    
	    

	    
		
		                  // Fonctionnalité STATISTIQUES

		public void afficherStatistiques() {
		    System.out.println("\n--- Statistiques du Système Medipass ---");
		    System.out.println("Nombre total de patients: " + patients.size());
		    System.out.println("Nombre total d'utilisateurs: " + utilisateurs.size());

		    // 1. Compter les Administrateurs
		    long nbAdmins = utilisateurs.stream()
		        .filter(u -> u instanceof Admin)
		        .count();
		    System.out.println("Nombre d'administrateurs: " + nbAdmins);

		    // 2. Compter les Médecins
		    long nbMedecins = utilisateurs.stream()
		        .filter(u -> u instanceof HealthPro && u.getRole().equals("MEDECIN"))
		        .count();
		    System.out.println("Nombre de médecins: " + nbMedecins);

		    // 3. Compter les Infirmiers
		    long nbInfirmiers = utilisateurs.stream()
		        .filter(u -> u instanceof HealthPro && u.getRole().equals("INFIRMIER"))
		        .count();
		    System.out.println("Nombre d'infirmiers: " + nbInfirmiers);
		    
		 // 3. Compter les Pharmaciens
		    long nbPharmaciens = utilisateurs.stream()
		        .filter(u -> u instanceof HealthPro && u.getRole().equals("PHARMACIEN"))
		        .count();
		    System.out.println("Nombre de pharmaciens: " + nbPharmaciens);

		    // 4. Statistiques sur les actes
		    long totalConsultations = patients.stream()
		        .mapToLong(p -> p.getDossier().getConsultations().size())
		        .sum();
		    System.out.println("Nombre total de consultations enregistrées: " + totalConsultations);
		    
		    long totalExamens = patients.stream()
		        .mapToLong(p -> p.getDossier().getExamens().size())
		        .sum();
		    System.out.println("Nombre total d'examens enregistrés: " + totalExamens);
		    System.out.println("----------------------------------------\n");
		}

		  
		
		              // Fonctionnalités BONUS
	                      
		                 //Méthode EXPORTATION CSV
		
	    public void exporterDonneesCSV() {
	        ExportCSV.exporterPatients(patients);
	        ExportCSV.exporterUtilisateurs(utilisateurs);
	    }
	    
	                      //Méthode AJOUT EXAM MEDICAL AU DOSSIER MEDICAL
	    
	    
	    // Seul un professionnel de santé peut faire l'ajout au dossier
	    public void ajouterExamen(Patient patient, String type, String resultat, String demandeur) {
	        if (utilisateurConnecte instanceof HealthPro) {
	            HealthPro pro = (HealthPro) utilisateurConnecte;
	            String examenId = "E" + (patient.getDossier().getExamens().size() + 1);
	            
	            MedicalExam nouvelExamen = new MedicalExam(examenId, LocalDateTime.now(), type, resultat, pro);
	            patient.getDossier().ajouterExamen(nouvelExamen);
	            System.out.println("Examen " + type + " ajouté au dossier de " + patient.getNom());
	        } else {
	            System.out.println("Erreur: Seul un professionnel de santé peut ajouter un examen.");
	        }
	    } 
	    
	                          //Méthodes ARCHIVAGE DU DOSSIER MEDICAL
	    
	    // Seul un médecin peut archiver
	    public boolean archiverDossier(String patientId) {

		    // Vérifie que l'utilisateur connecté est un médecin
		    if (!(utilisateurConnecte instanceof HealthPro) || 
		         !((HealthPro) utilisateurConnecte).getRole().equalsIgnoreCase("MEDECIN")) {
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
		
	    // Seul un médecin peut désarchiver
		public boolean desarchiverDossier(String patientId) {
		    if (!(utilisateurConnecte instanceof HealthPro) || 
		         !((HealthPro) utilisateurConnecte).getRole().equalsIgnoreCase("MEDECIN")) {
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
		            System.out.println(dossier); 
		            aucun = false;
		        }
		    }

		    if (aucun) {
		        System.out.println("Aucun dossier n'est actuellement archivé.");
		    }
		}
		
		
                             //Méthodes GESTION PRESCRIPTION
		
		// Créer et ajouter la prescription dans le dossier
		public Prescription creerPrescription(Patient patient, String medicament, String posologie, int duree) {
			if (patient == null)
				return null;
			String prescriptionId = "P" + (patient.getDossier().getPrescriptions().size() + 1);
			Prescription prescription = new Prescription(prescriptionId, medicament, posologie, duree);

			patient.getDossier().ajouterPrescription(prescription);

			return prescription;
		}

		
		public boolean modifierPrescription(String prescriptionId, Patient patient, String nouveauMedicament,
				String nouvellePosologie, int nouvelleDuree) {
			if (patient == null)
				return false;

			Optional<Prescription> opt = patient.getDossier().getPrescriptions().stream()
					.filter(p -> p.getId().equals(prescriptionId)).findFirst();
			if (!opt.isPresent())
				return false;

			Prescription p = opt.get();
			if (nouveauMedicament != null && !nouveauMedicament.isEmpty())
				p.setMedicament(nouveauMedicament);
			if (nouvellePosologie != null && !nouvellePosologie.isEmpty())
				p.setPosologie(nouvellePosologie);
			if (nouvelleDuree > 0)
				p.setDuree(nouvelleDuree);

			return true;
		}

		public Optional<Prescription> rechercherPrescription(String id) {
			return patients.stream().filter(p -> p.getDossier() != null)
					.flatMap(p -> p.getDossier().getPrescriptions().stream())
					.filter(presc -> presc.getId() != null && presc.getId().equalsIgnoreCase(id)).findFirst();
		}

		
	    
	    // --- Getters pour l'interface ---
		
		// Retourne la liste de tous les utilisateurs
	    public List<User> listerUtilisateurs() { return utilisateurs; }
	    
	    // Retourne la liste de tous les patients
	    public List<Patient> listerPatients() { return patients; }
	}












