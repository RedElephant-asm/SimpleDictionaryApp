/**
 * @author Savchenko Kirill
 * @version 1.0
 *
 * Класс, содержащий функциональность для цветного вывода.
 */
public class Log {

    /**
     * Цвет обыкновенного выводимого текста.
     */
    private static final String textLogColor = Colors.ANSI_WHITE;

    /**
     *  Цвет выводимых команд.
     */
    private static final String commandLogColor = Colors.ANSI_GREEN;

    /**
     * Цвет выводимых соощений об ошибках.
     */
    private static final String errorLogColor = Colors.ANSI_RED;

    /**
     * Функция вывода окрашенного текста.
     * @param string
     * Выводимый текст.
     */
    public static void textLog(String string){
        System.out.println(Colors.getColoredString(string, textLogColor));
    }

    /**
     * Функция вывода окрашенной команды.
     * @param string
     * Выводимый текст.
     */
    public static void commandLog(String string){
        System.out.println(Colors.getColoredString(string, commandLogColor));
    }

    /**
     * Функция вывода окрашенного сообщения об ошибке.
     * @param string
     * Выводимый текст.
     */
    public static void errorLog(String string){
        System.out.println(Colors.getColoredString(string, errorLogColor));
    }
}
