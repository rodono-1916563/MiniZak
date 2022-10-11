package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che viene sollevata se si prova ad aprire un'entità/link già aperta.
 * 
 * @author Gabriele
 *
 */
public class OggettoGiaApertoException extends Exception 
{

	/**
	 * Il costruttore dell'eccezione
	 * @param nomeOggetto il nome dell'oggetto già aperto
	 */
	public OggettoGiaApertoException(String nomeOggetto)
	{
		super(nomeOggetto);
	}
}
