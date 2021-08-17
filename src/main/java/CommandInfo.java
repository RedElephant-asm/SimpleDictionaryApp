/**
 * @author Savchenko Kirill
 * @version 1.0
 *
 * Класс - контейнер для информации о командых.
 */
public class CommandInfo {

    private static final CommandInfo[] commandInfo = {
            new CommandInfo("set_dictionary", "Назначением команды является установка текущего словаря.\nАргумент 1 : полное имя файла словаря\nАргумент 2 : кодировка словаря\nАргумент 3 : язык слова\nАргумент 4 : язык ключа\nАргумент 5 : разделитель", "set_dictionary C:\\test.txt utf8 russian latin /"),
            new CommandInfo("add_record", "Назначением команды является добавление записи в текущий словарь.\nАргумент 1 : ключ записи\nАргумент 2 : слово записи", "add_record new Запись"),
            new CommandInfo("delete_record", "Назначением команды является удаление записи по ключу.\nАргумент 1 : ключ записи", "delete_record new"),
            new CommandInfo("read_all", "Назначением команды является вывод всех записей текущего словаря на экран.", "read_all"),
            new CommandInfo("update_record", "Назначением функции является обновление слова по ключу.\nАргумент 1 : ключ записи\nАргумент 2 : новое слово", "update_record new Новый1"),
            new CommandInfo("write_dictionary", "Назначением функции является запись содержимого словаря на диск.", "write_dictionary"),
            new CommandInfo("help", "Назначением команды является вывод информации обо всех существующих командах на экран.", "help"),
            new CommandInfo("quit", "Назначением команд является выход из приложения с последующим его закрытием.", "quit")
    };

    private String name;

    private String description;

    private String example;

    private CommandInfo(String name, String description, String example) {
        this.name = name;
        this.description = description;
        this.example = example;
    }

    @Override
    public String toString() {
        return String.format("Command : %s\nDescription : %s\nExample : %s\n", name, description, example);
    }

    public static CommandInfo[] getCommandInfo() {
        return commandInfo;
    }
}
