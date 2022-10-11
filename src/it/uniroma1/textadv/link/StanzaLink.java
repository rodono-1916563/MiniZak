package it.uniroma1.textadv.link;


/**
 * Classe che modella una Stanza che fa da collegamento verso un altra.
 * Tali Link sono sempre attraversabili.
 * 
 * @author Gabriele
 *
 */
public class StanzaLink extends Link
{

	/**
	 * Costruttore della StanzaLink
	 * @param nomeStanza1 il nome della stanza1 (uguale al nome della stanza stessa)
	 * @param nomeStanza2 il nome della stanza2 (stanza vicina alla stanza1)
	 */
	public StanzaLink(String nomeStanza1, String nomeStanza2) 
	{
		super(nomeStanza1, nomeStanza2);
	}
	
	
	@Override
	public String toString()
	{
		return "un'apertura tra: " + getStanza1() + " e " + getStanza2();
	}

}
