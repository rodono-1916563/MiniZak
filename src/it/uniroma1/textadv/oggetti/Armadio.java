package it.uniroma1.textadv.oggetti;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import it.uniroma1.textadv.Entita;
import it.uniroma1.textadv.eccezioni.EntitaNonPresenteException;
import it.uniroma1.textadv.eccezioni.ImpossibileAprireException;
import it.uniroma1.textadv.eccezioni.OggettoGiaApertoException;
import it.uniroma1.textadv.eccezioni.OggettoGiaChiusoException;
import it.uniroma1.textadv.utilita.ApribileConStrumento;
import it.uniroma1.textadv.utilita.StatoApertura;
import it.uniroma1.textadv.utilita.Target;


/**
 * Classe che modella un armadio in grado di contenere entità.
 * Può essere aperto solo con uno strumento
 * 
 * @author Gabriele
 *
 */
public class Armadio extends Oggetto implements ApribileConStrumento
{
	/**
	 * Elenco degli oggetti che l'armadio contiene
	 */
	private Set<Entita> oggettiContenuti;
	
	/**
	 * Lo stato di apertura dell'armadio.
	 * Di default è chiuso.
	 */
	private StatoApertura statoApertura = StatoApertura.CHIUSO;
	
	
	/**
	 * Flag utile a capire se la porta è stata aperta dal giocatore
	 * usando lo strumento corretto
	 */
	private boolean apertaDalGiocatoreConStrumento;
	
	
	/**
	 * Flag utile a capire se l'Armadio è apribile senza usare uno strumento
	 * Se true: non è associato a nessuno strumento, pertanto è apribile senza strumento
	 * Se false: è stato impostato che può essere aperta solo con uno strumento
	 * 
	 * Di defualt, ogni armadio è apribile senza strumento
	 */
	private boolean apribileSenzaStrumento = true;
	
	
	/**
	 * Costruttore dell'armadio vuoto
	 * @param nome il nome dell'armadio
	 */
	public Armadio(String nome)
	{
		super(nome);
		this.oggettiContenuti = new HashSet<>();
	}
	
	/**
	 * Costruttore di un armadio che contiene già degli oggetti
	 * @param nome il nome dell'armadio
	 * @param oggetti gli oggetti già contenuti nell'armadio
	 */
	public Armadio(String nome, Oggetto...oggetti)
	{
		super(nome);
		this.oggettiContenuti = new HashSet<>(Arrays.asList(oggetti));
	}
	
	
	
	/**
	 * Dato un oggetto, lo inserisce nell'armadio
	 * @param ogg l'oggetto da inserire nell'armadio
	 */
	public void addOggettoNelArmadio(Oggetto ogg)
	{
		oggettiContenuti.add(ogg);
	}
	
	
	/**
	 * Dato un oggetto, se è nell'armadio lo rimuove 
	 * @param ogg l'oggetto che si vuole rimuovere
	 * 
	 * @throws EntitaNonPresenteException sollevata se l'oggetto non è presente nell'armadio
	 */
	public void removeOggettoFromArmadio(Oggetto ogg) throws EntitaNonPresenteException
	{
		if (oggettiContenuti.contains(ogg))
			oggettiContenuti.remove(ogg);
		else
			throw new EntitaNonPresenteException("Nell' " + getName() + " non c'è: " + ogg.toString());
	}
	
	
	/**
	 * Metodo che mostra ciò che contiene l'armadio
	 * @return l'elenco degli oggetti contenuti nell'armadio
	 * 
	 * @throws OggettoGiaChiusoException sollevata se l'armadio è chiuso
	 */
	@Override
	public String showContenuto() throws OggettoGiaChiusoException
	{
		// Se è chiuso
		if ( !isOpen() )
			throw new OggettoGiaChiusoException("L' " + getName() + " è chiuso!");
		
		StringBuffer sb = new StringBuffer();
		sb.append("Nell' " + getName() + " è presente:\n");
		
		for (Target ogg : getElencoTarget().values())
			sb.append(" - " + ogg.toString() + "\n");
		
		return sb.toString();
	}
	
	

	/**
	 * Metodo che ritorna lo stato di apertura dell'armadio
	 * @return lo stato di apertura dell'armadio
	 */
	@Override
	public StatoApertura getStatoApertura()
	{
		return statoApertura;
	}

	/**
	 * Metodo che ritorna true se l'armadio è aperto, false altrimenti
	 * @return true se l'armadio è aperto, false altrimenti
	 */
	@Override
	public boolean isOpen() 
	{
		return statoApertura == StatoApertura.APERTO;
	}
	
	
	/**
	 * Metodo per impostare che l'armadio può essere aperto solo con uno strumento
	 */
	@Override
	public void setAperturaConStrumento()
	{
		apribileSenzaStrumento = false;
	}
	
	/**
	 * Metodo che descrive l'armadio
	 */
	@Override
	public String toString()
	{
		// Se è aperto ne mostro anche il contenuto
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
		else
			return getName() + "(" + getStatoApertura() + ")";
	}

	/**
	 * Metodo per aprire l'armadio senza usare uno strumento
	 * 
	 * @throws OggettoGiaApertoException sollevata se l'armadio è già aperto
	 * @throws ImpossibileAprireException sollevata se l'armadio non può essere aperto
	 */
	@Override
	public void apri() throws OggettoGiaApertoException, ImpossibileAprireException 
	{
		// Se è chiuso
		if ( !isOpen() )
		{
			// Se l'armadio è stato aperto dal giocatore con uno strumento oppure
			// non necessita di essere aperto con uno strumento
			if (apertaDalGiocatoreConStrumento || apribileSenzaStrumento)
			{
				statoApertura = StatoApertura.APERTO;
				System.out.println("L' " + getName() + " è stato aperto!");
				
				// Tutti gli oggetti presenti nell'armadio diventano visibili nella stanza
				for (Entry<String, Target> entry : getElencoTarget().entrySet())
					stanzaCorrente.addEntitaNellaStanza(entry.getKey(), (Entita) entry.getValue());
			}
			// Altrimenti vuol dire che può essere aperto solo con uno strumento
			// NOTA: perchè di default l'armadio ha apribileSenzaStrumento=true
			else
				throw new ImpossibileAprireException("Per aprirlo occorre uno strumento!");
		}
		// Altrimenti l'armadio è già stato aperto
		else
			throw new OggettoGiaApertoException("E' già aperto: " + getName());
		
	}

	

	/**
	 * Metodo per aprire l'armadio usando uno strumento
	 */
	@Override
	public void apri(Entita oggDaUsarePerAprire)
	{
		// Se l'oggetto che si vuole usare per aprire l'armadio, ha nella sua lista dei target
		// l'armadio stesso, allora imposto che l'armadio può essere aperto e lo apro
		if (oggDaUsarePerAprire.getElencoTarget().containsKey(getName()))
		{
			apertaDalGiocatoreConStrumento = true;
			
			try
			{
				// Provo ad aprirlo
				apri();
			}
			catch (OggettoGiaApertoException | ImpossibileAprireException e) 
			{
				System.out.println(e.getMessage());
			}
		}
		
	}
	
}
