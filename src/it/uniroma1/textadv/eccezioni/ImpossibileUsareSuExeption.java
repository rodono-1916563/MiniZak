package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che modella l'errore che si può verificare quando si vuole usare un'entità
 * su di un'altra
 * 
 * @author Gabriele
 *
 */
public class ImpossibileUsareSuExeption extends Exception {

	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public ImpossibileUsareSuExeption(String messaggio)
	{
		super(messaggio);
	}
}
