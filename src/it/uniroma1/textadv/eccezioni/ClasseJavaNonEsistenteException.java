package it.uniroma1.textadv.eccezioni;

/**
 * Eccezione che modella l'errore in cui la classe Java non esiste
 * 
 * @author Gabriele
 *
 */
public class ClasseJavaNonEsistenteException extends Exception 
{

	/**
	 * Il costruttore dell'eccezione
	 * @param classeCheDaErrore il nome della classe che non esiste
	 */
	public ClasseJavaNonEsistenteException(String classeCheDaErrore)
	{
		super(classeCheDaErrore);
	}
}
