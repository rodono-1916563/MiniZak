package it.uniroma1.textadv.link;

import java.util.Objects;

import it.uniroma1.textadv.utilita.Target;


/**
 * Classe astratta che modella il collegamento tra due stanze.
 * 
 * Un link può essere un target per un'entità (ES: porta è target di chiave)
 * 
 * @author Gabriele
 *
 */
public abstract class Link implements Target
{
	
	/**
	 * Il nome del link
	 */
	protected String nomeCollegamento;
	
	
	/**
	 * Il nome della stanza 1
	 */
	protected String stanza1;
	
	
	/**
	 * Il nome della stanza 2
	 */
	protected String stanza2;
	

	
	/**
	 * Costruttore del Link
	 * @param nomeCollegamento il nome del collegamento
	 * @param stanza1 il nome della stanza1
	 * @param stanza2 il nome della stanza2
	 */
	public Link(String nomeCollegamento, String stanza1, String stanza2)
	{
		this.nomeCollegamento = nomeCollegamento;
		this.stanza1 = stanza1;
		this.stanza2 = stanza2;
	}
	
	
	/**
	 * Costruttore dei link che hanno lo stesso nome di una stanza
	 * @param nomeStanza1 il nome della stanza1
	 * @param nomeStanza2 il nome della stanza2
	 */
	public Link(String nomeStanza1, String nomeStanza2)
	{
		this.stanza1 = nomeStanza1;
		this.stanza2 = nomeStanza2;
		this.nomeCollegamento = "[" + nomeStanza1 + ":" + nomeStanza2 + "]";
	}
	
	
	/**
	 * Metodo che ritorna il nome del collegamento
	 * @return il nome del collegamento
	 */
	public String getName()
	{
		return nomeCollegamento;
	}
	
	
	/**
	 * Metodo per ottenere il nome della stanza1
	 * @return il nome della stanza1
	 */
	public String getStanza1()
	{
		return stanza1;
	}
	
	
	/**
	 * Metodo per ottenere il nome della stanza2
	 * @return il nome della stanza2
	 */
	public String getStanza2()
	{
		return stanza2;
	}
	
	
	/**
	 * Metodo che ritorna una stringa che mostra le due stanze collegate, oppure il nome del collegamento
	 * @return una stringa che mostra le due stanze collegate, oppure il nome del collegamento
	 */
	public String showLink()
	{
		if (stanza1 != null && stanza2 != null)
			return stanza1 + ":" + stanza2;
		else
			return nomeCollegamento;
	}
	
	
	@Override
	public String toString()
	{
		return getName() + " tra <" + showLink() + ">";
	}
	
	
	/**
	 * Due link sono uguali se sono uguali i rispettivi nomi e le rispettive stanze che collegano
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o == null || getClass() != o.getClass())
			return false;
		
		Link other = (Link) o;
		
		return nomeCollegamento.equals(other.nomeCollegamento) 
				&& stanza1.equals(other.stanza1) && stanza2.equals(other.stanza2);
	}
	
	
	@Override
	public int hashCode()
	{
		return Objects.hash(nomeCollegamento, stanza1, stanza2);
	}
}
