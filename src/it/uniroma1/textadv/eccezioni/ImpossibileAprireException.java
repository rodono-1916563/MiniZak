package it.uniroma1.textadv.eccezioni;


/**
 * Eccezione che modella gli errori nel cercare di aprire un'entità/link
 * 
 * @author Gabriele
 *
 */
public class ImpossibileAprireException extends Exception 
{
	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public ImpossibileAprireException(String messaggio)
	{
		super(messaggio);
	}
}
