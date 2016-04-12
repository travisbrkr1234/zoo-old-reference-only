import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos.ochoa on 4/1/2016.
 */
public class AnimalDataBase {
    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;

    static public void connectToDatabase() {

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:animals.db");
//            System.out.println("Connected to database successfully");

            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static boolean checkForAnimalTable(){
        boolean tableExists = true;
        connectToDatabase();

        String sql = "SELECT * FROM sqlite_master WHERE name ='ANIMAL' and type='table'";
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                tableExists = true;
            } else {
                tableExists = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableExists;
    }

    public static boolean checkForAnimals() {
        boolean checkResult = false;

        try {
            connectToDatabase();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM ANIMAL;");
            if (resultSet.next()) {
                checkResult = true;
            } else {
                checkResult = false;
            }
        } catch (Exception e) {
            System.err.println("Unable to check for animals" + e.getClass().getName() + ": " + e.getMessage());
        }
        closeConnectionToDatabase();
        return checkResult;
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

    private static void closeConnectionToDatabase() {
        try {
            connection.close();
        } catch (SQLException e1) {
            System.out.println("Unable to close connection to database");
        }
    }

    static public void initializeDataBase() {
        if (!checkForAnimalTable()) {
            final String sql = "CREATE TABLE ANIMAL " +
                    "(NUMBER INTEGER PRIMARY KEY    AUTOINCREMENT     NOT NULL," +
                    " NAME      TEXT    NOT NULL, " +
                    " TYPE      CHAR(50)     NOT NULL, " +
                    " HEALTH        CHAR(50), " +
                    " AGE       INT(11), " +
                    " ENCLOSURE     CHAR(50), " +
                    " ON_LOAN       BOOLEAN, " +
                    " LOAN_LOCATION     CHAR(50))";

            try {
                connectToDatabase();
                executeStatementAndClose(sql);
            } catch (Exception e) {
                System.out.println(e);
                closeConnectionToDatabase();
            }
            System.out.println("Table created successfully");
        } else {
            System.out.println("Table already exists");
        }
    }

    public static void saveAnimalToDB(Animal newAnimal) {

        String sql = "INSERT INTO ANIMAL (NAME, TYPE, HEALTH, AGE, ENCLOSURE, ON_LOAN, LOAN_LOCATION) " +
                "VALUES (' " +
                newAnimal.getName() + "', '" +
                newAnimal.getType() + "', '" +
                newAnimal.getHealth() + "', '" +
                newAnimal.getAge() + "', '" +
                newAnimal.getEnclosure() + "', '" +
                newAnimal.isOnLoan() + "', '" + newAnimal.getLoanLocation() + "' ); ";

        try {
            connectToDatabase();
            executeStatementAndClose(sql);
        } catch (Exception e) {
            System.err.println("Unable to save animals" + e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records created successfully");
    }

    private static List<Animal> convertResultSetToAnimals(ResultSet resultSet) {
        List<Animal> animalsList = new ArrayList<>();
        //Create empty list of animals
        //take in resultSet
        //Create animal object to add results from resultSet to
        //While next results add animal to list

        try {
            while (resultSet.next()) {
                Animal newAnimal = new Animal();
                newAnimal.setAnimalNumber(resultSet.getInt("NUMBER"));
                newAnimal.setName(resultSet.getString("NAME"));
                newAnimal.setHealth(AnimalHealthStatus.valueOf(resultSet.getString("HEALTH")));
                newAnimal.setAge(resultSet.getInt("AGE"));
                newAnimal.setEnclosure(resultSet.getString("enclosure"));
                newAnimal.setOnLoan(resultSet.getBoolean("ON_LOAN"));
                newAnimal.setLoanLocation(resultSet.getString("LOAN_LOCATION"));

                animalsList.add(newAnimal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animalsList;
    }

    public static List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();

        String sql = "SELECT * FROM ANIMAL;";

        try {
            connectToDatabase();

            ResultSet resultSet = statement.executeQuery(sql);

            animals.addAll(convertResultSetToAnimals(resultSet));

        } catch (Exception e) {
            System.err.println("There was a problem showing animals" + e.getClass().getName() + ": " + e.getMessage());
        }
        return animals;
    }

    public static void removeAnimal(int number) {
        connectToDatabase();

        String sql = "DELETE FROM ANIMAL WHERE NUMBER= ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,number);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Animal with id of " + number + " has been deleted");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        executeStatementAndClose(sql);

    }

    public static List<Animal> searchByAnimalType(String type) {

        List<Animal> animalsList = new ArrayList<>();

        connectToDatabase();

        String sql = "SELECT * FROM ANIMAL WHERE TYPE= ?";

        try {
            //prepare statement
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, type);

            //run prepared statement
            //get resultSet
            ResultSet resultSet = preparedStatement.executeQuery();

            //Convert records to animal
           animalsList.addAll(convertResultSetToAnimals(resultSet));
            //add animal to list of animals
            //close statement
            //return list of animals

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animalsList;
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
                String type = resultSet.getString("TYPE");
                String health = resultSet.getString("HEALTH");
                int age = resultSet.getInt("AGE");
                String enclosure = resultSet.getString("enclosure");
                boolean onLoan = resultSet.getBoolean("ON_LOAN");
                String loanLocation = resultSet.getString("LOAN_LOCATION");

                System.out.println(" ");
                System.out.println("ID = " + animalNumber);
                System.out.println("Name = " + name);
                System.out.println("Type = " + type);
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
        }
    }
}