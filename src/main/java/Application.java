import java.util.concurrent.TimeUnit;

public class Application {

    private static Database database;

    public static void main(String[] args) throws InterruptedException {
        try {
            database = new Database("./MyDb");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            database.create("Prayas","{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}",100);
            database.create("Prateek","{\"name\":\"Prateek\",\"salary\":1200000.0,\"age\":25}",10);
            database.create("Raj","{\"name\":\"Raj\",\"salary\":120000.0,\"age\":25}",100);
            database.create("Raj","{\"name\":\"Raj\",\"salary\":120000.0,\"age\":25}",100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TimeUnit.SECONDS.sleep(10);
        try {
            System.out.println(database.read("Prayas"));
            System.out.println(database.read("Prateek"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            database.delete("Raj");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            database.read("Raj");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
