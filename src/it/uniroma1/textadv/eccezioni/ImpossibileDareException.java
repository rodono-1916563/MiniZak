package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che modella l'errore che può verificarsi nel dare un entità
 * 
 * @author Gabriele
 *
 */
public class ImpossibileDareException extends Exception {

	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public ImpossibileDareException(String messaggio)
	{
		super(messaggio);
	}
}
