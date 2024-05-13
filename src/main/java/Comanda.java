public class Comanda {
    int idComanda, idUser;
    public Comanda(int idUser) {
        this.idUser = idUser;
        this.idComanda = MyTrashql.getIdComanda(idUser);
    }
    public static void adauga(int idUser){ MyTrashql.adaugaComanda(idUser); }

    public static Comanda getIncomplet(int idUser){
        if (!MyTrashql.existaComanda(idUser)) {
            Comanda.adauga(idUser);
        }
        return new Comanda(idUser);
    }

    public void adaugaProdus (int idProdus, int cantitate){
        // inserez in produs_comanda ^^
        MyTrashql.modificaComandaADD(this.idComanda, idProdus, cantitate); //eroare ∄ prod + eroare insuficient stoc
    }

    public void stergeProdus (int idProdus){
        //sterg in produs_comanda
        MyTrashql.modificaComandaDEL(this.idComanda, idProdus); // eroare ∄ prod in comanda
    }

    public void modificaCantitate (int idProdus, int cantitate){
        //modific cantitatea
        MyTrashql.modificaComandaCANT( this.idComanda, idProdus, cantitate); // eroare ∄ prod in comanda + eroare insuficient stoc
    }

    public void veziComanda (){
        //vad cos
        MyTrashql.veziCom(this.idComanda);
    }

    public void veziAdrese (){
        //vad adrese
        MyTrashql.veziAdrese(this.idUser);
    }

    public void adaugaAdresa (String strada, int numar){
        //adaug adresa
        MyTrashql.modificaAdreseADD(this.idUser, strada, numar);
    }

    public void finalizereComanda (int idAdresa){
        //finalizez comanda
        MyTrashql.finalizareCom(idAdresa, this.idUser, this.idComanda);
    }
}