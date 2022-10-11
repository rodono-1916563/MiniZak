package it.uniroma1.textadv.oggetti;

import it.uniroma1.textadv.Stanza;
import it.uniroma1.textadv.utilita.Comprabile;
import it.uniroma1.textadv.utilita.Potente;

/**
 * Classe che modella le Tronchesi.
 * Sono un'entità che può essere acquistata e che ha la potenzialità di rompere altri oggetti
 * 
 * @author Gabriele
 *
 */
public class Tronchesi extends Oggetto implements Potente, Comprabile
{

	/**
	 * Costruttore delle tronchesi
	 * @param nome il nome delle tronchesi
	 */
	public Tronchesi(String nome)
	{
		super(nome);
	}
	

	@Override
	public void updateStanzaCorrente(Stanza newStanzaCorrente) 
	{
		super.setNewStanzaCorrente(newStanzaCorrente);
		
	}
}
