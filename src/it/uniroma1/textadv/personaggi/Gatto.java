package it.uniroma1.textadv.personaggi;

import it.uniroma1.textadv.Personaggio;
import it.uniroma1.textadv.Stanza;
import it.uniroma1.textadv.utilita.EntitaPrendibile;

/**
 * Classe che modella i Gatti.
 * 
 * Un gatto è un personaggio animale, pertanto è dotato di un proprio nome e di una propria posizione nel mondo
 * 
 * E' allo stesso tempo sia subject (un personaggio) che observer (un'entità):
 * - perchè può mantenere oggetti nel suo inventario (nella bocca, un collare, ecc) e cambiare stanza
 * - ma può anche essere preso da altri giocatori per portarlo con se.
 * 
 * @author Gabriele
 *
 */
public class Gatto extends Personaggio implements Animale, EntitaPrendibile
{

	/**
	 * Costruttore del Gatto
	 * @param nome il nome del gatto
	 */
	public Gatto(String nome)
	{
		super(nome);
	}
	
	/**
	 * Metodo che stampa il verso del gatto
	 */
	@Override
	public void emettiVerso()
	{
		System.out.println("Miaooo");
	}
	
	
	/**
	 * Metodo per aggiornare la sua stanza corrente
	 */
	@Override
	public void updateStanzaCorrente(Stanza newStanzaCorrente) 
	{
		super.setNewStanzaCorrente(newStanzaCorrente);
	}
	
	
	/**
	 * Metodo che fa ricevere al gatto una carezza
	 */
	@Override
	public void riceviCarezza()
	{
		emettiVerso();
	}
	
	
	/**
	 * Metodo che permette al Gatto di parlare (ossia di emettere il suo verso)
	 */
	@Override
	public void parla(String nomePersonaggioConCuiParlare)
	{
		emettiVerso();
	}
	

}
