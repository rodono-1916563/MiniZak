package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che modella l'errore che pu� verificarsi durante la rimozione di un'entit�
 * @author Gabriele
 *
 */
public class ImpossibileRimuovereException extends Exception {

	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public ImpossibileRimuovereException(String messaggio)
	{
		super(messaggio);
	}
}
