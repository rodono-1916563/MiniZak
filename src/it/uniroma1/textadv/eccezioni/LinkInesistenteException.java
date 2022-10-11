package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che modella l'errore per cui un link non esiste
 * @author Gabriele
 *
 */
public class LinkInesistenteException extends Exception {

	/**
	 * Il costruttore dell'eccezione
	 * @param nomeLink il nome del link che non esiste
	 */
	public LinkInesistenteException(String nomeLink)
	{
		super(nomeLink);
	}
}
