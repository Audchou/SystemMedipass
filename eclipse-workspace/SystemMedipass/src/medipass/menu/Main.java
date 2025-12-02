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
		    	
	            while (systeme.getUtilisateurConnecte() == null) {
	                System.out.println("\n--- Connexion ---");
	                System.out.print("Login: ");
	                String login = scanner.nextLine();
	                System.out.print("Mot de passe: ");
	                String mdp = scanner.nextLine();
     
	            User utilisateur = systeme.seConnecter(login, mdp); 
	            if (utilisateur != null) {
	            	
	            	System.out.println("\nConnexion réussie !");
	                System.out.println("Bienvenu " + utilisateur.getNom() + ", vous êtes connecté en tant que " 
	                                   + (utilisateur instanceof Admin ? "Administrateur" : "Professionnel de Santé"));
	            
	                // Redirection vers le menu correspondant au rôle
	            if (utilisateur instanceof Admin) {
		            menuAdministrateur(utilisateur);
		        } else if (utilisateur instanceof HealthPro) {
		            menuProfessionnelSante(utilisateur);
		        }
	            break;
	          }else {
	              // Si le login ou mot de passe incorrect
	              System.out.println("Erreur de connexion. Veuillez réessayer.");
	          }
	        }
	     }
		    
		

		     private static void menuAdministrateur(User admin) {
		    
		        int choix = -1;
		        while (choix != 0) {
		        	if (admin != null) {
		        	    System.out.println("\n--- Menu Administrateur ---");
		        	   
		            System.out.println("1. Gestion des utilisateurs");
		            System.out.println("2. Afficher les statistiques du système");
		            System.out.println("3. Exporter les données (CSV - Bonus)");
		            System.out.println("4. Consulter le dossier médical d'un patient (Admin autorisé avec code de dérogation)");
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
		                case 3:
		                	systeme.exporterDonneesCSV();
		                	break;
		                case 4:
		                	consulterDossierPatient(admin);
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
		  }
		    		    
		    private static void menuProfessionnelSante(User healthpro) {
		    	
		        int choix = -1;
		        while (choix != 0) {
		        	if (healthpro != null) {
		        	    System.out.println("\n--- Menu Professionnel de Santé ---");
		        	    
		        	System.out.println("1. Gestion des patients");
		        	System.out.println("2. Gestion des consultations (Réservé uniquement au Médécin)");
		        	System.out.println("3. Gestion des prescriptions(Bonus) ( Médécin et Pharmacien)");
		            System.out.println("4. Ajouter un examen médical (Bonus) (Médécin))");
		            System.out.println("5. Gestionnaire d'archives(Bonus) et (Réservé uniquement au Médécin et à l'Infirmier)");
		            System.out.println("0. Déconexion");
		            System.out.print("Votre choix: ");
		            int choixPatient= lireChoix();
		           
		           
		            switch (choixPatient) {
		                case 1:
		                	System.out.println("1. Inscrire un nouveau patient (Médécin et Infirmier)");
		                	System.out.println("2. Ajouter des antécédents au dossier médical du patient (Médécin)");
		 		            System.out.println("3. Supprimer un patient (Médécin)");
		 		            System.out.println("4. Rechercher et consulter un dossier patient");
		 		            System.out.println("5. Afficher la spécialité dominante d'un dossier médical patient (Bonus)(Médécin et Infirmier)");
		 		            System.out.print("Votre choix: ");
		 		            choix = lireChoix();

		 		           switch (choix) {
			            case 1:
		                    inscrirePatient(healthpro);
		                    break;
		                case 2:
		                    ajouterAntecedent(healthpro);
		                    break;
		                    	
		                case 3:
							supprimerPatient(healthpro);
		                	break;
		                case 4:
		                	consulterDossierPatient(healthpro);
		                	break;
		                case 5:
		                	afficherSpecialiteDominante(healthpro);
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
			                	creerConsultation(healthpro);
		                    break;
		                    case 2:
		                	    modifierConsultation(healthpro);
		                	break;
		                case 3:
		                	    consultationsParSpecialite(healthpro);
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
			                	creerPrescription(healthpro);
		                    break;
		                    case 2:
		                	    modifierPrescription(healthpro);
		                	break;
		                default:
		                    System.out.println("Choix invalide.");
		 		           }
				            break;
				            
		              case 4:
		            	  ajouterExamen(healthpro);
		            	  break;
		            	  
		              case 5:

		            	  System.out.println("1. Archiver un dossier");
		 		          System.out.println("2. Désarchiver un dossier");
		 		          System.out.println("3. Voir la liste des dossiers archivés");
		 		          System.out.print("Votre choix: ");
		 		         int choixArchive = lireChoix();
		 		        switch (choixArchive) {
		                case 1:
		                	archiverDossier(healthpro);
	                    break;
	                    case 2:
	                	    desarchiverDossier(healthpro);
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
		            return null;
		        }
		        return patientOpt.get();
		    }
		                          
		                           //USERS

		    private static void ajouterUtilisateur() {
		        System.out.println("\n--- Ajout d'un Utilisateur ---");
		        System.out.print("ID de l'utilisateur à créer (Exemple U006) : ");
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
		    
		    

		              // Gestion PATIENTS
		    
		    private static void inscrirePatient(User healthpro) {
		    	
		    	if (!(healthpro instanceof HealthPro) || !((HealthPro) healthpro).peutAjouterDossier()) {
		            System.out.println("Erreur : seul un médecin ou un infirmier peut ajouter un dossier médical pour un patient.");
		            return;
		        }
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
	
		    private static void consulterDossierPatient(User utilisateur) {
		        Patient patient = trouverPatient();
		        if (patient == null) {
		            System.out.println("Patient introuvable !");
		            return;
		        }
		        if (utilisateur instanceof HealthPro) {
		            System.out.println(patient.getDossier());
		            return;
		        }

                    if (utilisateur instanceof Admin) {
		            System.out.print("Code de dérogation requis : ");
		            String code = scanner.nextLine().trim();
		            if ("MEDIPASS2025".equals(code)) { 
		                System.out.println("Vous êtes habilité. Voici le dossier");
		                System.out.println(patient.getDossier());
		            } else {
		                System.out.println("Code invalide, vous n'êtes pas autorisé à consulter les dossiers médicaux");
		            }
		        } 
		    }


	        
	        private static void supprimerPatient(User healthpro) {
	        	 if (!(healthpro instanceof HealthPro) || !((HealthPro) healthpro).peutSupprimerDossier()) {
	        	        System.out.println("Erreur : seul un médecin peut supprimer un patient.");
	        	        return;
	        	    }
				   System.out.print("Entrez l'ID du patient à supprimer : ");
				   String id = scanner.nextLine();

				   Patient patient = systeme.rechercherPatient(id).orElse(null);

				   if (patient == null) {
				       System.out.println("Patient inexistant !");
				   } else {
				       if (systeme.supprimerPatient(patient)) {
				           System.out.println("Patient supprimé avec succès.");
				       } else {
				           System.out.println("Erreur : impossible de supprimer ce patient.");
				       }
				   }

		    }
               
	        
	        
	        
	                       // Gestion CONSULTATIONS
	        
		    private static void creerConsultation(User healthpro) {
		    	
		    	 if (!(healthpro instanceof HealthPro hp) || !hp.peutCreerConsultation()) {
		    	        System.out.println("Erreur : seul un médecin peut créer une consultation.");
		    	        return;
		    	    }
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


		        HealthPro pro = (HealthPro) systeme.getUtilisateurConnecte();

		     // Appel à la méthode singleton systeme pour créer la consultation
		        try {
		            systeme.creerConsultation(id,date, motif, observations, pro, patient);
		        } catch (IllegalArgumentException e) {
		            System.out.println(e.getMessage());
		        }
		    }
		    
		    
			private static void modifierConsultation(User healthpro) {
				
				if (!(healthpro instanceof HealthPro hp) || !hp.peutModifierConsultation()) {
		            System.out.println("Erreur : seul un médecin peut modifier une consultation.");
		            return;
		        }
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

				LocalDateTime nouvelleDate = null;
				while (true) {
				    System.out.print("Nouvelle date (yyyy-MM-dd HH:mm) ou ENTER pour garder l'ancienne : ");
				    String dateStr = scanner.nextLine();
				    if (dateStr.isEmpty()) break;

				    try {
				    	LocalDateTime dateTemp = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
				    	if (dateTemp.isBefore(LocalDateTime.now())) {
				    	    System.out.println("Erreur : la date doit être futur !");
				    	    continue;  // redemande encore une date nouvelle date jusqu'a reussite
				    	}
				        boolean occupe = systeme.getConsultations().stream()
				                .anyMatch(c ->
				                        c.getProfessionnel().equals(ancienne.getProfessionnel()) &&
				                        c.getDate().equals(dateTemp) &&
				                        !c.getId().equals(id)
				                );

				        if (occupe) {
				            System.out.println("Le professionnel n'est pas disponible à cette date. Réessayez.");
				            continue;
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
				
				systeme.modifierConsultation(id, nouvelleDate, nouveauMotif, nouvellesObservations);
				 
		}
			
			               //Gestion PRESCRITIONS
			private static void creerPrescription(User healthpro) {
				
				if (!(healthpro instanceof HealthPro pro) || !pro.peutModifierPrescription()) {
			        System.out.println("Erreur : seul le médecin ou le pharmacien peut créer une prescription.");
			        return;
			    }
		        Patient patient = trouverPatient(); 
		        if (patient == null) return;
		        
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

		    
		   
		    	private static void modifierPrescription(User healthpro) {
		    		
		    		 if (!(healthpro instanceof HealthPro pro) || !pro.peutModifierPrescription()) {
					        System.out.println("Erreur : seul le médecin ou le pharmacien peut modifier une prescription.");
					        return;
					    }
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


            
		    private static void ajouterExamen(User healthpro) {
		    	
		    	if (!(healthpro instanceof HealthPro pro) || !pro.peutAjouterExamenMedical()) {
		    	    System.out.println("Erreur : seul un médecin peut ajouter un examen médical.");
		    	    return;
		    	}
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

		    private static void afficherSpecialiteDominante(User healthpro) {
		    	
		    	if (!(healthpro instanceof HealthPro hp) || !hp.voirSpecialiteDominante()) {
		            System.out.println("Erreur : seul le médecin peut voir la spécialité dominante.");
		            return;
		        }
		        Patient patient = trouverPatient();
		        if (patient != null) {
		            System.out.println("\n--- Catégorisation du Dossier ---");
		            System.out.println("La spécialité dominante du dossier est: " + patient.getDossier().getSpecialiteDominante());
		        }
		    }
		    
		    // Afficher toutes les consultations pour une spécialité donnée    
		    private static void consultationsParSpecialite(User healthpro) {
		    	
		    	 if (!(healthpro instanceof HealthPro pro) || !pro.voirSpecialiteDominante()) {
		    	        System.out.println("Erreur : seul un médecin peut consulter les consultations par spécialité.");
		    	        return;
		    	    }
		    	
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

	        
		                  // ARCHIVAGE
		     
		   private static void archiverDossier(User healthpro) {
			   
			   if (!(healthpro instanceof HealthPro pro) || !pro.peutArchiverDossier()) {
		            System.out.println("Action non autorisée : seul un médecin ou un infirmier peut archiver un dossier.");
		            return;
		        }
			    System.out.print("Entrez l'ID du patient à archiver : ");
			    String patientId = scanner.nextLine();

			    if (systeme.archiverDossier(patientId)) {
			        System.out.println("Dossier archivé avec succès !");
			    } else {
			        System.out.println("Impossible d'archiver ce dossier. Vérifiez votre rôle ou l'état du dossier.");
			    }
				       
	    }
		  
		   
		   private static void desarchiverDossier(User healthpro) {
		   
			   if (!(healthpro instanceof HealthPro pro) || !pro.peutDesarchiverDossier()) {
			        System.out.println("Action non autorisée : seul un médecin ou un infirmier peut désarchiver un dossier.");
			        return;  }
			 
		   System.out.print("Entrez l'ID du patient à désarchiver : ");
		    String patientId = scanner.nextLine();

		    if (systeme.desarchiverDossier(patientId)) {
		        System.out.println("Dossier désarchivé avec succès !");
		    } else {
		        System.out.println("Impossible de désarchiver ce dossier. Vérifiez votre rôle ou l'état du dossier.");
		    }
		   }
		   
		   private static void ajouterAntecedent(User healthpro) {
			   
			   if (!(healthpro instanceof HealthPro pro) || !pro.peutAjouterAntecedent()) {
			        System.out.println("Erreur : seul le médecin peut ajouter des antécédents.");
			        return;
			    }
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

				private static int lireChoix() {
					try {
						return Integer.parseInt(scanner.nextLine());
					} catch (NumberFormatException e) {
						return -1; //Retourne -1 si le choix est invalide
					}
					
			}
}	
				
		
				
	
		

	
	
	
	
   
	
	
	    
	   

	    
	       
	       
	    	
	    	
		


	
	
	
	
	


