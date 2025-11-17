package fs.master.asynccommunicationservice.model;


public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    // getters/setters
    public boolean isSuccess(){return success;}
    public void setSuccess(boolean s){this.success = s;}
    public String getMessage(){return message;}
    public void setMessage(String m){this.message = m;}
    public T getData(){return data;}
    public void setData(T d){this.data = d;}
}

