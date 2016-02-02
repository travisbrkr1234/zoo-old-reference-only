import java.util.ArrayList;

/**
 * Created by carlos.ochoa on 2/1/2016.
 */
public class Animal {
    private String type;
    private String name;
    private int health;
    private int age;
    private String enclosure;
    private boolean onLoan;
    private String loanLocation;
    private ArrayList animals = new ArrayList();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public boolean isOnLoan() {
        return onLoan;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    public String getLoanLocation() {
        return loanLocation;
    }

    public void setLoanLocation(String loanLocation) {
        this.loanLocation = loanLocation;
    }

    public ArrayList getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList animals) {
        this.animals = animals;
    }
}
