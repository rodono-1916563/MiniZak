package it.uniroma1.textadv.oggetti;

import it.uniroma1.textadv.eccezioni.EntitaNonPresenteException;
import it.uniroma1.textadv.eccezioni.ImpossibileRiempireException;
import it.uniroma1.textadv.eccezioni.OggettoGiaApertoException;
import it.uniroma1.textadv.eccezioni.OggettoGiaChiusoException;
import it.uniroma1.textadv.utilita.Apribile;
import it.uniroma1.textadv.utilita.EntitaPrendibile;
import it.uniroma1.textadv.utilita.StatoApertura;

/**
 * Classe che modella una scrivania in grado di contenere degli oggetti.
 * Può essere aperta.
 * 
 * @author Gabriele
 *
 */
public class Scrivania extends Oggetto implements Apribile
{
	/**
	 * Lo stato di apertura della Scrivania
	 */
	private StatoApertura statoApertura;
	
	
	/**
	 * Costruttore della Scrivania, inizialmente chiusa
	 * @param nome il nome della scrivania
	 */
	public Scrivania(String nome) 
	{
		super(nome);
		statoApertura = StatoApertura.CHIUSO;
	}
	
	
	/**
	 * Metodo per ottenere lo stato di apertura della scrivania
	 */
	@Override
	public StatoApertura getStatoApertura() 
	{
		return statoApertura;
	}

	
	/**
	 * Metodo per verificare se la scrivania è aperta
	 * @return true se è aperta, false altrimenti
	 */
	@Override
	public boolean isOpen() 
	{
		return statoApertura == StatoApertura.APERTO;
	}
	
		
	/**
	 * Dato un oggetto, lo inserisce nella scrivania
	 * @param ogg l'oggetto da inserire nella scrivania
	 * 
	 * @throws ImpossibileRiempireException sollevata se l'oggetto non può essere messo nella scrivania
	 */
	public void addOggettoNellaScrivania(Oggetto ogg) throws ImpossibileRiempireException
	{
		// Se l'oggetto è un prendibile allora lo metto nella scrivania
		if (ogg instanceof EntitaPrendibile)
			getElencoTarget().put(ogg.getName(), ogg);
		
		// Altrimenti, non può essere messo nella scrivania
		else
			throw new ImpossibileRiempireException(ogg + " non può essere messo nella scrivania");
	}
	
	/**
	 * Dato un oggetto, se è nella scrivania lo rimuove 
	 * @param ogg l'oggetto che si vuole rimuovere
	 * 
	 * @throws EntitaNonPresenteException se l'oggetto non è presente nella scrivania
	 */
	public void removeOggettoFromScrivania(Oggetto ogg) throws EntitaNonPresenteException
	{
		// Se nella scrivania è presente l'oggetto allora lo rimuovo
		if ( getElencoTarget().containsValue(ogg))
			getElencoTarget().remove(ogg.getName());
		
		// Altrimenti, esso non è presente nella scrivania
		else
			throw new EntitaNonPresenteException("La " + getName() + " non contiene: " + ogg);
	}
	
	
	/**
	 * Metodo che mostra ciò che la scrivania contiene
	 * @return l'elenco del contenuto della scrivania
	 * 
	 * @throws OggettoGiaChiusoException sollevata se la scrivania è chiusa
	 */
	@Override
	public String showContenuto() throws OggettoGiaChiusoException
	{
		// Se è chiusa
		if ( !isOpen() )
			throw new OggettoGiaChiusoException("La " + getName() + " è chiusa!");
		
		// Altrimenti, se è aperta
		StringBuffer sb = new StringBuffer();
		sb.append("Nella scrivania è presente:\n");
		
		for (String nomeOgg : getElencoTarget().keySet())
			sb.append(" - " + nomeOgg + "\n");
		
		return sb.toString();
	}
	
	
	/**
	 * Dato un oggetto, risponde se esso è presente o meno nella scrivania
	 * @param ogg l'oggetto di cui vogliamo verificare la presenza nella scrivania
	 * @return true se l'oggetto è nella scrivania, false altrimenti
	 */
	public boolean contains(Oggetto ogg)
	{
		// Se l'oggetto non è un prendibile, non sta nella scrivania sicuramente
		if ( !(ogg instanceof EntitaPrendibile))
			return false;
		
		return getElencoTarget().containsKey(ogg.getName());
	}
	

	
	/**
	 * Metodo che descrive la scrivania
	 */
	@Override
	public String toString()
	{
		// Se la scrivania è aperta
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
		// Altrimenti, se è chiusa
		else
			return getName() + "(" + getStatoApertura() + ")";

	}


	/**
	 * Metodo per aprire la scrivania
	 */
	@Override
	public void apri() throws OggettoGiaApertoException
	{
		// Se la scrivania è chiusa
		if(!isOpen())
		{
			statoApertura = StatoApertura.APERTO;
			System.out.println("La " + getName() + " è stata aperta!");
		}
		// Altrimenti vuol dire che è già aperta
		else
			throw new OggettoGiaApertoException(getName() + " è già aperto!");
		
	}

}
