package it.uniroma1.textadv.oggetti;

import it.uniroma1.textadv.Stanza;
import it.uniroma1.textadv.utilita.EntitaPrendibile;

/**
 * Classe che modella i soldi
 * 
 * @author Gabriele
 *
 */
public class Soldi extends Oggetto implements EntitaPrendibile
{
	/**
	 * Un valore di default per i soldi
	 */
	public final static double QUANTITA_DFL = 5.0;
	
	/**
	 * Un nome di default per i soldi
	 */
	public final static String SOLDI_DFL = "soldi";

	/**
	 * Quantità di soldi disponibile
	 */
	private double quantita;
	
	
	/**
	 * Costruttore che imposta i soldi disponibili alla quantità di default
	 */
	public Soldi()
	{
		this(SOLDI_DFL, QUANTITA_DFL);
	}
	
	/**
	 * Costruttore dei soldi, che imposta i soldi disponibili a una quantità di default
	 * @param nome il nome dei soldi
	 */
	public Soldi(String nome)
	{
		this(nome, QUANTITA_DFL);
	}
	
	/**
	 * Costruttore che imposta i soldi disponibili alla quantità data
	 * @param nome il nome dei soldi
	 * @param soldi la quantità di soldi da impostare
	 */
	public Soldi(String nome, double soldi)
	{
		super(nome);
		this.quantita = soldi;
	}
	

	@Override
	public void updateStanzaCorrente(Stanza newStanzaCorrente) 
	{
		super.setNewStanzaCorrente(newStanzaCorrente);
		
	}
	
	/**
	 * Metodo per settare la quantità di soldi
	 * @param quantita la quantità dei soldi che si vuole impostare
	 */
	protected void setQuantitaSoldi(double quantita)
	{
		this.quantita = quantita;
	}
	
	
	/**
	 * Metodo per ottenere i soldi come double
	 * @return la quantità di soldi disponibile
	 */
	public double getSoldi()
	{
		return quantita;
	}
	
}
