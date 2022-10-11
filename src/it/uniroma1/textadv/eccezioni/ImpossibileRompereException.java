package it.uniroma1.textadv.eccezioni;


/**
 * Eccezione che modella l'errore che si può verificare quando si vuole rompere un'entità
 * @author Gabriele
 *
 */
public class ImpossibileRompereException extends Exception 
{
	
	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public ImpossibileRompereException(String messaggio)
	{
		super(messaggio);
	}
}
