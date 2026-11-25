package ObserverPat;

class User implements Subscriber {

    String name;

    User(String name) {
        this.name = name;
    }

    @Override
    public void update(String videoName) {
        System.out.println(name+" received notification: " + videoName);
    }
}