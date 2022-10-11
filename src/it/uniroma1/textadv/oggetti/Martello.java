package it.uniroma1.textadv.oggetti;

import it.uniroma1.textadv.Stanza;
import it.uniroma1.textadv.utilita.EntitaPrendibile;
import it.uniroma1.textadv.utilita.Potente;

/**
 * Classe che modella un martello.
 * Un martello è un oggetto che può essere preso dal giocatore,
 * che ha la potenzialità di rompere altri oggetti
 * 
 * @author Gabriele
 *
 */
public class Martello extends Oggetto implements EntitaPrendibile, Potente
{
	/**
	 * Costruttore del martello
	 * @param nome il nome del martello
	 */
	public Martello(String nome)
	{
		super(nome);
	}
	

	@Override
	public void updateStanzaCorrente(Stanza newStanzaCorrente) 
	{
		super.setNewStanzaCorrente(newStanzaCorrente);
		
	}
}
