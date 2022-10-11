package it.uniroma1.textadv.oggetti;

import it.uniroma1.textadv.Stanza;
import it.uniroma1.textadv.utilita.Controllabile;
import it.uniroma1.textadv.utilita.EntitaPrendibile;

/**
 * Classe che modella il Tesoro.
 * Esso è un entità che può essere presa dai personaggi e che può essere controllata
 * 
 * @author Gabriele
 *
 */
public class Tesoro extends Oggetto implements EntitaPrendibile, Controllabile
{
	/**
	 * Enumerazione che modella lo stato di sicurezza degli oggetti
	 * 
	 * @author Gabriele
	 */
	public enum StatoSicurezza { CONTROLLATO, LIBERO }
	
	/**
	 * Lo stato di sicurezza del tesoro.
	 */
	private StatoSicurezza statoSicurezza;
	
	
	/**
	 * Costruttore del tesoro (inizialmente libero)
	 * @param nome il nome del tesoro
	 */
	public Tesoro(String nome)
	{
		super(nome);
		statoSicurezza = StatoSicurezza.LIBERO;
	}
	
	
	/**
	 * Metodo per ottenere lo stato di sicurezza del tesoro
	 * @return lo stato di sicurezza del tesoro
	 */
	@Override
	public StatoSicurezza getStatoSicurezza()
	{
		return statoSicurezza;
	}
	
	/**
	 * Metodo per controllare se il tesoro è controllato
	 * @return true se è controllato, false altrimenti
	 */
	@Override
	public boolean isCheked()
	{
		return getStatoSicurezza() == StatoSicurezza.CONTROLLATO;
	}
	
	
	/**
	 * Metodo per settare un nuovo stato di sicurezza del tesoro
	 * @param newStatoSicurezza il nuovo stato di sicurezza che si vuole settare
	 */
	@Override
	public void setStatoSicurezza(StatoSicurezza newStatoSicurezza)
	{
		statoSicurezza = newStatoSicurezza;
	}
	

	@Override
	public void updateStanzaCorrente(Stanza newStanzaCorrente) 
	{
		super.setNewStanzaCorrente(newStanzaCorrente);
		
	}
}
