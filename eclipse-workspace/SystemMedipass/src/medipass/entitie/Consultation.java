package medipass.entitie;

import java.time.LocalDateTime;


public class Consultation {

    
	private String id;
    private LocalDateTime date;
    private String motif;
    private String observations;
    private HealthPro professionnel;
    private Patient patient;

    public Consultation(String id, LocalDateTime date, String motif, String observations, HealthPro professionnel, Patient patient) {
        this.id = id;
        this.date = date;
        this.motif = motif;
        this.observations = observations;
        this.professionnel = professionnel;
        this.patient = patient;
    }

    // Getters
    public String getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getMotif() {
        return motif;
    }

    public String getObservations() {
        return observations;
    }

    public HealthPro getProfessionnel() {
        return professionnel;
    }

    public Patient getPatient() {
        return patient;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Date: " + date.toLocalDate() + " à " + date.toLocalTime() +
               ", Motif: " + motif + ", Pro: " + professionnel.getNom() + " " + professionnel.getPrenom();
    }
}
