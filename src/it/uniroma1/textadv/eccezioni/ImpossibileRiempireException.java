package it.uniroma1.textadv.eccezioni;


/**
 * Eccezione che modella l'errore che pu� verificarsi durante il tentativo 
 * di riempire un'entit�
 * 
 * @author Gabriele
 *
 */
public class ImpossibileRiempireException extends Exception {

	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public ImpossibileRiempireException(String messaggio)
	{
		super(messaggio);
	}
}
