package StrategyDesignPattern;


interface DriveStrategy {
    void drive();
}

class NormalDriveStrategy implements DriveStrategy {
    public void drive() {
        System.out.println("Normal Driving");
    }
}

class SpecialDriveStrategy implements DriveStrategy {
    public void drive() {
        System.out.println("Special Driving");
    }
}

class Vehicle1 {

    DriveStrategy driveStrategy;

    Vehicle1(DriveStrategy driveStrategy) {
        this.driveStrategy = driveStrategy;// constructor injection
    }

    public void drive() {
        driveStrategy.drive();
    }
}

class NormalCars extends Vehicle1 {
    NormalCars() {
        super(new NormalDriveStrategy());
    }
}

class SportsCars extends Vehicle1 {
    SportsCars() {
        super(new SpecialDriveStrategy());
    }
}

class Main {
    public static void main(String[] args) {

        Vehicle1 v1 = new NormalCars();
        v1.drive();

        Vehicle1 v2 = new SportsCars();
        v2.drive();
    }
}
