package it.uniroma1.textadv.link;

import it.uniroma1.textadv.Entita;
import it.uniroma1.textadv.eccezioni.ImpossibileAprireException;
import it.uniroma1.textadv.eccezioni.OggettoGiaApertoException;
import it.uniroma1.textadv.eccezioni.OggettoGiaChiusoException;
import it.uniroma1.textadv.utilita.Apribile;
import it.uniroma1.textadv.utilita.ApribileConStrumento;
import it.uniroma1.textadv.utilita.StatoApertura;

/**
 * Classe che modella la Porta, apribile con o senza uno strumento
 * 
 * @author Gabriele
 *
 */
public class Porta extends Link implements ApribileConStrumento
{

	/**
	 * Lo stato di apertura della porta
	 */
	private StatoApertura statoApertura;
	
	
	/**
	 * Flag utile a capire se la porta è stata aperta usando lo strumento corretto
	 * Se true: è stata aperta usando lo strumento
	 */
	private boolean apertaDalGiocatoreConChiave;
	
	
	/**
	 * Flag utile a capire se la porta è apribile senza usare uno strumento
	 * Se true: non è associata a nessuna chiave, pertanto è apribile senza strumento
	 * Se false: è stato impostato che può essere aperta solo con la chiave
	 * 
	 * Di default tutte le porte sono apribili senza strumento.
	 * NOTA: sarà il costruttore del mondo a cambiare questo parametro nel momento in cui una porta
	 * 		 deve essere aperta solo con uno strumento
	 */
	private boolean apribileSenzaStrumento = true;
	
	
	
	/**
	 * Costruttore della Porta, inizialmente chiusa
	 * 
 	 * @param nomeCollegamento il nome della porta
	 * @param stanza1 il nome della stanza1
	 * @param stanza2 il nome della stanza2
	 */
	public Porta(String nomeCollegamento, String stanza1, String stanza2)
	{
		super(nomeCollegamento, stanza1, stanza2);
		statoApertura = StatoApertura.CHIUSO;
	}

	
	/**
	 * Metodo che ritorna lo stato di apertura della Porta
	 * @return lo stato di apertura della Porta
	 */
	public StatoApertura getStatoApertura()
	{
		return statoApertura;
	}
	
	
	/**
	 * Metodo che ritorna true se la Porta è aperta, false altrimenti
	 * @return true se la Porta è aperta, false altrimenti
	 */
	@Override
	public boolean isOpen() 
	{
		return statoApertura == StatoApertura.APERTO;
	}
	
	
	/**
	 * Metodo per impostare che la porta può essere aperta solo con l'opportuno strumento
	 */
	@Override
	public void setAperturaConStrumento()
	{
		// Imposto il flag a false per indicare che nel Mondo esiste uno strumento associato alla porta
		apribileSenzaStrumento = false;
	}
	
	
	@Override
	public String toString()
	{
		return super.toString() + " (" + getStatoApertura() + ")";
	}


	/**
	 * Metodo per aprire la Porta senza usare strumenti
	 * 
	 * @throws OggettoGiaApertoException sollevata se la Porta è già aperta
	 * @throws ImpossibileAprireException sollevata se è impossibile aprire la Porta
	 */
	@Override
	public void apri() throws OggettoGiaApertoException, ImpossibileAprireException 
	{
		// Se la porta è chiusa
		if ( !isOpen() )
		{
			// Se è stata aperta dal giocatore usando una chiave
			// oppure è apribile senza strumento
			if ( apertaDalGiocatoreConChiave || apribileSenzaStrumento)
			{
				statoApertura = StatoApertura.APERTO;
				System.out.println("La " + getName() + " è stata aperta!");
			}
			// Altrimenti per aprirla serve uno strumento
			else
				throw new ImpossibileAprireException("Per aprirla occorre uno strumento!");
		}
		// Altrimenti è già aperta
		else
			throw new OggettoGiaApertoException("La " + getName() + " è già stata aperta!");
		
	}


	/**
	 * Metodo utile per aprire la Porta utilizzando uno strumento
	 * @param oggDaUsarePerAprire lo strumento che si vuole provare ad usare
	 * 
	 * @throws ImpossibileAprireException sollevata se è impossibile aprire la porta con lo strumento indicato
	 */
	@Override
	public void apri(Entita oggDaUsarePerAprire) throws ImpossibileAprireException
	{
		// Se l'oggetto che si vuole usare per aprire la porta, ha nella sua lista dei target
		// la porta stessa, allora imposto che la porta può essere aperta e la apro
		if (oggDaUsarePerAprire != null && oggDaUsarePerAprire.getElencoTarget().containsKey(getName()))
		{
			apertaDalGiocatoreConChiave = true;
			
			try
			{
				// Provo ad aprirla
				apri();
			}
			catch (OggettoGiaApertoException | ImpossibileAprireException e) 
			{
				System.out.println(e.getMessage());
			}
		}
		// Altrimenti non è lo strumento corretto per aprire la Porta
		else
			throw new ImpossibileAprireException("Impossibile aprire " + getName() + " con: " + oggDaUsarePerAprire);
		
	}

	
	/**
	 * La porta non ha un contenuto da mostrare
	 */
	@Override
	public String showContenuto() throws OggettoGiaChiusoException 
	{
		return Apribile.NO_CONTENUTO;
	}
	
}
