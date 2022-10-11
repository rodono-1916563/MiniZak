package it.uniroma1.textadv.link;

import it.uniroma1.textadv.Entita;
import it.uniroma1.textadv.eccezioni.ImpossibileAprireException;
import it.uniroma1.textadv.eccezioni.OggettoGiaApertoException;
import it.uniroma1.textadv.eccezioni.OggettoGiaChiusoException;
import it.uniroma1.textadv.utilita.Apribile;
import it.uniroma1.textadv.utilita.ApribileConStrumento;
import it.uniroma1.textadv.utilita.StatoApertura;

/**
 * Classe che modella una Botola, ossia un collegamento tra due stanze.
 * Di default può essere aperta solamente usando il giusto strumento.
 * 
 * @author Gabriele
 *
 */
public class Botola extends Link implements ApribileConStrumento
{
	
	/**
	 * Lo stato di apertura della Botola
	 */
	private StatoApertura statoApertura;

	
	/**
	 * Flag utile a verificare se la botola è stata aperta dal giocatore con uno strumento
	 */
	private boolean apertaDalGiocatoreConStrumento;
	
	
	/**
	 * Flag utile a capire se la botola può essere aperta senza strumento
	 */
	private boolean apribileSenzaStrumento;
	
	/**
	 * Costruttore di una Botola inizialmente chiusa, che unisce due stanza
	 * 
	 * @param nomeCollegamento il nome della Botola
	 * @param stanza1 la stanza1 che collega
	 * @param stanza2 la stanza2 che collega
	 */
	public Botola(String nomeCollegamento, String stanza1, String stanza2)
	{
		super(nomeCollegamento, stanza1, stanza2);
		statoApertura = StatoApertura.CHIUSO;
	}
	
	
	/**
	 * Metodo che ritorna lo stato di apertura della Botola
	 * @return lo stato di apertura della Botola
	 */
	@Override
	public StatoApertura getStatoApertura()
	{
		return statoApertura;
	}
	
	/**
	 * Metodo che ritorna true se la Botola è aperta, false altrimenti
	 * @return true se la Botola è aperta, false altrimenti
	 */
	@Override
	public boolean isOpen() 
	{
		return statoApertura == StatoApertura.APERTO;
	}
	
	
	/**
	 * Metodo per impostare che la botola può essere aperta solo con l'opportuno strumento
	 */
	@Override
	public void setAperturaConStrumento()
	{
		apribileSenzaStrumento = true;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " (" + getStatoApertura() + ")";
	}


	/**
	 * Metodo per aprire la Botola senza usare uno strumento
	 * 
	 * @throws OggettoGiaApertoException sollevata se la botola è già aperta
	 * @throws ImpossibileAprireException sollevata se la Botola è impossibile da aprire
	 */
	@Override
	public void apri() throws OggettoGiaApertoException, ImpossibileAprireException 
	{
		// Se la botola è chiusa
		if ( !isOpen() )
		{
			// Se è stata aperta dal giocatore usando un apposito strumento
			// oppure è apribile senza strumento
			if (apertaDalGiocatoreConStrumento || apribileSenzaStrumento)
			{
				statoApertura = StatoApertura.APERTO;
				System.out.println("La " + getName() + " è stata aperta!");
			}
			// Altrimenti vuol dire che va aperta usando uno strumento
			else
				throw new ImpossibileAprireException("Per aprirla occorre uno strumento!");
		}
		// Altrimenti, la botola è già stata aperta
		else
			throw new OggettoGiaApertoException("La " + getName() + " è già aperta!");
		
	}


	/**
	 * Metodo per aprire la Botola usando uno strumento
	 * @param oggDaUsarePerAprire il nome dell'oggetto che si prova ad usare per aprire la Botola
	 * 
	 * @throws ImpossibileAprireException sollevata se la Botola non può essere aperta con l'entità indicata
	 */
	@Override
	public void apri(Entita oggDaUsarePerAprire) throws ImpossibileAprireException
	{
		// Se l'oggetto che si vuole usare per aprire la botola ha nella sua lista dei target
		// la botola stessa, allora imposto che la botola può essere aperta
		if ( oggDaUsarePerAprire != null && oggDaUsarePerAprire.getElencoTarget().containsKey(getName()))
		{
			apertaDalGiocatoreConStrumento = true;
			
		}
		// Altrimenti, la botola non può essere aperta con tale strumento
		else
			throw new ImpossibileAprireException("La " + getName() + " non può essere aperta con: " + oggDaUsarePerAprire);
	}


	/**
	 * La botola non ha un contenuto
	 */
	@Override
	public String showContenuto() throws OggettoGiaChiusoException 
	{
		return Apribile.NO_CONTENUTO;
	}

}
