package it.uniroma1.textadv.oggetti;

import java.util.HashSet;
import java.util.Set;

import it.uniroma1.textadv.eccezioni.OggettoGiaChiusoException;
import it.uniroma1.textadv.eccezioni.ImpossibileAprireException;
import it.uniroma1.textadv.eccezioni.ImpossibileRimuovereException;
import it.uniroma1.textadv.eccezioni.OggettoGiaApertoException;
import it.uniroma1.textadv.utilita.Apribile;
import it.uniroma1.textadv.utilita.StatoApertura;

/**
 * Classe che modella i Cassetti
 * Un cassetto � un oggetto che pu� essere aperto
 * 
 * @author Gabriele
 *
 */
public class Cassetto extends Oggetto implements Apribile
{	
	/**
	 * Oggetti contenuti nel cassetto
	 */
	private Set<Oggetto> oggettiContenuti;
	
	/**
	 * Lo stato di apertura del cassetto
	 */
	private StatoApertura statoApertura;
	
	/**
	 * Costruttore del cassetto inizialmente chiuso e vuoto
	 * @param nome il nome del cassetto
	 */
	public Cassetto(String nome)
	{
		super(nome);
		this.oggettiContenuti = new HashSet<>();
		statoApertura = StatoApertura.CHIUSO;
	}
	
	
	/**
	 * Metodo per aggiungere un oggetto al cassetto
	 * @param ogg l'oggetto da mettere nel cassetto
	 */
	public void addOggettoNelCassetto(Oggetto ogg)
	{
		oggettiContenuti.add(ogg);
	}
	
	
	/**
	 * Metodo per rimuovere l'oggetto dal cassetto
	 * @param ogg l'oggetto da togliere dal cassetto
	 * @return il riferimento all'oggetto tolto
	 * 
	 * @throws ImpossibileRimuovereException se l'oggetto non � nel cassetto
	 */
	public Oggetto removeOggettoDalCassetto(Oggetto ogg) throws ImpossibileRimuovereException
	{
		// Se l'oggetto � nel cassetto lo rimuovo
		if (oggettiContenuti.contains(ogg))
		{
			oggettiContenuti.remove(ogg);
			return ogg;
		}
		// Altrimenti, l'oggetto non � nel cassetto e non pu� essere rimosso
		else 
			throw new ImpossibileRimuovereException(ogg.toString() + " non pu� essere tolto dal " + getName());
	}
	
	
	/**
	 * Metodo che indica se il cassetto � aperto
	 * @return true se il cassetto � aperto, false se � chiuso
	 */
	public boolean isOpen()
	{
		return statoApertura == StatoApertura.APERTO;
	}
	
	
	/**
	 * Metodo che ritorna una stringa che presenta il contenuto del cassetto
	 * @return una stringa che presenta il contenuto del cassetto
	 * 
	 * @throws OggettoGiaChiusoException sollevata se si prova a guardare in un cassetto chiuso
	 */
	@Override
	public String showContenuto() throws OggettoGiaChiusoException
	{
		// Se � chiuso
		if ( !isOpen() )
			throw new OggettoGiaChiusoException("Il " + getName() + " � chiuso!");
		
		// Altrimenti � aperto, e si pu� vedere il suo contenuto
		StringBuffer sb = new StringBuffer();
		sb.append("Nel cassetto � presente:\n");
		
		for (String nomeOgg : getElencoTarget().keySet())
			sb.append(" - " + nomeOgg + "\n");
		
		return sb.toString();
	}
	
	
	@Override
	public String toString()
	{
		// Se il cassetto � aperto, si pu� vedere il suo contenuto
		if (isOpen())
		{
			String str = null;
			try
			{
				str = getName() + "(" + getStatoApertura() + ")\n" + showContenuto();
			}
			catch (OggettoGiaChiusoException e)
			{
				System.out.println(e.getMessage());
			}
			
			return str;
		}
		// Se � chiuso
		else
			return getName() + "(" + getStatoApertura() + ")";
	}


	/**
	 * Metodo che ritorna lo stato di apertura del cassetto
	 * @return lo stato di apertura del cassetto
	 */
	@Override
	public StatoApertura getStatoApertura() 
	{
		return statoApertura;
	}


	/**
	 * Metodo per aprire il cassetto senza usare nessuno strumento
	 * 
	 * @throws OggettoGiaApertoException sollevata se il cassetto � gi� aperto
	 * @throws ImpossibileAprireException sollevata se � impossibile aprire il cassetto
	 */
	@Override
	public void apri() throws OggettoGiaApertoException, ImpossibileAprireException 
	{
		// Se � chiuso
		if( !isOpen() )
		{
			statoApertura = StatoApertura.APERTO;
			System.out.println("Il " + getName() + " � stato aperto!");
		}
		// Altrimenti � gi� aperto
		else
			throw new OggettoGiaApertoException(getName() + " � gi� aperto");
	}
	
}
