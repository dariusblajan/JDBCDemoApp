import java.sql.*;
import java.util.Scanner;


/**
 * Created by condor on 26/02/15.
 * FastTrackIT, 2015
 * <p/>
 * DEMO ONLY PURPOSES, IT MIGHT CONTAINS INTENTIONALLY ERRORS OR ESPECIALLY BAD PRACTICES
 *
 * make sure you refactor it and remove lots of bad practices like loading the driver multiple times or
 * repeating the same common code multiple times
 *
 * BTW, exercise 1: how we reorg this/refactor in small pieces
 */
public class DemoCRUDOperations {

    public static void defineConn(){
        final String URL = "jdbc:postgresql://54.93.65.5:5432/dariussAgenda";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

    }

    public static void printMenu(){
        System.out.println("1.read");
        System.out.println("2.create");
        System.out.println("3.update");
        System.out.println("4.delete");
        System.out.println("0=exit");
    }

    public static void main(String[] args) {
        defineConn();
        System.out.println("Hello database users! We are going to call DB from Java");
        int option;
        Scanner read = new Scanner(System.in);
        try {
            do {
                printMenu();

                System.out.print("option: "); //citste optiunea
                option=read.nextInt();
                //demo CRUD operations
                switch (option){
                    case 1: {
                        demoRead();
                        break;
                    }
                    case 2: {
                        demoCreate();
                        break;
                    }
                    case 3: {
                        demoUpdate();
                        break;
                    }
                    case 4: {
                        demoDelete();
                        break;
                    }
                    case 0: {
                        break;
                    }

                }
                // demoBlobInsert();
                // demoBlobRead();
            } while(option!=0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void demoCreate() throws ClassNotFoundException, SQLException {

        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/dariussAgenda";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";
        Scanner read = new Scanner(System.in);
        String name;
        String phoneNo;
        System.out.print("name: ");
        name=read.nextLine();
        System.out.println("phone: ");
        phoneNo=read.nextLine();

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("INSERT INTO agenda (NAME, PHONE) VALUES (?,?)");
        pSt.setString(1, name);
        pSt.setString(2, phoneNo);
        // 5. execute a prepared statement
        int rowsInserted = pSt.executeUpdate();

        // 6. close the objects
        pSt.close();
        conn.close();
    }

    private static void demoRead() throws ClassNotFoundException, SQLException {
        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/dariussAgenda";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        Statement st = conn.createStatement();

        // 5. execute a query
        ResultSet rs = st.executeQuery("SELECT name,phone,id_agenda FROM agenda");

        // 6. iterate the result set and print the values
        while (rs.next()) {
            System.out.print(rs.getString("name").trim());
            System.out.print("---");
            System.out.print(rs.getString("phone").trim());
            System.out.print("---");
            System.out.println(rs.getLong("id_agenda"));
        }
        System.out.print("\n");
        // 7. close the objects
        rs.close();
        st.close();
        conn.close();
    }

    private static void demoUpdate() throws ClassNotFoundException, SQLException {

        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/dariussAgenda";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";
        String name;
        String phoneNo;
        Long key;
        Scanner read = new Scanner(System.in);
        System.out.print("name: ");
        name=read.nextLine();
        System.out.print("phone: ");
        phoneNo=read.nextLine();
        System.out.print("id to be updated: ");
        key=read.nextLong();

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("UPDATE agenda SET name=?, phone=? WHERE id_agenda=?"); //so we have 3 params
        pSt.setString(1, name);
        pSt.setString(2, phoneNo);
        pSt.setLong(3, key);

        // 5. execute a prepared statement
        int rowsUpdated = pSt.executeUpdate();

        // 6. close the objects
        pSt.close();
        conn.close();
    }


    private static void demoDelete() throws ClassNotFoundException, SQLException {

        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/dariussAgenda";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";
        Scanner read = new Scanner(System.in);
        String name ;
        System.out.print("name to be deleted: ");
        name=read.nextLine();

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("DELETE FROM agenda WHERE name=?");
        pSt.setString(1, name);

        // 5. execute a prepared statement
        int rowsDeleted = pSt.executeUpdate();
        System.out.println(rowsDeleted + " rows were deleted.");
        
        // 6. close the objects
        pSt.close();
        conn.close();
    }
}

