package it.uniroma1.textadv;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import it.uniroma1.textadv.eccezioni.EntitaNonPresenteException;
import it.uniroma1.textadv.eccezioni.ImpossibileAndareA;
import it.uniroma1.textadv.eccezioni.ImpossibileAprireException;
import it.uniroma1.textadv.eccezioni.ImpossibileComprareException;
import it.uniroma1.textadv.eccezioni.ImpossibileDareException;
import it.uniroma1.textadv.eccezioni.ImpossibilePrendereException;
import it.uniroma1.textadv.eccezioni.ImpossibileRiempireException;
import it.uniroma1.textadv.eccezioni.ImpossibileRompereException;
import it.uniroma1.textadv.eccezioni.ImpossibileUsareSuExeption;
import it.uniroma1.textadv.eccezioni.LinkInesistenteException;
import it.uniroma1.textadv.eccezioni.OggettoGiaAccesoException;
import it.uniroma1.textadv.eccezioni.OggettoGiaApertoException;
import it.uniroma1.textadv.eccezioni.OggettoGiaRottoException;
import it.uniroma1.textadv.eccezioni.OggettoGiaSpentoException;
import it.uniroma1.textadv.eccezioni.OggettoGiaVuotoException;
import it.uniroma1.textadv.eccezioni.OperazioneNonSupportataException;
import it.uniroma1.textadv.link.Link;
import it.uniroma1.textadv.link.StanzaLink;
import it.uniroma1.textadv.personaggi.Animale;
import it.uniroma1.textadv.personaggi.Guardiano;
import it.uniroma1.textadv.personaggi.Venditore;
import it.uniroma1.textadv.utilita.AccendibileSpegnibile;
import it.uniroma1.textadv.utilita.Apribile;
import it.uniroma1.textadv.utilita.ApribileConStrumento;
import it.uniroma1.textadv.utilita.Comprabile;
import it.uniroma1.textadv.utilita.Controllabile;
import it.uniroma1.textadv.utilita.Direzione;
import it.uniroma1.textadv.utilita.EntitaPrendibile;
import it.uniroma1.textadv.utilita.Fragile;
import it.uniroma1.textadv.utilita.MezzoDiTrasposto;
import it.uniroma1.textadv.utilita.Potente;
import it.uniroma1.textadv.utilita.RiempibileSvuotabile;
import it.uniroma1.textadv.utilita.Target;

/**
 * Classe che modella il Giocatore protagonista della storia.
 * 
 * Il gioco ammette un solo giocatore, pertanto è realizzato implementando il Singleton.
 * Esso è un personaggio, e in quanto tale è un'entità del mondo.
 * 
 * @author Gabriele
 *
 */
public class Giocatore extends Personaggio
{
	
	/**
	 * L'unico riferimento all'istanza del giocatore
	 */
	private static Giocatore istanzaGiocatore;
	
	
	/**
	 * Costruttore del Giocatore, con inventario vuoto
	 * @param nome il nome del giocatore
	 */
	private Giocatore(String nome)
	{
		super(nome);
	}
	
	/**
	 * Metodo per ottenere l'unica istanza del Giocatore
	 * @param nome il nome del giocatore
	 * @return l'unica istanza del Giocatore
	 */
	public static Giocatore getInstance(String nome)
	{
		if (istanzaGiocatore == null)
			istanzaGiocatore = new Giocatore(nome);
		
		return istanzaGiocatore;
	}
	
	
	@Override
	public String toString()
	{
		return getName();
	}
	
	
	/**
	 * Metodo per guardare ciò che ci sta nella stanza.
	 * Viene stampato a video tutto ciò che è visibile al giocatore.
	 */
	public void guarda()
	{
		// Mostro le entità presenti nella stanza
		String str = "Sei in: <" + stanzaCorrente.getDescrizione() + ">\n" + 
					  stanzaCorrente.showEntitaNellaStanza() + "\n";
		
		// Mostro i collegamenti disponibili dalla stanza
		str += stanzaCorrente.showCollegamenti();
		
		System.out.println(str);
	}
	
	
	/**
	 * Metodo per guardare un entità oppure per guardare in una direzione.
	 * Mostra a video ciò che viene visto.
	 * @param entitaDaGuardare l'entità/Direzione da guardare
	 * 
	 * @throws EntitaNonPresenteException sollevata se non c'è l'entità da guardare
	 * @throws ImpossibileAndareA sollevata se non si può guardare in una data direzione
	 */
	public void guarda(String entitaDaGuardare) throws EntitaNonPresenteException, ImpossibileAndareA
	{
		// Ricavo il riferimento all'entità che si potrebbe voler guardare
		Entita entita = stanzaCorrente.getEntitaByName(entitaDaGuardare);
		
		
		// Se l'entità esiste nella stanza
		if ( entita != null )
		{
			String str = "Stai guardando: " + entita.toString();
			System.out.println(str);
		}
		// Se è stato richiesto di guardare in una direzione valida (ES: guarda a N)
		else if ( Arrays.stream(Direzione.values()).anyMatch( x -> x.toString().equals(entitaDaGuardare.toUpperCase())) )
		{
			// Ricavo il riferimento alla direzione che si potrebbe voler guardare
			Direzione dir = Direzione.valueOf(Direzione.class, entitaDaGuardare);
			
			// Se la direzione data è valida, mostro i collegamenti della stanza nella direzione richiesta
			if (dir != null)
				System.out.println(stanzaCorrente.showCollegamentiByDirezione(dir));
			
			// Altrimenti, la direzione non è valida
			else
				throw new ImpossibileAndareA(entitaDaGuardare);
		}
		else
			throw new EntitaNonPresenteException(entitaDaGuardare + " non è nella stanza!");
	}
	

	
	/**
	 * Metodo per aprire un oggetto Apribile
	 * @param nomeOggettoDaAprire il nome dell'oggetto da aprire
	 * 
	 * @throws EntitaNonPresenteException sollevata se l'elemento da aprire non è presente nella stanza
	 */
	public void apri(String nomeOggettoDaAprire) throws EntitaNonPresenteException
	{
		// Prendo dalla stanza corrente il riferimento alla possibile entità da aprire
		Entita entita = stanzaCorrente.getEntitaByName(nomeOggettoDaAprire);
		
		Link link = null;
		
		// Se nella stanza non esiste l' entità da aprire
		if (entita == null)
		{
			try 
			{
				// Prendo il riferimento al possibile link da aprire (ES: botola, porta ingresso, ecc)
				link = stanzaCorrente.getLinkByName(nomeOggettoDaAprire);
			} 
			catch (LinkInesistenteException e) 
			{
				System.out.println(e.getMessage());
			}
		}

		
		// Se si vuole provare ad aprire un'entità (ES: scrivania)
		if (entita != null && entita instanceof Apribile)
			apriApribile((Apribile)entita);
		
		// Se invece è un link apribile allora provo a aprirlo
		else if (link != null && link instanceof Apribile) 
			apriApribile((Apribile)link);
		
		else
			throw new EntitaNonPresenteException(nomeOggettoDaAprire + " non è nella stanza!");
			
	}
	
	/**
	 * Metodo di utilità che dato un elemento Apribile, lo prova ad aprire
	 * @param elDaAprire l'elemento che si vuole aprire
	 */
	private void apriApribile(Apribile elDaAprire)
	{
		try 
		{
			// Provo ad aprire l'elemento
			elDaAprire.apri();
		}
		catch (OggettoGiaApertoException | ImpossibileAprireException e) 
		{
			// Se l'elemento è già aperto oppure non è apribile in questo momento
			// verrà comunicato all'utente
			System.out.println(e.getMessage());
		}
	}
	
	
	
	/**
	 * Metodo per aprire un oggetto o link usando un altro oggetto
	 * @param nomeOggettoDaAprire il nome dell'oggetto da aprire
	 * @param nomeOggettoDaUsare il nome dello strumento che si vuole usare per aprirlo
	 * 
	 * @throws ImpossibileAprireException sollevata se è impossibile aprire l'oggetto indicato
	 */
	public void apri(String nomeOggettoDaAprire, String nomeOggettoDaUsare) throws ImpossibileAprireException
	{
		// Ricavo il riferimento all'oggetto che si vuole usare
		Entita oggDaUsare = Mondo.getOggettoByName(nomeOggettoDaUsare);
		
		// Se l'oggetto che si vuole usare è nell'inventario del giocatore
		if (oggDaUsare != null && 
			oggDaUsare instanceof EntitaPrendibile && inventario.contains((EntitaPrendibile) oggDaUsare))
		{
			
			Link linkDaAprire = null;
			
			try 
			{
				// Prendo il riferimento al link che si potrebbe voler aprire
				linkDaAprire = stanzaCorrente.getLinkByName(nomeOggettoDaAprire);
			} 
			catch (LinkInesistenteException e) 
			{
				// Non faccio niente perchè potrebbe ancora essere aperta un'entità
				//System.out.println(e.getMessage());
			}
			
			// Prendo il riferimento all'entità che si potrebbe voler aprire
			Entita entDaAprire = stanzaCorrente.getEntitaByName(nomeOggettoDaAprire);
			
			// Se ciò che si vuole aprire è un link apribile con uno strumento allora provo ad aprirlo
			if (linkDaAprire != null && linkDaAprire instanceof ApribileConStrumento )
				apriApribileConStrumento((ApribileConStrumento) linkDaAprire, oggDaUsare);
			
			// Se ciò che si vuole aprire è un entità apribile con strumento, allora provo a aprirla
			else if (entDaAprire != null && entDaAprire instanceof ApribileConStrumento )
				apriApribileConStrumento((ApribileConStrumento) entDaAprire, oggDaUsare);
			
			// Altrimenti, l'oggetto indicato non può essere aperto
			else
				throw new ImpossibileAprireException("Impossibile aprire: " + nomeOggettoDaAprire);
			
		}
		// Se l'oggetto indicato per aprire non può essere usato, l'elemento indicato non può essere aperto
		else
			throw new ImpossibileAprireException("Impossibile usare: " + nomeOggettoDaUsare + " per aprire: " + nomeOggettoDaAprire);
	}
	
	
	/**
	 * Metodo di utilità per aprire un elemento apribile con strumento usando un entità
	 * @param elemDaAprire il riferimento all'elemento che si vuole aprire
	 * @param oggDaUsare il riferimento all'entità che si vuole usare per aprire
	 */
	private void apriApribileConStrumento(ApribileConStrumento elemDaAprire, Entita oggDaUsare)
	{
		try 
		{
			// Apro l'elemento da aprire usando l'entità indicata
			elemDaAprire.apri(oggDaUsare);
		} 
		catch (ImpossibileAprireException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Metodo per prendere un entità da un entità specificata
	 * 
	 * @param nomeEntitaDaPrendere il nome dell'entità che si vuole prendere
	 * @param nomeOggettoDaCuiPrendere il nome dell'entità da cui prendere l'entità desiderata
	 * 
	 * @throws EntitaNonPresenteException sollevata se l'entità cercata non è presente dove è stato indicato
	 * @throws ImpossibilePrendereException sollevata se è impossibile prendere l'elemento indicato
	 */
	public void prendi(String nomeEntitaDaPrendere, String nomeOggettoDaCuiPrendere) 
			throws EntitaNonPresenteException, ImpossibilePrendereException
	{
		// Ricavo l'elenco delle entità presenti nella stanza corrente del giocatore
		Map<String, Entita> elenco = stanzaCorrente.getElencoEntitaNellaStanza();
		
		// Ricavo il riferimento all'entità da cui provare a prendere l'entità desiderata
		Entita entDaCuiPrendere = elenco.get(nomeOggettoDaCuiPrendere);
		
		// Provo a prendere dai target dell'entità daCuiPrendere il riferimento all'entità da prendere
		Target entPresa = entDaCuiPrendere.getTargetByName(nomeEntitaDaPrendere);
		
		
		// Se nell'entità data in input non è presente l'entità cercata, sollevo eccezione
		if (entPresa == null)
			throw new EntitaNonPresenteException("In " + nomeOggettoDaCuiPrendere + " non c'è: " + nomeEntitaDaPrendere);
		
		// Altrimenti, se l'entità è prendibile la prendo e aggiungo all'inventario
		else if (entPresa instanceof EntitaPrendibile)
			prendiOggEMettiInInventario((EntitaPrendibile) entPresa);
		
		// Altrimenti l'entità indicata non può essere presa
		else
			throw new ImpossibilePrendereException("Impossibile prendere: " + nomeEntitaDaPrendere + " da " + nomeOggettoDaCuiPrendere);
	}
	
	
	/**
	 * Metodo di utilità che data un'entità prendidibile la aggiunge all'inventario
	 * @param entPresa il riferimento all'entità da aggiungere all'inventario
	 */
	private void prendiOggEMettiInInventario(EntitaPrendibile entPresa)
	{
		addToInventario((EntitaPrendibile) entPresa);
		System.out.println("Hai preso " + entPresa.toString());
	}
	
	
	/**
	 * Metodo per prendere un oggetto dalla stanza corrente
	 * @param oggettoDaPrendere il nome dell'oggetto che si vuole prendere
	 * 
	 * @throws ImpossibilePrendereException sollevata se l'oggetto non può essere preso per qualche motivo
	 */
	public void prendi(String oggettoDaPrendere) throws ImpossibilePrendereException
	{
		// Ricavo l'elenco delle entità presenti nella stanza corrente del giocatore
		Map<String, Entita> elenco = stanzaCorrente.getElencoEntitaNellaStanza();
		
		// Ricavo il riferimento all'entità che si vuole prendere
		Entita oggDesiderato = elenco.get(oggettoDaPrendere);
		
		// Se l'oggetto che si vuole prendere è un Comprabile
		if (oggDesiderato != null && oggDesiderato instanceof Comprabile)
			throw new ImpossibilePrendereException("Per prendere " + oggettoDaPrendere + " devi pagarlo!");

		// Se invece è un'entità prendibile non controllata, allora la prendo
		else if (oggDesiderato != null && oggDesiderato instanceof EntitaPrendibile && !(oggDesiderato instanceof Controllabile))
		{
			// Aggiungo all'inventario l'oggetto preso
			prendiOggEMettiInInventario((EntitaPrendibile) oggDesiderato);
			
			// Rimuovo dalla stanza l'entità presa
			stanzaCorrente.getElencoEntitaNellaStanza().remove(oggettoDaPrendere);
		}
		
		// Se invece è un'entità prendibile controllata
		else if (oggDesiderato != null && oggDesiderato instanceof EntitaPrendibile && oggDesiderato instanceof Controllabile)
		{
			// Se l'oggetto è controllato, non può essere preso
			if ( ((Controllabile) oggDesiderato).isCheked() )
				throw new ImpossibilePrendereException(oggettoDaPrendere + " non può essere preso, perchè qualcuno lo sta controllando!");
			
			// Se non è controllato, allora può essere preso
			else
				prendiOggEMettiInInventario((EntitaPrendibile) oggDesiderato);

		}
		// Altrimenti, se è un link che può essere preso (ES: prendi navetta), allora lo prendo
		else if (oggDesiderato == null && Mondo.getLinkByName(oggettoDaPrendere) != null)
		{
			try
			{
				// Ricavo la direzione del Link
				Direzione dir = stanzaCorrente.getDirezioneByLinkName(oggettoDaPrendere);
				
				// Provo ad andare in quella direzione
				vai(dir.name());
			}
			catch (ImpossibileAndareA | LinkInesistenteException e)
			{
				System.out.println(e.getMessage());
			}
		}
		// Altrimenti, se nella stanza esiste un oggetto che ha tra i target l'oggetto che si vuole prendere
		// ( ossia un oggetto che per qualche motivo impedisce l'azione di prendere l'oggetto (ES: cassetto chiuso))
		else if ( stanzaCorrente.getElencoEntitaNellaStanza()
								.values()
								.stream()
								.map(ent -> ent.getTargetByName(oggettoDaPrendere))
								.anyMatch(ogg -> ogg != null) )
		{
			throw new ImpossibilePrendereException("Questo oggetto al momento non può essere preso");	
		}
		// Altrimenti l'oggetto non può essere preso!!
		else
			throw new ImpossibilePrendereException("[ERROR] " + oggettoDaPrendere + " non può essere preso!!");
	}
	
	
	
	/**
	 * Metodo che data una direzione, sposta il giocatore nella stanza raggiungibile percorrendo tale direzione
	 * @param direzioneInCuiAndare la direzione in cui andare
	 * 
	 * @throws ImpossibileAndareA sollevata se non ci si può spostare in tale direzione
	 */
	public void vai(String direzioneInCuiAndare) throws ImpossibileAndareA
	{
		// Ottengo l'istanza associata alla direzione che mi interessa
		Direzione dir = Direzione.valueOf(Direzione.class, direzioneInCuiAndare);
		
		// Se la direzione non è valida, non ci si può spostare
		if (dir == null)
			throw new ImpossibileAndareA("Impossibile andare a: " + direzioneInCuiAndare);
		
		// Ricavo i collegamenti disponibili nella direzione specificata
		List<Link> listaLink = stanzaCorrente.getCollegamentiByDirezione(dir);
		
		// Se nella stanza corrente ci sta almeno un link
		if ( listaLink.size() > 0 )
		{
			// TODO feature: in caso di più stanze in una data direzione, si potrebbe dover scegliere in
			// 				 quale dover andare a run time
			for (Link link : listaLink)
			{
				// Se il link è un apribile ed è aperto, 
				// allora ci si può spostare
				if ((link instanceof Apribile && ((Apribile) link).isOpen()))
				{
					spostati(link);
					break;
				}
				// Altrimenti, se il link è un apribile ed è chiuso
				else if ( (link instanceof Apribile && !((Apribile) link).isOpen()) )
				{
					System.out.println("Il link " + link.getName() + " è chiuso");
					System.out.println("Sei ancora in: " + getStanzaCorrente());
				}
				// Altrimenti, se il link è un mezzo di trasporto oppure è una stanza aperta
				else if ( link instanceof MezzoDiTrasposto || link instanceof StanzaLink )
				{
					spostati(link);
					break;
				}
			}
			
		}
		// Altrimenti, se la lista dei link della stanza è vuota, non ci si può spostare
		else
			throw new ImpossibileAndareA("Impossibile andare a: " + direzioneInCuiAndare);
	}
	

	/**
	 * Metodo privato di utilità per spostarsi secondo un link
	 * @param link il link che permette di spostarsi
	 */
	private void spostati(Link link)
	{
		// Ricavo il nome di una delle stanze del link
		String nomeStanza = link.getStanza1();
		
		// Se il nome di tale stanza è diverso da quello della stanza corrente allora posso andarci
		if ( !nomeStanza.equals(stanzaCorrente.getNomeStanza()))
			setNewStanzaCorrente(Mondo.getStanzaByName(nomeStanza));
		
		// Altrimenti posso andare nell'altra stanza
		else
			setNewStanzaCorrente(Mondo.getStanzaByName(link.getStanza2()));
		
		// Mostro a video dove il giocatore si è spostato
		System.out.println("Ti sei spostato in: " + getStanzaCorrente());
	}
	
	
	/**
	 * Metodo che dato il nome di una stanza, se è raggiungibile dalla stanza corrente, ti ci fa entrare
	 * @param nomeStanzaInCuiAndare il nome della stanza in cui entrare
	 */
	public void entra(String nomeStanzaInCuiAndare)
	{
		try 
		{
			// Ricavo la direzione in cui è il link rispetto alla stanza corrente
			Direzione dir = stanzaCorrente.getDirezioneByLinkName(nomeStanzaInCuiAndare);
			
			// Provo ad andare in quella direzione
			vai(dir.name());
		}
		catch(LinkInesistenteException | ImpossibileAndareA e) 
		{
			System.out.println(e.getMessage());
		}
		
	}
	
	
	/**
	 * Metodo che dato il nome di un oggetto che si vuole rompere, se è nella stanza del giocatore lo rompe
	 * @param nomeOggettoDaRompere il nome dell'oggetto da rompere
	 * 
	 * @throws EntitaNonPresenteException sollevata se l'oggetto che si vuole rompere non è nella stanza del giocatore
	 * @throws ImpossibileRompereException sollevata se l'oggetto non può essere rotto
	 */
	public void rompi(String nomeOggettoDaRompere) 
			throws EntitaNonPresenteException, ImpossibileRompereException
	{
		// Ricavo il riferimento all'oggeto che si vuole rompere
		Entita entDaRompere = stanzaCorrente.getEntitaByName(nomeOggettoDaRompere);
		
		// Se il giocatore non ha un oggetto in grado di romperlo
		if (entDaRompere != null && !inventario.containsOggettoPotente())
			throw new ImpossibileRompereException("Per rompere questo oggetto serve uno strumento molto forte!");
		
		// Altrimenti, se l'oggetto può essere rotto a mani nude lo rompo
		else if (entDaRompere != null && entDaRompere instanceof Fragile)
		{
			try
			{
				((Fragile) entDaRompere).rompi();
			}
			catch (OggettoGiaRottoException e)
			{
				System.out.println(e.getMessage());
			}
		}
		// Altrimenti, se l'oggetto da rompere non è nella stanza
		else if (entDaRompere == null)
			throw new EntitaNonPresenteException("In questa stanza non ci sta: " + nomeOggettoDaRompere);
	}
	
	
	/**
	 * Metodo per rompere un oggetto usandone un altro
	 * @param nomeOggettoDaUsare il nome dell'oggetto che si vuole usare per rompere l'altro oggetto
	 * @param nomeOggettoDaRompere il nome dell'oggetto da rompere
	 */
	public void rompi(String nomeOggettoDaRompere, String nomeOggettoDaUsare)
	{
		try 
		{
			// Richiamo il metodo usa (che permette di usare un oggetto su un altro)
			usa(nomeOggettoDaUsare, nomeOggettoDaRompere);
		} 
		catch (ImpossibileUsareSuExeption e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * Metodo per utilizzare uno strumento su di un altro
	 * 
	 * @param nomeOggettoDaUsare il nome dello strumento da usare
	 * @param nomeOggettoSuCuiUsarlo il nome dell'oggetto su cui usarlo
	 * 
	 * @throws ImpossibileUsareSuExeption sollevata se è impossibile usare lo strumento sull'oggetto indicato
	 */
	public void usa(String nomeOggettoDaUsare, String nomeOggettoSuCuiUsarlo) throws ImpossibileUsareSuExeption
	{
		// Ricavo il riferimento all'oggetto che si vuole usare
		Entita oggDaUsare = Mondo.getOggettoByName(nomeOggettoDaUsare);
		
		// Ricavo il riferimento all'oggetto su cui usare l'oggetto che si vuole usare
		Entita oggSuCuiUsare = stanzaCorrente.getEntitaByName(nomeOggettoSuCuiUsarlo);
		
		// Ricavo il riferimento al link che si potrebbe voler usare
		Link linkDaUsare = Mondo.getLinkByName(nomeOggettoSuCuiUsarlo);
		
		
		// Se l'oggetto che si vuole usare non esiste oppure non è nell'inventario
		if (oggDaUsare == null || (oggDaUsare instanceof EntitaPrendibile && !inventario.contains((EntitaPrendibile)oggDaUsare)) )
			throw new ImpossibileUsareSuExeption("Non puoi usare: " + nomeOggettoDaUsare);
		
		
		// Se l'oggetto da usare è potente e l'oggetto su cui usarlo è fragile
		// --> allora posso rompere l'oggetto
		if (oggSuCuiUsare != null && oggDaUsare instanceof Potente && oggSuCuiUsare instanceof Fragile)
		{
			try 
			{	
				// Rompo l'oggetto
				((Fragile)oggSuCuiUsare).rompi();
			}
			catch (OggettoGiaRottoException e)
			{
				System.out.println(e.getMessage());
			}
		}
		// Altrimenti, voglio provare ad usare l'oggetto su di un altro per altri motivi
		// Se l'oggetto da usare è nell'inventario del giocatore e l'oggetto sul quale voglio usarlo esiste
		else if (oggSuCuiUsare != null)
		{
			// Sel'oggetto su cui usarlo è uno Spegnibile
			if (oggSuCuiUsare instanceof AccendibileSpegnibile)
			{
				try
				{
					AccendibileSpegnibile ogg = (AccendibileSpegnibile) oggSuCuiUsare;
					
					// Se è acceso lo provo a spegnere
					if (ogg.isAcceso())
						ogg.spegni(nomeOggettoDaUsare);
					
					// Se è spento, lo provo a accendere
					else
						ogg.accendi();
					
					// Mostro l'esito dell'operazione
					System.out.println("Ora hai: " + oggDaUsare);
					
				}
				catch (OggettoGiaSpentoException | OggettoGiaAccesoException e) 
				{
					System.out.println(e.getMessage());
				}
			}
			// Se l'oggetto che voglio usare è un riempibile
			// --> allora lo provo a riempire
			else if (oggDaUsare instanceof RiempibileSvuotabile)
			{
				try 
				{
					RiempibileSvuotabile ogg = (RiempibileSvuotabile) oggDaUsare;
					
					// Se l'oggetto è vuoto, lo provo a riempire tramite l'oggetto dato
					if (ogg.isEmpty())
						ogg.riempiDal(nomeOggettoSuCuiUsarlo);
					
					// Se invece è pieno, provo a svuotarlo
					else
						ogg.svuotaSu(nomeOggettoSuCuiUsarlo);
					
					// Mostro l'esito dell'operazione
					System.out.println("Ora hai: " + oggDaUsare);
	
				}
				catch (ImpossibileRiempireException | OggettoGiaVuotoException e)
				{
					System.out.println(e.getMessage());
				}
			}
		
			// Un'altro motivo: è per aprire un link (ES: il cacciavite sulla vite serve ad aprire la botola)
			else if (oggSuCuiUsare.getElencoTarget() != null)	// -> Ha almeno un target?
			{
				// Itero sui target dell'oggetto su cui voglio usare lo strumento
				// fino a che non trovo la corrispondenza che mi interessa
				for (String nomeLink : oggSuCuiUsare.getElencoTarget().keySet())
				{
					// Ottengo il riferimento al linkTarget dell'oggetto su cui usare lo strumento 
					Link link = Mondo.getLinkByName(nomeLink);
					
					// Se tale link è un apribile con strumento
					if (link != null && link instanceof ApribileConStrumento)
					{
						// Allora lo apro
						apriApribileConStrumento((ApribileConStrumento) link, oggSuCuiUsare);
						
						System.out.println("Operazione conclusa con successo");
						break;
					}
				}
			}
		}
		// Altrimenti, se voglio usare un link (MezzoDiTrasporto), allora prendo il link
		else if (linkDaUsare != null && linkDaUsare instanceof MezzoDiTrasposto)
		{
			try 
			{
				// Prendo il link (ES: prendo il teletrasporto se ho usato la chiave_teletrasporto
				prendi(nomeOggettoSuCuiUsarlo);
			}
			catch (ImpossibilePrendereException e) 
			{
				throw new ImpossibileUsareSuExeption("Impossibile muoversi con " + nomeOggettoSuCuiUsarlo + " usando " + oggDaUsare);
			}
		}
		// Altrimenti, è impossibile usare un oggetto sull'altro
		else
			throw new ImpossibileUsareSuExeption("Impossibile usare: " + nomeOggettoDaUsare + " su " + nomeOggettoSuCuiUsarlo);
	}
	

	/**
	 * Metodo per utilizzare dei link che sono mezzi di trasporto
	 * ES: usa teletrasporto, usa navetta
	 * 
	 * @param nomeElementoDaUsare, il nome del link che si vuole usare
	 * @throws ImpossibileUsareSuExeption sollevata se l'elemento indicato non può essere usato
	 */
	public void usa(String nomeElementoDaUsare) throws ImpossibileUsareSuExeption
	{
		// Ricavo il riferimento al link che voglio usare
		Link link = Mondo.getLinkByName(nomeElementoDaUsare);
		
		// Se tale link esiste ed è un mezzo di trasporto (ES: teletrasporto)
		if (link != null && link instanceof MezzoDiTrasposto)
		{
			try 
			{
				// Ricavo la direzione in cui è il link rispetto alla stanza corrente
				Direzione dir = stanzaCorrente.getDirezioneByLinkName(nomeElementoDaUsare);
				
				// Provo ad andare in quella direzione
				vai(dir.name());
			} 
			catch (LinkInesistenteException | ImpossibileAndareA e) 
			{
				System.out.println(e.getMessage());
			}
			
		}
		// Se è impossibile usare l'elemento indicato
		else
			throw new ImpossibileUsareSuExeption("Impossibile usare: " + nomeElementoDaUsare);
	}
	
	/**
	 * Comando che stampa tutto il contenuto dell'inventario
	 */
	public void inventario()
	{
		System.out.println(showInventario());
	}
	
	
	/**
	 * Metodo per far parlare il giocatore con un personaggio presente nella sua stessa stanza
	 */
	@Override
	public void parla(String nomePersonaggioConCuiParlare)
	{
		// Ricavo un riferimento all'entità nella stanza con il nome che mi interessa
		Entita pers = stanzaCorrente.getEntitaByName(nomePersonaggioConCuiParlare);
		
		// Se il personaggio esiste ed è dunque nella stessa stanza del giocatore
		if (pers != null && pers instanceof Personaggio)
		{
			// Ci parlo
			((Personaggio) pers).parla(getName());
		}
	}
	
	
	/**
	 * Metodo per accarezzare un animale del mondo
	 * @param nomeAnimale il nome dell'animale che si vuole accarezzare
	 * @throws OperazioneNonSupportataException sollevata se non stai provando ad accarezzare un animale
	 */
	public void accarezza(String nomeAnimale) throws OperazioneNonSupportataException
	{
		// Ricavo il riferimento dell'entità dal nome
		Entita animale = stanzaCorrente.getEntitaByName(nomeAnimale);
		
		// Se l'entità è un animale allora lo accarezzo
		if (animale != null && animale instanceof Animale)
			((Animale)animale).riceviCarezza();
		
		// Altrimenti non può essere accarezzato
		else
			throw new OperazioneNonSupportataException("Non puoi accarezzare " + nomeAnimale);
		
	}
	
	
	/**
	 * Metodo che permette di dare un entità ad un personaggio nella stessa stanza del gocatore
	 * 
	 * @param oggettoDaDare il nome dell'oggetto da dare
	 * @param personaggioACuiDarlo il nome del personaggio a cui darlo
	 * 
	 * @throws ImpossibileDareException sollevata se è impossibile dare l'oggetto al personaggio
	 */
	public void dai(String oggettoDaDare, String personaggioACuiDarlo) throws ImpossibileDareException
	{
		// Ricavo il riferimento all'oggetto che potrei voler dare
		Entita oggDaDare = Mondo.getOggettoByName(oggettoDaDare);
		
		// Ricavo il riferimento al personaggio che potrei voler dare
		Personaggio entDaDare = Mondo.getPersonaggioByName(oggettoDaDare);
		
		// Ricavo il riferimento al personaggio a cui voler dare l'entità
		Entita pers = stanzaCorrente.getEntitaByName(personaggioACuiDarlo);
		
		
		// Se l'oggetto/entità da dare non è nell'inventario, l'operazione non può essere
		// portata a termine. Verrà
		if ( (oggDaDare != null && oggDaDare instanceof EntitaPrendibile && !inventario.contains((EntitaPrendibile) oggDaDare))
			|| (entDaDare != null && entDaDare instanceof EntitaPrendibile && !inventario.contains((EntitaPrendibile) entDaDare)) )
		{
			throw new ImpossibileDareException("Non puoi dare " + oggettoDaDare + ". Non lo hai!");
		}
		
		// Se si sta cercando di dare qualcosa non ad un personaggio nella stanza, verrà sollevata
		// l'eccezione
		if (pers == null || !(pers instanceof Personaggio) )
			throw new ImpossibileDareException("Non puoi dare niente a:" + personaggioACuiDarlo);
		
		// Se ciò che si vuole dare lo si sta dando ad un Venditore
		if ( pers instanceof Venditore)
		{
			try 
			{
				// Il venditore vende al personaggio ciò che ha in cambio di qualcosa (soldi)
				List<EntitaPrendibile> oggComprati = ((Venditore) pers).vendi(oggettoDaDare);
				
				// Aggiungo all'inventario del giocatore gli oggetti comprati
				inventario.addAllOggetti(oggComprati);
				
				// Comunico che l'acquisto è andato a buon fine
				System.out.println("Acquisto effettuato: " + oggComprati);
			}
			catch (ImpossibileComprareException e)
			{
				System.out.println(e.getMessage());
			}
		}
		// Altrimenti, se si vuole dare qualcosa al Guardiano
		else if ( pers instanceof Guardiano)
		{
			EntitaPrendibile entData = null;
			
			// Se gli si vuole dare un oggetto dell'inventario
			if (oggDaDare != null && inventario.contains((EntitaPrendibile)oggDaDare) )
				entData = (EntitaPrendibile) oggDaDare;
			
			// Se gli si vuole dare un personaggio dell'inventario
			else if (entDaDare != null && inventario.contains((EntitaPrendibile)entDaDare) )
				entData = (EntitaPrendibile) entDaDare;
			
			// Do al guardiano l'entità prendibile che gli si vuole dare
			((Guardiano) pers).ricevi(entData);
		}
	}
	
	
}
