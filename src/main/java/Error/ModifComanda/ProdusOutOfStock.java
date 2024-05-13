package Error.ModifComanda;

public class ProdusOutOfStock extends Exception{
    public ProdusOutOfStock() {
        super("Error - Produsul nu e in stoc!");
    }
}
