package Model;

public class TextColor {
	public static final String RESET = "\u001B[0m";      // Réinitialise la couleur
    public static final String BOLD = "\u001B[1m";		// Gras
    public static final String UNDERLINE = "\u001B[4m"; // Souligné
    
    public static final String GREEN = "\u001B[32m";     // Texte vert
    public static final String CYAN = "\u001B[36m";      // Texte cyan
    public static final String MAGENTA = "\u001B[35m";  // Texte magenta

    
    public static final String CYANBOLD = "\u001B[36m\u001B[1m";		// Gras cyan
    public static final String CYANUNDERLINE = "\u001B[36m\u001B[4m"; // Souligné cyan
    public static final String CYANUNDERLINEBOLD = "\u001B[36m\u001B[1m\u001B[4m"; // Gras Souligné cyan

    
    public static final String MAGENTABOLD = "\u001B[35m\u001B[1m";		// Gras magenta
    public static final String MAGENTAUNDERLINE = "\u001B[35m\u001B[4m"; // Souligné magenta
    public static final String MAGENTAUNDERLINEBOLD = "\u001B[35m\u001B[1m\u001B[4m"; // Souligné gras magenta

    
    public static final String GREENBOLD = "\u001B[32m\u001B[1m";		// Gras vert
    public static final String GREENUNDERLINE = "\u001B[32m\u001B[4m"; // Souligné vert
    public static final String GREENUNDERLINEBOLD = "\u001B[32m\u001B[1m\u001B[4m"; // Souligné gras vert 

}
