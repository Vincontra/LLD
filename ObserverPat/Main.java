package ObserverPat;

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
