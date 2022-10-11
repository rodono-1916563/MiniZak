package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che modella l'impossibilità di creare il mondo
 * @author Gabriele
 *
 */
public class ImpossibileCreareMondoException extends Exception {

	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public ImpossibileCreareMondoException(String messaggio)
	{
		super(messaggio);
	}
}
