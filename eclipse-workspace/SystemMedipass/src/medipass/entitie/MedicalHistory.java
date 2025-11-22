package medipass.entitie;

import java.time.LocalDate;

public class MedicalHistory {
	
	 private String type; // Ex: Pathologie, Allergie, Chirurgie
	    private String description;
	    private LocalDate dateEnregistrement;

	    public MedicalHistory(String type, String description, LocalDate dateEnregistrement) {
	        this.type = type;
	        this.description = description;
	        this.dateEnregistrement = dateEnregistrement;
	    }

	    // Getters
	    public String getType() {
	        return type;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public LocalDate getDateEnregistrement() {
	        return dateEnregistrement;
	    }

	    @Override
	    public String toString() {
	        return "Type: " + type + ", Description: " + description + ", Date: " + dateEnregistrement;
	    }
}

