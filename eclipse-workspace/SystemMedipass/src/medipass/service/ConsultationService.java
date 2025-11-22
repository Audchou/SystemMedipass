package medipass.service;



public class ConsultationService {
	// Méthodes à implementer : creerConsultation(); modifierConsultation(); consultationParSpecialite()
	
	
	private List<Consultation> consultations = new ArrayList<>();

    // creerConsultation
    public Consultation creerConsultation(String id, LocalDateTime date, String motif,
                                          String observations, HealthPro professionnel, Patient patient) {

        boolean occupe = consultations.stream()
                .anyMatch(c -> c.getProfessionnel().getId().equals(professionnel.getId())
                        && c.getDate().equals(date));

        if (occupe) {
            throw new IllegalArgumentException("Le professionnel n'est pas disponible à cette date.");
        }

        Consultation c = new Consultation(id, date, motif, observations, professionnel, patient);
        consultations.add(c);
        return c;
    }

    // modifierConsultation()
    public Consultation modifierConsultation(String id, LocalDateTime nouvelleDate,
                                             String nouveauMotif, String nouvellesObservations) {

        Consultation ancienne = consultations.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (ancienne == null) {
            return null;
        }

        if (nouvelleDate != null && !nouvelleDate.equals(ancienne.getDate())) {
            boolean occupe = consultations.stream()
                    .anyMatch(c -> c.getProfessionnel().equals(ancienne.getProfessionnel())
                            && c.getDate().equals(nouvelleDate))
                            && !c.getId().equals(id);

            if (occupe) {
                throw new IllegalArgumentException("Le professionnel n'est pas disponible à la nouvelle date.");
            }
        }

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

        return cModifiee;
    }

    // consultationParSpecialite()
    public List<Consultation> consultationParSpecialite(String specialite) {
        return consultations.stream()
                .filter(c -> c.getProfessionnel().getSpecialite() != null &&
                		c.getProfessionnel().getSpecialite().equalsIgnoreCase(specialite))
                .collect(Collectors.toList());
    }
}

