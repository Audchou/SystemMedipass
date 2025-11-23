package medipass.service;

import medipass.entitie.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    private List<User> listeUtilisateurs;
    private User utilisateurConnecte;
    private final String CODE_DEROGATION = "CODE2025";

    public UserService() {
        this.listeUtilisateurs = new ArrayList<>();
        this.utilisateurConnecte = null;
    }

    public User seConnecter(String login, String motDePasse) {
        for (User u : this.listeUtilisateurs) {
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
        if (utilisateur != null) {
            this.listeUtilisateurs.add(utilisateur);
        }
    }

    public boolean modifierMotDePasse(String login, String nouveauMotDePasse) {
        for (User u : this.listeUtilisateurs) {
            if (u.getLogin().equals(login)) {
                u.setMotDePasse(nouveauMotDePasse);
                return true;
            }
        }
        return false;
    }

    public boolean modifierSpecialite(String login, String nouvelleSpecialite) {
        for (User u : this.listeUtilisateurs) {
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

        for (User u : this.listeUtilisateurs) {
            if (u.getLogin().equals(login)) {
                utilisateurAModifier = u;
                break;
            }
        }

        if (utilisateurAModifier == null) {
            return false;
        }

        // Récupérer le mot de passe actuel via réflexion ou méthode protégée
        String motDePasseActuel = utilisateurAModifier.getMotDePasse();
        this.listeUtilisateurs.remove(utilisateurAModifier);

        User nouveauUtilisateur = null;
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
            this.listeUtilisateurs.add(nouveauUtilisateur);
            return true;
        }

        return false;
    }

    public void modifierUtilisateur() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Modification d'un Utilisateur ---");
        System.out.print("Login de l'utilisateur à modifier: ");
        String login = scanner.nextLine();

        User utilisateur = null;
        for (User u : this.listeUtilisateurs) {
            if (u.getLogin().equals(login)) {
                utilisateur = u;
                break;
            }
        }

        if (utilisateur == null) {
            System.out.println("Utilisateur non trouvé.");
            return;
        }

        System.out.println("1. Modifier le mot de passe");
        System.out.println("2. Modifier la spécialité (pour professionnel de santé)");
        System.out.println("3. Modifier le rôle");
        System.out.print("Votre choix: ");
        int choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1:
                System.out.print("Nouveau mot de passe: ");
                String nouveauMdp = scanner.nextLine();
                if (modifierMotDePasse(login, nouveauMdp)) {
                    System.out.println("Mot de passe modifié avec succès.");
                } else {
                    System.out.println("Erreur lors de la modification.");
                }
                break;
            case 2:
                if (utilisateur instanceof HealthPro) {
                    System.out.print("Nouvelle spécialité: ");
                    String nouvelleSpecialite = scanner.nextLine();
                    if (modifierSpecialite(login, nouvelleSpecialite)) {
                        System.out.println("Spécialité modifiée avec succès.");
                    } else {
                        System.out.println("Erreur lors de la modification.");
                    }
                } else {
                    System.out.println("Seuls les professionnels de santé ont une spécialité.");
                }
                break;
            case 3:
                System.out.print("Nouveau rôle (ADMIN, MEDECIN, INFIRMIER, PHARMACIEN): ");
                String nouveauRole = scanner.nextLine();
                System.out.print("Nouvelle spécialité (si professionnel de santé): ");
                String specialite = scanner.nextLine();
                if (modifierRole(login, nouveauRole, specialite)) {
                    System.out.println("Rôle modifié avec succès.");
                } else {
                    System.out.println("Erreur lors de la modification.");
                }
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }

    public void supprimerUtilisateur() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Suppression d'un Utilisateur ---");
        System.out.print("Login de l'utilisateur à supprimer: ");
        String login = scanner.nextLine();

        User utilisateurASupprimer = null;
        for (User u : this.listeUtilisateurs) {
            if (u.getLogin().equals(login)) {
                utilisateurASupprimer = u;
                break;
            }
        }

        if (utilisateurASupprimer == null) {
            System.out.println("Utilisateur non trouvé.");
            return;
        }

        if (utilisateurASupprimer.equals(utilisateurConnecte)) {
            System.out.println("Vous ne pouvez pas supprimer l'utilisateur actuellement connecté.");
            return;
        }

        System.out.print("Êtes-vous sûr de vouloir supprimer cet utilisateur ? (oui/non): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("oui")) {
            this.listeUtilisateurs.remove(utilisateurASupprimer);
            System.out.println("Utilisateur supprimé avec succès.");
        } else {
            System.out.println("Suppression annulée.");
        }
    }

    public User getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public List<User> getListeUtilisateurs() {
        return new ArrayList<>(listeUtilisateurs);
    }

    public String getCodeDerogation() {
        return CODE_DEROGATION;
    }
}