package Error.ModifComanda;

public class ProdusDejaInCos extends Exception{
    public ProdusDejaInCos() {
        super("Error - Produsul se afla deja in cos!");
    }
}
