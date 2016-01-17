import com.sun.org.apache.xpath.internal.SourceTree;

import java.sql.*;
import java.util.Scanner;


public class SQLonnector {

    java.sql.Connection con;
    Statement st;
    ResultSet rs;

    public static final int ID = 1;
    public static final int NAME = 2;
    public static final int EMAIL = 3;
    public static final int PASS = 4;

    public final static String[] raws = {"id","NAME","EMAIL","PASS","DATER"};

    String selectAll = "select count(*) from userBase";
    String selectData = "select id, name, email, pass from userBase";

    SQLonnector() {
        String url = "jdbc:mysql://localhost:3306/neverUsers";

        /*System.out.print("Input db root pass: ");
        String pass = new Scanner(System.in).nextLine();
        System.out.println();*/

        con = null;
        st = null;
        rs = null;


        try {
            con = DriverManager.getConnection(url, "root", "dbPASS42");

            st = con.createStatement();

            rs = st.executeQuery(selectAll);

            while (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("There are " + count + " rows in table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } /*finally {
            try{ con.close(); } catch(SQLException e) {}
            try{ st.close(); } catch(SQLException e) {}
            try{ rs.close(); } catch(SQLException e) {}
        }*/
    }

    public int cheqUser(String login,String pass){
        boolean found = false;
        try{
            rs = st.executeQuery(selectData);

            while(rs.next()){
                String dbLog, dbEmail, dbPass;
                int dbID = 0;
                dbID = rs.getInt(raws[0]);
                dbLog = rs.getString(raws[1]);
                dbEmail = rs.getString(raws[2]);
                dbPass = rs.getString(raws[3]);
                System.out.printf("Got from db: %d, %s, %s, %s \n",dbID,dbLog,dbEmail,dbPass);
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
