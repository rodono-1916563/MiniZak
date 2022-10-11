package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che modella l'errore dato dall'inesistenza di un'entità
 * 
 * @author Gabriele
 *
 */
public class EntitaNonPresenteException extends Exception 
{

	/**
	 * Il costruttore dell'eccezione
	 * @param nomeEntita il nome dell'entità che non esiste
	 */
	public EntitaNonPresenteException(String nomeEntita)
	{
		super(nomeEntita);
	}
}
