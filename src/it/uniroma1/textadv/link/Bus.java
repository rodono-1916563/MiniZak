package it.uniroma1.textadv.link;

import it.uniroma1.textadv.utilita.MezzoDiTrasposto;

/**
 * Classe che modella un Bus, per collegare due stanze
 * 
 * @author Gabriele
 *
 */
public class Bus extends Link implements MezzoDiTrasposto
{

	/**
	 * Costruttore del Bus.
	 * 
	 * @param nomeCollegamento il nome del Bus
	 * @param stanza1 il nome della stanza1
	 * @param stanza2 il nome della stanza2
	 */
	public Bus(String nomeCollegamento, String stanza1, String stanza2)
	{
		super(nomeCollegamento, stanza1, stanza2);
	}

	
}
