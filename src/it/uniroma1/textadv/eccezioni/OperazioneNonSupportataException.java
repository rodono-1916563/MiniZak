package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che viene sollevata se un operazione non è supportata nel gioco
 * 
 * @author Gabriele
 *
 */
public class OperazioneNonSupportataException extends Exception 
{

	/**
	 * Il costruttore dell'eccezione
	 * @param operazione il nome dell'operazione non supportata
	 */
	public OperazioneNonSupportataException(String operazione)
	{
		super(operazione);
	}
}
