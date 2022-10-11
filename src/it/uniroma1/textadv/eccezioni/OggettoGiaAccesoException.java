package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che viene sollevata se si prova ad accendere un'entit�/link gi� accesa.
 * 
 * @author Gabriele
 *
 */
public class OggettoGiaAccesoException extends Exception {

	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public OggettoGiaAccesoException(String messaggio)
	{
		super(messaggio);
	}
}
