# Observer Design Pattern in Java

## 1. Introduction

The **Observer Design Pattern** is a behavioural design pattern that establishes a one-to-many relationship between objects.

Whenever the state of one object (Subject) changes, all its registered dependent objects (Observers) are automatically notified.

### In Simple Words

Imagine you subscribe to a YouTube channel.

Whenever that channel uploads a new video, all its subscribers automatically receive a notification.

You don't have to keep checking the channel again and again.

This is exactly what Observer Design Pattern does.

### Real-World Example

Consider a YouTube application with:

- One YouTube Channel.
- Multiple Subscribers.

Whenever the channel uploads a new video, all its subscribers should receive a notification.

The question is:

**How can we design this functionality in Java using Object-Oriented Programming?**

---

## 2. Understanding the Problem

Let's assume we have a YouTube channel and three subscribers:

- Vinay
- Rahul
- Amit

Whenever the channel uploads a new video, all three subscribers should be notified.

Initially, we might try implementing this using a simple approach.

### Approach 1: Directly Calling Subscribers

```java
class YoutubeChannel {

    public void uploadVideo() {

        System.out.println("New Video Uploaded");

        subscriber1.notify();
        subscriber2.notify();
        subscriber3.notify();
    }
}
```

This approach might work for a small application, but it introduces several problems.

### Problems with This Approach

**1. Tight Coupling**

The YoutubeChannel class directly depends on individual subscriber objects.

It knows exactly whom to notify.

**2. Difficult to Maintain**

Imagine tomorrow we have 100 subscribers.

We cannot keep adding individual notification calls inside the YoutubeChannel class.

**3. Difficult to Manage Subscriptions**

What happens when:

- A new subscriber joins?
- An existing subscriber unsubscribes?
- We want to add another type of notification?

We would have to modify the channel's implementation repeatedly.

**4. Poor Extensibility**

The channel should not be responsible for knowing the internal implementation of every subscriber.

It should simply notify all registered subscribers.

### Actual Problem

We are mixing two responsibilities:

- Managing the channel and its videos.
- Managing individual subscriber notifications.

We need to separate these responsibilities.

This is where the **Observer Design Pattern** comes into the picture.

---

## 3. Observer Design Pattern

The main idea of Observer Pattern is to create a mechanism where multiple objects can register themselves with another object.

Whenever that object's state changes, it automatically notifies all registered objects.

There are two main components:

### 1. Subject

The Subject is the object whose state changes.

It maintains a list of registered observers and notifies them whenever its state changes.

In our example:

`YoutubeChannel` is the Subject.

### 2. Observer

Observers are the objects that want to receive notifications whenever the Subject changes.

In our example:

`Subscribers` are the Observers.

### Mapping Our Example

| Component | YouTube Example |
|---|---|
| Subject | YouTube Channel |
| Observer | Subscriber |
| State Change | New video uploaded |
| Notification | New video notification |
| Subscribe | Register as a subscriber |
| Unsubscribe | Remove a subscriber |
| Notify | Send updates to all subscribers |

---

## 4. Implementation Using Java

Let's implement Observer Pattern step by step.

### Step 1: Create Observer Interface

First, we create an interface called `Subscriber`.

```java
interface Subscriber {
    void update(String videoName);
}
```

### Why an Interface?

Because every subscriber should follow a common contract.

Whenever a subscriber receives a notification, its `update()` method will be called.

Any class implementing this interface must provide its own implementation of the `update()` method.

### Step 2: Create Concrete Observers

Now, we create a class called `User` that implements the Subscriber interface.

```java
class User implements Subscriber {

    String name;

    User(String name) {
        this.name = name;
    }

    @Override
    public void update(String videoName) {
        System.out.println(name +
            " received notification: " + videoName);
    }
}
```

### Understanding the Code

- `User` implements the `Subscriber` interface.
- Each User object represents an individual subscriber.
- The `name` variable stores the subscriber's name.
- The `update()` method defines how that particular subscriber handles a notification.

Notice that we are not directly connecting individual users to the YouTube Channel.

We are using a common interface.

### Step 3: Create Subject Interface

Now we need an interface for our YouTube Channel.

```java
interface Channel {

    void subscribe(Subscriber subscriber);

    void unsubscribe(Subscriber subscriber);

    void notifySubscribers();
}
```

This interface defines three important methods.

**1. subscribe()**

Adds a new subscriber to the channel.

**2. unsubscribe()**

Removes an existing subscriber from the channel.

**3. notifySubscribers()**

Notifies all registered subscribers whenever the channel's state changes.

### Step 4: Implement the Subject

Now comes the most important part.

We create a concrete class called `YoutubeChannel` that implements the Channel interface.

```java
import java.util.*;

class YoutubeChannel implements Channel {

    List<Subscriber> subscribers = new ArrayList<>();

    String videoName;

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers() {

        for (Subscriber subscriber : subscribers) {
            subscriber.update(videoName);
        }
    }

    public void uploadVideo(String videoName) {

        this.videoName = videoName;

        System.out.println("New Video Uploaded: " + videoName);

        notifySubscribers();
    }
}
```

### Understanding the YoutubeChannel Class

Let's understand each part carefully.

#### 1. Maintaining the Subscriber List

```java
List<Subscriber> subscribers = new ArrayList<>();
```

We maintain a list of all registered subscribers.

Why are we using `List<Subscriber>` instead of `List<User>`?

Because we want to depend on the interface rather than a specific implementation.

Any class implementing the Subscriber interface can register with our channel.

This makes the design flexible and loosely coupled.

#### 2. Subscribe Method

```java
public void subscribe(Subscriber subscriber) {
    subscribers.add(subscriber);
}
```

Whenever someone subscribes to the channel, their object is added to the subscriber list.

#### 3. Unsubscribe Method

```java
public void unsubscribe(Subscriber subscriber) {
    subscribers.remove(subscriber);
}
```

Whenever someone unsubscribes, their object is removed from the list.

After removal, they will no longer receive notifications from this channel.

#### 4. Notify Subscribers Method

```java
public void notifySubscribers() {

    for (Subscriber subscriber : subscribers) {
        subscriber.update(videoName);
    }
}
```

This is the heart of Observer Pattern.

We iterate through the entire subscriber list.

For every registered subscriber, we call its `update()` method.

Since we are using an interface reference, Java executes the implementation provided by the actual subscriber object.

Each subscriber independently handles the notification.

#### 5. Upload Video Method

```java
public void uploadVideo(String videoName) {

    this.videoName = videoName;

    System.out.println("New Video Uploaded: " + videoName);

    notifySubscribers();
}
```

Whenever a new video is uploaded:

1. We update the channel's state by storing the video name.
2. We print the upload message.
3. We call `notifySubscribers()`.
4. Every registered subscriber receives the update.

The channel does not need to know how individual subscribers handle the notification.

---

## 5. Complete Execution Flow

Now let's understand the complete implementation with an actual example.

```java
public class Main {

    public static void main(String[] args) {

        YoutubeChannel channel = new YoutubeChannel();

        Subscriber user1 = new User("Vinay");
        Subscriber user2 = new User("Rahul");
        Subscriber user3 = new User("Amit");

        // Users subscribe to the channel
        channel.subscribe(user1);
        channel.subscribe(user2);
        channel.subscribe(user3);

        // Channel uploads a video
        channel.uploadVideo("Observer Design Pattern");

        // Rahul unsubscribes
        channel.unsubscribe(user2);

        // Channel uploads another video
        channel.uploadVideo("Strategy Design Pattern");
    }
}


### Step-by-Step Execution

#### Step 1: Create the YouTube Channel

YoutubeChannel channel = new YoutubeChannel();

We create an object of YoutubeChannel.

Initially, its subscriber list is empty.

#### Step 2: Create Subscribers

Subscriber user1 = new User("Vinay");
Subscriber user2 = new User("Rahul");
Subscriber user3 = new User("Amit");


We create three User objects.

Each object represents a subscriber.

#### Step 3: Subscribe Users


channel.subscribe(user1);
channel.subscribe(user2);
channel.subscribe(user3);


All three users are added to the subscriber list.

The list now contains:


[Vinay, Rahul, Amit]


#### Step 4: Upload First Video


channel.uploadVideo("Observer Design Pattern");


The channel updates its state and calls `notifySubscribers()`.

All three registered users receive notifications.

#### Step 5: Rahul Unsubscribes

channel.unsubscribe(user2);


Rahul is removed from the subscriber list.

The updated list becomes:


[Vinay, Amit]


#### Step 6: Upload Second Video


channel.uploadVideo("Strategy Design Pattern");


This time, only Vinay and Amit receive notifications.

Rahul does not receive any notification because he has unsubscribed.

### Output


New Video Uploaded: Observer Design Pattern

Vinay received notification: Observer Design Pattern
Rahul received notification: Observer Design Pattern
Amit received notification: Observer Design Pattern

New Video Uploaded: Strategy Design Pattern

Vinay received notification: Strategy Design Pattern
Amit received notification: Strategy Design Pattern


### Execution Flow Diagram


              YoutubeChannel
                  Subject
                     |
          -----------------------
          |          |          |
        Vinay      Rahul       Amit
       Observer   Observer    Observer
          |          |          |
          -----------------------
                     |
                New Video
                  Uploaded
                     |
               notifySubscribers()
                     |
          -----------------------
          |          |          |
         update()   update()   update()
          |          |          |
        Notify     Notify     Notify
         Vinay      Rahul      Amit
```

The same notification method is called for every observer.

Java's runtime polymorphism ensures that each observer executes its own `update()` implementation.

---

## 6. Understanding the Architecture

Observer Pattern consists of four important parts.

| Component | Responsibility |
|---|---|
| Subject Interface | Defines subscribe, unsubscribe and notify operations |
| Concrete Subject | Maintains observers and manages state changes |
| Observer Interface | Defines the update contract |
| Concrete Observer | Implements how notifications are handled |

### Architecture Diagram

```text
              Channel
          (Subject Interface)
                  |
                  |
            YoutubeChannel
          (Concrete Subject)
                  |
                  |
          List<Subscriber>
                  |
                  |
             Subscriber
          (Observer Interface)
                  |
          -------------------
          |                 |
         User           MobileApp
    (Concrete Observer) (Concrete Observer)


### Important Relationship

The Subject maintains references to Observer objects through the Observer interface.

It does not need to know the internal implementation of each observer.

This is called **Loose Coupling**.

---

## 7. Adding Another Type of Observer

One important advantage of Observer Pattern is that we can add different types of observers without modifying the existing Subject.

For example, imagine we want to send notifications to a mobile application as well.

We can create another class implementing the Subscriber interface.

```java
class MobileApp implements Subscriber {

    @Override
    public void update(String videoName) {
        System.out.println("Mobile App Notification: " + videoName);
    }
}


Now we can register it with the existing YouTube Channel.


Subscriber mobile = new MobileApp();

channel.subscribe(mobile);


Whenever a new video is uploaded, the mobile application will also receive the notification.

We do not need to modify the YoutubeChannel class.

This makes the design extensible.

---

## 8. Important OOP Concepts Used

### 1. Interface

We use the Subscriber interface to define a common contract for all observers.

### 2. Polymorphism

Different subscriber objects implement the same `update()` method differently.

The Subject calls the same method, but the actual implementation depends on the object.

### 3. Composition

The YoutubeChannel HAS-A collection of Subscriber objects.

The channel maintains references to its observers.

### 4. Encapsulation

Each observer handles its own notification logic inside its `update()` method.

### 5. Loose Coupling

The Subject depends on the Subscriber interface rather than concrete subscriber implementations.

This allows us to add or remove observer types without changing the Subject.

---

## 9. Real-World Applications

Observer Pattern is useful whenever multiple objects need to react to changes in another object.

### 1. Stock Market Application

When a stock price changes, all registered investors receive the latest update.

```text
Stock Price Changes
        |
        v
  Stock Exchange
        |
  -----------------
  |       |       |
Investor Investor Investor
```

### 2. Weather Application

When weather information changes, multiple displays can automatically update.

```text
Weather Data Changes
        |
        v
   Weather Station
        |
  --------------------
  |         |        |
Mobile    Website   Display
```

### 3. E-Commerce Application

Whenever an order status changes, different components can receive updates.

For example:

- Customer receives a notification.
- Seller receives an update.
- Order tracking system updates its status.

### 4. GUI Event Listeners

When a user clicks a button, registered event listeners execute their respective actions.

For example, Java's event-listener mechanism follows the Observer concept.

---

## 10. Strategy Pattern vs Observer Pattern

Both are behavioural design patterns, but they solve different problems.

| Strategy Pattern | Observer Pattern |
|---|---|
| Focuses on changing behaviour | Focuses on notifying multiple objects |
| Defines a family of interchangeable algorithms | Defines a one-to-many notification mechanism |
| One class uses a selected strategy | One Subject notifies multiple Observers |
| Uses composition and polymorphism | Uses registration and notification |
| Example: Payment methods | Example: YouTube notifications |

### Remember This Difference

**Strategy Pattern:**

Which behaviour should I use?

**Observer Pattern:**

Who should I notify when something changes?

---

## 11. Advantages of Observer Pattern

1. **Loose Coupling:** Subject and Observers communicate through interfaces.
2. **Easy Extensibility:** New observers can be added without modifying the Subject.
3. **Dynamic Subscription:** Observers can subscribe and unsubscribe at runtime.
4. **Automatic Notifications:** All registered observers receive updates whenever the Subject changes.
5. **Separation of Responsibilities:** Subject manages state and notifications, while Observers handle their own update logic.
6. **Reusability:** Observer implementations can work with different subjects that follow the same contract.

---

## 12. Disadvantages of Observer Pattern

1. **Additional Classes:** It introduces extra interfaces and implementation classes.
2. **Notification Overhead:** Notifying a large number of observers can take additional processing time.
3. **Unpredictable Notification Order:** Unless explicitly controlled, the notification order should not be relied upon.
4. **Memory Management:** If observers are not properly unsubscribed, the Subject may continue holding references to them.
5. **Debugging Complexity:** In large applications, it can become difficult to track which observers are triggered by a state change.

---

## 13. When Should We Use Observer Pattern?

Use Observer Pattern when:

1. Multiple objects need to be notified whenever another object's state changes.
2. You want to establish a one-to-many relationship between objects.
3. You want objects to subscribe and unsubscribe dynamically.
4. You want to avoid tightly coupling the Subject with individual observers.
5. You want to add new notification receivers without modifying existing business logic.

---

## 14. Interview Preparation

### Q1. What is Observer Design Pattern?

Observer Pattern is a behavioural design pattern that establishes a one-to-many relationship between objects.

Whenever the state of one object (Subject) changes, all its registered dependent objects (Observers) are automatically notified.

**In simple words:**

One object changes, and all the objects interested in that change automatically receive notifications.

### Q2. What is a Subject?

A Subject is an object that maintains a list of observers and notifies them whenever its state changes.

In our example, YoutubeChannel is the Subject.

### Q3. What is an Observer?

An Observer is an object that wants to receive updates whenever the Subject changes.

In our example, User is the Observer.

### Q4. Why do we use an interface for Observers?

We use an interface to define a common contract.

This allows the Subject to work with different observer implementations without depending on specific classes.

### Q5. What is the purpose of subscribe() and unsubscribe()?

- `subscribe()` registers an observer with the Subject.
- `unsubscribe()` removes an observer from the Subject.

### Q6. How does Observer Pattern achieve loose coupling?

The Subject depends on the Observer interface rather than concrete observer classes.

Therefore, new observer implementations can be added without modifying the Subject.

### Q7. What happens when a new observer is added?

The new observer is registered with the Subject.

Whenever the Subject changes its state and sends notifications, the new observer also receives the update.

### Q8. Is Observer Pattern a behavioural design pattern?

Yes. It is a behavioural design pattern because it focuses on communication between objects and how they react to state changes.

---

## 15. Final Conclusion

Let's summarize what we learned.

Initially, we tried directly calling individual subscribers from the YoutubeChannel class.

However, this created tight coupling and made the application difficult to maintain.

To solve this problem, we introduced Observer Pattern.

We:

1. Created a Subscriber interface for all observers.
2. Created concrete observers that implement the interface.
3. Created a Channel interface defining subscribe, unsubscribe and notify operations.
4. Implemented YoutubeChannel as the concrete Subject.
5. Maintained a list of registered subscribers.
6. Automatically notified all registered subscribers whenever a new video was uploaded.

### Final Execution Flow

```text
Observer Registers with Subject
             |
             v
     Subject State Changes
             |
             v
     notifySubscribers()
             |
             v
    Iterate Through Observers
             |
             v
      Call update() Method
             |
             v
  All Registered Observers Notified
```

### One-Line Summary

**Observer Pattern is used when we want multiple objects to get automatically notified whenever the state of another object changes, without tightly coupling them.**

That's the core idea behind the Observer Design Pattern in Java.

---

## References

- [Observer Design Pattern – Refactoring Guru](https://refactoring.guru/design-patterns/observer)
- [Observer Pattern – Wikipedia](https://en.wikipedia.org/wiki/Observer_pattern)
- [Java Observer Pattern Concepts – Oracle Documentation](https://docs.oracle.com/javase/tutorial/uiswing/events/intro.html)