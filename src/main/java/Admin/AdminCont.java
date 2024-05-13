package Admin;
import Admin.AdminErrors.*;

public class AdminCont {
    private final int id;
    String username, parola, email;
    public AdminCont(String username, String parola, String email, int id) {
        this.id = id;
        this.username = username;
        this.parola = parola;
        this.email = email;
    }

//             -- GETTER --
    public int getId() {return id;}
    public String getUsername() {return username;}
    public String getParola() {return parola;}
    public String getEmail() {return email;}

//             -- LOGIN --
    public static AdminCont login (String user, String pass){
        try {
            AdminCont cont = AdminTrashQL.getCont(user);
            if(cont.getParola().equals(pass)){
                return cont;
            } else throw new UserPassGresit();
        } catch (UserPassGresit e) {System.out.println(e.getMessage());}
        return null;
    }

//            -- ADD PRODUSE --

    public void veziProduse() { AdminTrashQL.veziProduse(); }

    public void adaugaProdus(String nume, double pret, int stoc, String categorie) {
        AdminTrashQL.modificaProduseADD(nume, pret, stoc, categorie);
    }

    public void stergeProdus(int id) { AdminTrashQL.modificaProduseDEL(id); }

    public void modificaStoc(int id, int cantitate) { AdminTrashQL.modifStoc(id, cantitate); }

    public void seteazaStoc(int id, int cantitate) {
        AdminTrashQL.setStoc(id, cantitate);
    }
}
