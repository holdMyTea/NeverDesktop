import java.sql.*;
import java.util.Scanner;


public class SQLonnector {

    java.sql.Connection con;
    Statement st;
    ResultSet rs;

    String querySelection = "select count(*) from userBase";

    SQLonnector() {
        String url = "jdbc:mysql://localhost:3306/neverUsers";

        System.out.print("Input db root pass: ");
        String pass = new Scanner(System.in).nextLine();
        System.out.println();

        con = null;
        st = null;
        rs = null;



        try {
            con = DriverManager.getConnection(url, "root", "dbPASS42");

            st = con.createStatement();

            rs = st.executeQuery(querySelection);

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

    public int cheqUser(String login,String pass){
        boolean found = false;
        try{
            rs = st.executeQuery(querySelection);

            while(rs.next()){
                String dbLog, dbEmail, dbPass;
                int dbID = 0;
                dbID = rs.getInt("_id");
                dbLog = rs.getString("name");
                dbEmail = rs.getString("email");
                dbPass = rs.getString("password");
                if((login.equals(dbLog) || login.equals(dbEmail))&&(pass.equals(dbPass))){
                    return dbID;
                }
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
