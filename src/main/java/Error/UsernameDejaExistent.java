package Error;

public class UsernameDejaExistent extends Exception{
    public UsernameDejaExistent() {
        super("Error - Username deja inregistrat!");
    }
}
