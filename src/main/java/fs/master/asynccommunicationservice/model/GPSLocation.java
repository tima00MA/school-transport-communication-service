package fs.master.asynccommunicationservice.model;


public class GPSLocation {
    private Long id;
    private Double latitude;
    private Double longitude;
    private String entityType; // student or bus
    private Long entityId;
    // getters/setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public Double getLatitude(){return latitude;}
    public void setLatitude(Double d){this.latitude=d;}
    public Double getLongitude(){return longitude;}
    public void setLongitude(Double d){this.longitude=d;}
    public String getEntityType(){return entityType;}
    public void setEntityType(String t){this.entityType=t;}
    public Long getEntityId(){return entityId;}
    public void setEntityId(Long id){this.entityId=id;}
}

