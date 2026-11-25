package ObserverPat;

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