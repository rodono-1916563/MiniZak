package it.uniroma1.textadv.oggetti;

import it.uniroma1.textadv.Stanza;
import it.uniroma1.textadv.utilita.EntitaPrendibile;


/**
 * Classe che modella una chiave
 * 
 * @author Gabriele
 *
 */
public class Chiave extends Oggetto implements EntitaPrendibile
{

	/**
	 * Costruttore della chiave
	 * @param nomeChiave il nome della chiave
	 */
	public Chiave(String nomeChiave)
	{
		super(nomeChiave);
	}
	

	@Override
	public void updateStanzaCorrente(Stanza newStanzaCorrente) 
	{
		super.setNewStanzaCorrente(newStanzaCorrente);
		
	}
}
