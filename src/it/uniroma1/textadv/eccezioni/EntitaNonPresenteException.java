package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che modella l'errore dato dall'inesistenza di un'entit�
 * 
 * @author Gabriele
 *
 */
public class EntitaNonPresenteException extends Exception 
{

	/**
	 * Il costruttore dell'eccezione
	 * @param nomeEntita il nome dell'entit� che non esiste
	 */
	public EntitaNonPresenteException(String nomeEntita)
	{
		super(nomeEntita);
	}
}
