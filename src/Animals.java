import java.util.ArrayList;

/**
 * Created by carlos.ochoa on 2/2/2016.
 */
public class Animals {
    private ArrayList<Animal> animals;

    private Animals() {
        animals=new ArrayList<Animal>();
    }

    private static Animals instance;

    public static Animals getInstance() {
        if (instance == null) {
            instance = new Animals();
        }
        return instance;
    }

    public boolean isEmpty() {
        return animals.isEmpty();
    }

    public int size() {
        return animals.size();
    }

    public void remove(int choice) {
        for (Animal animal: animals) {
            int animalNumber = animal.getAnimalNumber();
            if (animalNumber == choice) {
                animals.remove(animal);
            }
            break;
        }
    }

    public void listByType(String animalTypeInput) {
        for (Animal animal: animals) {
            String animalType = animal.getType();
            if (animalType.equals(animalTypeInput)) {

                System.out.println(" ");
                System.out.println(animal.getAnimalNumber());
                System.out.println("Name: " + animal.getName());
                System.out.println("Type: " + animal.getType());
                System.out.println("Health: " + animal.getHealth());
                System.out.println("Age: " + animal.getAge());
                System.out.println("On loan: " + animal.isOnLoan());
                System.out.println("Enclosure: " + animal.getEnclosure());
                System.out.println("Loan location: " + animal.getLoanLocation());
            }

        }
    }

    public void listByName(String nameInput) {
        for (Animal animal: animals) {
            String animalType = animal.getName();
            if (animalType.equals(nameInput)) {

                System.out.println(" ");
                System.out.println(animal.getAnimalNumber());
                System.out.println("Name: " + animal.getName());
                System.out.println("Type: " + animal.getType());
                System.out.println("Health: " + animal.getHealth());
                System.out.println("Age: " + animal.getAge());
                System.out.println("On loan: " + animal.isOnLoan());
                System.out.println("Enclosure: " + animal.getEnclosure());
                System.out.println("Loan location: " + animal.getLoanLocation());
            }

        }
    }

    public void add(Animal newAnimal) {
        animals.add(newAnimal);
    }

    public void listAll() {
        for (Animal animal : animals) {
            System.out.println(" ");
            System.out.println(animal.getAnimalNumber());
            System.out.println("Name: " + animal.getName());
            System.out.println("Type: " + animal.getType());
            System.out.println("Health: " + animal.getHealth());
            System.out.println("Age: " + animal.getAge());
            System.out.println("On loan: " + animal.isOnLoan());
            System.out.println("Enclosure: " + animal.getEnclosure());
            System.out.println("Loan location: " + animal.getLoanLocation());
        }
    }

}