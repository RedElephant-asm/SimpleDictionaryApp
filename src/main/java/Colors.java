public class Colors {
    private static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static String getColoredString(String string, String colorCode){
        if (colorCode.equals(ANSI_BLACK) || colorCode.equals(ANSI_RED) || colorCode.equals(ANSI_GREEN) || colorCode.equals(ANSI_YELLOW) || colorCode.equals(ANSI_BLUE) ||
            colorCode.equals(ANSI_PURPLE) || colorCode.equals(ANSI_CYAN) || colorCode.equals(ANSI_WHITE)){
            return String.format("%s%s%s", colorCode, string, ANSI_RESET);
        }
        return string;
    }

}
