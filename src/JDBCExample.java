//STEP 1. Import required packages
import javax.xml.crypto.Data;
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
        String createCustomers = "CREATE TABLE Customers " +
                "(taxId int, " +
                "name varchar(255) NOT NULL, " +
                "address varchar(255) NOT NULL, " +
                "pin varchar(4) NOT NULL, " +
                "PRIMARY KEY(taxId))";
        ResultSet create = stmt.executeQuery(createCustomers);
        //String sql = "INSERT INTO Customers (taxId, name, address, pin) VALUES (001, 'Denise', 'SB', '1121')";
        //int result = stmt.executeUpdate(sql);
        //System.out.println("num rows edited: " + result);
    }

    public static void createAccountsTable(Statement stmt) throws SQLException{

        String sql = "CREATE TABLE Accounts (accountId int, primaryOwner varchar(255), bankBranchName varchar(255))";
        ResultSet result = stmt.executeQuery(sql);

        //String s1 = "DROP TABLE Accounts";
        //int result2 = stmt.executeUpdate(s1);
        //System.out.println("Insert items: " + result2);
    }

    public static void sampleQueries(Statement stmt) throws SQLException{
//        String sql4 = "DROP TABLE Accounts";
//        stmt.executeUpdate(sql4);
//        String sql5 = "DROP TABLE Customers";
//        stmt.executeUpdate(sql5);
        String sql2 = "INSERT INTO Accounts (accountId, primaryOwner, bankBranchName) VALUES (111, 'Richard', 'bofa')";
        String sql3 = "SELECT accountId, primaryOwner FROM Accounts";
        ResultSet result3 = stmt.executeQuery(sql3);
        while(result3.next()) {
            String aid = result3.getString("accountId");
            String owner = result3.getString("primaryOwner");
            System.out.print("accountId: " + aid);
            System.out.println(", owner: " + owner);
        }
        result3.close();

        String sql = "INSERT INTO Customers (taxId, name, address, pin) VALUES (001, 'Denise', 'SB', '1121')";
        int result = stmt.executeUpdate(sql);
        System.out.println("num rows edited: " + result);
    }

    public static void createTables() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "Customers", null);
            if(tables.next()) {
                //Customers table exists
                //sampleQueries(stmt);
                System.out.println("Table exists");
            } else {
                //createCustomersTable(stmt);
                System.out.println("Table does not exist");
            }

            tables = dbm.getTables(null, null, "Accounts", null);
            if(tables.next()) {
                //Accounts table exists
                //sampleQueries(stmt);
                System.out.println("Table exists");
            } else {
                //createAccountsTable(stmt);
                System.out.println("Table does not exist");
            }
            sampleQueries(stmt);


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