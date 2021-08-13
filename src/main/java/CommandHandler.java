import org.SimpleDictionaryService.DictionaryService;
import org.SimpleDictionaryService.ExecutionStyle;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Savchenko Kirill
 * @version 1.0
 *
 * Модель потока - обработчика команд.
 */
public class CommandHandler implements Runnable, Interruptible {

    /**
     * Указатель на текущий сервис словаря.
     */
    private DictionaryService dictionaryService;

    /**
     * Переменная - статус выполнения потока.
     * Был выбран AtomicBoolean дабы предотвратить переключение контекста во время выполнения операции изменения статуса
     * потока с последующим неопределенным поведением.
     */
    private AtomicBoolean threadStatus = new AtomicBoolean(true);

    /**
     * Указатель на текущий слушатель консоли.
     */
    private ConsoleListener consoleListener;

    /**
     * Указатель на строку - приглашение консольного приложения.
     */
    private static final String applicationPrompt = "> ";

    /**
     * Булевое значение, идентифицирующее статус поля сервиса словаря, характеризующее указатель на текущий словарь.
     * Было выбрано в целях сокращения операций с использованием null.
     */
    private boolean isDictionarySelected;

    public CommandHandler(){
        consoleListener = new ConsoleListener(this);
        dictionaryService = new DictionaryService();
        dictionaryService.setExecutionStyle(ExecutionStyle.HARD);
    }

    /**
     * Функция является точкой вхождения текущего потока.
     */
    @Override
    public void run() {
        Thread consoleListenerThread = new Thread(consoleListener);
        consoleListenerThread.start();
        String currentCommand = "";
        System.out.println("[ Simple dictionary app ]");
        while (threadStatus.get()){
            System.out.print(applicationPrompt);
            handledWait(consoleListener);
            if ( !consoleListener.getCurrentCommand().isEmpty() ){
                handleCommand(consoleListener.getCurrentCommand());
            }
            handledNotify();
        }
        System.out.println("Bye...");
    }

    /**
     * Назначением функции является завершение текущего потока.
     */
    @Override
    public void closeThread() {
        threadStatus.set(false);
    }

    /**
     * Назначением функции является обработка поступившей от слушателя консоли команды в строковом виде.
     * @param commandString
     * Строковое представление поступившей команды.
     */
    public void handleCommand(String commandString){
        String[] commandParameters = Command.getArgumentsFromCommandBody(commandString);
        Command currentCommand = Command.getCommandFromString(commandString);
        switch (currentCommand){

            case QUIT:
                consoleListener.closeThread();
                this.closeThread();
                break;

            case UNKNOWN_COMMAND:
                System.out.printf("Unknown command \"%s\".\n", commandParameters[0]);
                break;

            case SET_DICTIONARY:
                if (! currentCommand.isNumberOfParametersCorrect(commandParameters.length - 1)){
                    System.out.printf("Incorrect parameters count for command %s. %d parameters expected, %d received.\n", currentCommand.getConsoleEquivalent(), currentCommand.getParametersCount(), commandParameters.length - 1);
                    return;
                }
                currentCommand.execute(dictionaryService, Arrays.copyOfRange(commandParameters, 1, commandParameters.length));
                break;

            default:
                if (! dictionaryService.isDictionarySelected()){
                    System.out.printf("It is required to select a dictionary before work. Use command : \nset_dictionary [filepath] [file encoding] [word language] [key language] [separator]\n");
                    return;
                }

                if (! currentCommand.isNumberOfParametersCorrect(commandParameters.length - 1)){
                    System.out.printf("Incorrect parameters count for command %s. %d parameters expected, %d received.\n", currentCommand.getConsoleEquivalent(), currentCommand.getParametersCount(), commandParameters.length - 1);
                    return;
                }

                currentCommand.execute(dictionaryService, Arrays.copyOfRange(commandParameters, 1, commandParameters.length));
                break;
        }
    }
}