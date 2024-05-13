package Admin;
import java.sql.*;
public class AdminTrashQL {

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/magazin",
                    "root",
                    "root"
            );
        } catch (SQLException e) {System.out.println(e.getMessage());;}
        return null;
    }

//             -- LOGIN --
    public static AdminCont getCont(String user){
        try {
            Connection connection = AdminTrashQL.getConnection();
            String query = "SELECT * FROM admin WHERE username = ?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, user);
            ResultSet result = pstm.executeQuery();
            if(result.next()){
                return new AdminCont(result.getString("username"),
                        result.getString("password"),
                        result.getString("email"),
                        result.getInt("id"));
            } else return null;
        } catch (SQLException e) {e.printStackTrace(); System.out.println(".ADMgetCont");}
        return null;
    }

//             -- PRODUSE --
    public static void veziProduse() {
        try {
            String query = "select * from produs";
            PreparedStatement ps = AdminTrashQL.getConnection().prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("nume") + " " +
                        rs.getInt("pret") + " " + rs.getInt("stoc") + " " + rs.getString("categorie"));

            }
        } catch (SQLException e) {System.out.println(e.getMessage() + " .ADMveziProduse");}
    }

    public static void modificaProduseADD(String nume, double pret, int stoc, String categorie){
        try {
            Connection connection = AdminTrashQL.getConnection();
            String query = "INSERT INTO produs (nume, pret, stoc, categorie) VALUES (?, ?, ?, ?)";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, nume);
            pstm.setDouble(2, pret);
            pstm.setInt(3, stoc);
            pstm.setString(4, categorie);
            pstm.execute();

        }
        catch (SQLException e) {e.printStackTrace(); System.out.println(" .ADMmodificaProduseADD");}

    }

    public static void modificaProduseDEL(int id){
        try {
            Connection connection = AdminTrashQL.getConnection();
            String query = "DELETE FROM produs WHERE id = ?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, id);
            //-------------------------------------------------------------------
            pstm.execute();

        } catch (SQLException e) {e.printStackTrace(); System.out.println(" .ADMmodificaProduseDEL");}
    }

    public static void modifStoc(int id, int cant){
        try {
            Connection connection = AdminTrashQL.getConnection();
            String query = "select * from produs WHERE (id = ?)";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, id);
            //-------------------------------------------------------------------
            ResultSet rs = pstm.executeQuery();
            rs.next();
            cant = cant + rs.getInt("stoc");

            query = "UPDATE produs SET stoc = ? WHERE (id = ?)";
            pstm = connection.prepareStatement(query);
            pstm.setInt(1, cant);
            pstm.setInt(2, id);
            //-------------------------------------------------------------------
            pstm.execute();

        } catch (SQLException e) {System.out.println(e.getMessage() + " .ADMmodifCant");}
    }

    public static void setStoc(int id, int cant){
        try {
            Connection connection = AdminTrashQL.getConnection();
            String query = "UPDATE produs SET stoc = ? WHERE (id = ?)";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, cant);
            pstm.setInt(2, id);
            //-------------------------------------------------------------------
            pstm.execute();

        } catch (SQLException e) {System.out.println(e.getMessage() + " .ADMmodifCant");}
    }

//             -- PRODUSE --

}
