package it.uniroma1.textadv.link;

import it.uniroma1.textadv.utilita.MezzoDiTrasposto;

/**
 * Classe che modella il Teletrasporto.
 * E' un link in grado di collegare due stanze.
 * 
 * @author Gabriele
 *
 */
public class Teletrasporto extends Link implements MezzoDiTrasposto
{

	/**
	 * Costruttore del Teletrasporto
	 * @param nomeCollegamento il nome del Teletrasporto
	 * @param stanza1 il nome della stanza1
	 * @param stanza2 il nome della stanza2
	 */
	public Teletrasporto(String nomeCollegamento, String stanza1, String stanza2) 
	{
		super(nomeCollegamento, stanza1, stanza2);
	}

}
