package Error.ModifComanda;

public class ProdusNuEInCos extends Exception{
    public ProdusNuEInCos() {
        super("Error - Produsul NU se afla in cos!");
    }
}
