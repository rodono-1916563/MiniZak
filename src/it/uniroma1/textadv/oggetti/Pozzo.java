package it.uniroma1.textadv.oggetti;

import it.uniroma1.textadv.Entita;
import it.uniroma1.textadv.eccezioni.ImpossibileRiempireException;
import it.uniroma1.textadv.eccezioni.OggettoGiaChiusoException;
import it.uniroma1.textadv.utilita.StatoRiempimento;
import it.uniroma1.textadv.utilita.Target;


/**
 * Classe che modella un Pozzo
 * 
 * @author Gabriele
 *
 */
public class Pozzo extends Oggetto
{
	/**
	 * Lo stato di riempimento del pozzo
	 */
	private StatoRiempimento stato;
	
	
	/**
	 * Costruttore di un pozzo inizialmente pieno
	 * @param nome il nome del pozzo
	 */
	public Pozzo(String nome)
	{
		super(nome);
		this.stato = StatoRiempimento.PIENO;
	}
	
	
	/**
	 * Ritorna lo stato di riempimento del pozzo
	 * @return lo stato di riempimento del pozzo
	 */
	public StatoRiempimento getStato()
	{
		return stato;
	}
	
	
	/**
	 * Ritorna true se il pozzo è vuoto, false altrimenti
	 * @return true se il pozzo è vuoto, false altrimenti
	 */
	public boolean isEmpty()
	{
		return stato == StatoRiempimento.VUOTO;
	}
	
	
	/**
	 * Metodo per svuotare il pozzo
	 */
	public void svuota()
	{
		stato = StatoRiempimento.VUOTO;
		
		// Disperdo nella stanza tutte le entità che conteneva
		for (Target ent : getElencoTarget().values())
			if (ent != null)
				stanzaCorrente.addEntitaNellaStanza( ((Entita) ent).getName(), (Entita) ent);
	}
	
	/**
	 * Dato un secchio, esso viene riempito dal pozzo
	 * @param secchio il secchio da riempire
	 * 
	 * @throws ImpossibileRiempireException sollevata se il secchio non può essere riempito
	 */
	public void riempi(Secchio secchio) throws ImpossibileRiempireException
	{
		if (secchio == null)
			throw new ImpossibileRiempireException("Impossibile riempire: " + secchio);
		
		try 
		{
			// Il secchio viene riempito dal pozzo
			secchio.riempiDal(getName());
		}
		catch (ImpossibileRiempireException e)
		{
			System.out.println(e.getMessage());
		}

	}
	
	
	/**
	 * Metodo che se il pozzo è vuoto ne mostra il contenuto
	 * @return la stringa con il contenuto del pozzo
	 * 
	 * @throws OggettoGiaChiusoException sollevata se il pozzo è pieno
	 */
	public String showContenuto() throws OggettoGiaChiusoException
	{
		// Se il secchio è pieno non può essere visto il suo contenuto
		if ( !isEmpty() )
			throw new OggettoGiaChiusoException("Il " + getName() + " è pieno!");
		
		
		// Altrimenti, se è vuoto è possibile vedere ciò che contiene (ES: sul fondo)
		StringBuffer sb = new StringBuffer();
		sb.append("Nel pozzo è presente:\n");
		
		for (String nomeOgg : getElencoTarget().keySet())
			sb.append(" - " + nomeOgg + "\n");
		
		return sb.toString();
	}
	
	/**
	 * Metodo che descrive il pozzo
	 */
	@Override
	public String toString()
	{
		// Se è vuoto
		if ( isEmpty() )
		{
			String str = null;
			try 
			{
				str = getName() + "(" + getStato() + ")\n" + showContenuto();
			} 
			catch (OggettoGiaChiusoException e) 
			{
				System.out.println(e.getMessage());
			}
			
			return str;
		}
		else
			return getName() + "(" + getStato() + ")";
	}
	
}
