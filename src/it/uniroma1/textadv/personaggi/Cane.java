package it.uniroma1.textadv.personaggi;

import it.uniroma1.textadv.Personaggio;

/**
 * Classe che modella i Cani.
 * Un cane è un personaggio animale, pertanto è dotato di un proprio nome e di una propria posizione nel mondo.
 * 
 * @author Gabriele
 *
 */
public class Cane extends Personaggio implements Animale
{

	/**
	 * Costruttore del Cane
	 * @param nome il nome del cane
	 */
	public Cane(String nome)
	{
		super(nome);
	}
	
	
	/**
	 * Metodo che stampa il verso del cane
	 */
	@Override
	public void emettiVerso()
	{
		System.out.println("Bau Bauu Woff");
	}

	/**
	 * Metodo che fa ricevere una carezza al cane
	 */
	@Override
	public void riceviCarezza()
	{
		emettiVerso();
	}

	/**
	 * Metodo che permette al Cane di parlare (ossia di emettere il suo verso)
	 */
	@Override
	public void parla(String nomePersonaggioConCuiParlare)
	{
		emettiVerso();
	}
	
}
 