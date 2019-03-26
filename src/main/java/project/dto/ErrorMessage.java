package project.dto;

/**
 * Created by Danylo on 25.03.2019
 */
public class ErrorMessage {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage() {
    }
}
