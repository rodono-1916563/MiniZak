package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che modella gli errori che posso verificarsi durante l'acquisto
 * @author Gabriele
 *
 */
public class ImpossibileComprareException extends Exception {

	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public ImpossibileComprareException(String messaggio)
	{
		super(messaggio);
	}
}
