# Strategy Design Pattern in Java

## 1. Introduction

The **Strategy Design Pattern** is a behavioural design pattern that allows us to define a family of algorithms, encapsulate each algorithm into a separate class, and make them interchangeable at runtime.

In simple words:

Instead of writing different behaviours inside one class or its subclasses, we create separate classes for each behaviour and inject whichever behaviour we need.

### Why do we need it?

Let's understand this with a real-world example.

Imagine we are developing a Vehicle Application with different types of vehicles:

- Normal Car
- Sports Car
- Off-Road Vehicle

All vehicles have a common functionality: **Driving**.

However, not every vehicle drives in the same way.

| Vehicle | Driving Behaviour |
|---|---|
| Normal Car | Normal Driving |
| Sports Car | Special Driving |
| Off-Road Vehicle | Off-Road Driving |

Now the question is: How can we implement this using Java and OOP concepts?

---

## 2. Approach 1: Using Inheritance

Initially, we can create a parent class called `Vehicle` and define a common `drive()` method.

```java
class Vehicle {
    public void drive() {
        System.out.println("Normal Driving");
    }
}
```

Now, we create child classes that extend the Vehicle class.

```java
class NormalCar extends Vehicle {
    // Uses parent's drive() method
}

class SportsCar extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Special Driving");
    }
}

class OffRoadVehicle extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Off-Road Driving");
    }
}
```

This implementation works, but it introduces some design problems.

### Problems with Inheritance

**1. Code Duplication**

Imagine we have multiple vehicles that require the same special driving functionality.

We might end up writing the same implementation repeatedly in different child classes.

**2. Difficult to Maintain**

Whenever we introduce a new vehicle with a different driving behaviour, we may need to override the `drive()` method again.

As the number of vehicle types increases, maintaining these implementations becomes difficult.

**3. Tight Coupling**

Driving behaviour is directly connected to the vehicle class.

If we want to change or reuse a particular driving behaviour independently, inheritance makes the design less flexible.

### Actual Problem

We are mixing two different responsibilities:

- What a vehicle is.
- How a vehicle drives.

These two responsibilities should be handled separately.

This is where the **Strategy Design Pattern** comes into the picture.

---

## 3. Approach 2: Strategy Design Pattern

The main idea of Strategy Pattern is to separate changing behaviours from the class that uses those behaviours.

Instead of implementing driving functionality directly inside vehicle classes, we create separate classes for each driving strategy.

### Step 1: Create a Strategy Interface

First, we create an interface called `DriveStrategy`.

```java
interface DriveStrategy {
    void drive();
}
```

**Why an interface?**

Because all driving strategies should follow a common contract.

Every class implementing `DriveStrategy` must provide its own implementation of the `drive()` method.

### Step 2: Create Different Strategy Implementations

Now, we create separate classes for different driving behaviours.

**NormalDriveStrategy**

```java
class NormalDriveStrategy implements DriveStrategy {

    @Override
    public void drive() {
        System.out.println("Normal Driving");
    }
}
```

**SpecialDriveStrategy**

```java
class SpecialDriveStrategy implements DriveStrategy {

    @Override
    public void drive() {
        System.out.println("Special Driving");
    }
}
```

Notice that both classes implement the same interface but provide different implementations of the `drive()` method.

This is an example of **Polymorphism**.

We have successfully separated driving logic into independent classes.

---

## 4. Modifying the Vehicle Class

This is the most important part of the Strategy Design Pattern.

Earlier, the Vehicle class itself contained the driving logic.

Now, instead of implementing the driving logic directly, Vehicle will use a `DriveStrategy` object.

```java
class Vehicle {

    DriveStrategy driveStrategy;

    Vehicle(DriveStrategy driveStrategy) {
        this.driveStrategy = driveStrategy;
    }

    public void drive() {
        driveStrategy.drive();
    }
}
```

Let's understand each part.

### 1. DriveStrategy driveStrategy;

We create a reference of the `DriveStrategy` interface.

We use an interface reference instead of a specific implementation because Vehicle should not depend on one particular driving strategy.

### 2. Vehicle(DriveStrategy driveStrategy)

We receive a driving strategy object through the constructor.

### 3. this.driveStrategy = driveStrategy;

We store the received strategy object inside the Vehicle class.

### 4. driveStrategy.drive();

Whenever the `drive()` method of Vehicle is called, it internally calls the `drive()` method of the strategy object.

The Vehicle class does not need to know which specific strategy is being used.

It only knows that the object follows the `DriveStrategy` interface.

This concept is called **Programming to an Interface**.

### Why Constructor Injection?

We pass the required strategy object through the constructor.

This is called **Constructor Injection**.

It allows us to provide the required behaviour when creating a vehicle.

---

## 5. Connecting Vehicles with Their Strategies

Now, we connect each vehicle with its respective driving strategy.

```java
class NormalCar extends Vehicle {

    NormalCar() {
        super(new NormalDriveStrategy());
    }
}

class SportsCar extends Vehicle {

    SportsCar() {
        super(new SpecialDriveStrategy());
    }
}
```

Let's understand what happens here.

**NormalCar:**

```java
super(new NormalDriveStrategy());
```

NormalCar passes a `NormalDriveStrategy` object to its parent class.

**SportsCar:**

```java
super(new SpecialDriveStrategy());
```

SportsCar passes a `SpecialDriveStrategy` object to its parent class.

The Vehicle class receives these objects and stores them.

Therefore:

- NormalCar uses NormalDriveStrategy.
- SportsCar uses SpecialDriveStrategy.

Notice that we are not writing any driving implementation inside these vehicle classes.

We are only selecting which strategy they should use.

---

## 6. Complete Execution Flow

Let's understand how the entire implementation works.

### Creating a NormalCar

```java
Vehicle v1 = new NormalCar();
v1.drive();
```

Execution flow:

1. An object of `NormalCar` is created.
2. Its constructor passes a `NormalDriveStrategy` object to the Vehicle constructor.
3. Vehicle stores the received strategy object.
4. When `v1.drive()` is called, Vehicle internally executes `driveStrategy.drive()`.
5. Since the actual object is `NormalDriveStrategy`, normal driving gets executed.

**Output:**

```text
Normal Driving
```

### Creating a SportsCar

```java
Vehicle v2 = new SportsCar();
v2.drive();
```

Execution flow:

1. An object of `SportsCar` is created.
2. Its constructor passes a `SpecialDriveStrategy` object to the Vehicle constructor.
3. Vehicle stores the received strategy object.
4. When `v2.drive()` is called, Vehicle internally executes `driveStrategy.drive()`.
5. Since the actual object is `SpecialDriveStrategy`, special driving gets executed.

**Output:**

```text
Special Driving
```

### Visual Representation

```text
                  DriveStrategy
                       |
             ----------------------
             |                    |
    NormalDriveStrategy    SpecialDriveStrategy
             |                    |
             |                    |
         NormalCar             SportsCar
             |                    |
             -------- Vehicle -----
                       |
                   drive()
                       |
              driveStrategy.drive()
```

The same method call executes different implementations depending on the actual strategy object.

This is Strategy Pattern in action.

---

## 7. Adding a New Strategy

Now imagine tomorrow we introduce another vehicle called `OffRoadVehicle`.

It requires a new driving behaviour.

We simply create another strategy class.

```java
class OffRoadDriveStrategy implements DriveStrategy {

    @Override
    public void drive() {
        System.out.println("Off-Road Driving");
    }
}
```

We can connect this strategy to a new vehicle without modifying the existing Vehicle class.

```java
class OffRoadVehicle extends Vehicle {

    OffRoadVehicle() {
        super(new OffRoadDriveStrategy());
    }
}
```

This makes our application easier to extend and maintain.

We are following an important object-oriented design principle:

### Open-Closed Principle (OCP)

A class should be:

- Open for extension.
- Closed for modification.

In our example, we can introduce new driving strategies without modifying the existing Vehicle class.

---

## 8. Complete Java Implementation

```java
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

class OffRoadDriveStrategy implements DriveStrategy {
    public void drive() {
        System.out.println("Off-Road Driving");
    }
}

class Vehicle {

    DriveStrategy driveStrategy;

    Vehicle(DriveStrategy driveStrategy) {
        this.driveStrategy = driveStrategy;
    }

    public void drive() {
        driveStrategy.drive();
    }
}

class NormalCar extends Vehicle {
    NormalCar() {
        super(new NormalDriveStrategy());
    }
}

class SportsCar extends Vehicle {
    SportsCar() {
        super(new SpecialDriveStrategy());
    }
}

class OffRoadVehicle extends Vehicle {
    OffRoadVehicle() {
        super(new OffRoadDriveStrategy());
    }
}

public class Main {

    public static void main(String[] args) {

        Vehicle v1 = new NormalCar();
        v1.drive();

        Vehicle v2 = new SportsCar();
        v2.drive();

        Vehicle v3 = new OffRoadVehicle();
        v3.drive();
    }
}
```

**Output:**

```text
Normal Driving
Special Driving
Off-Road Driving
```

---

## 9. Real-Life Example: Payment System

Let's understand another example using an e-commerce application.

Imagine a customer can make payments using:

- UPI
- Credit Card
- PayPal

All these payment methods perform the same high-level operation: `pay()`.

However, their internal implementations are different.

We can apply the Strategy Pattern here as well.

### Step 1: Create PaymentStrategy Interface

```java
interface PaymentStrategy {
    void pay(int amount);
}
```

### Step 2: Create Payment Implementations

**UPI Payment**

```java
class UpiPayment implements PaymentStrategy {

    public void pay(int amount) {
        System.out.println("Paid using UPI: " + amount);
    }
}
```

**Card Payment**

```java
class CardPayment implements PaymentStrategy {

    public void pay(int amount) {
        System.out.println("Paid using Card: " + amount);
    }
}
```

### Step 3: Create Checkout Class

```java
class Checkout {

    PaymentStrategy paymentStrategy;

    Checkout(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void makePayment(int amount) {
        paymentStrategy.pay(amount);
    }
}
```

### Step 4: Execute

```java
public class Main {

    public static void main(String[] args) {

        Checkout c1 = new Checkout(new UpiPayment());
        c1.makePayment(1000);

        Checkout c2 = new Checkout(new CardPayment());
        c2.makePayment(2000);
    }
}
```

**Output:**

```text
Paid using UPI: 1000
Paid using Card: 2000
```

The Checkout class does not need to know how UPI or Card payments work internally.

It simply uses the provided payment strategy.

We can introduce another payment method without modifying the existing Checkout class.

This is the same concept we implemented in our Vehicle application.

---

## 10. Important OOP Concepts Used

| Concept | Meaning in Our Example |
|---|---|
| Interface | DriveStrategy defines a common contract. |
| Polymorphism | Same `drive()` call executes different implementations. |
| Composition | Vehicle HAS-A DriveStrategy. |
| Constructor Injection | Strategy object is passed through the constructor. |
| Encapsulation | Each strategy keeps its implementation separate. |
| Open-Closed Principle | New strategies can be added without modifying Vehicle. |

### Important Relationship: IS-A vs HAS-A

**IS-A Relationship (Inheritance)**

- NormalCar IS-A Vehicle.
- SportsCar IS-A Vehicle.

**HAS-A Relationship (Composition)**

- Vehicle HAS-A DriveStrategy.

We use inheritance to represent the relationship between a vehicle and its type.

We use composition to connect a vehicle with its driving behaviour.

This separation is one of the most important ideas behind this design pattern.

---

## 11. Inheritance vs Strategy Pattern

| Inheritance Approach | Strategy Pattern |
|---|---|
| Behaviour is implemented inside parent or child classes. | Behaviour is implemented in separate strategy classes. |
| Can lead to code duplication. | Promotes behaviour reuse. |
| Behaviour is tightly coupled to the class hierarchy. | Behaviour is loosely coupled through an interface. |
| Changing behaviour independently is difficult. | Different strategies can be supplied or replaced. |
| Adding new behaviours may require changes to subclasses. | New strategy implementations can be added independently. |

**Important:** Strategy Pattern does not eliminate inheritance completely.

We still use inheritance for `NormalCar` and `SportsCar`.

We simply move the varying behaviour out of the vehicle hierarchy.

---

## 12. When Should We Use Strategy Pattern?

Use Strategy Pattern when:

1. You have multiple ways of performing the same operation.
2. Different classes share similar behaviour but implement it differently.
3. You want to avoid repeated conditional statements for selecting algorithms.
4. You want to change behaviour independently of the class using it.
5. You want to add new behaviours without modifying existing business logic.

### Common Real-World Applications

- Payment methods: UPI, Card, PayPal.
- Sorting algorithms: Merge Sort, Quick Sort, Bubble Sort.
- Navigation systems: Different route calculation algorithms.
- Discount calculations: Festival Discount, Membership Discount.
- Compression algorithms: ZIP, GZIP.

---

## 13. Advantages and Disadvantages

### Advantages

- Separates behaviour from the classes that use it.
- Reduces code duplication.
- Promotes composition over inheritance for varying behaviours.
- Makes code easier to maintain and extend.
- Supports the Open-Closed Principle.
- Allows different implementations to be used through a common interface.

### Disadvantages

- Introduces additional classes and interfaces.
- Can increase complexity for very simple applications.
- The client or application must select the appropriate strategy.
- Each strategy may need to be managed and configured separately.

---

## 14. Interview Preparation

### What is Strategy Design Pattern?

Strategy Pattern is a behavioural design pattern that defines a family of algorithms, encapsulates each algorithm into a separate class, and makes them interchangeable through a common interface.

**In simple words:**

Instead of writing different behaviours inside one class or its subclasses, we create separate classes for each behaviour and inject whichever behaviour we need.

### Three Important Things to Remember

**1. Composition**

Vehicle HAS-A DriveStrategy.

The Vehicle class contains a reference to a driving strategy object.

**2. Polymorphism**

The same `drive()` method call can execute different implementations depending on the actual strategy object.

**3. Constructor Injection**

We pass the required strategy object through the constructor.

### One-Line Summary

**Strategy Pattern separates changing behaviours from the class that uses them, making those behaviours reusable, flexible, and interchangeable.**

---

## 15. Final Conclusion

The main problem with our original inheritance approach was that we were mixing vehicle identity with driving behaviour.

By introducing Strategy Pattern:

- We created a common interface for driving behaviours.
- We moved each driving implementation into a separate class.
- We made Vehicle depend on the interface instead of a specific implementation.
- We injected the required strategy through the constructor.
- We made it possible to introduce new behaviours without modifying the existing Vehicle class.

The most important line to remember is:

**We use Strategy Pattern when we want to separate changing behaviours from the main class and make those behaviours interchangeable.**

That's the core idea behind the Strategy Design Pattern in Java.