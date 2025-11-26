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
	
	 public boolean ajouterPatient(Patient patient) {
		 for (Patient p : patients) {
		        if (p.getId().equals(patient.getId())) {
		            System.out.println("Erreur : un patient avec cet ID existe déjà !");
		            return false;
		        }
		 }
	        this.patients.add(patient);
	        dossiers.add(new MedicalRecord(patient.getId()));
		 return true;
	 }
	
	 public boolean supprimerPatient(Patient patient) {
		    return this.patients.remove(patient);
		}
	 
	 public Prescription creerPrescription(Patient patient, String medicament, String posologie, int duree) {
		    if (patient == null) return null;
		    String prescriptionId = "P" + (patient.getDossier().getPrescriptions().size() + 1);
		    Prescription prescription = new Prescription(prescriptionId, medicament, posologie, duree);

		    patient.getDossier().ajouterPrescription(prescription);

		    return prescription;
		}

	 
	 public boolean modifierPrescription(String prescriptionId, Patient patient, String nouveauMedicament,
             String nouvellePosologie, int nouvelleDuree) {
         if (patient == null) return false;

        Optional<Prescription> opt = patient.getDossier().getPrescriptions().stream()
                  .filter(p -> p.getId().equals(prescriptionId))
                  .findFirst();
        if (!opt.isPresent()) return false;

     Prescription p = opt.get();
        if (nouveauMedicament != null && !nouveauMedicament.isEmpty()) p.setMedicament(nouveauMedicament);
        if (nouvellePosologie != null && !nouvellePosologie.isEmpty()) p.setPosologie(nouvellePosologie);
        if (nouvelleDuree > 0) p.setDuree(nouvelleDuree);

            return true;
}
  

	public boolean archiverDossier(String patientId) {
		 System.out.println("DEBUG → Type utilisateur connecté : " 
			        + utilisateurConnecte.getClass().getSimpleName());
			    if (utilisateurConnecte instanceof HealthPro) {
			        System.out.println("DEBUG → Rôle : " 
			            + ((HealthPro) utilisateurConnecte).getRole());
			    } else {
			        System.out.println("DEBUG → Ce n'est PAS un HealthPro");
			    }
			    for (MedicalRecord dossier : dossiers) {
			        System.out.println("DEBUG dossier présent → " + dossier.getPatientId());
			    }

	    // Vérifier que l'utilisateur connecté est un médecin
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
                 System.out.println("Aucune consultation trouvée pour l'ID : " + id);
                  return; // STOP si consultation introuvable
 }

       // Vérifie si la nouvelle date est occupée par le même professionnel
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

           // Crée la consultation modifiée
           Consultation cModifiee = new Consultation(ancienne.getId(),
               nouvelleDate != null ? nouvelleDate : ancienne.getDate(),
               nouveauMotif != null && !nouveauMotif.isBlank() ? nouveauMotif : ancienne.getMotif(),
               nouvellesObservations != null && !nouvellesObservations.isBlank() ? nouvellesObservations : ancienne.getObservations(),
               ancienne.getProfessionnel(),
               ancienne.getPatient());

          // Remplacer dans la liste des consultations
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
	
	public Optional<Prescription> rechercherPrescription(String id) {
	    return patients.stream()
	        .filter(p -> p.getDossier() != null)   
	        .flatMap(p -> p.getDossier().getPrescriptions().stream())
	        .filter(presc -> presc.getId() != null && presc.getId().equalsIgnoreCase(id))
	        .findFirst();
	}
	
	

	public boolean ajouterAntecedent(Patient patient, String type, String description) {
	    if (patient == null) return false;
	    MedicalRecord dossier = patient.getDossier();
	    if (dossier == null) return false;
	    MedicalHistory antecedent = new MedicalHistory(type, description);
	    dossier.ajouterAntecedent(antecedent);
	    return true;
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
	        }
	        return true;
	}
	    
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
	    public User rechercherUtilisateur(String id) {
	        for (User u : utilisateurs) {
	            if (u.getId().equals(id)) {
	                return u;
	            }
	        }
	        return null; // si aucun utilisateur trouvé
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
		
		 // --- Fonctionnalités Statistiques ---

	    public void afficherStatistiques() {
	        System.out.println("\n--- Statistiques du Système Medipass ---");
	        System.out.println("Nombre total de patients: " + patients.size());
	        System.out.println("Nombre total d'utilisateurs: " + utilisateurs.size());

	        long nbProSante = utilisateurs.stream()
	            .filter(u -> u instanceof HealthPro)
	            .count();
	        System.out.println("Nombre de professionnels de santé: " + nbProSante);

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

		  // --- Fonctionnalités Bonus ---
	    
	    public void exporterDonneesCSV() {
	        ExportCSV.exporterPatients(patients);
	        ExportCSV.exporterUtilisateurs(utilisateurs);
	    }
	    
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
	    
	    // --- Getters pour l'interface ---
	    public List<User> listerUtilisateurs() { return utilisateurs; }
	    public List<Patient> listerPatients() { return patients; }
	}












