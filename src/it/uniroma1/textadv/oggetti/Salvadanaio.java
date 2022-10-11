package it.uniroma1.textadv.oggetti;

import it.uniroma1.textadv.eccezioni.ImpossibilePrendereException;
import it.uniroma1.textadv.eccezioni.OggettoGiaRottoException;
import it.uniroma1.textadv.utilita.Fragile;
import it.uniroma1.textadv.utilita.StatoRottura;

/**
 * Classe che modella un Salvadanaio
 * 
 * @author Gabriele
 *
 */
public class Salvadanaio extends Oggetto implements Fragile
{
	/**
	 * Stato che indica se il salvadanaio � rotto oppure � intero
	 */
	private StatoRottura statoRottura;
	
	/**
	 * I soldi contenuti nel salvadanaio
	 */
	private Soldi soldiContenuti;
	
	
	/**
	 * Costruttore del salvadanaio.
	 * Inizialmente intero e contenente dei soldi
	 * @param nome il nome del salvadanaio
	 */
	public Salvadanaio(String nome)
	{
		super(nome);
		statoRottura = StatoRottura.INTERO;
		soldiContenuti = new Soldi();	
	}
	
	
	/**
	 * Metodo che ritorna se il salvadanaio � rotto o meno
	 * @return true se il salvadanaio � rotto, false atrimenti
	 */
	public boolean isBroken()
	{
		return statoRottura == StatoRottura.ROTTO;
	}
	
	
	/**
	 * Metodo per vedere lo stato di rottura del salvadanaio
	 * @return lo stato di rottura del salvadanaio
	 */
	public StatoRottura getStatoRottura()
	{
		return statoRottura;
	}
	

	@Override
	public String toString()
	{
		return getName() + " (" + getStatoRottura() + ")";
	}

	
	/**
	 * Metodo che disperde i soldi nel salvadanaio nella stanza
	 * 
	 * @throws ImpossibilePrendereException sollevata se il salvadanaio � intero oppure se non ci sono soldi.
	 * 		   In questo caso � impossibile prendere i soldi
	 */
	private void disperdiSoldi() throws ImpossibilePrendereException
	{
		// Se il salvadanaio � rotto oppure contiene dei soldi
		if ( isBroken() || soldiContenuti != null )
		{
			Soldi soldi = soldiContenuti;
			soldiContenuti = null;
			
			// Quando rompo il salvadanaio, i soldi diventano un entit� della stanza
			// In questo modo possono essere presi
			stanzaCorrente.addEntitaNellaStanza(soldi.getName(), soldi);
			
		}
		else
			throw new ImpossibilePrendereException("Impossibile prendere: soldi");		
	}
	

	@Override
	public void rompi() throws OggettoGiaRottoException 
	{
		// Se il salvadanaio � intero
		if ( !isBroken() )
		{
			// Lo rompo
			statoRottura = StatoRottura.ROTTO;
			try 
			{
				// Disperdo i soldi contenuti nella stanza e comunico al giocatore che � stato rotto
				disperdiSoldi();
				System.out.println("Il " + getName() + " � stato rotto");
				
			}
			catch (ImpossibilePrendereException e)
			{
				System.out.println(e.getMessage());
			}
		}
		// Altrimenti, il salvadanaio � gi� stato rotto
		else 
			throw new OggettoGiaRottoException("Il " + getName() + " � gi� rotto!");
		
	}
	
}
