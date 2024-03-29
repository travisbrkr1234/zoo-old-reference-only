import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by carlos.ochoa on 2/1/2016.
 */
public class Animal implements Serializable {
    private int animalNumber;
    private String type;
    private String name;
    private AnimalHealthStatus health;
    private int age;
    private String enclosure;
    private boolean onLoan;
    private String loanLocation;

    public int getAnimalNumber() {
        return animalNumber;
    }

    public void setAnimalNumber(int animalNumber) {
        this.animalNumber = animalNumber;
    }

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

    public AnimalHealthStatus getHealth() {
        return health;
    }

    public void setHealth(AnimalHealthStatus health) {
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

    @Override
    public String toString() {
        return String.valueOf(new StringBuffer("Animal Number: ")
                .append(this.animalNumber)
                .append("Name: ")
                .append(this.name)
                .append("Type: ")
                .append(this.type)
                .append("Health: ")
                .append(this.health)
                .append("Age: ")
                .append(this.age)
                .append("On loan: ")
                .append(this.onLoan)
                .append("Enclosure: ")
                .append(this.enclosure)
                .append("Loan location: ")
                .append(this.loanLocation));
    }

}