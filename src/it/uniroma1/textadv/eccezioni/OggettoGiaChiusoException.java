package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che viene sollevata se si prova a chiudere un'entità/link già chiusa.
 * 
 * @author Gabriele
 *
 */
public class OggettoGiaChiusoException extends Exception 
{

	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public OggettoGiaChiusoException(String messaggio)
	{
		super(messaggio);
	}
}
