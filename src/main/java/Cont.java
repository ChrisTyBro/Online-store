import Error.*;
public class Cont {
    String username, parola, email;
    private final int id;

    public Cont(String username, String password, String email, int id) {
        this.username = username;
        this.parola = password;
        this.email = email;
        this.id = id;
    }
    //             -- GETTER --
    public String getUsername() {return username;}
    public String getParola() {return parola;}
    public String getEmail() {return email;}
    public int getId() {return id;}

    //-------------------------------------------------------------------
    public static Comanda login (String user, String pass){
        try {
            if(MyTrashql.Login(user, pass)){
                MyTrashql.veziProd();
                return Comanda.getIncomplet(MyTrashql.getIdUser(user));
            }
            else {
                return null;
            }
        } catch (UserPassGresit e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static boolean register (String user, String pass, String email) {
        try {
            MyTrashql.adaugaUser(user, pass, email);
        } catch (EmailDejaExistent e) {
            System.out.println(e.getMessage());
        } catch (UsernameDejaExistent e) {
            System.out.println(e.getMessage());
        } catch (NuSeFaceContul e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //-------------------------------------------------------------------

}
