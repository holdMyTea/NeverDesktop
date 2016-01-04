import java.sql.*;
import java.util.Scanner;


public class SQLonnector {


    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/neverUsers";

        System.out.print("Input db root pass: ");
        String pass = new Scanner(System.in).nextLine();
        System.out.println();

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String query = "select count(*) from userBase";

        try {
            con = DriverManager.getConnection(url, "root", "dbPASS42");

            st = con.createStatement();

            rs = st.executeQuery(query);

            while (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("There are " + count + " rows in table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{ con.close(); } catch(SQLException e) {}
            try{ st.close(); } catch(SQLException e) {}
            try{ rs.close(); } catch(SQLException e) {}
        }
    }
}
