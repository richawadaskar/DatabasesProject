//STEP 1. Import required packages
import java.sql.*;

public class JDBCExample {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@cloud-34-133.eci.ucsb.edu:1521:XE";

    //  Database credentials
    static final String USERNAME = System.getenv("USERNAME");
    static final String PASSWORD = System.getenv("PASSWORD");

    public static void main(String[] args) {
        createTables();
    }

    public static void createCustomersTable(Statement stmt) throws SQLException {
        String sql = "INSERT INTO Customers (taxId, name, address, pin) VALUES (001, 'Denise', 'SB', '1121')";
        int result = stmt.executeUpdate(sql);
        System.out.println("num rows edited: " + result);
    }

    public static void createAccountsTable(Statement stmt) throws SQLException{
        //String s1 = "DROP TABLE Accounts";
        //String sql = "CREATE TABLE IF NOT EXISTS 'Accounts' (accountId int, primaryOwner varchar(255), bankBranchName varchar(255))";
        //String sql2 = "INSERT INTO Accounts (accountId, primaryOwner, bankBranchName) VALUES (111, 'Richard', 'bofa')";
        String sql3 = "SELECT accountId, primaryOwner FROM Accounts";
        //ResultSet result = stmt.executeQuery(sql);
        //int result2 = stmt.executeUpdate(s1);
        //System.out.println("Insert items: " + result2);

        ResultSet result3 = stmt.executeQuery(sql3);
        while(result3.next()) {
            String aid = result3.getString("accountId");
            String owner = result3.getString("primaryOwner");
            System.out.print("accountId: " + aid);
            System.out.println(", owner: " + owner);
        }
        result3.close();
    }

    public static void createTables() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();

            createCustomersTable(stmt);
            createAccountsTable(stmt);

        } catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}