package medipass.menu;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;
import medipass.system.SystemeMedipass;
import java.util.List;

import medipass.entitie.*;


//Classe principale qui contient le menu pour la navigation en console du programme
public class Main {
	
	 // Instance du système centralisé Medipass
	 private static SystemeMedipass systeme = SystemeMedipass.getInstance();
	  private static Scanner scanner = new Scanner(System.in);
	 private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	 

	public static void main(String[] args) {
		
		 // Affichage du menu principal entrée  
		 System.out.println("===================================================");
	        System.out.println("  Système d'Information Médical Medipass (Console) ");
	        System.out.println("===================================================");
	        System.out.println("Bienvenu dans votre gestionnaire d'information médical !");
	        System.out.println("1 - Se connecter");
		    System.out.println("2 - Quitter le programme");
		    System.out.print("Entrez votre choix : ");
		    int choix = lireChoix();
		    boolean continuer = true;
		    
		    while(continuer) {
		    
                if (choix == 1) {
		        System.out.println("Super, tu choisis de te connecter !");
	            menuConnexion();
	    }
		    else if (choix == 2) {
		    	 System.out.println("Merci d'avoir utilisé l'application et à bientôt peut être !");
			        continuer = false;
		        } else {
		        System.out.println("Votre choix ne correspoond à aucune option");
		    }
		} 
	}
		    
		    private static void menuConnexion() {
	            User utilisateur = null;
	            while (utilisateur == null) {
	                System.out.println("\n--- Connexion ---");
	                System.out.print("Login: ");
	                String login = scanner.nextLine();
	                System.out.print("Mot de passe: ");
	                String mdp = scanner.nextLine();

	                utilisateur = systeme.seConnecter(login, mdp);

	                if (utilisateur == null) {
	                    System.out.println("Erreur de connexion. Veuillez réessayer.");
	                }
	            }
	            // Redirection vers le menu correspondant au rôle
	            if (utilisateur instanceof Admin) {
		            menuAdministrateur();
		        } else if (utilisateur instanceof HealthPro) {
		            menuProfessionnelSante();
		        }
		    }

		     private static void menuAdministrateur() {
		    	 User admin = systeme.getUtilisateurConnecte();
		        int choix = -1;
		        while (choix != 0) {
		        	if (admin != null) {
		        	    System.out.println("\n--- Menu Administrateur ---");
		        	    System.out.println("Bienvenue Admin " + admin.getNom());
		        	} else {
		        	    System.out.println("Aucun utilisateur connecté !");
		        	}
		            System.out.println("1. Gestion des utilisateurs");
		            System.out.println("2. Afficher les statistiques du système");
		            System.out.println("0. Déconnexion");
		            System.out.print("Votre choix: ");
		            int choixUser = lireChoix();
		         
		            // Gestion des sous-menus
		            switch (choixUser) {
		                case 1:
		                	System.out.println("1. Ajouter un utilisateur");
		                	System.out.println("2. Modifier un utilisateur");
		 		            System.out.println("3. Supprimer un utilisateur");
		 		            System.out.println("4. Afficher un utilisateur");
		 		            System.out.print("Votre choix: ");
		 		            choix = lireChoix();
		 		           switch (choix) {
			                case 1:
			                	ajouterUtilisateur();
		                    break;
			                case 2:
			                	System.out.println("1. Modifier mot de passe");
			                	System.out.println("2. Modifier spécialité");
			                	System.out.println("3. Modifier rôle");
			                	System.out.print("Votre choix: ");
			                	choix = lireChoix();
			                	
			                switch (choix) {
			                case 1:
			                	modifierMotDePasse();
			                	break;
			                case 2:
			                	modifierSpecialite();
			                	break;
			                case 3:
			                	modifierRole();
			                	break;
			                	default:
			                		 System.out.println("Choix invalide.");
			                }
		                    break;
		                    case 3:
		                    	supprimerUtilisateur();
		                        break;
		                    case 4:
		                    	afficherUtilisateur();
		                    	break;
		                    default:
			                    System.out.println("Choix invalide.");
			            }
		 		           break;
		                case 2:
		                	systeme.afficherStatistiques();
		                	break;
		                case 0:
		                	systeme.seDeconnecter();
		                	 menuConnexion();
			                    return;
		                default:
		                    System.out.println("Choix invalide.");
		            }
		                	
		        }
		    }
		    		    
		    private static void menuProfessionnelSante() {
		    	User healthpro = systeme.getUtilisateurConnecte();
		        int choix = -1;
		        while (choix != 0) {
		        	if (healthpro != null) {
		        	    System.out.println("\n--- Menu Professionnel de Santé ---");
		        	    System.out.println("Bienvenue ProSante " + healthpro.getNom());
		        	} else {
		        	    System.out.println("Aucun utilisateur connecté !");
		        	}
		        	System.out.println("1. Gestion des patients");
		        	System.out.println("2. Gestion des consultations");
		        	System.out.println("3. Gestion des prescriptions(Bonus)");
		            System.out.println("4. Ajouter un examen médical (Bonus)");
		            System.out.println("5. Exporter les données (CSV - Bonus)");
		            System.out.println("6. Gestionnaire d'archives(Bonus)");
		            System.out.println("0. Déconexion");
		            System.out.print("Votre choix: ");
		            int choixPatient= lireChoix();
		           
		           
		            switch (choixPatient) {
		                case 1:
		                	System.out.println("1. Inscrire un nouveau patient");
		                	System.out.println("2. Ajouter des antécédents au dossier médical du patient");
		 		            System.out.println("3. Supprimer un patient");
		 		            System.out.println("4. Rechercher et consulter un dossier patient");
		 		            System.out.println("5. Afficher la spécialité dominante d'un dossier médical patient (Bonus)");
		 		            System.out.print("Votre choix: ");
		 		            choix = lireChoix();

		 		           switch (choix) {
			            case 1:
		                    inscrirePatient();
		                    break;
		                case 2:
		                    ajouterAntecedent();
		                    break;
		                    	
		                case 3:
							supprimerPatient();
		                	break;
		                case 4:
		                	consulterDossierPatient();
		                	break;
		                case 5:
		                	afficherSpecialiteDominante();
		                	break;


		                default:
		                    System.out.println("Choix invalide.");
		 		           }
		 		           break;
		               
		                 case 2:
		                	System.out.println("1. Créer une nouvelle consultation");
				            System.out.println("2. Modifier une consultation");
				            System.out.println("3. Liste des consultations par specialité médicale (Bonus)");
				            System.out.print("Votre choix: ");
				            int choixConsu = lireChoix();
				            
				            switch (choixConsu) {
			                case 1:
			                	creerConsultation();
		                    break;
		                    case 2:
		                	    modifierConsultation();
		                	break;
		                case 3:
		                	    consultationsParSpecialite();
		                	break;
		                default:
		                    System.out.println("Choix invalide.");
		 		           }
				            break;
		               
		              case 3:
		                  System.out.println("1. Créer une prescription");
		 		          System.out.println("2. Modifier une prescription");
		 		          System.out.print("Votre choix: ");
		 		         int choixPresc = lireChoix();
				            
				            switch (choixPresc) {
			                case 1:
			                	creerPrescription();
		                    break;
		                    case 2:
		                	    modifierPrescription();
		                	break;
		                default:
		                    System.out.println("Choix invalide.");
		 		           }
				            break;
				            
		              case 4:
		            	  ajouterExamen();
		            	  break;
		              case 5:
		            	  systeme.exporterDonneesCSV();
		                  break;
		              case 6:

		            	  System.out.println("1. Archiver un dossier");
		 		          System.out.println("2. Désarchiver un dossier");
		 		          System.out.println("3. Voir la liste des dossiers archivés");
		 		          System.out.print("Votre choix: ");
		 		         int choixArchive = lireChoix();
		 		        switch (choixArchive) {
		                case 1:
		                	archiverDossier();
	                    break;
	                    case 2:
	                	    desarchiverDossier();
	                	break;
	                    case 3:
	                    	systeme.afficherDossiersArchives();
	                	break;
	                default:
	                    System.out.println("Choix invalide.");
	 		           }
		 		        break;
		                 
		                  
		              case 0:
		            	  systeme.seDeconnecter();
		                	 menuConnexion();
		                	 return;
		                default:
		                    System.out.println("Choix invalide.");
		            }
		            
		        }
		    }
		        
		                	 
		            	   // --- Méthodes de Saisie et Logique Métier ---

		   
		    private static LocalDate lireDate(String prompt) {
		        LocalDate date = null;
		        while (date == null) {
		            System.out.print(prompt + " (Format YYYY-MM-DD): ");
		            try {
		                date = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
		            } catch (DateTimeParseException e) {
		                System.out.println("Format de date invalide. Veuillez utiliser YYYY-MM-DD.");
		            }
		        }
		        return date;
		    }

		    private static Patient trouverPatient() {
		        System.out.print("Entrez l'ID du patient (ex: P001): ");
		        String id = scanner.nextLine();
		        Optional<Patient> patientOpt = systeme.rechercherPatient(id);
		        if (patientOpt.isEmpty()) {
		            System.out.println("Patient non trouvé.");
		            return null;
		        }
		        return patientOpt.get();
		    }
		                          
		                           //USERS

		    private static void ajouterUtilisateur() {
		        System.out.println("\n--- Ajout d'un Utilisateur ---");
		        System.out.print("ID de l'utilisateur à créer (Exemple U004) : ");
		        String id = scanner.nextLine().trim();
		        
		     
		        if (systeme.idExiste(id)) {
		            System.out.println("Erreur : un utilisateur avec cet ID existe déjà !");
		            return;
		        }

		        System.out.print("Nom: ");
		        String nom = scanner.nextLine();
		        System.out.print("Prénom: ");
		        String prenom = scanner.nextLine();
		        LocalDate dob = lireDate("Date de naissance");
		        System.out.print("Login: ");
		        String login = scanner.nextLine();
		        System.out.print("Mot de passe: ");
		        String mdp = scanner.nextLine();
		        System.out.print("Rôle (ADMIN, MEDECIN, INFIRMIER, PHARMACIEN): ");
		        String role = scanner.nextLine().toUpperCase();

		        User nouvelUtilisateur = null;
		        if (role.equals("ADMIN")) {
		            nouvelUtilisateur = new Admin(id, nom, prenom, dob, login, mdp);
		        } else if (role.equals("MEDECIN") || role.equals("INFIRMIER") || role.equals("PHARMACIEN")) {
		            System.out.print("Spécialité: ");
		            String specialite = scanner.nextLine();
		            nouvelUtilisateur = new HealthPro(id, nom, prenom, dob, login, mdp, role, specialite);
		        } else {
		            System.out.println("Rôle invalide. Utilisateur non créé.");
		            return;
		        }
		        // Ajout au système
		        systeme.ajouterUtilisateur(nouvelUtilisateur);
		    }
		    
		    

		              // Gestion PATIENTS
		    
		    private static void inscrirePatient() {
		        System.out.println("\n--- Inscription d'un Patient ---");
		        System.out.print("ID Patient (ex: P003): ");
		        String id = scanner.nextLine().trim().toUpperCase();

		        //Vérifier si l'ID existe déjà
		        if (systeme.idExistePatient(id)) {
		            System.out.println("Erreur : un patient avec cet ID existe déjà !");
		            return; 
		        }
		        System.out.print("Nom: ");
		        String nom = scanner.nextLine();
		        System.out.print("Prénom: ");
		        String prenom = scanner.nextLine();
		        LocalDate dob = lireDate("Date de naissance");

		        Patient nouveauPatient = new Patient(id, nom, prenom, dob);
		        systeme.ajouterPatient(nouveauPatient);
		        System.out.println("Patient créé avec succès : " + nom + " " + prenom);
		        

			}
		    
		    private static void supprimerUtilisateur() {
				
				System.out.print("Login de l'utilisateur à supprimer: ");
				String login = scanner.nextLine();
	
				System.out.print("Êtes-vous sûr de vouloir supprimer cet utilisateur ? (oui/non): ");
				String confirmation = scanner.nextLine();
	
				if (confirmation.equalsIgnoreCase("oui")) {
					boolean succes = systeme.supprimerUtilisateur(login);
					if (succes) {
						System.out.println("Utilisateur supprimé avec succès.");
					} else {
						System.out.println("Erreur : utilisateur introuvable ou suppression impossible (peut-être vous-même).");
					}
				} else {
					System.out.println("Suppression annulée.");
				}
			}
		    
		    private static void afficherUtilisateur()  {		    
			    System.out.print("Entrez l'ID de l'utilisateur à rechercher: ");
			    String id = scanner.nextLine();

			    User user = systeme.rechercherUtilisateur(id);
			    systeme.afficherUtilisateur(user);
			    }

	        private static void consulterDossierPatient() {
		        Patient patient = trouverPatient();
		        if (patient != null) {
		            System.out.println(patient.getDossier());
		        }
		    }
                       // Gestion CONSULTATIONS
	        
		    private static void creerConsultation() {
		        Patient patient = trouverPatient();
		        if (patient == null) return;
		        System.out.print("Entrez l'ID de la consultation (ex: C001): ");
		        String id = scanner.nextLine().trim().toUpperCase();
		        if (systeme.idConsultationExiste(id)) {
		            System.out.println("Erreur : une consultation avec cet ID existe déjà !");
		            return;
		        }
		        if (patient.getDossier().getConsultations().stream()
		                .anyMatch(c -> c.getId().equalsIgnoreCase(id))) {
		            System.out.println("Erreur : ce patient a déjà une consultation avec cet ID !");
		            return;
		        }
		        System.out.print("Observations: ");
		        String observations = scanner.nextLine();
		        System.out.print("Motif de la consultation: ");
		        String motif = scanner.nextLine();
               
		        
		        LocalDateTime date = null;
		     
		        // Vérifier si la date est dans antérieure a celle actuelle
		         while (true) {
		            System.out.print("Date de la consultation (yyyy-MM-dd HH:mm): ");
		            String dateStr = scanner.nextLine().trim();


		            try {
		                date = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    
		            if (date.isBefore(LocalDateTime.now())) {
			            System.out.println("Erreur : impossible de programmer une consultation antérieure à celle d'aujourd'hui !");
			            continue;
			        }
		            break;
		        }
		            catch (DateTimeParseException e) {
		                System.out.println("Format de date invalide ! Exemple correct : 2025-03-12 14:30");
		                System.out.println("Veuillez réessayer...\n");
		            }
		        }


		        
		        if (!(systeme.getUtilisateurConnecte() instanceof HealthPro)) {
		            System.out.println("Erreur : Seul un professionnel de santé peut créer une consultation.");
		            return;
		        }
		        HealthPro pro = (HealthPro) systeme.getUtilisateurConnecte();

		     // Appel à la méthode singleton du système pour créer la consultation
		        try {
		            systeme.creerConsultation(id,date, motif, observations, pro, patient);
		            System.out.print("Consultation programmé avec succès"); 
		        } catch (IllegalArgumentException e) {
		            System.out.println(e.getMessage());
		        }
		    }
		    
		    
			private static void modifierConsultation() {
				System.out.print("ID de la consultation à modifier : ");
				String id = scanner.nextLine();

				// Chercher la consultation
				Optional<Consultation> opt = systeme.getConsultations().stream()
				        .filter(c -> c.getId().equals(id))
				        .findFirst();

				if (!opt.isPresent()) {
				    System.out.println("Aucune consultation trouvée pour cet ID !");
				    return;
				}
				Consultation ancienne = opt.get();

				// Nouvelle date avec réessai si invalide ou indisponible
				LocalDateTime nouvelleDate = null;
				while (true) {
				    System.out.print("Nouvelle date (yyyy-MM-dd HH:mm) ou ENTER pour garder l'ancienne : ");
				    String dateStr = scanner.nextLine();
				    if (dateStr.isEmpty()) break;

				    try {
				    	LocalDateTime dateTemp = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
				    	if (dateTemp.isBefore(LocalDateTime.now())) {
				    	    System.out.println("Erreur : la date doit être futur !");
				    	    continue;  // redemande une date
				    	}
				        boolean occupe = systeme.getConsultations().stream()
				                .anyMatch(c ->
				                        c.getProfessionnel().equals(ancienne.getProfessionnel()) &&
				                        c.getDate().equals(dateTemp) &&
				                        !c.getId().equals(id)
				                );

				        if (occupe) {
				            System.out.println("Le professionnel n'est pas disponible à cette date. Réessayez.");
				            continue; // reessayer
				        }
				        nouvelleDate = dateTemp;
				        break;
		}

				    catch (DateTimeParseException e) {
				        System.out.println("Format de date invalide ! Réessayez.");
				    }
			}

				// Nouveau motif
				System.out.print("Nouveau motif ou ENTER pour garder l'ancien : ");
				String nouveauMotif = scanner.nextLine();
				if (nouveauMotif.isEmpty()) nouveauMotif = null;

				// Nouvelles observations
				System.out.print("Nouvelles observations ou ENTER pour garder l'ancien : ");
				String nouvellesObservations = scanner.nextLine();
				if (nouvellesObservations.isEmpty()) nouvellesObservations = null;

				// Modifier la consultation
				systeme.modifierConsultation(id, nouvelleDate, nouveauMotif, nouvellesObservations);
				 
		}
			
			               //Gestion PRESCRITIONS
			private static void creerPrescription() {
		        Patient patient = trouverPatient(); 
		        if (patient == null) return;
		        
		        // Saisie des infos de la prescription
		        System.out.print("Médicament: ");
		        String medicament = scanner.nextLine();
		        System.out.print("Posologie: ");
		        String posologie = scanner.nextLine();
		        System.out.print("Durée (en jours): ");
		        int duree = lireChoix();

		        Prescription prescription = systeme.creerPrescription(patient, medicament, posologie, duree);

		        if (prescription != null) {
		            System.out.println("Prescription créée avec l'ID : " + prescription.getId());
		        } else {
		            System.out.println("Erreur lors de la création de la prescription.");
		        }
		    }

		    
		   
		    	private static void modifierPrescription() {
		    	    Patient patient = trouverPatient();
		    	    if (patient == null) return;

		    	    System.out.print("Entrez l'ID de la prescription à modifier : ");
		    	    String prescriptionId = scanner.nextLine();

		    	    System.out.print("Nouveau médicament (ENTER pour conserver) : ");
		    	    String medicament = scanner.nextLine();
		    	    System.out.print("Nouvelle posologie (ENTER pour conserver) : ");
		    	    String posologie = scanner.nextLine();
		    	    System.out.print("Nouvelle durée (0 pour conserver) : ");
		    	    int duree = lireChoix();

		    	    boolean succes = systeme.modifierPrescription(prescriptionId, patient, medicament, posologie, duree);

		    	    if (succes) {
		    	        System.out.println("Prescription modifiée avec succès !");
		    	    } else {
		    	        System.out.println("Prescription introuvable !");
		    	    }
		    	}


            
		    private static void ajouterExamen() {
		        Patient patient = trouverPatient();
		        if (patient == null) return;

		        System.out.print("Type d'examen (ex: Radio, Analyse de sang): ");
		        String type = scanner.nextLine();
		        System.out.print("Demandeur: ");
		        String demandeur = scanner.nextLine();
		        System.out.print("Résultat de l'examen: ");
		        String resultat = scanner.nextLine();

				systeme.ajouterExamen(patient, type, resultat, demandeur);
		    }

		    private static void afficherSpecialiteDominante() {
		        Patient patient = trouverPatient();
		        if (patient != null) {
		            System.out.println("\n--- Catégorisation du Dossier ---");
		            System.out.println("La spécialité dominante du dossier est: " + patient.getDossier().getSpecialiteDominante());
		        }
		    }
		    
		    // Afficher toutes les consultations pour une spécialité donnée    
		    private static void consultationsParSpecialite() {
		    	
		    	    System.out.print("Entrez la spécialité recherchée: ");
		    	    String specialite = scanner.nextLine();

		    	    List<Consultation> liste = systeme.consultationParSpecialite(specialite);

		    	    if (liste.isEmpty()) {
		    	        System.out.println("Aucune consultation trouvée pour cette spécialité.");
		    	    } else {
		    	        System.out.println("--- Consultations pour la spécialité " + specialite + " ---");
		    	        for (Consultation c : liste) {
		    	            System.out.println(c);
		    	        }
		    	    }
		    	}

	         private static void modifierMotDePasse() {
	        	 
	        	System.out.print("Login de l'utilisateur à modifier: ");
	        	    String login = scanner.nextLine();
		    	System.out.print("Nouveau mot de passe: ");
	            String mdp = scanner.nextLine();
	            if (systeme.modifierMotDePasse(login, mdp)) {
	                System.out.println("Mot de passe modifié !");
	            } else {
	                System.out.println("Erreur : utilisateur non trouvé.");
	            }
		    }
		    
		    private static void modifierSpecialite() {
		    	
		    	System.out.print("Login de l'utilisateur à modifier: ");
        	    String login = scanner.nextLine();
		    	System.out.print("Nouvelle spécialité: ");
	            String specialite = scanner.nextLine();
	            if (systeme.modifierSpecialite(login, specialite)) {
	                System.out.println("Spécialité modifiée !");
	            } else {
	                System.out.println("Erreur : utilisateur non trouvé ou pas un professionnel de santé.");
	            }
		    }
		    
		    private static void modifierRole() {

		    	System.out.print("Login de l'utilisateur à modifier: ");
        	    String login = scanner.nextLine();
		    	 System.out.print("Nouveau rôle (ADMIN, MEDECIN, INFIRMIER, PHARMACIEN): ");
		            String role = scanner.nextLine();
		            System.out.print("Nouvelle spécialité (si pro santé) : ");
		            String specialite = scanner.nextLine();
		            if (systeme.modifierRole(login, role, specialite)) {
		                System.out.println("Rôle modifié !");
		            } else {
		                System.out.println("Erreur : modification impossible.");
		            }
		        }
		    
		                  // ARCHIVAGE
		     
		   private static void archiverDossier() {
			    System.out.print("Entrez l'ID du patient à archiver : ");
			    String patientId = scanner.nextLine();

			    // Appel via singleton
			    if (systeme.archiverDossier(patientId)) {
			        System.out.println("Dossier archivé avec succès !");
			    } else {
			        System.out.println("Impossible d'archiver ce dossier. Vérifiez votre rôle ou l'état du dossier.");
			    }
				       
	    }
		   
		   private static void supprimerPatient() {
			   System.out.print("Entrez l'ID du patient à supprimer : ");
			   String id = scanner.nextLine();

			   // étape 1 : retrouver le patient
			   Patient patient = systeme.rechercherPatient(id).orElse(null);


			   if (patient == null) {
			       System.out.println("Patient inexistant !");
			   } else {
			       // étape 2 : supprimer
			       if (systeme.supprimerPatient(patient)) {
			           System.out.println("Patient supprimé avec succès.");
			       } else {
			           System.out.println("Erreur : impossible de supprimer ce patient.");
			       }
			   }

	    }
		  
		   
		   private static void desarchiverDossier() {
		   
		   System.out.print("Entrez l'ID du patient à désarchiver : ");
		    String patientId = scanner.nextLine();

		    if (systeme.desarchiverDossier(patientId)) {
		        System.out.println("Dossier désarchivé avec succès !");
		    } else {
		        System.out.println("Impossible de désarchiver ce dossier. Vérifiez votre rôle ou l'état du dossier.");
		    }
		   }
		   
		   private static void ajouterAntecedent() {
			    System.out.print("Entrez l'ID du patient : ");
			    String id = scanner.nextLine().trim().toUpperCase();


			  
			    Patient patient = systeme.rechercherPatient(id).orElse(null); 
			    if (patient == null) {
			        System.out.println("Patient introuvable !");
			        return;
			    }

			  
			    System.out.print("Entrez l'antécédent à ajouter : ");
			    String type = scanner.nextLine();
			    System.out.print("Ajouter une courte description : ");
			    String description = scanner.nextLine();

			    
			    boolean ok = systeme.ajouterAntecedent(patient, type, description);
			    if (ok) {
			        System.out.println("Antécédent ajouté avec succès !");
			    } else {
			        System.out.println("Impossible d'ajouter l'antécédent.");
			    }
			}

		         // Pour récupérer le choix d'option de l'utilisateur qui interagit avec le systeme
				private static int lireChoix() {
					try {
						return Integer.parseInt(scanner.nextLine());
					} catch (NumberFormatException e) {
						return -1; //Retourne -1 si entrée invalide
					}
					
			}
}	
				
		
				
	
		

	
	
	
	
   
	
	
	    
	   

	    
	       
	       
	    	
	    	
		


	
	
	
	
	


