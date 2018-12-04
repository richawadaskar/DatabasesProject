package ATM;

import java.io.*;
import java.sql.SQLException;

public class ReadExcelFile {
    public static void main(String[] args) throws SQLException 
    {
        try {

            BufferedReader file = new BufferedReader(new FileReader("/cs/student/cindylu/cs174A/DatabasesProject/users.csv"));
            
            String line = file.readLine();
            
            while(!line.equals("")) {
            	String[] lineParts = line.split(",");
            	for(int i = 0; i < lineParts.length; i++) {
            		System.out.print(lineParts[i] + ",");
            	}
            	System.out.println();
            	int ssn = Integer.parseInt(lineParts[0]);
            	int pin = Integer.parseInt(lineParts[3]);
            	ATMOptionUtility.addToCustomersTable(ssn, lineParts[1], lineParts[2], pin);
            	
            	line = file.readLine();
            }
            
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    


}
