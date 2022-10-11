package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che modella gli errori di configurazione
 * 
 * @author Gabriele
 *
 */
public class ConfigurazioneNonPossibileException extends Exception
{

	/**
	 * Il costruttore dell'eccezione
	 * @param messaggio il messaggio dell'errore
	 */
	public ConfigurazioneNonPossibileException(String messaggio)
	{
		super(messaggio);
	}
}
