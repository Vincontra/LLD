public class test {
    public static void main(String[] args) {
        long start = System.nanoTime();
        long sum = 0;
        for (long i = 0; i < 10_000_000L; i++)
            sum += (i * i) % 7;
        long end = System.nanoTime();
        System.out.println(sum + "\n" + (end - start) / 1_000_000 + " ms");
    }
}