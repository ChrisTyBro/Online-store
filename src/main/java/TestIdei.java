import java.sql.*;

public class TestIdei {
/*
    // III - sa vad produsele

    public static void main(String[] args) {
        PreparedStatement ps= null;
        try {
            ps = MyTrashql.getConnection().prepareStatement("select * from produs");
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                System.out.println(rs.getInt("id") + " " + rs.getString("nume") + " " + rs.getString("pret") + " " +
                                    rs.getString("stoc") + " " + rs.getString("categorie"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
*/
    public static void main(String[] args) {
        int idComanda = 1;
        try {
            Connection con = MyTrashql.getConnection();
            String query = "SELECT * FROM produs_comanda WHERE id_comanda = ?";
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setInt(1, 1);
            ResultSet rs = pstm.executeQuery();

            System.out.println("---- Coș ----");
            String queryTemp = "SELECT * FROM produs WHERE id = ?";
            PreparedStatement pstmTemp = con.prepareStatement(queryTemp);
            while(rs.next()) {
                int idp = rs.getInt("id_produs");
                pstmTemp.setInt(1, idp);
                ResultSet rsTemp = pstmTemp.executeQuery();
                rsTemp.next();
                System.out.println(rsTemp.getInt("id") + " " + rsTemp.getString("nume") + " " +
                        rsTemp.getDouble("pret"));

            }

        } catch (SQLException e) {e.printStackTrace();}
    }
}
