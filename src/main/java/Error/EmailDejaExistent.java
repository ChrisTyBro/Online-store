package Error;

public class EmailDejaExistent extends Exception{
    public EmailDejaExistent() {
        super("Error - Email deja inregistrat!");
    }
}
