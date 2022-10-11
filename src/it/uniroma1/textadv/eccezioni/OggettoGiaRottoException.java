package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che viene sollevata se si prova a rompere un'entit�/link gi� rotta.
 * 
 * @author Gabriele
 *
 */
public class OggettoGiaRottoException extends Exception {

	/**
	 * Il costruttore dell'eccezione
	 * @param oggRotto il nome dell'oggetto gi� rotto
	 */
	public OggettoGiaRottoException(String oggRotto)
	{
		super(oggRotto);
	}
}
