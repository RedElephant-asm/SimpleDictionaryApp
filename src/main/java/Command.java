import org.SimpleDictionaryService.Dictionary;
import org.SimpleDictionaryService.DictionaryRecord;
import org.SimpleDictionaryService.DictionaryService;
import org.SimpleDictionaryService.language.Language;
import org.SimpleDictionaryService.throwable.UnknownEncodingException;
import org.SimpleDictionaryService.throwable.UnknownLanguageException;
import org.SimpleEncodings.Encoding;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Arrays;

/**
 * @author Savchenko Kirill
 * @version 1.0
 *
 * Перечисление, которое обьединяет модели возможных команд.
 */
public enum Command {

    SET_DICTIONARY           (5){

        @Override
        public void execute(DictionaryService context, String[] parameters) {
            Dictionary newDictionary = new Dictionary(
                    parameters[0],
                    Encoding.getEncodingByName(parameters[1]),
                    Language.getLanguageByName(parameters[2]),
                    Language.getLanguageByName(parameters[3]),
                    parameters[4]
            );
            if (!newDictionary.exists()){
                System.out.printf("Dictionary file \"%s\" does not exists!\n", parameters[0]);
                return;
            }
            try {
                context.setCurrentDictionary(newDictionary);
            }catch (UnknownEncodingException exception){
                System.out.printf("Unknown encoding \"%s\".\n", parameters[1]);
            }catch (UnknownLanguageException exception){
                System.out.printf("Unknown word or key language.\n");
            }
        }
    },

    ADD_RECORD          (2){

        @Override
        public void execute(DictionaryService context, String[] parameters) {
            context.createRecord(new DictionaryRecord(parameters[0], parameters[1]));
        }
    },

    DELETE_RECORD       (1){

        @Override
        public void execute(DictionaryService context, String[] parameters) {
            context.deleteRecord(parameters[0]);
        }
    },

    READ_ALL            (0){

        @Override
        public void execute(DictionaryService context, String[] parameters) {
            context.getDictionaryData().forEach(record -> System.out.printf("%s %s\n", record.getKey(), record.getWord()));
        }
    },

    UPDATE_RECORD       (2){

        @Override
        public void execute(DictionaryService context, String[] parameters) {
            context.updateRecord(parameters[0], parameters[1]);
        }
    },

    WRITE_DICTIONARY    (0){

        @Override
        public void execute(DictionaryService context, String[] parameters) {
            context.writeDictionary();
        }
    },

    HELP                (0){

        @Override
        public void execute(DictionaryService context, String[] parameters) {
            for (CommandInfo commandInfo : CommandInfo.getCommandInfo()) {
                System.out.println(commandInfo.toString());
            }
        }
    },

    QUIT                (0){

        @Override
        public void execute(DictionaryService context, String[] parameters) {}
    },

    UNKNOWN_COMMAND     (0){

        @Override
        public void execute(DictionaryService context, String[] parameters) {}
    };

    /**
     * Количество параметров той или иной команды.
     */
    private int parametersCount;

    Command(int parametersCount){
        this.parametersCount = parametersCount;
    }

    /**
     * Основной метод, содержащий функционал той или иной команды. Предназначен для перегрузки с последующим преданием коду
     * метода свойств, характерных той или иной командой.
     * @param context
     * Контекст выполнения(указатель на текущий сервис словарей).
     * @param parameters
     * Параметры для выполнения той или иной команды.
     */
    public abstract void execute(DictionaryService context, String[] parameters);

    /**
     * Назначением функции является извлечение агрументов команды из строки, полученныой посредством ввода с клавиатуры.
     * @param commandBody
     * Строка, содержащая агрументы команды.
     * @return
     * Указатель на массив строк, каждая из которых представляет отдельный аргумент команды.
     */
    public static String[] getArgumentsFromCommandBody(String commandBody){
        return commandBody.replaceAll("\\s{2,}", " ").split(" ");
    }

    /**
     * Назначением функции является извлечение названия команды из строки, с последующим поиском указателя на команду, имя которой
     * соответствует извлеченному имени
     * @param commandBody
     * Строка, полученная из терминалом посредством набора с клавиатуры.
     * @return
     * Указатель на команду, имя которой соответствует имени, извлеченному из строки, если такая команда находится,
     * в противном слуячае UNKNOWN_COMMAND.
     */
    public static Command getCommandFromString(String commandBody){
        String commandName = getArgumentsFromCommandBody(commandBody)[0];
        if(Arrays.stream(Command.values()).anyMatch(currentCommand -> currentCommand.getConsoleEquivalent().equals(commandName))){
            return Arrays.stream(Command.values()).filter(currentCommand -> currentCommand.getConsoleEquivalent().equals(commandName)).findFirst().get();
        }else return UNKNOWN_COMMAND;
    }

    /**
     * Назначением метода является проверка сопоставление поступившего количества аргументов, с количеством аргументов,
     * треюуемых для выполнения той или иной команды.
     * @param receivedCount
     * Входящее количество аргументов.
     * @return
     * true, если вхлдящее количество аргументов соответствует требуемому количеству аргументов,
     * в противном случае false.
     */
    public boolean isNumberOfParametersCorrect(int receivedCount){
        if (receivedCount != this.parametersCount){
            return false;
        }
        return true;
    }

    /**
     * Назначением функции является вывод информации обо всех известны командах.
     */
    public static void printCommandsInfo(){
        System.out.println("");
    }

    public int getParametersCount() {
        return parametersCount;
    }

    public String getConsoleEquivalent() {
        return this.name().toLowerCase();
    }
}
