package it.uniroma1.textadv.eccezioni;


/**
 * Eccezione che modella gli errori che possono verificarsi
 * nel provare a muoversi verso una certa direzione
 * 
 * @author Gabriele
 *
 */
public class ImpossibileAndareA extends Exception 
{

	/**
	 * Il costruttore dell'eccezione
	 * @param direzioneInCuiAndare la direzione in cui andare
	 */
	public ImpossibileAndareA(String direzioneInCuiAndare)
	{
		super(direzioneInCuiAndare);
	}
}
