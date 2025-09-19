package FileAnalyser;



	/**
	 *  Classe d exception personnalisee qui represente une situation ou un element specifique ne peut pas etre trouve. Elle etend la classe RuntimeException, qui est une classe d exception qui represente des erreurs qui ne sont pas necessairement detectables lors de la compilation.
	 * @author Ayoub ABDELLI
	 * @since 12-09-2025
	 */
public class ElementNotFoundException extends RuntimeException {
   
	private static final long serialVersionUID = -409678518709814696L;
	 /**
     * Cree une nouvelle instance de l exception avec le message specifie
     *
     * @param message Le message d erreur a afficher.
     */
	public ElementNotFoundException(String message) {
        super(message);
    }
}

