package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che viene sollevata se si prova a spegnere un'entità/link già spenta.
 * 
 * @author Gabriele
 *
 */
public class OggettoGiaSpentoException extends Exception {

	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public OggettoGiaSpentoException(String messaggio)
	{
		super(messaggio);
	}
}
