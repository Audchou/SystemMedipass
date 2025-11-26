package medipass.service;

import medipass.entitie.Prescription;

public class PrescriptionService {
	// Méthode pour modifier une prescription existante
	public void modifierPrescription(Prescription prescription, String medicament, String posologie, int dureeJours) {
		if (prescription != null) {
			if (medicament != null && !medicament.isEmpty()) {
				// Met à jour le médicament de la prescription
				prescription.setMedicament(medicament);
			}
			if (posologie != null && !posologie.isEmpty()) {
				prescription.setPosologie(posologie);
			}
			if (dureeJours > 0) {
				prescription.setDuree(dureeJours);
			}
		}
	}
}