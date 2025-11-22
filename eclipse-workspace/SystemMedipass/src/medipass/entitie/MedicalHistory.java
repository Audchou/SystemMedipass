package medipass.entitie;


public class MedicalHistory {
	
	 private String type; // Ex: Pathologie, Allergie, Chirurgie
	    private String description;

	    public MedicalHistory(String type, String description) {
	        this.type = type;
	        this.description = description;
	      
	    }

	    // Getters
	    public String getType() {
	        return type;
	    }

	    public String getDescription() {
	        return description;
	    }

	    @Override
	    public String toString() {
	        return "Type: " + type + ", Description: " + description;
	    }
}

