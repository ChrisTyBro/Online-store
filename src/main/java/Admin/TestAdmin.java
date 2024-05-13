package Admin;

public class TestAdmin {
    public static void main(String[] args) {
        String username = "admin1";
        String password = "ParolaAdmin1";
        AdminCont cont = AdminCont.login(username, password);

        //.adaugaProdus("Diamant", 11353.32, 5, "valoroase");
        //cont.stergeProdus(7);
        //cont.modificaCantitate(8, 20);
        //cont.seteazaCantitate(8, 5);
        cont.veziProduse();
    }
}
