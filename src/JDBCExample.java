//STEP 1. Import required packages
import javax.xml.crypto.Data;
import java.sql.*;

public class JDBCExample {
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@cloud-34-133.eci.ucsb.edu:1521:XE";
    static final String USERNAME = System.getenv("USERNAME");
    static final String PASSWORD = System.getenv("PASSWORD");

    public static void main(String[] args) {
        createTables();
        // hello 
    }

    public static void createCustomersTable(Statement stmt) throws SQLException {
        String createCustomers = String.format("CREATE TABLE %sCustomers " +
                "(taxId int, " +
                "name varchar(255) NOT NULL, " +
                "address varchar(255) NOT NULL, " +
                "pin varchar(4) NOT NULL, " +
                "PRIMARY KEY(taxId))", USERNAME);
        System.out.println(createCustomers);
        ResultSet create = stmt.executeQuery(createCustomers);
        //String sql = "INSERT INTO Customers (taxId, name, address, pin) VALUES (001, 'Denise', 'SB', '1121')";
        //int result = stmt.executeUpdate(sql);
        //System.out.println("num rows edited: " + result);
    }

    public static void createAccountsTable(Statement stmt) throws SQLException{

        String sql = String.format("CREATE TABLE %sAccounts " +
                "(accountId int, " +
                "primaryOwner varchar(255), " +
                "bankBranchName varchar(255))", USERNAME);
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


            // USE THIS TO OUTPUT ALL EXISTING TABLES IN DATABASE
            String sql2 = "SELECT table_name FROM user_tables";
            ResultSet tables1 = stmt.executeQuery(sql2);
            while(tables1.next()){
                System.out.println(tables1.getString(1));
            }

            String customersTableName = String.format("%sCUSTOMERS", USERNAME);
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables("user_tables", null, customersTableName.toUpperCase(), null);
            if(tables.next()) {
                //Customers table exists
                //sampleQueries(stmt);
                System.out.println("Table exists");
            } else {
                createCustomersTable(stmt);
                System.out.println("Table does not exist");
            }

            String accountsTableName = String.format("%sACCOUNTS", USERNAME);
            tables = dbm.getTables("user_tables", null, accountsTableName.toUpperCase(), null);
            if(tables.next()) {
                //Accounts table exists
                //sampleQueries(stmt);
                System.out.println("Table exists");
            } else {
                createAccountsTable(stmt);
                System.out.println("Table does not exist");
            }
            //sampleQueries(stmt);

            // USE THIS TO OUTPUT ALL EXISTING TABLES IN DATABASE
            tables1 = stmt.executeQuery(sql2);
            while(tables1.next()){
                System.out.println(tables1.getString(1));
            }


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