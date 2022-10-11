package it.uniroma1.textadv.eccezioni;


/**
 * Eccezione che modella l'errore che può verificarsi durante il tentativo 
 * di riempire un'entità
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
