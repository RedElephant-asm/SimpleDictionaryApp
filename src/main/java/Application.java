/**
 * @author Savchenko Kirill
 * @version 1.0
 *
 * Класс содержит точку входа программы.
 */
public class Application {
    public static void main(String[] args) {
        Thread applicationThread = new Thread(new CommandHandler());
        applicationThread.start();
        try {
            applicationThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
