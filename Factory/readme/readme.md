# Factory Design Pattern & Abstract Factory Design Pattern

## Table of Contents
1. [Introduction](#1-introduction)
2. [Factory Pattern](#2-factory-pattern)
    - Problem Statement
    - Solution
    - Class Structure
    - Java Implementation
    - Advantages
3. [Abstract Factory Pattern](#3-abstract-factory-pattern)
    - Problem Statement
    - Solution
    - Class Structure
    - Java Implementation
    - Advantages
4. [Factory vs Abstract Factory](#4-factory-vs-abstract-factory)
5. [Real-World Use Cases](#5-real-world-use-cases)
6. [Interview Questions](#6-interview-questions)
7. [Key Takeaways](#7-key-takeaways)

---

# 1. Introduction

Both Factory and Abstract Factory are creational design patterns.

**Creational Design Patterns** deal with how objects are created.

The main goal is to separate object creation logic from the code that actually uses those objects.

This makes our application more flexible, maintainable, and easier to extend.

We will understand both patterns using simple Java examples.

---

# 2. Factory Pattern

## What is Factory Pattern?

Factory Pattern provides a separate class responsible for creating objects instead of creating objects directly inside the main application.

In simple words:

**Instead of creating an object yourself, you ask a factory to create the required object for you.**

## Problem Statement

Suppose we are developing a drawing application that supports:

- Circle
- Square
- Rectangle

Without a factory, we create objects directly:

```java
Circle circle = new Circle();
circle.draw();

Square square = new Square();
square.draw();
```

### Problems

1. The main class needs to know about all concrete classes.
2. Object creation logic is mixed with business logic.
3. Adding new shapes requires modifying the object creation code.

We need a better approach.

## Solution: Factory Pattern

Create a separate `ShapeFactory` class that handles object creation.

### Step 1: Create Shape Interface

```java
interface Shape {
    void draw();
}
```

This interface defines a common method that every shape must implement.

### Step 2: Create Concrete Classes

```java
class Circle implements Shape {
    public void draw() {
        System.out.println("Drawing Circle");
    }
}

class Square implements Shape {
    public void draw() {
        System.out.println("Drawing Square");
    }
}

class Rectangle implements Shape {
    public void draw() {
        System.out.println("Drawing Rectangle");
    }
}
```

Each class provides its own implementation of the `draw()` method.

### Step 3: Create ShapeFactory

```java
class ShapeFactory {

    public Shape getShape(String type) {

        if (type.equalsIgnoreCase("circle")) {
            return new Circle();
        }

        if (type.equalsIgnoreCase("square")) {
            return new Square();
        }

        if (type.equalsIgnoreCase("rectangle")) {
            return new Rectangle();
        }

        return null;
    }
}
```

The factory checks the requested shape and creates the corresponding object.

### Step 4: Main Class

```java
public class Main {

    public static void main(String[] args) {

        ShapeFactory factory = new ShapeFactory();

        Shape shape = factory.getShape("circle");

        shape.draw();
    }
}
```

**Output:**

```text
Drawing Circle
```

## How Does It Work?

1. Main creates a `ShapeFactory` object.
2. Main requests a shape by calling `getShape("circle")`.
3. ShapeFactory checks the requested type.
4. ShapeFactory creates a Circle object.
5. The object is returned using the Shape interface.
6. Main calls `draw()`.

## Class Structure

```text
              Main
                |
                | has-a
                v
          ShapeFactory
                |
                | creates
                v
           Shape Interface
                |
        ---------------------
        |         |         |
      Circle    Square   Rectangle
```

### Important Relationships

- Main has-a ShapeFactory.
- ShapeFactory creates objects that implement Shape.
- Circle, Square and Rectangle are-a Shape.

## Advantages

- Centralizes object creation logic.
- Reduces dependency on concrete classes.
- Makes code easier to maintain.
- Makes it easier to introduce new object types.
- Separates object creation from business logic.

---

# 3. Abstract Factory Pattern

## What is Abstract Factory Pattern?

Abstract Factory is a creational design pattern that provides an interface for creating families of related objects without specifying their concrete classes.

In simple words:

**A factory creates a family of related objects instead of creating just one individual object.**

## Real-World Example: Furniture Shop

Imagine a furniture application that supports two furniture styles:

1. Modern Furniture
2. Victorian Furniture

Each furniture style contains:

- Chair
- Sofa

If the customer selects Modern Furniture, the application should create:

- Modern Chair
- Modern Sofa

If the customer selects Victorian Furniture, the application should create:

- Victorian Chair
- Victorian Sofa

We want a factory that creates the complete family of related furniture objects.

This is where Abstract Factory comes in.

## Solution: Abstract Factory Pattern

### Step 1: Create Product Interfaces

```java
interface Chair {
    void sit();
}

interface Sofa {
    void relax();
}
```

These interfaces define the common behavior of chairs and sofas.

### Step 2: Create Concrete Products

**Modern Chair and Victorian Chair**

```java
class ModernChair implements Chair {

    public void sit() {
        System.out.println("Sitting on Modern Chair");
    }
}

class VictorianChair implements Chair {

    public void sit() {
        System.out.println("Sitting on Victorian Chair");
    }
}
```

**Modern Sofa and Victorian Sofa**

```java
class ModernSofa implements Sofa {

    public void relax() {
        System.out.println("Relaxing on Modern Sofa");
    }
}

class VictorianSofa implements Sofa {

    public void relax() {
        System.out.println("Relaxing on Victorian Sofa");
    }
}
```

Each concrete product implements its respective interface.

### Step 3: Create Abstract Factory Interface

```java
interface FurnitureFactory {

    Chair createChair();

    Sofa createSofa();
}
```

This interface defines which products every concrete furniture factory must create.

### Step 4: Create Concrete Factories

**Modern Factory**

```java
class ModernFactory implements FurnitureFactory {

    public Chair createChair() {
        return new ModernChair();
    }

    public Sofa createSofa() {
        return new ModernSofa();
    }
}
```

**Victorian Factory**

```java
class VictorianFactory implements FurnitureFactory {

    public Chair createChair() {
        return new VictorianChair();
    }

    public Sofa createSofa() {
        return new VictorianSofa();
    }
}
```

Each factory creates its own family of related products.

### Step 5: Main Class

```java
public class Main {

    public static void main(String[] args) {

        FurnitureFactory factory = new ModernFactory();

        Chair chair = factory.createChair();
        Sofa sofa = factory.createSofa();

        chair.sit();
        sofa.relax();
    }
}
```

**Output:**

```text
Sitting on Modern Chair
Relaxing on Modern Sofa
```

If we want Victorian furniture, we only need to change:

```java
FurnitureFactory factory = new VictorianFactory();
```

The remaining client code stays the same.

## How Does It Work?

1. Main selects a concrete factory.
2. The selected factory implements FurnitureFactory.
3. Main requests a chair and sofa.
4. The factory creates the corresponding concrete products.
5. Products are returned using their respective interfaces.
6. Main uses the products without knowing their concrete implementations.

## Class Structure

```text
             FurnitureFactory
                  Interface
                     |
             -----------------
             |               |
        ModernFactory   VictorianFactory
             |               |
        -------------    -------------
        |           |    |           |
   ModernChair ModernSofa VictorianChair VictorianSofa
```

### Important Relationships

- ModernFactory and VictorianFactory implement FurnitureFactory.
- ModernChair and VictorianChair implement Chair.
- ModernSofa and VictorianSofa implement Sofa.
- Each factory creates a family of related products.

## Advantages

- Creates families of related objects.
- Hides concrete object creation logic.
- Makes switching between product families easier.
- Promotes loose coupling.
- Ensures related products are created by the same factory.
- Makes the application easier to maintain and extend.

---

# 4. Factory vs Abstract Factory

This is one of the most important differences to understand.

| Factory Pattern | Abstract Factory Pattern |
|---|---|
| Creates individual objects. | Creates families of related objects. |
| Usually uses one factory class. | Uses an abstract factory and multiple concrete factories. |
| Focuses on creating a particular product. | Focuses on creating related products together. |
| Example: ShapeFactory. | Example: FurnitureFactory. |
| Creates Circle, Square or Rectangle. | Creates Modern Chair and Modern Sofa together. |

### Easy Way to Remember

**Factory Pattern:**

You ask a factory for one shape.

```text
ShapeFactory
     |
     v
   Circle
```

**Abstract Factory Pattern:**

You select a furniture factory that provides an entire furniture family.

```text
ModernFactory
      |
      |------> ModernChair
      |
      |------> ModernSofa
```

**Remember:**

Factory = Individual Object Creation

Abstract Factory = Family of Related Object Creation

---

# 5. Real-World Use Cases

## Factory Pattern

1. **Payment Processing:** Create a payment processor based on the selected payment method.
2. **Notification System:** Create Email, SMS or Push Notification objects.
3. **Database Connections:** Create the required database connection implementation based on configuration.

## Abstract Factory Pattern

1. **Cross-Platform UI:** Create Windows or Mac buttons, text fields and other UI components.
2. **Furniture Application:** Create Modern or Victorian furniture collections.
3. **Cloud Infrastructure:** Create related resource implementations for different cloud providers.

---

# 6. Interview Questions

### Q1. What is Factory Pattern?

Factory Pattern is a creational design pattern that provides a separate class for object creation. Instead of creating objects directly, we ask the factory to create and return the required object.

### Q2. What is Abstract Factory Pattern?

Abstract Factory is a creational design pattern that provides an interface for creating families of related objects without specifying their concrete classes.

### Q3. What is the main difference between Factory and Abstract Factory?

Factory focuses on creating individual objects, whereas Abstract Factory focuses on creating families of related objects.

### Q4. Why do we use interfaces in these patterns?

Interfaces provide a common contract. This allows the client code to work with different implementations without depending on their concrete classes.

### Q5. What is loose coupling?

Loose coupling means reducing direct dependency between classes so that changes in one class have minimal impact on other classes.

### Q6. Can we add new products in Abstract Factory?

Yes, but adding a new product type requires updating the abstract factory interface and all its concrete factory implementations.

### Q7. Is Factory Pattern a creational design pattern?

Yes. Both Factory and Abstract Factory are creational patterns.

### Q8. Is Simple Factory the same as Factory Method?

No. Simple Factory is a commonly used object-creation idiom. Factory Method is a separate, formally recognized GoF design pattern that delegates object creation to subclasses.

---

# 7. Key Takeaways

| Concept | Remember |
|---|---|
| Creational Pattern | Deals with object creation. |
| Factory Pattern | Separate class creates objects. |
| Abstract Factory | Creates families of related objects. |
| Interface | Defines a common contract. |
| Concrete Class | Provides actual implementation. |
| Loose Coupling | Reduces dependency between classes. |

### Final Understanding

**Factory Pattern:** Main does not create objects directly. It asks a factory to create the required object.

**Abstract Factory Pattern:** Main selects a factory, and that factory creates a complete family of related objects.

Both patterns help separate object creation from object usage, making software easier to maintain and extend.

---

*Learning Note: Start by understanding the ShapeFactory example. Once you understand how one factory creates different shapes, the Abstract Factory example becomes easier because it simply extends the idea to families of related objects.*