package it.uniroma1.textadv.oggetti;

import it.uniroma1.textadv.Stanza;
import it.uniroma1.textadv.utilita.EntitaPrendibile;

/**
 * Classe che modella un cacciavite
 * 
 * @author Gabriele
 *
 */
public class Cacciavite extends Oggetto implements EntitaPrendibile
{
	
	/**
	 * Costruttore del cacciavite
	 * @param nome il nome del cacciavite
	 */
	public Cacciavite(String nome)
	{
		super(nome);
	}


	@Override
	public void updateStanzaCorrente(Stanza newStanzaCorrente) 
	{
		super.setNewStanzaCorrente(newStanzaCorrente);
		
	}
}
