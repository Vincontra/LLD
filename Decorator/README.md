# Decorator Design Pattern --- Pizza Shop Example

## 1. What is the Decorator Pattern?

The **Decorator Design Pattern** is a structural design pattern that
allows us to add new features or responsibilities to an existing object
dynamically, without changing its original class.

In simple words, we take an existing object and wrap it inside another
object that adds some extra functionality.

The important idea is that a decorator is also treated as the same base
type as the object it wraps. Therefore, we can wrap a decorated object
again and again.

### Pizza shop example

Imagine ordering a basic pizza. You can add different toppings:

-   Base Pizza
-   Extra Cheese
-   Mushroom
-   Extra Cheese again

Instead of creating a separate class for every possible topping
combination, we use decorators to add toppings dynamically.

Example:

``` text
Base Pizza
    |
    v
Extra Cheese
    |
    v
Mushroom
    |
    v
Final Pizza
```

Each topping adds its own cost to the pizza.

------------------------------------------------------------------------

## 2. Why do we need the Decorator Pattern?

Suppose we have these pizza options:

-   Base Pizza
-   Base Pizza with Cheese
-   Base Pizza with Mushroom
-   Base Pizza with Cheese and Mushroom
-   Base Pizza with Cheese twice
-   Base Pizza with Cheese, Mushroom, and extra Cheese

If we create a separate class for every combination, the number of
classes keeps increasing.

This problem is called **Class Explosion**.

### Problems with Class Explosion

1.  Too many classes are created.
2.  The code becomes difficult to maintain.
3.  Adding a new topping may require creating many more combination
    classes.
4.  Different combinations become difficult to manage.

### Solution

Keep the base pizza classes separate and add toppings dynamically using
decorator classes.

This avoids creating a class for every possible combination.

------------------------------------------------------------------------

## 3. Main components of the Decorator Pattern

The pizza example has three main parts.

### A. BasePizza

`BasePizza` is the common abstract parent class. Every pizza and every
decorator must provide the `cost()` method.

### B. Concrete Pizza classes

These are the actual pizza types, such as:

-   `Margherita`
-   `FarmHouse`

Each concrete pizza returns its own base cost.

### C. ToppingDecorator

`ToppingDecorator` is the important part of this design.

It:

1.  Extends `BasePizza`.
2.  Contains a reference of type `BasePizza`.
3.  Stores the pizza object that it decorates.

Why does it both extend and contain `BasePizza`?

-   **Inheritance (`extends BasePizza`):** A decorated pizza should also
    be usable wherever a `BasePizza` is expected.
-   **Composition (`BasePizza pizza`):** The decorator needs to store
    the pizza object whose functionality or cost it is extending.

This gives the key relationship:

> A decorator **IS-A** `BasePizza` and **HAS-A** `BasePizza`.

------------------------------------------------------------------------

## 4. Java implementation

### Step 1: Create the BasePizza abstract class

``` java
abstract class BasePizza {
    public abstract int cost();
}
```

Every pizza must implement the `cost()` method.

### Step 2: Create concrete pizza classes

``` java
class Margherita extends BasePizza {

    public int cost() {
        return 100;
    }
}

class FarmHouse extends BasePizza {

    public int cost() {
        return 200;
    }
}
```

For this example:

-   Margherita costs ₹100.
-   FarmHouse costs ₹200.

### Step 3: Create the ToppingDecorator abstract class

``` java
abstract class ToppingDecorator extends BasePizza {

    BasePizza pizza;

    ToppingDecorator(BasePizza pizza) {
        this.pizza = pizza;
    }
}
```

The field `BasePizza pizza` stores the object being decorated.

For example:

``` java
new ExtraCheese(new Margherita());
```

Here, the `ExtraCheese` decorator receives a `Margherita` object.

### Step 4: Create the ExtraCheese decorator

``` java
class ExtraCheese extends ToppingDecorator {

    ExtraCheese(BasePizza pizza) {
        super(pizza);
    }

    public int cost() {
        return pizza.cost() + 10;
    }
}
```

The decorator first gets the existing pizza's cost and then adds ₹10 for
extra cheese.

### Step 5: Create the Mushroom decorator

``` java
class Mushroom extends ToppingDecorator {

    Mushroom(BasePizza pizza) {
        super(pizza);
    }

    public int cost() {
        return pizza.cost() + 15;
    }
}
```

The Mushroom decorator adds ₹15 to the cost of the pizza it receives.

------------------------------------------------------------------------

## 5. How does the Decorator Pattern work?

Suppose we want:

-   Margherita pizza = ₹100
-   Extra Cheese = ₹10
-   Mushroom = ₹15

We can write:

``` java
BasePizza pizza =
    new Mushroom(
        new ExtraCheese(
            new Margherita()
        )
    );

System.out.println(pizza.cost());
```

**Output:**

``` text
125
```

### Object creation

The expression is evaluated from the innermost object outward:

1.  `new Margherita()` creates the base pizza.
2.  `new ExtraCheese(...)` wraps the Margherita object.
3.  `new Mushroom(...)` wraps the ExtraCheese object.

The final object is a `Mushroom` decorator, which contains an
`ExtraCheese` decorator, which contains a `Margherita`.

### Object wrapping structure

``` text
Mushroom
   |
   | contains
   v
ExtraCheese
   |
   | contains
   v
Margherita
```

### How is `cost()` calculated?

When we call:

``` java
pizza.cost();
```

The call travels through the decorators:

1.  `Mushroom.cost()` is called.
2.  Mushroom calls `pizza.cost()` on its wrapped `ExtraCheese` object.
3.  `ExtraCheese.cost()` is called.
4.  ExtraCheese calls `pizza.cost()` on its wrapped `Margherita` object.
5.  `Margherita.cost()` returns `100`.
6.  ExtraCheese adds `10` and returns `110`.
7.  Mushroom adds `15` and returns `125`.

Calculation:

``` text
Margherita       = ₹100
Extra Cheese     = ₹10
Mushroom         = ₹15
----------------------
Final Cost       = ₹125
```

------------------------------------------------------------------------

## 6. Can we apply the same decorator multiple times?

Yes. A decorator can wrap another decorator, including another instance
of the same decorator.

For example, suppose we want:

-   Margherita = ₹100
-   Extra Cheese = ₹10
-   Mushroom = ₹15
-   Extra Cheese again = ₹10

Code:

``` java
BasePizza pizza =
    new ExtraCheese(
        new Mushroom(
            new ExtraCheese(
                new Margherita()
            )
        )
    );

System.out.println(pizza.cost());
```

**Output:**

``` text
135
```

Calculation:

``` text
Margherita       = ₹100
Extra Cheese     = ₹10
Mushroom         = ₹15
Extra Cheese     = ₹10
----------------------
Final Cost       = ₹135
```

This works because every decorator is also a `BasePizza`. Therefore, it
can be passed to another decorator.

------------------------------------------------------------------------

## 7. Complete example with Main class

The following code shows different combinations:

``` java
public class Main {
    public static void main(String[] args) {

        // Only Margherita
        BasePizza pizza1 = new Margherita();
        System.out.println(pizza1.cost());

        // Margherita + Extra Cheese
        BasePizza pizza2 =
            new ExtraCheese(new Margherita());
        System.out.println(pizza2.cost());

        // Margherita + Extra Cheese + Mushroom
        BasePizza pizza3 =
            new Mushroom(
                new ExtraCheese(new Margherita())
            );
        System.out.println(pizza3.cost());

        // Margherita + Cheese + Mushroom + Cheese
        BasePizza pizza4 =
            new ExtraCheese(
                new Mushroom(
                    new ExtraCheese(new Margherita())
                )
            );
        System.out.println(pizza4.cost());
    }
}
```

**Output:**

``` text
100
110
125
135
```

------------------------------------------------------------------------

## 8. Class relationships

The important relationships in this implementation are:

  -----------------------------------------------------------------------
  Class                   Relationship            Meaning
  ----------------------- ----------------------- -----------------------
  `Margherita`            Extends `BasePizza`     A concrete pizza

  `FarmHouse`             Extends `BasePizza`     A concrete pizza

  `ToppingDecorator`      Extends `BasePizza`     A decorator is also a
                                                  pizza

  `ExtraCheese`           Extends                 A concrete topping
                          `ToppingDecorator`      decorator

  `Mushroom`              Extends                 A concrete topping
                          `ToppingDecorator`      decorator

  `ToppingDecorator`      Contains `BasePizza`    Stores the object being
                                                  decorated
  -----------------------------------------------------------------------

The most important relationship is that `ToppingDecorator` both extends
`BasePizza` and contains a `BasePizza` reference.

------------------------------------------------------------------------

## 9. Real-world Java example: Java I/O

Java I/O uses the decorator-style wrapping approach.

``` java
FileInputStream file =
    new FileInputStream("abc.txt");

BufferedInputStream buffered =
    new BufferedInputStream(file);

DataInputStream data =
    new DataInputStream(buffered);
```

Here:

-   `FileInputStream` reads data from a file.
-   `BufferedInputStream` adds buffering.
-   `DataInputStream` provides methods to read Java primitive data
    types.

Each wrapper adds functionality around the underlying stream.

------------------------------------------------------------------------

## 10. Interview questions

### Q1. Why do we use the Decorator Pattern?

It allows us to add new functionality to an existing object dynamically
without modifying its original class.

### Q2. What problem does it solve?

It helps avoid Class Explosion, where we would otherwise need separate
classes for every possible combination of features.

### Q3. Can we apply multiple decorators?

Yes. Since every decorator is also of the base type, we can wrap
multiple decorators around the same object.

### Q4. Is the Decorator Pattern based on inheritance or composition?

It uses both:

-   Inheritance to ensure that decorators follow the same base type.
-   Composition to store the object being decorated.

### Q5. Where is the Decorator Pattern used in Java?

Java I/O streams are a common example. Wrappers such as
`BufferedInputStream` add functionality around an existing input stream.

------------------------------------------------------------------------

## 11. Key points to remember

-   Decorator is a **structural design pattern**.
-   It adds functionality to an object dynamically.
-   It avoids creating a separate class for every feature combination.
-   It uses both inheritance and composition.
-   Multiple decorators can be stacked.
-   A decorator is both an object of the base type and a wrapper around
    another base-type object.
-   It supports the **Open/Closed Principle**: we can introduce new
    decorators without modifying existing pizza classes.

### One-line interview answer

> Decorator Pattern allows us to add new functionalities to an object
> dynamically by wrapping it inside decorator objects, without modifying
> its original class.

### The most important concept

**A decorator IS-A BasePizza and HAS-A BasePizza.**

This is the heart of the Decorator Design Pattern.
