import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Savchenko Kirill
 * @version 1.0
 *
 * Модель потока - слушателя консоли.
 */
public class ConsoleListener implements Runnable, Interruptible{

    /**
     * Указатель на строку, которая является буффером принимаемых команд слушателя консоли.
     */
    private String currentCommand = "";

    /**
     * Указатель на поток - обработчик команд, с которым будет сообщаться поток - слушатель консоли.
     */
    private CommandHandler commandHandler;

    /**
     * Булевое значение, идентифицирующее статус поля сервиса словаря, характеризующее указатель на текущий словарь.
     * Было выбрано в целях сокращения операций с использованием null.
     */
    private AtomicBoolean threadStatus = new AtomicBoolean(true);

    public ConsoleListener(CommandHandler commandHandler){
        this.commandHandler = commandHandler;
    }

    /**
     * Функция является точкой вхождения текущего потока.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (threadStatus.get()){
            if(scanner.hasNextLine()){
                currentCommand = scanner.nextLine();
                handledNotify();
                handledWait(commandHandler);
            }
        }
    }

    /**
     * Назначением функции является завершение текущего потока.
     */
    @Override
    public void closeThread() {
        this.threadStatus.set(false);
    }

    public String getCurrentCommand() {
        return currentCommand;
    }
}
