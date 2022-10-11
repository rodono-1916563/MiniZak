package it.uniroma1.textadv.oggetti;


import java.util.Map.Entry;

import it.uniroma1.textadv.Entita;
import it.uniroma1.textadv.Mondo;
import it.uniroma1.textadv.eccezioni.ImpossibileUsareSuExeption;
import it.uniroma1.textadv.eccezioni.OggettoGiaAccesoException;
import it.uniroma1.textadv.eccezioni.OggettoGiaSpentoException;
import it.uniroma1.textadv.utilita.AccendibileSpegnibile;
import it.uniroma1.textadv.utilita.RiempibileSvuotabile;
import it.uniroma1.textadv.utilita.StatoAccensione;
import it.uniroma1.textadv.utilita.StatoRiempimento;
import it.uniroma1.textadv.utilita.Target;

/**
 * Classe che modella un camino
 * 
 * @author Gabriele
 *
 */
public class Camino extends Oggetto implements AccendibileSpegnibile
{
	/**
	 * Lo stato di accensione del camino
	 */
	private StatoAccensione statoAccensione;
	
	
	/**
	 * Costruttore di un camino inizialmente acceso
	 * @param nome il nome del camino
	 */
	public Camino(String nome)
	{
		super(nome);
		statoAccensione = StatoAccensione.ACCESO;
	}
	
	/**
	 * Ritorna lo stato di accensione del camino
	 * @return stato di accensione del camino
	 */
	@Override
	public StatoAccensione getStatoAccensione()
	{
		return statoAccensione;
	}
	
	/**
	 * Metodo che ritorna true se il camino è acceso, false altrimenti
	 * @return true se il camino è acceso, false altrimenti
	 */
	@Override
	public boolean isAcceso() 
	{
		return getStatoAccensione() == StatoAccensione.ACCESO;
	}
	
	
	/**
	 * Metodo che ritorna una stringa che indica se il camino è acceso o spento
	 * @return una stringa che indica se il camino è acceso o spento
	 */
	@Override
	public String toString()
	{
		return getName() + " (" + getStatoAccensione() + ")";
	}

	/**
	 * Se è spento, accende il camino
	 * 
	 * @throws OggettoGiaAccesoException sollevata se il camino è già acceso
	 */
	@Override
	public void accendi() throws OggettoGiaAccesoException
	{
		// Se il camino è spento
		if ( !isAcceso() )
		{
			// Lo imposto ad acceso
			statoAccensione = StatoAccensione.ACCESO;
			System.out.println("Il " + getName() + " è stato acceso!");
		}
		// Se invece è già acceso
		else
			throw new OggettoGiaAccesoException(getName() + " già acceso");
	}
	
	
	/**
	 * Se acceso, spegni il camino usando uno strumento
	 * @param nomeOggDaUsare il nome dell'oggetto che si vuole usare per spegnere il camino
	 * 
	 * @throws OggettoGiaSpentoException sollevata se il camino è già spento
	 * @throws ImpossibileUsareSuExeption sollevata se l'oggetto indicato non può essere usato per spegnere il camino
	 */
	@Override
	public void spegni(String nomeOggDaUsare) throws OggettoGiaSpentoException, ImpossibileUsareSuExeption
	{
		// Ricavo il riferimento all'oggetto da usare per spegnere il camino
		Entita oggDaUsare = Mondo.getOggettoByName(nomeOggDaUsare);
		
		// Se il camino è acceso e se l'oggetto da usare esiste ed è un RiempibileSvuotabile pieno
		if ( isAcceso() && oggDaUsare != null && 
			oggDaUsare instanceof RiempibileSvuotabile && !((RiempibileSvuotabile) oggDaUsare).isEmpty() )
		{
			// Modifico lo stato del camino
			statoAccensione = StatoAccensione.SPENTO;
			
			// Modifico lo stato dell'oggetto usato per spegnere il camino
			((RiempibileSvuotabile) oggDaUsare).setStatoRiempimento(StatoRiempimento.VUOTO);
			
			System.out.println("Il " + getName() + " è stato spento!");
			
			
			// Tutti gli oggetti target protetti dal camino diventano visibili nella stanza
			for (Entry<String, Target> entry : this.getElencoTarget().entrySet())
				stanzaCorrente.addEntitaNellaStanza(entry.getKey(), (Entita) entry.getValue());
			
		}
		// Se il camino è spento
		else if ( !isAcceso() )
			throw new OggettoGiaSpentoException(getName() + " già spento!");
		
		// Altrimenti, non è stato possibile spegnere il camino con l'oggetto indicato
		else
			throw new ImpossibileUsareSuExeption("Operazione non riuscita!\nImpossibile usare: " + nomeOggDaUsare + " per spegnere: " + getName());
	}
	
	
	
}