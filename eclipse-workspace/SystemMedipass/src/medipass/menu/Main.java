package medipass.menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;


import medipass.system.SystemeMedipass;

import medipass.entitie.*;



public class Main {
	 private static SystemeMedipass systeme = SystemeMedipass.getInstance();
	  private static Scanner scanner = new Scanner(System.in);
	 private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	

	
	
	public static void main(String[] args) {
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
		        // Méthode connexion
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
		            choix = lireChoix();

		            switch (choix) {
		                case 1:
		                	System.out.println("1. Ajouter un utilisateur");
		                	System.out.println("1. Modifier un utilisateur");
		 		            System.out.println("2. Supprimer un utilisateur");
		 		            System.out.print("Votre choix: ");
		 		            choix = lireChoix();
		 		           switch (choix) {
			                case 1:
			                	ajouterUtilisateur();
		                    break;
			                case 2:
			                	modifierUtilisateur();
		                    break;
		                    case 3:
		                    	supprimerUtilisateur();
		                    break;
		                    default:
			                    System.out.println("Choix invalide.");
			            }
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
		        	    System.out.println("Bienvenue Admin " + healthpro.getNom());
		        	} else {
		        	    System.out.println("Aucun utilisateur connecté !");
		        	}
		        	System.out.println("1. Gestion des patients");
		        	System.out.println("2. Gestion des consultations");
		        	System.out.println("3. Gestion des prescriptions");
		            System.out.println("4. Ajouter un examen médical (Bonus)");
		            System.out.println("5. Exporter les données (CSV - Bonus)");
		            System.out.println("0. Déconnexion");
		            System.out.print("Votre choix: ");
		            choix = lireChoix();
		           
		           
		            switch (choix) {
		                case 1:
		                	System.out.println("1. Inscrire un nouveau patient");
		                	System.out.println("2. Ajouter des infos au dossier médical du patient");
		 		            System.out.println("3. Supprimer un patient");
		 		            System.out.println("4. Rechercher et consulter un dossier patient");
		 		            System.out.println("5. Afficher la spécialité dominante d'un dossier médical patient (Bonus)");
		 		            choix = lireChoix();

		 		           switch (choix) {
			                case 1:
		                    inscrirePatient();
		                    break;
		                case 2:
		                	System.out.println("2. Ajouter antécédent");
		                	System.out.println("2. Ajouter consultation");
		                	System.out.println("2. Ajouter examen médical");
		                	choix = lireChoix();
		                	
		                	switch (choix) {
			                case 1:
		                        ajoutAntecedent();
		                        break;
		                    case 2:
		                	    ajoutConsultation();
		                	    break;
		                    case 3:
		                    	ajoutExamen();
		                    	break;
		                    default:
			                    System.out.println("Choix invalide.");
			            }
		                    	
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
		               
		                case 2:
		                	System.out.println("1. Créer une nouvelle consultation");
				            System.out.println("2. Modifier une consultation");
				            System.out.println("3. Liste des consultations par specialité médicale (Bonus)");
				            choix = lireChoix();
				            
				            switch (choix) {
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
		               
		              case 3:
		                  System.out.println("1. Créer une prescription (Bonus)");
		 		          System.out.println("2. Modifier une prescription (Bonus)");
		 		         choix = lireChoix();
				            
				            switch (choix) {
			                case 1:
			                	creerPrescription();
		                    break;
		                    case 2:
		                	    modifierPrescription();
		                	break;
		                default:
		                    System.out.println("Choix invalide.");
		 		           }
				            
		              case 4:
		            	  ajouterExamen();
		            	  break;
		              case 5:
		            	  systeme.exporterDonneesCSV();
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

		    private static void ajouterUtilisateur() {
		        System.out.println("\n--- Ajout d'un Utilisateur ---");
		        System.out.print("ID Utilisateur (ex: U004): ");
		        String id = scanner.nextLine();
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
		        } else if (role.equals("MEDECIN") || role.equals("INFIRMIER")) {
		            System.out.print("Spécialité: ");
		            String specialite = scanner.nextLine();
		            nouvelUtilisateur = new HealthPro(id, nom, prenom, dob, login, mdp, role, specialite);
		        } else {
		            System.out.println("Rôle invalide. Utilisateur non créé.");
		            return;
		        }

		        systeme.ajouterUtilisateur(nouvelUtilisateur);
		    }

		    private static void inscrirePatient() {
		        System.out.println("\n--- Inscription d'un Patient ---");
		        System.out.print("ID Patient (ex: P003): ");
		        String id = scanner.nextLine();
		        System.out.print("Nom: ");
		        String nom = scanner.nextLine();
		        System.out.print("Prénom: ");
		        String prenom = scanner.nextLine();
		        LocalDate dob = lireDate("Date de naissance");

		        Patient nouveauPatient = new Patient(id, nom, prenom, dob);
		        systeme.ajouterPatient(nouveauPatient);
		    }

		    private static void consulterDossierPatient() {
		        Patient patient = trouverPatient();
		        if (patient != null) {
		            System.out.println(patient.getDossier());
		        }
		    }

		    private static void creerConsultation() {
		        Patient patient = trouverPatient();
		        if (patient == null) return;

		        System.out.print("Motif de la consultation: ");
		        String motif = scanner.nextLine();
		        System.out.print("Observations: ");
		        String observations = scanner.nextLine();

		        systeme.creerConsultation(patient, motif, observations);
		    }

		    private static void creerPrescription() {
		        Patient patient = trouverPatient();
		        if (patient == null) return;

		        System.out.print("Médicament: ");
		        String medicament = scanner.nextLine();
		        System.out.print("Posologie: ");
		        String posologie = scanner.nextLine();
		        System.out.print("Durée (en jours): ");
		        int duree = lireChoix();

		        if (duree > 0) {
		            Prescription prescription = new Prescription(medicament, posologie, duree);
		            // Le professionnel de santé connecté est utilisé pour prescrire
		            ((HealthPro) systeme.getUtilisateurConnecte()).prescrire(patient, prescription);
		        } else {
		            System.out.println("Durée invalide.");
		        }
		    }

		    private static void ajouterExamen() {
		        Patient patient = trouverPatient();
		        if (patient == null) return;

		        System.out.print("Type d'examen (ex: Radio, Analyse de sang): ");
		        String type = scanner.nextLine();
		        System.out.print("Résultat de l'examen: ");
		        String resultat = scanner.nextLine();

		        systeme.ajouterExamen(patient, type, resultat);
		    }

		    private static void afficherSpecialiteDominante() {
		        Patient patient = trouverPatient();
		        if (patient != null) {
		            System.out.println("\n--- Catégorisation du Dossier ---");
		            System.out.println("La spécialité dominante du dossier est: " + patient.getDossier().getSpecialiteDominante());
		        }
		    }
		        
		    private static void consultationsParSpecialite() {
		    }
		    
		    private static void modifierUtilisateur() {
		    	systeme.modifierUtilisateur();
		    }
		    
		    private static void supprimerUtilisateur() {
		    	systeme.supprimerUtilisateu();
		    }
		    
		    private static void supprimerPatient() {
		    	systeme.supprimerPatient();
		    }
				        
			private static void modifierPrescription() {
				systeme.modifierPrescription();
					       
		    }
	        
		   private static void modifierConsultation() {
			   systeme.modifierConsultation();
				       
	    }
		
			
			
		        private static int lireChoix() {
			        try {
			            return Integer.parseInt(scanner.nextLine());
			        } catch (NumberFormatException e) {
			            return -1;
			        }
			 
		    }
		}

	
	
	
	
   
	
	
	    
	   

	    
	       
	       
	    	
	    	
		


	
	
	
	
	


