package it.uniroma1.textadv.eccezioni;


/**
 * Eccezione che modella l'errore che si pu� verificare quando si vuole rompere un'entit�
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
