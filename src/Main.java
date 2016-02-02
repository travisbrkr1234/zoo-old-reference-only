import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by carlos.ochoa on 2/1/2016.
 */
public class Main {

    public static void main(String[] args) {

        int option = -1;

        while (option != 0) {
            option = menu();

            switch (option) {
                case 1:
                    option = 1;
                    createAnimal();
                    break;

                case 2:
                    option = 2;
                    listAnimals();
                    break;

                case 3:
                    option = 3;
                    removeAnimal();
                    break;

                default:
                    return;
            }
        }
    }

    static int menu() {
        System.out.println("-------------------------------------------------");
        System.out.println("***********000000*******0000*****0000************");
        System.out.println("**************//*******0****0***0****0***********");
        System.out.println("*************0000000****0000*****0000************");
        System.out.println("-------------------------------------------------");
        System.out.println("-------------Please enter a choice---------------");
        System.out.println("1. Add animal------------------------------------");
        System.out.println("2. list animals----------------------------------");
        System.out.println("3. remove animal---------------------------------");

        Scanner menu = new Scanner(System.in);
        int option = menu.nextInt();
        return option;
    }

    static void createAnimal() {
        Animals animals = Animals.getInstance();
        Animal newAnimal = new Animal();

        System.out.println("Please enter a type for this animal");
        Scanner animalType = new Scanner(System.in);
        newAnimal.setType(animalType.nextLine());
        //System.out.println("Your animal has the type of: " + animal.getType());

        System.out.println("Please enter a name for this animal");
        Scanner animalName = new Scanner(System.in);
        newAnimal.setName(animalName.nextLine());
        //System.out.println("Your animal has the name of: " + animal.getName());

        System.out.println("Health status: ");
        System.out.println("1 - Good health\n" +
                "2 - Mediocre health\n" +
                "3 - Bad health\n" +
                "4 - Pregnant");
        Scanner animalHealth = new Scanner(System.in);
        newAnimal.setHealth(animalHealth.nextInt());

        System.out.println("age: ");
        Scanner animalAge = new Scanner(System.in);
        newAnimal.setAge(animalAge.nextInt());

        System.out.println("on loan? true or false");
        Scanner animalStatus = new Scanner(System.in);
        newAnimal.setOnLoan(animalStatus.nextBoolean());

        System.out.println("enclosure: ");
        Scanner animalEnclosure = new Scanner(System.in);
        newAnimal.setEnclosure(animalEnclosure.nextLine());

        animals.add(newAnimal);
    }

    static void listAnimals() {
        Animals animals = Animals.getInstance();
        animals.listAll();
    }

    static void removeAnimal() {
        Animals animals = Animals.getInstance();
        int choice;
        listAnimals();
        Scanner input = new Scanner(System.in);
        System.out.println("Which animal do you want to remove?");
        choice = input.nextInt();
        animals.remove(choice);
    }
}

