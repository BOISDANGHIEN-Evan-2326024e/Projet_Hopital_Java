package Model;

public class TextColor {
    // Réinitialisation
    public static final String RESET = "\u001B[0m";      // Réinitialise la couleur

    // Styles généraux
    public static final String BOLD = "\u001B[1m";       // Gras
    public static final String UNDERLINE = "\u001B[4m";  // Souligné

    // Couleurs standard
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // Couleurs vives
    public static final String BRIGHT_BLACK = "\u001B[90m";
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_MAGENTA = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";

    // Combinaisons : Couleur + Gras
    public static final String BLACK_BOLD = BLACK + BOLD;
    public static final String RED_BOLD = RED + BOLD;
    public static final String GREEN_BOLD = GREEN + BOLD;
    public static final String YELLOW_BOLD = YELLOW + BOLD;
    public static final String BLUE_BOLD = BLUE + BOLD;
    public static final String MAGENTA_BOLD = MAGENTA + BOLD;
    public static final String CYAN_BOLD = CYAN + BOLD;
    public static final String WHITE_BOLD = WHITE + BOLD;

    public static final String BRIGHT_BLACK_BOLD = BRIGHT_BLACK + BOLD;
    public static final String BRIGHT_RED_BOLD = BRIGHT_RED + BOLD;
    public static final String BRIGHT_GREEN_BOLD = BRIGHT_GREEN + BOLD;
    public static final String BRIGHT_YELLOW_BOLD = BRIGHT_YELLOW + BOLD;
    public static final String BRIGHT_BLUE_BOLD = BRIGHT_BLUE + BOLD;
    public static final String BRIGHT_MAGENTA_BOLD = BRIGHT_MAGENTA + BOLD;
    public static final String BRIGHT_CYAN_BOLD = BRIGHT_CYAN + BOLD;
    public static final String BRIGHT_WHITE_BOLD = BRIGHT_WHITE + BOLD;

    
}
