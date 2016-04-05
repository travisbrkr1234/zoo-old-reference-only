/**
 * Created by carlos.ochoa on 3/8/2016.
 */
public enum AnimalHealthStatus {
    GOOD_HEALTH(1),
    MEDIOCRE_HEALTH(2),
    BAD_HEALTH(3),
    PREGNANT(4);

    private int statusCode;

    AnimalHealthStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getAnimalHealthStatus() {
        return statusCode;
    }

    public static boolean animalHealthStatusExists(int statusCode) {
        return GOOD_HEALTH.getAnimalHealthStatus() == statusCode ||
                MEDIOCRE_HEALTH.getAnimalHealthStatus() == statusCode ||
                BAD_HEALTH.getAnimalHealthStatus() == statusCode ||
                PREGNANT.getAnimalHealthStatus() == statusCode;
    }

    public static AnimalHealthStatus getAnimalHealthStatusByStatusCode(int statusCode) {
        AnimalHealthStatus animalHealthStatus;
        switch (statusCode) {
            case 1:
                animalHealthStatus = AnimalHealthStatus.GOOD_HEALTH;
                break;
            case 2:
                animalHealthStatus = AnimalHealthStatus.MEDIOCRE_HEALTH;
                break;
            case 3:
                animalHealthStatus = AnimalHealthStatus.BAD_HEALTH;
                break;
            case 4:
                animalHealthStatus = AnimalHealthStatus.PREGNANT;
                break;
            default:
                animalHealthStatus = null;
        }
        return animalHealthStatus;
    }

}