package it.uniroma1.textadv.oggetti;

import java.util.Objects;

import it.uniroma1.textadv.Entita;

/**
 * Classe astratta che modella gli ogetti del mondo.
 * Ogni oggetto è un'entità, pertanto gode di un nome e di una posizione nel mondo
 * 
 * @author Gabriele
 *
 */
public abstract class Oggetto extends Entita
{
	/**
	 * Costruttore dell'oggetto
	 * @param nome il nome dell'oggetto
	 */
	public Oggetto(String nome)
	{
		super(nome);
	}
	
	
	/**
	 * Ogni oggetto ha il proprio nome univoco che lo rappresenta.
	 * @param o l'oggetto con cui confrontarsi
	 * 
	 * @return true se i due oggetti sono in realtà lo stesso
	 */
	@Override
	public boolean equals(Object o)
	{
		if ( o == null || getClass() != o.getClass())
			return false;
		
		Oggetto obj = (Oggetto) o;
		
		// Due oggetti sono uguali se hanno lo stesso nome
		return getName().equals(obj.getName());
	}
	
	
	@Override
	public int hashCode()
	{
		return Objects.hash(getName());
	}
	
	
	/**
	 * Metodo che ritorna il nome dell'oggetto
	 * @return una stringa con il nome dell'oggetto
	 */
	@Override
	public String toString() { return getName(); }

	
	
}
