import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by carlos.ochoa on 4/1/2016.
 */
public class AnimalDataBase {
    static public void initializeDataBase() {
        {
            /**
             * Create database and animal table
             */

            Connection conn = null;
            Statement statement = null;
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:animals.db");
                System.out.println("Opened database successfully");

                statement = conn.createStatement();
                String sql = "CREATE TABLE ANIMAL " +
                        "(NUMBER INT PRIMARY KEY     NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " TYPE            CHAR(50)     NOT NULL, " +
                        " HEALTH        CHAR(50), " +
                        " AGE        INT(11), " +
                        " ENCLOSURE        CHAR(50), " +
                        " ON_LOAN        BOOLEAN, " +
                        " LOAN_LOCATION         CHAR(50))";
                statement.executeUpdate(sql);
                statement.close();
                conn.close();
            } catch (Exception e) {
//                System.err.println(e.getClass().getName() + ": " + e.getMessage());
//                System.exit(0);
                System.out.println("Database already exists");
            }
            System.out.println("Table created successfully");
        }
    }

    static public void insertSeedData() {
        /**
         * Insert a default animal into the animals database
         */
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:animals.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO ANIMAL (NUMBER, NAME, TYPE, HEALTH, AGE, ENCLOSURE, ON_LOAN, LOAN_LOCATION) " +
                    "VALUES (99999999, 'SeedAnimal', 'Seedtest-type', 'GOOD_HEALTH', 33, 'pen', 'true', 'SeedZoo' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Database already seeded");
        }
        System.out.println("Records created successfully");
    }

    static public void selectAllAnimals() {
        /**
         * Select all animals from animal database
         */
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:animals.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM ANIMAL;");
            while (resultSet.next()) {
                int animalNumber = resultSet.getInt("NUMBER");
                String name = resultSet.getString("NAME");
                String health = resultSet.getString("HEALTH");
                int age = resultSet.getInt("AGE");
                String enclosure = resultSet.getString("enclosure");
                boolean onLoan = resultSet.getBoolean("ON_LOAN");
                String loanLocation = resultSet.getString("LOAN_LOCATION");

                System.out.println(" ");
                System.out.println("ID = " + animalNumber);
                System.out.println("Name = " + name);
                System.out.println("Health = " + health);
                System.out.println("Age = " + age);
                System.out.println("Enclosure = " + enclosure);
                System.out.println("On Loan = " + onLoan);
                System.out.println("Loan Location = " + loanLocation);
                System.out.println(" ");
            }
            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println("Unable to select animals" + e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
        }
    }

    public static void saveAnimalToDB(Animal newAnimal) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:animals.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            int number = newAnimal.getAnimalNumber();
            String name = newAnimal.getName();
            String type = newAnimal.getType();
            AnimalHealthStatus health = newAnimal.getHealth();
            int age = newAnimal.getAge();
            String enclosure = newAnimal.getEnclosure();
            boolean onLoan = newAnimal.isOnLoan();
            String loanLocation = newAnimal.getLoanLocation();

            String sql = "INSERT INTO ANIMAL (NUMBER, NAME, TYPE, HEALTH, AGE, ENCLOSURE, ON_LOAN, LOAN_LOCATION) " +
                    "VALUES ('"+number+"', '"+name+"', '"+type+"', '"+health+"', '"+age+"', '"+enclosure+"', '"+onLoan+"', '"+loanLocation+"');";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println("Unable to save animals" + e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public static void showAnimalsInDB() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:animals.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM ANIMAL;");
            while (resultSet.next()) {
                int animalNumber = resultSet.getInt("NUMBER");
                String name = resultSet.getString("NAME");
                String health = resultSet.getString("HEALTH");
                int age = resultSet.getInt("AGE");
                String enclosure = resultSet.getString("enclosure");
                boolean onLoan = resultSet.getBoolean("ON_LOAN");
                String loanLocation = resultSet.getString("LOAN_LOCATION");

                System.out.println("ID = " + animalNumber);
                System.out.println("Name = " + name);
                System.out.println("Health = " + health);
                System.out.println("Age = " + age);
                System.out.println("Enclosure = " + enclosure);
                System.out.println("On Loan = " + onLoan);
                System.out.println("Loan Location = " + loanLocation);
            }
            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println("There was a problem showing animals" +e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
        }

    }

    public static boolean checkForAnimals() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:animals.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM ANIMAL;");
            while (resultSet.next()) {
                int animalNumber = resultSet.getInt("NUMBER");
                String name = resultSet.getString("NAME");
                String health = resultSet.getString("HEALTH");
                int age = resultSet.getInt("AGE");
                String enclosure = resultSet.getString("enclosure");
                boolean onLoan = resultSet.getBoolean("ON_LOAN");
                String loanLocation = resultSet.getString("LOAN_LOCATION");

                System.out.println(" ");
                System.out.println("ID = " + animalNumber);
                System.out.println("Name = " + name);
                System.out.println("Health = " + health);
                System.out.println("Age = " + age);
                System.out.println("Enclosure = " + enclosure);
                System.out.println("On Loan = " + onLoan);
                System.out.println("Loan Location = " + loanLocation);
                System.out.println(" ");
            }
            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println("Unable to check for animals" + e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
        }

        return true;
    }
}
