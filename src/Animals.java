import java.util.ArrayList;

/**
 * Created by carlos.ochoa on 2/2/2016.
 */
public class Animals {
    public ArrayList<Animal> animals;

    private Animals() {
    animals=new ArrayList<Animal>();
    }

    private static Animals instance;

    public static Animals getInstance() {
        if (instance == null)
            instance = new Animals();
        return instance;
    }

    public void remove(int choice) {
        animals.remove(choice);
    }

    public void add(Animal newAnimal) {
        animals.add(newAnimal);
    }

    public void listAll() {
        for (Animal animal : animals) {
            System.out.println("'/'/'/'/'/'/'/'/'/");
            System.out.println("Type: " + animal.getType());
            System.out.println("Name: " + animal.getName());
            System.out.println("Health: " + animal.getHealth());
            System.out.println("Age: " + animal.getAge());
            System.out.println("On loan: " + animal.isOnLoan());
            System.out.println("Enclosure: " + animal.getEnclosure());
            System.out.println("Loan location: " + animal.getLoanLocation());
            System.out.println("'/'/'/'/'/'/'/'/'/");
        }
    }

    public void findAnimal() {
    }
}