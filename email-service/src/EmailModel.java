public class EmailModel {
    private String to;
    private String subject;
    private String body;

    public EmailModel() {

    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String toString() {
        return "Email sent to: " + to + " - With subject: " + subject + " - With body: " + body;
    }
}
