package it.uniroma1.textadv.eccezioni;


/**
 * Eccezione che modella gli errori che possono verificarsi quando si vuole prendere
 * un'entità
 * 
 * @author Gabriele
 *
 */
public class ImpossibilePrendereException extends Exception {

	
	/**
	 * Il costruttore dell'eccezione
	 * @param oggDaPrendere il nome dell'oggetto impossibile da prendere
	 */
	public ImpossibilePrendereException(String oggDaPrendere)
	{
		super(oggDaPrendere);
	}
}
