import Admin.AdminTrashQL;
import Error.*;
import Error.ModifComanda.*;

import java.sql.*;
public class MyTrashql {
    public static Connection getConnection(){
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/magazin",
                    "root",
                    "root"
            );
            return connection;
        } catch (SQLException sqlException) {System.out.println(sqlException.getMessage() + " .getConnection");}
        return null;
    }

    public static void adaugaUser(String user, String pass, String email) throws EmailDejaExistent, UsernameDejaExistent, NuSeFaceContul {

    }
// ------------- CONT --------------------------------------------------------------------------------------------------
    public static Cont getCont(String userEmail){
        try {
            Connection connection = MyTrashql.getConnection();
            String query = "SELECT * FROM user WHERE username = ? OR `e-mail` = ?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, userEmail);
            pstm.setString(2, userEmail);
            //-------------------------------------------------------------------
            ResultSet result = pstm.executeQuery();
            if(result.next()){
                return new Cont(result.getString("username"), result.getString("password"), result.getString("e-mail"), result.getInt("id"));
            } else return null;


        } catch (SQLException e) {System.out.println(e.getMessage() + " .getCont");}
        return null;
    }

    public static boolean existaUser(String user){
        return MyTrashql.getCont(user) != null;
    }

    public static boolean existaEmail(String email){
        return MyTrashql.getCont(email) != null;
    }

    public static boolean Login (String user, String pass) throws UserPassGresit{
        Cont cont = MyTrashql.getCont(user);
        if(cont!=null){
            String parolaReala = cont.getParola();
            return pass.equals(parolaReala); // if(pass.equals(parolaReala)) == true -> return ture     else -> retrun false;
        } else throw new UserPassGresit();
    }

    public static int getIdUser(String user) throws UserPassGresit {
        Cont cont = MyTrashql.getCont(user);
        if(cont!=null){
            return cont.getId();
        } else throw new UserPassGresit();
    }


// ------------- COMANDA -----------------------------------------------------------------------------------------------
    public static int getIdComanda(int idUser){
        try {
            Connection connection = MyTrashql.getConnection();
            String query = "SELECT * FROM comanda WHERE id_user = ? AND status = 0";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, idUser);
            //-------------------------------------------------------------------
            ResultSet result = pstm.executeQuery();
            if(result.next()){
                return result.getInt("id");
            } else return 0;
        } catch (SQLException e) {System.out.println(e.getMessage() + " .getIdComanda");}
        return 0;
    }
    public static boolean existaComanda(int idUser) {
        try {
            Connection connection = MyTrashql.getConnection();
            String query = "SELECT * FROM comanda WHERE id_user = ?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, idUser);
            //-------------------------------------------------------------------
            ResultSet result = pstm.executeQuery();
            while(result.next()) {
                if(result.getInt("status") == 0)
                    return true;
            }
            return false;
        } catch (SQLException e) {System.out.println(e.getMessage() + " .existaComanda");}
        return false;
    }

    public static void adaugaComanda(int idUser) {
        try {
            Connection connection = MyTrashql.getConnection();
            String query = "INSERT INTO comanda (`id_user`, `status`) VALUES (?, '0');";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, idUser);
            //-------------------------------------------------------------------
            pstm.execute();

        } catch (SQLException e) {System.out.println(e.getMessage() + " .adaugaComanda");}
        // nu ma prind cum sa fac sa bag date dar am gasit cv pe net care pare sa ajute
            // nu a ajutat asa ca o fac null :3
                // adresa se introduce cand da comanda, nu acum :3
    }


    public static void modificaComandaADD(int idComanda, int idProd, int cant) {
        try {
            Connection connection = MyTrashql.getConnection();
            String query = "select * from produs_comanda WHERE (id_comanda = ? AND id_produs = ?)";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, idComanda);
            pstm.setInt(2, idProd);
            //-------------------------------------------------------------------
            ResultSet rs = pstm.executeQuery();
            if(rs.next()) throw new ProdusDejaInCos();
            else {
                String queryTemp = "select * from produs WHERE id = ?";
                PreparedStatement pstmTemp = connection.prepareStatement(queryTemp);
                pstmTemp.setInt(1, idProd);
                //-------------------------------------------------------------------
                ResultSet rsTemp = pstmTemp.executeQuery(); rsTemp.next();
                if (rsTemp.getInt(4) < cant) throw new ProdusOutOfStock();
            }

            query = "INSERT INTO produs_comanda (id_comanda, id_produs, cantitate) VALUES (?, ?, ?)";
            pstm = connection.prepareStatement(query);
            pstm.setInt(1, idComanda);
            pstm.setInt(2, idProd);
            pstm.setInt(3, cant);
            //-------------------------------------------------------------------
            pstm.execute();

        }
        catch (SQLException | ProdusDejaInCos | ProdusOutOfStock e) {System.out.println(e.getMessage() + " .modificaComandaADD");}
    }

    public static void modificaComandaDEL(int idComanda, int idProd) {
        try {
            Connection connection = MyTrashql.getConnection();
            String query = "select * from produs_comanda WHERE (id_comanda = ? AND id_produs = ?)";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, idComanda);
            pstm.setInt(2, idProd);
            //-------------------------------------------------------------------
            ResultSet rs = pstm.executeQuery();
            if(!rs.next()) throw new ProdusNuEInCos();

            query = "DELETE FROM produs_comanda WHERE (id_comanda = ? AND id_produs = ?);";
            pstm = connection.prepareStatement(query);
            pstm.setInt(1, idComanda);
            pstm.setInt(2, idProd);
            //-------------------------------------------------------------------
            pstm.execute();

        } catch (SQLException | ProdusNuEInCos e) {System.out.println(e.getMessage() + " .modificaComandaDEL");}
    }

    public static void modificaComandaCANT(int idComanda, int idProd, int cant) {
        try {
            Connection connection = MyTrashql.getConnection();
            String query = "select * from produs_comanda WHERE (id_comanda = ? AND id_produs = ?)";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, idComanda);
            pstm.setInt(2, idProd);
            //-------------------------------------------------------------------
            ResultSet rs = pstm.executeQuery();
            if(!rs.next()) throw new ProdusNuEInCos();
            else {
                String queryTemp = "select * from produs WHERE id = ?";
                PreparedStatement pstmTemp = connection.prepareStatement(queryTemp);
                pstmTemp.setInt(1, idProd);
                //-------------------------------------------------------------------
                ResultSet rsTemp = pstm.executeQuery(); rsTemp.next();
                if (rsTemp.getInt("stoc") < cant) throw new ProdusOutOfStock();
            }

            query = "UPDATE produs_comanda SET cantitate = ? WHERE (id_comanda = ? AND id_produs = ?)";
             pstm = connection.prepareStatement(query);
            pstm.setInt(2, idComanda);
            pstm.setInt(3, idProd);
            pstm.setInt(1, cant);
            //-------------------------------------------------------------------
            pstm.execute();

        } catch (SQLException | ProdusOutOfStock | ProdusNuEInCos e) {System.out.println(e.getMessage() + " .modificaComandaCANT");}
    }

    public static void veziCom(int idComanda) {
        try {
            Connection con = MyTrashql.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM produs_comanda WHERE id_comanda = ?");
            pstm.setInt(1, idComanda);
            ResultSet rs = pstm.executeQuery();

            int cnt = 0;
            System.out.println();
            System.out.println("---- Coș ----");
            String queryTemp = "SELECT * FROM produs WHERE id = ?";
            PreparedStatement pstmTemp = con.prepareStatement(queryTemp);
            while(rs.next()) {
                cnt++;
                pstmTemp.setInt(1, rs.getInt("id_produs"));
                ResultSet rsTemp = pstmTemp.executeQuery();
                rsTemp.next();
                System.out.println(rsTemp.getInt("id") + " " + rsTemp.getString("nume") + " " +
                        rsTemp.getDouble("pret") + " " + rs.getInt("cantitate"));
            }
            if(cnt==0) System.out.println("COSUL ESTE GOL");

        } catch (SQLException e) {e.printStackTrace();}
    }

    public static void finalizareCom(int idAdresa, int idUser, int idComanda){
        try {
            if (MyTrashql.verifAdresa(idUser, idAdresa)) {
                // --------------- SCHIMBARE STATUIS ---------------
                Connection connection = MyTrashql.getConnection();
                String query = "UPDATE comanda SET id_adresa = ?, status = ? WHERE (id = ?)";
                PreparedStatement pstm = connection.prepareStatement(query);
                pstm.setInt(1, idAdresa);
                pstm.setInt(2, 1);
                pstm.setInt(3,idComanda);
                //-------------------------------------------------------------------
                pstm.execute();

                // --------------- UPDATARE STOC ---------------
                connection = MyTrashql.getConnection();
                pstm = connection.prepareStatement("SELECT * FROM produs_comanda WHERE id_comanda = ?");
                pstm.setInt(1, idComanda);
                ResultSet rs = pstm.executeQuery();

                query = "UPDATE produs SET stoc = ? WHERE (id = ?)";
                String queryTemp = "SELECT * FROM produs WHERE id = ?";
                PreparedStatement pstmTemp = connection.prepareStatement(queryTemp);
                while(rs.next()) {
                    pstmTemp.setInt(1, rs.getInt("id_produs")); //cauta produs
                    ResultSet rsTemp = pstmTemp.executeQuery(); rsTemp.next();             //salveaza produs
                    int cant =  rsTemp.getInt("stoc") - rs.getInt("cantitate");

                    pstm = connection.prepareStatement(query);
                    pstm.setInt(1, cant);
                    pstm.setInt(2, rs.getInt("id_produs"));
                    //-------------------------------------------------------------------
                    pstm.execute();
                }
            }
        } catch (SQLException e) {System.out.println(e.getMessage() + " .finalizareCom");}
    }

// ------------- PRODUSE -----------------------------------------------------------------------------------------------
    public static void veziProd() {
        try {
            PreparedStatement ps = MyTrashql.getConnection().prepareStatement("select * from produs");
            ResultSet rs=ps.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("nume") + " " +
                        rs.getInt("pret") + " " + rs.getInt("stoc") + " " + rs.getString("categorie"));

            }
        } catch (SQLException e) {System.out.println(e.getMessage() + " .veziProd");}
    }


// ------------- ADRESE ------------------------------------------------------------------------------------------------
    public static void veziAdrese(int idUser) {
        try {
            Connection con = MyTrashql.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM adresa_user WHERE id_user = ?");
            pstm.setInt(1, idUser);
            ResultSet rs = pstm.executeQuery();

            int cnt = 0;
            System.out.println();
            System.out.println("--- Adrese ---");
            String queryTemp = "SELECT * FROM adresa WHERE id = ?";
            PreparedStatement pstmTemp = con.prepareStatement(queryTemp);
            while(rs.next()) {
                cnt++;
                pstmTemp.setInt(1, rs.getInt("id_adresa"));
                ResultSet rsTemp = pstmTemp.executeQuery();
                rsTemp.next();
                System.out.println(rsTemp.getInt("id") + " " + rsTemp.getString("strada") + " " +
                        rsTemp.getInt("numar"));
            }
            if(cnt==0) System.out.println("Conul nu are adrese asociate");

        } catch (SQLException e) {e.printStackTrace();}
    }

    public static void modificaAdreseADD(int idUser, String strada, int numar) {
        try {

        // verific daca exista deja adresa
            Connection connection = MyTrashql.getConnection();
            String query = "SELECT * FROM adresa WHERE (strada = ? AND numar = ?)";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, strada);
            pstm.setInt(2, numar);
            //-------------------------------------------------------------------
            ResultSet rs = pstm.executeQuery();
            if(!rs.next()) {
                query = "INSERT INTO adresa (strada, numar) VALUES (?, ?)";
                pstm = connection.prepareStatement(query);
                pstm.setString(1, strada);
                pstm.setInt(2, numar);
                //-------------------------------------------------------------------
                pstm.execute();
            }

        // verific care e id-ul adresei
            query = "SELECT * FROM adresa WHERE (strada = ? AND numar = ?)";
            pstm = connection.prepareStatement(query);
            pstm.setString(1, strada);
            pstm.setInt(2, numar);
            //-------------------------------------------------------------------
            rs = pstm.executeQuery(); rs.next();

        //verific daca exista deja adresa in contul lui. daca nu o pun.
            int idadr = rs.getInt("id");
            query = "SELECT * FROM adresa_user WHERE (id_adresa = ? AND id_user = ?)";
            pstm = connection.prepareStatement(query);
            pstm.setInt(1, idadr);
            pstm.setInt(2, idUser);
            //-------------------------------------------------------------------
            rs = pstm.executeQuery();
            if(!rs.next()){
                query = "INSERT INTO adresa_user (id_adresa, id_user) VALUES (?, ?)";
                pstm = connection.prepareStatement(query);
                pstm.setInt(1, idadr);
                pstm.setInt(2, idUser);
                //-------------------------------------------------------------------
                pstm.execute();
            } else System.out.println("Adresa deja introdusa in cont");

        }
        catch (SQLException e) {e.printStackTrace(); System.out.println(" .modificaAdreseADD");}

    }

    public static boolean verifAdresa(int idUser, int idAdresa) {
        try {
            Connection connection = MyTrashql.getConnection();
            String query = "SELECT * FROM adresa_user WHERE (id_adresa = ? AND id_user = ?)";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, idAdresa);
            pstm.setInt(2, idUser);
            //-------------------------------------------------------------------
            ResultSet rs = pstm.executeQuery();
            return rs.next();
        } catch (SQLException e) {System.out.println(e.getMessage());}
        return false;
    }

    public static void modifStoc(int id_prod, int cant) {
        try {
            Connection connection = AdminTrashQL.getConnection();
            String query = "select * from produs WHERE (id = ?)";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, id_prod);
            //-------------------------------------------------------------------
            ResultSet rs = pstm.executeQuery();
            rs.next();
            cant = cant + rs.getInt("stoc");

            query = "UPDATE produs SET stoc = ? WHERE (id = ?)";
            pstm = connection.prepareStatement(query);
            pstm.setInt(1, cant);
            pstm.setInt(2, id_prod);
            //-------------------------------------------------------------------
            pstm.execute();

        } catch (SQLException e) {System.out.println(e.getMessage() + " .modifCant");}
    }
}