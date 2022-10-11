package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che modella l'errore che impedisce al giocatore di giocare
 * @author Gabriele
 *
 */
public class ImpossibileGiocareException extends Exception {

	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public ImpossibileGiocareException(String messaggio)
	{
		super(messaggio);
	}
}
