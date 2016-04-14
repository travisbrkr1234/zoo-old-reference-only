import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by carlos.ochoa on 2/1/2016.
 */

public class Main {

    final static StringBuilder zooMenu = new StringBuilder();

    public static void main(String[] args) {

        if (!AnimalDataBase.checkForAnimals()) {
            AnimalDataBase.initializeDataBase();
            System.out.println("initialized database");
        }

        zooMenu.append("-------------------------------------------------\n")
                .append("********000000*******0000*****0000***************\n")
                .append("***********//*******0****0***0****0**************\n")
                .append("**********000000*****0000*****0000***************\n")
                .append("-------------------------------------------------\n")
                .append("-------------Please enter a choice---------------\n")
                .append("1. Add animal------------------------------------\n")
                .append("2. List animals----------------------------------\n")
                .append("3. Remove animal---------------------------------\n")
                .append("4. Find animal-----------------------------------\n")
                .append("9. SelectAll Animals-----------------------------\n")
                .append("----Type e, exit, q, or quit to leave program----\n");

        boolean run = true;
        String option;

        while (run) {
            option = menu();

            switch (option) {
                case "1":
                    createAnimal();
                    break;

                case "2":
                    listAnimals();
                    break;

                case "3":
                    removeAnimal();
                    break;

                case "4":
                    searchAnimals();
                    break;

                case "9":
                    AnimalDataBase.selectAllAnimals();
                    break;

                case "e":
                case "exit":
                case "q":
                case "quit":
                    run = false;
                    break;

                default:
                    System.out.println("Please enter a valid option");
            }
        }
    }

    static String menu() {
        System.out.println(zooMenu.toString());

        Scanner menu = new Scanner(System.in);
        return menu.nextLine();
    }

    static boolean animalTypeValidation(String animalType) {
        final String regexPattern = "^[a-zA-z]+(-+[a-zA-Z]+)?$";
        final Pattern pattern = Pattern.compile(regexPattern);
        final Matcher matcher = pattern.matcher(animalType);
        return matcher.find();
    }

    static void createAnimal() {
        Animal newAnimal = new Animal();

        newAnimal.setName(setAName("animal"));
        newAnimal.setOwnerName(setAName("owner"));

        Scanner animalGenderInput = new Scanner(System.in);
        boolean stopLoop = false;
        while (!stopLoop) {
            System.out.println("Please enter gender, such as 'm' or 'f'");
            String animalGender = animalGenderInput.nextLine().toLowerCase();

            if (animalGender.equals("m")) {
                newAnimal.setGender("male");
                stopLoop = true;
            } if (animalGender.equals("f")) {
                newAnimal.setGender("female");
                stopLoop = true;
            }
        }

        Scanner animalTypeInput = new Scanner(System.in);

        boolean something = false;
        while(!something) {
            System.out.println("Please enter a type for this animal");
            String animalType = animalTypeInput.nextLine();

            if (animalTypeValidation(animalType)) {
                newAnimal.setType(animalType);
                something = true;
            } else {
                System.out.println("Please enter a valid type, such as\n hipa-potamus");
            }

        }

        boolean animalHealthSet = false;
        Scanner animalHealthInput = new Scanner(System.in);  //take user input

        while (!animalHealthSet) {
            System.out.println("Health status: ");
            System.out.println("1 - Good health\n" +
                    "2 - Mediocre health\n" +
                    "3 - Bad health\n" +
                    "4 - Pregnant");
            try {
                int statusCode = animalHealthInput.nextInt();

                if (AnimalHealthStatus.animalHealthStatusExists(statusCode)) {
                    newAnimal.setHealth(AnimalHealthStatus.getAnimalHealthStatusByStatusCode(statusCode));
                    animalHealthSet = true;
                } else {
                    System.out.println("Please enter a valid choice for health");
                }

            } catch (InputMismatchException e) {
                System.out.println("Please enter a health status code");
                animalHealthInput.nextLine();
            }
        }

        // do validation similar to new animal
        Scanner inputAnimalAge = new Scanner(System.in);
        String animalAgePattern = "^\\d+$";
        Pattern pattern = Pattern.compile(animalAgePattern);

        boolean userInputAnimalAgeSet = false;

        while (!userInputAnimalAgeSet) {
            System.out.println("Animal Age: ");
            String animalAge = inputAnimalAge.nextLine();
            Matcher matcher = pattern.matcher(animalAge);
            //create least amount of objects each time

            if (matcher.find()) {
                newAnimal.setAge(Integer.parseInt(animalAge));
                userInputAnimalAgeSet = true;
            } else {
                System.out.println("Please enter a valid choice for animal age");
            }

        }

        /*Animal Loan Status*/
        Scanner inputAnimalLoanStatus = new Scanner(System.in);
        boolean userInputAnimalLoanStatusSet = false;

        while (!userInputAnimalLoanStatusSet) {
            System.out.println("Is the animal on loan to another zoo? Enter 'yes' or 'no' ");
            String animalLoanStatus = inputAnimalLoanStatus.nextLine();

            if (animalLoanStatus.equalsIgnoreCase("yes")) {
                newAnimal.setOnLoan(true);
                userInputAnimalLoanStatusSet = true;
            }
            else if (animalLoanStatus.equalsIgnoreCase("no")) {
                newAnimal.setOnLoan(false);
                userInputAnimalLoanStatusSet = true;
            }

        }

            /*Animal loan location*/
        if (newAnimal.isOnLoan()) {
            Scanner inputAnimalLoanLocation = new Scanner(System.in);
            String animalLoanLocationRegex = "([A-Za-z]+){2,}(: [A-Za-z]+)?";
            Pattern animalLoanLocationPattern = Pattern.compile((animalLoanLocationRegex), Pattern.CASE_INSENSITIVE);

            boolean userInputAnimalLoanLocationSet = false;

            while (!userInputAnimalLoanLocationSet) {
                System.out.println("What zoo is this animal on loan to?");
                String animalLoanLocation = inputAnimalLoanLocation.nextLine();
                Matcher matcher = animalLoanLocationPattern.matcher(animalLoanLocation);

                if (matcher.find()) {
                    newAnimal.setLoanLocation(animalLoanLocation);

                    userInputAnimalLoanLocationSet = true;
                } else {
                    System.out.println("Invalid location. ex. lincoln: park zoo");
                }

            }
        }

        /*Animal Enclosure*/
        Scanner userInputAnimalEnclosure = new Scanner(System.in);

        boolean animalEnclosureSet = false;

        while (!animalEnclosureSet) {
            System.out.println("Which enclosure is this animal in?\nPen, Cage, Window");

            String animalEnclosure = userInputAnimalEnclosure.nextLine();

            if (animalEnclosure.equalsIgnoreCase("pen") || animalEnclosure.equalsIgnoreCase("cage") || animalEnclosure.equals("window")) {
                newAnimal.setEnclosure(animalEnclosure);
                animalEnclosureSet = true;
            } else {
                System.out.println("Please enter a valid enclosure\n" +
                        "Pen\n" +
                        "Cage\n" +
                        "Window");
            }

            AnimalDataBase.saveAnimalToDB(newAnimal);

        }
    }

    static void removeAnimal() {

        if (AnimalDataBase.checkForAnimals()) {
            int number;

            AnimalDataBase.getAllAnimals();

            Scanner input = new Scanner(System.in);
            System.out.println("\nPlease enter the number of the animal do you want to remove?");
            number = input.nextInt();

            try {
                AnimalDataBase.removeAnimal(number);
            }catch (Exception exception) {
                System.out.println("Invalid choice, please select one of the following choices: ");
            }

        } else {
            transitionTimer("No animals exist, please add an animal before removing any.", 5);
        }

    }

    static void searchAnimals() {

        if (AnimalDataBase.checkForAnimals()) {
            System.out.println("Please enter an option to search by");
            System.out.println("1. -------------Search Type of Animal-------------");
            System.out.println("2. -------------Search Name of Animal-------------");
            Scanner searchChoice = new Scanner(System.in);
            try {
                int animalSearchOption = searchChoice.nextInt();

                switch (animalSearchOption) {
                    case 1:
                        //ask user which type of search they would like to run
                        //take user choice as input from user
                        //run animal search option to return list of animals by type
                        //

                        System.out.println("Please enter a type of animal to search by: ");
                        Scanner animalTypeUserInput = new Scanner(System.in);
                        String animalTypeInput = animalTypeUserInput.nextLine();
                        List<Animal> animalList = AnimalDataBase.searchByAnimalType(animalTypeInput);
                        System.out.println("Running Search");
                        animalList.forEach(System.out::println);

                        break;

                    case 2:
                        System.out.println("Please enter the name of the \nanimal that you would like to find.");
                        Scanner animalNameUserInput = new Scanner(System.in);
                        String nameInput = animalNameUserInput.nextLine();
                        List<Animal> animalList2 = AnimalDataBase.searchByAnimalName(nameInput);
                        System.out.println("Running Search");
                        animalList2.forEach(System.out::println);
                        break;

                    default:
                        System.out.println("Please enter a valid option");
                }
            } catch (InputMismatchException e) {
                searchChoice.nextLine();

            }
        } else {
            transitionTimer("No animals exist, please add an animal first.", 3);
        }
    }

    private static String setAName(String subject) {
        String name;
        String verifiedName = "";
        Scanner scanner = new Scanner(System.in);

//        final String regexNamePattern = "\\b[a-zA-Z]+\\b";
        final String regexNamePattern = "(<script(\\s|\\S)*?<\\/script>)|(<style(\\s|\\S)*?<\\/style>)|(<!--(\\s|\\S)*?-->)|(<\\/?(\\s|\\S)*?>)";
        Pattern namePattern = Pattern.compile(regexNamePattern);

        boolean flag = false;

        while (!flag) {
            System.out.println("Please enter an " + subject + " name ex.'Larry'");
            name = scanner.nextLine();
            Matcher matcher = namePattern.matcher(name);

            if (!matcher.find()) {
                verifiedName = name;
                flag = true;
            }
        }
        return verifiedName;
    }


    /**
     * @param message Used to pass a message to the console for the transition
     * @param timeout Timeout transition in seconds
     */
    static void transitionTimer(String message, int timeout) {

        try {
            System.out.println(message);
            System.out.println("returning to main menu in: ");
            for (int i = timeout; i >=1; i--) {
                System.out.println(i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void listAnimals() {
        if (AnimalDataBase.checkForAnimals()) {
            List<Animal> animals = AnimalDataBase.getAllAnimals();

            animals.forEach(System.out::println);
        } else {
            transitionTimer("No animals exist yet, please add one.", 3);
        }
    }

}