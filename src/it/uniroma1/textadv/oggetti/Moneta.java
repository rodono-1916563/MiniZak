package it.uniroma1.textadv.oggetti;

import it.uniroma1.textadv.Stanza;
import it.uniroma1.textadv.utilita.Collezionabile;

/**
 * ==> Implementata per l'EASTEREGG
 * 
 * Classe che modella una moneta Collezionabile
 * 
 * @author Gabriele
 *
 */
public class Moneta extends Oggetto implements Collezionabile
{

	/**
	 * La descrizione della moneta.
	 */
	private String descrizione;
	
	
	/**
	 * Il costruttore della moneta
	 * @param nomeMoneta il suo nome
	 */
	public Moneta(String nomeMoneta)
	{
		super(nomeMoneta);
	}
	
	/**
	 * Metodo per ottenere la descrizione della moneta
	 * @return la descrizione della moneta
	 */
	public String getDescrizione()
	{
		return descrizione;
	}

	/**
	 * Metodo per settare la descrizione della moneta
	 */
	@Override
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}
	
	/**
	 * Metodo che ritorna una stringa che descrive la moneta
	 * @return una stringa che descrive la moneta
	 */
	@Override
	public String toString()
	{
		return getName() + " (" + getDescrizione() + ")";
	}

	
	@Override
	public void updateStanzaCorrente(Stanza newStanzaCorrente) 
	{
		super.setNewStanzaCorrente(newStanzaCorrente);
	}
	
	
}
