package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che viene sollevata se si prova a svuotare un'entit�/link gi� vuota.
 * 
 * @author Gabriele
 *
 */
public class OggettoGiaVuotoException extends Exception {

	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public OggettoGiaVuotoException(String messaggio)
	{
		super(messaggio);
	}
}
