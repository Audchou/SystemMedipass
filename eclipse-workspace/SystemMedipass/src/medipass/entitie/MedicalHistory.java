package medipass.entitie;

public class MedicalHistory {
	
	    public static final String PATHOLOGIE = "Pathologie";
	    public static final String INTERVENTION = "Intervention";
	    public static final String ALLERGIE = "Allergie";
	   

   
    private String id;           
    private String description;  
    private String type;       

    
    public MedicalHistory(String id, String description, String type) {
        this.id = id;
        this.description = description;
        this.type = type;
    }

 
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    
    public void afficher() {
        System.out.println("Antécédent ID : " + id);
        System.out.println("Type : " + type);
        System.out.println("Description : " + description);
    }
}

