package fs.master.asynccommunicationservice.model;


public class Notification {
    private Long id;
    private Long userId;
    private String title;
    private String message;
    // getters/setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public Long getUserId(){return userId;}
    public void setUserId(Long u){this.userId=u;}
    public String getTitle(){return title;}
    public void setTitle(String s){this.title=s;}
    public String getMessage(){return message;}
    public void setMessage(String s){this.message = s;}
}
