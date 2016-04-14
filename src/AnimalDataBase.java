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
                    "(Number INTEGER PRIMARY KEY    AUTOINCREMENT     NOT NULL," +
                    " Name      TEXT    NOT NULL, " +
                    " Owner_name    CHAR(50)    NOT NULL, " +
                    " Type      CHAR(50)     NOT NULL, " +
                    " Gender      CHAR(50)     NOT NULL, " +
                    " Health        CHAR(50), " +
                    " Age       INT(11), " +
                    " Enclosure     CHAR(50), " +
                    " On_loan       BOOLEAN, " +
                    " Loan_location     CHAR(50))";

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

        String sql = "INSERT INTO ANIMAL (Name, Owner_name, Type, Gender, Health, Age, Enclosure, On_loan, Loan_location) " +
                "VALUES (' " +
                newAnimal.getName() + "', '" +
                newAnimal.getOwnerName() + "', '" +
                newAnimal.getType() + "', '" +
                newAnimal.getGender() + "', '" +
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
                newAnimal.setAnimalNumber(resultSet.getInt("Number"));
                newAnimal.setName(resultSet.getString("Name"));
                newAnimal.setOwnerName(resultSet.getString("Owner_name"));
                newAnimal.setType(resultSet.getString("Type"));
                newAnimal.setGender(resultSet.getString("Gender"));
                newAnimal.setHealth(AnimalHealthStatus.valueOf(resultSet.getString("Health")));
                newAnimal.setAge(resultSet.getInt("Age"));
                newAnimal.setEnclosure(resultSet.getString("Enclosure"));
                newAnimal.setOnLoan(Boolean.parseBoolean(resultSet.getString("On_loan")));
                newAnimal.setLoanLocation(resultSet.getString("Loan_location"));

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
            System.err.println("There was a problem getting the list of animals" + e.getClass().getName() + ": " + e.getMessage());
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

    public static List<Animal> searchByAnimalName(String name) {

        List<Animal> animalsList = new ArrayList<>();

        connectToDatabase();

        String sql = "SELECT * FROM ANIMAL WHERE Name= ?";

        try {
            //prepare statement
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            animalsList.addAll(convertResultSetToAnimals(resultSet));

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
                int animalNumber = resultSet.getInt("Number");
                String name = resultSet.getString("Name");
                String ownerName = resultSet.getString("Owner_name");
                String type = resultSet.getString("Type");
                String health = resultSet.getString("Health");
                int age = resultSet.getInt("Age");
                String enclosure = resultSet.getString("Enclosure");
                boolean onLoan = resultSet.getBoolean("On_loan");
                String loanLocation = resultSet.getString("Loan_location");

                System.out.println(" ");
                System.out.println("ID = " + animalNumber);
                System.out.println("Name = " + name);
                System.out.println("Owner name = " + ownerName);
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