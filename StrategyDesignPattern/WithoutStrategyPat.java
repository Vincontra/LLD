package StrategyDesignPattern;

class Vehicle {
    public void drive() {
        System.out.println("Normal Driving Capability");
    }
}
class NormalCar extends Vehicle {
    // Uses parent's drive()
}

class SportsCar extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Special Sports Driving");
    }
}

class OffRoadVehicle extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Special Off-Road Driving");
    }
}