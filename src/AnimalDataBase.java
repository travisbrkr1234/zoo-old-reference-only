import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos.ochoa on 4/1/2016.
 */
public class AnimalDataBase {
    private static Connection connection = null;
    private static Statement statement = null;

        static private void connectToDatabase() {

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:animals.db");
                System.out.println("Opened database successfully");

                statement = connection.createStatement();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        static private void closeConnectionToDatabase() {
            try {
                connection.close();
            } catch (SQLException e1) {
                System.out.println("Unable to close connection to database");
            }
        }

        private static void executeStatementAndClose(String sql) {
            try {
                statement.execute(sql);
                statement.close();
                closeConnectionToDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        static public void initializeDataBase() {
            {
                /**
                 * Create database and animal table
                 */
                final String sql = "CREATE TABLE ANIMAL " +
                        "(NUMBER INT PRIMARY KEY     NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " TYPE            CHAR(50)     NOT NULL, " +
                        " HEALTH        CHAR(50), " +
                        " AGE        INT(11), " +
                        " ENCLOSURE        CHAR(50), " +
                        " ON_LOAN        BOOLEAN, " +
                        " LOAN_LOCATION         CHAR(50))";
                try {
                    connectToDatabase();
                    executeStatementAndClose(sql);
                } catch (Exception e) {
                    System.out.println("Database already exists");
                    closeConnectionToDatabase();
                }
                System.out.println("Table created successfully");
            }
        }

        static public void insertSeedData() {
            /**
             * Insert a default animal into the animals database
             */

            String sql = "INSERT INTO ANIMAL (NUMBER, NAME, TYPE, HEALTH, AGE, ENCLOSURE, ON_LOAN, LOAN_LOCATION) " +
                    "VALUES (99999999, 'SeedAnimal', 'Seedtest-type', 'GOOD_HEALTH', 33, 'pen', 'true', 'SeedZoo' );";

            try {
                connectToDatabase();
                executeStatementAndClose(sql);
                closeConnectionToDatabase();
            } catch (Exception e) {
                System.out.println("Database already seeded");
            }
            System.out.println("Records created successfully");
        }

        static public void selectAllAnimals() {
            /**
             * Select all animals from animal database
             */
            Connection connection = null;
            Statement statement = null;
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:animals.db");
                connection.setAutoCommit(false);
                System.out.println("Opened database successfully");

                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM ANIMAL;");
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
                statement.close();
                connection.commit();
                connection.close();

            } catch (Exception e) {
                System.err.println("Unable to select animals" + e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
            }
        }

        public static void saveAnimalToDB(Animal newAnimal) {

            String sql = "INSERT INTO ANIMAL (NUMBER, NAME, TYPE, HEALTH, AGE, ENCLOSURE, ON_LOAN, LOAN_LOCATION) " +
                    "VALUES (' " +
                    newAnimal.getAnimalNumber() + " ',' " +
                    newAnimal.getName() + "', '" +
                    newAnimal.getType() + "', '" +
                    newAnimal.getHealth() + "', '" +
                    newAnimal.getAge() + "', '" +
                    newAnimal.getEnclosure() + "', '" +
                    newAnimal.isOnLoan() + "', '" +
                    newAnimal.getLoanLocation() + "');";

            try {
                connectToDatabase();
                executeStatementAndClose(sql);
            } catch (Exception e) {
                System.err.println("Unable to save animals" + e.getClass().getName() + ": " + e.getMessage());
            }
            System.out.println("Records created successfully");
        }

        public static void showAnimalsInDB() {
            List<Animal> animals = new ArrayList<>();
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
                    //Add animals to list
                    // Fix other methods to do just their job
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