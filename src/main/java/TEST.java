public class TEST {
    public static void main(String[] args) {
        String username = "gigica";
        String password = "123456789";
        String email = "gigica123@gmail.com";
        //Cont.register(username, password, email);
        Comanda comanda = Cont.login(username, password);

        // comanda.adaugaProdus(8, 3);
        // comanda.adaugaProdus(5, 2);
        // comanda.modificaCantitate(1, 2);
        comanda.veziComanda();
        // comanda.adaugaAdresa("Marioarei", 10);
        comanda.veziAdrese();
        //comanda.finalizereComanda(5);
        // comanda.finalizereComanda(4);

        // username -> password de la om ?= password
        // data = 1656201600
    }
    // ---------------Cazuri---------------
    //   I - register cu user si parola         todo - facut
    //  II - Log-in cu user si parola (-"-)     todo - facut
    // III - sa vad produsele                   todo - o sa o fac (AM FACUT!!!!!!!) odata cu login; pare mai corect.
    //  IV - adaug in cos un produs             todo - facut
    //   V - vad cosul de cumparaturi           todo - facut
    //  VI - finalizare comanda         (todo - contine joaca cu adrese) DAR E OK, MA DESCURC DE MINUNE!!!
    //
    //          CAZURI FINALIZATE


    //todo - INTREBARE: pot face clasa Comanda in clasa Cont (nested class?) pentru a face un obiect cont si o functie Cont.Comanda.functie()
    //------------ AM TERMINAT ------------
}