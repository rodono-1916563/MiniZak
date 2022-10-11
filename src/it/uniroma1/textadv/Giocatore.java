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
 * Il gioco ammette un solo giocatore, pertanto � realizzato implementando il Singleton.
 * Esso � un personaggio, e in quanto tale � un'entit� del mondo.
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
	 * Metodo per guardare ci� che ci sta nella stanza.
	 * Viene stampato a video tutto ci� che � visibile al giocatore.
	 */
	public void guarda()
	{
		// Mostro le entit� presenti nella stanza
		String str = "Sei in: <" + stanzaCorrente.getDescrizione() + ">\n" + 
					  stanzaCorrente.showEntitaNellaStanza() + "\n";
		
		// Mostro i collegamenti disponibili dalla stanza
		str += stanzaCorrente.showCollegamenti();
		
		System.out.println(str);
	}
	
	
	/**
	 * Metodo per guardare un entit� oppure per guardare in una direzione.
	 * Mostra a video ci� che viene visto.
	 * @param entitaDaGuardare l'entit�/Direzione da guardare
	 * 
	 * @throws EntitaNonPresenteException sollevata se non c'� l'entit� da guardare
	 * @throws ImpossibileAndareA sollevata se non si pu� guardare in una data direzione
	 */
	public void guarda(String entitaDaGuardare) throws EntitaNonPresenteException, ImpossibileAndareA
	{
		// Ricavo il riferimento all'entit� che si potrebbe voler guardare
		Entita entita = stanzaCorrente.getEntitaByName(entitaDaGuardare);
		
		
		// Se l'entit� esiste nella stanza
		if ( entita != null )
		{
			String str = "Stai guardando: " + entita.toString();
			System.out.println(str);
		}
		// Se � stato richiesto di guardare in una direzione valida (ES: guarda a N)
		else if ( Arrays.stream(Direzione.values()).anyMatch( x -> x.toString().equals(entitaDaGuardare.toUpperCase())) )
		{
			// Ricavo il riferimento alla direzione che si potrebbe voler guardare
			Direzione dir = Direzione.valueOf(Direzione.class, entitaDaGuardare);
			
			// Se la direzione data � valida, mostro i collegamenti della stanza nella direzione richiesta
			if (dir != null)
				System.out.println(stanzaCorrente.showCollegamentiByDirezione(dir));
			
			// Altrimenti, la direzione non � valida
			else
				throw new ImpossibileAndareA(entitaDaGuardare);
		}
		else
			throw new EntitaNonPresenteException(entitaDaGuardare + " non � nella stanza!");
	}
	

	
	/**
	 * Metodo per aprire un oggetto Apribile
	 * @param nomeOggettoDaAprire il nome dell'oggetto da aprire
	 * 
	 * @throws EntitaNonPresenteException sollevata se l'elemento da aprire non � presente nella stanza
	 */
	public void apri(String nomeOggettoDaAprire) throws EntitaNonPresenteException
	{
		// Prendo dalla stanza corrente il riferimento alla possibile entit� da aprire
		Entita entita = stanzaCorrente.getEntitaByName(nomeOggettoDaAprire);
		
		Link link = null;
		
		// Se nella stanza non esiste l' entit� da aprire
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

		
		// Se si vuole provare ad aprire un'entit� (ES: scrivania)
		if (entita != null && entita instanceof Apribile)
			apriApribile((Apribile)entita);
		
		// Se invece � un link apribile allora provo a aprirlo
		else if (link != null && link instanceof Apribile) 
			apriApribile((Apribile)link);
		
		else
			throw new EntitaNonPresenteException(nomeOggettoDaAprire + " non � nella stanza!");
			
	}
	
	/**
	 * Metodo di utilit� che dato un elemento Apribile, lo prova ad aprire
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
			// Se l'elemento � gi� aperto oppure non � apribile in questo momento
			// verr� comunicato all'utente
			System.out.println(e.getMessage());
		}
	}
	
	
	
	/**
	 * Metodo per aprire un oggetto o link usando un altro oggetto
	 * @param nomeOggettoDaAprire il nome dell'oggetto da aprire
	 * @param nomeOggettoDaUsare il nome dello strumento che si vuole usare per aprirlo
	 * 
	 * @throws ImpossibileAprireException sollevata se � impossibile aprire l'oggetto indicato
	 */
	public void apri(String nomeOggettoDaAprire, String nomeOggettoDaUsare) throws ImpossibileAprireException
	{
		// Ricavo il riferimento all'oggetto che si vuole usare
		Entita oggDaUsare = Mondo.getOggettoByName(nomeOggettoDaUsare);
		
		// Se l'oggetto che si vuole usare � nell'inventario del giocatore
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
				// Non faccio niente perch� potrebbe ancora essere aperta un'entit�
				//System.out.println(e.getMessage());
			}
			
			// Prendo il riferimento all'entit� che si potrebbe voler aprire
			Entita entDaAprire = stanzaCorrente.getEntitaByName(nomeOggettoDaAprire);
			
			// Se ci� che si vuole aprire � un link apribile con uno strumento allora provo ad aprirlo
			if (linkDaAprire != null && linkDaAprire instanceof ApribileConStrumento )
				apriApribileConStrumento((ApribileConStrumento) linkDaAprire, oggDaUsare);
			
			// Se ci� che si vuole aprire � un entit� apribile con strumento, allora provo a aprirla
			else if (entDaAprire != null && entDaAprire instanceof ApribileConStrumento )
				apriApribileConStrumento((ApribileConStrumento) entDaAprire, oggDaUsare);
			
			// Altrimenti, l'oggetto indicato non pu� essere aperto
			else
				throw new ImpossibileAprireException("Impossibile aprire: " + nomeOggettoDaAprire);
			
		}
		// Se l'oggetto indicato per aprire non pu� essere usato, l'elemento indicato non pu� essere aperto
		else
			throw new ImpossibileAprireException("Impossibile usare: " + nomeOggettoDaUsare + " per aprire: " + nomeOggettoDaAprire);
	}
	
	
	/**
	 * Metodo di utilit� per aprire un elemento apribile con strumento usando un entit�
	 * @param elemDaAprire il riferimento all'elemento che si vuole aprire
	 * @param oggDaUsare il riferimento all'entit� che si vuole usare per aprire
	 */
	private void apriApribileConStrumento(ApribileConStrumento elemDaAprire, Entita oggDaUsare)
	{
		try 
		{
			// Apro l'elemento da aprire usando l'entit� indicata
			elemDaAprire.apri(oggDaUsare);
		} 
		catch (ImpossibileAprireException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Metodo per prendere un entit� da un entit� specificata
	 * 
	 * @param nomeEntitaDaPrendere il nome dell'entit� che si vuole prendere
	 * @param nomeOggettoDaCuiPrendere il nome dell'entit� da cui prendere l'entit� desiderata
	 * 
	 * @throws EntitaNonPresenteException sollevata se l'entit� cercata non � presente dove � stato indicato
	 * @throws ImpossibilePrendereException sollevata se � impossibile prendere l'elemento indicato
	 */
	public void prendi(String nomeEntitaDaPrendere, String nomeOggettoDaCuiPrendere) 
			throws EntitaNonPresenteException, ImpossibilePrendereException
	{
		// Ricavo l'elenco delle entit� presenti nella stanza corrente del giocatore
		Map<String, Entita> elenco = stanzaCorrente.getElencoEntitaNellaStanza();
		
		// Ricavo il riferimento all'entit� da cui provare a prendere l'entit� desiderata
		Entita entDaCuiPrendere = elenco.get(nomeOggettoDaCuiPrendere);
		
		// Provo a prendere dai target dell'entit� daCuiPrendere il riferimento all'entit� da prendere
		Target entPresa = entDaCuiPrendere.getTargetByName(nomeEntitaDaPrendere);
		
		
		// Se nell'entit� data in input non � presente l'entit� cercata, sollevo eccezione
		if (entPresa == null)
			throw new EntitaNonPresenteException("In " + nomeOggettoDaCuiPrendere + " non c'�: " + nomeEntitaDaPrendere);
		
		// Altrimenti, se l'entit� � prendibile la prendo e aggiungo all'inventario
		else if (entPresa instanceof EntitaPrendibile)
			prendiOggEMettiInInventario((EntitaPrendibile) entPresa);
		
		// Altrimenti l'entit� indicata non pu� essere presa
		else
			throw new ImpossibilePrendereException("Impossibile prendere: " + nomeEntitaDaPrendere + " da " + nomeOggettoDaCuiPrendere);
	}
	
	
	/**
	 * Metodo di utilit� che data un'entit� prendidibile la aggiunge all'inventario
	 * @param entPresa il riferimento all'entit� da aggiungere all'inventario
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
	 * @throws ImpossibilePrendereException sollevata se l'oggetto non pu� essere preso per qualche motivo
	 */
	public void prendi(String oggettoDaPrendere) throws ImpossibilePrendereException
	{
		// Ricavo l'elenco delle entit� presenti nella stanza corrente del giocatore
		Map<String, Entita> elenco = stanzaCorrente.getElencoEntitaNellaStanza();
		
		// Ricavo il riferimento all'entit� che si vuole prendere
		Entita oggDesiderato = elenco.get(oggettoDaPrendere);
		
		// Se l'oggetto che si vuole prendere � un Comprabile
		if (oggDesiderato != null && oggDesiderato instanceof Comprabile)
			throw new ImpossibilePrendereException("Per prendere " + oggettoDaPrendere + " devi pagarlo!");

		// Se invece � un'entit� prendibile non controllata, allora la prendo
		else if (oggDesiderato != null && oggDesiderato instanceof EntitaPrendibile && !(oggDesiderato instanceof Controllabile))
		{
			// Aggiungo all'inventario l'oggetto preso
			prendiOggEMettiInInventario((EntitaPrendibile) oggDesiderato);
			
			// Rimuovo dalla stanza l'entit� presa
			stanzaCorrente.getElencoEntitaNellaStanza().remove(oggettoDaPrendere);
		}
		
		// Se invece � un'entit� prendibile controllata
		else if (oggDesiderato != null && oggDesiderato instanceof EntitaPrendibile && oggDesiderato instanceof Controllabile)
		{
			// Se l'oggetto � controllato, non pu� essere preso
			if ( ((Controllabile) oggDesiderato).isCheked() )
				throw new ImpossibilePrendereException(oggettoDaPrendere + " non pu� essere preso, perch� qualcuno lo sta controllando!");
			
			// Se non � controllato, allora pu� essere preso
			else
				prendiOggEMettiInInventario((EntitaPrendibile) oggDesiderato);

		}
		// Altrimenti, se � un link che pu� essere preso (ES: prendi navetta), allora lo prendo
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
			throw new ImpossibilePrendereException("Questo oggetto al momento non pu� essere preso");	
		}
		// Altrimenti l'oggetto non pu� essere preso!!
		else
			throw new ImpossibilePrendereException("[ERROR] " + oggettoDaPrendere + " non pu� essere preso!!");
	}
	
	
	
	/**
	 * Metodo che data una direzione, sposta il giocatore nella stanza raggiungibile percorrendo tale direzione
	 * @param direzioneInCuiAndare la direzione in cui andare
	 * 
	 * @throws ImpossibileAndareA sollevata se non ci si pu� spostare in tale direzione
	 */
	public void vai(String direzioneInCuiAndare) throws ImpossibileAndareA
	{
		// Ottengo l'istanza associata alla direzione che mi interessa
		Direzione dir = Direzione.valueOf(Direzione.class, direzioneInCuiAndare);
		
		// Se la direzione non � valida, non ci si pu� spostare
		if (dir == null)
			throw new ImpossibileAndareA("Impossibile andare a: " + direzioneInCuiAndare);
		
		// Ricavo i collegamenti disponibili nella direzione specificata
		List<Link> listaLink = stanzaCorrente.getCollegamentiByDirezione(dir);
		
		// Se nella stanza corrente ci sta almeno un link
		if ( listaLink.size() > 0 )
		{
			// TODO feature: in caso di pi� stanze in una data direzione, si potrebbe dover scegliere in
			// 				 quale dover andare a run time
			for (Link link : listaLink)
			{
				// Se il link � un apribile ed � aperto, 
				// allora ci si pu� spostare
				if ((link instanceof Apribile && ((Apribile) link).isOpen()))
				{
					spostati(link);
					break;
				}
				// Altrimenti, se il link � un apribile ed � chiuso
				else if ( (link instanceof Apribile && !((Apribile) link).isOpen()) )
				{
					System.out.println("Il link " + link.getName() + " � chiuso");
					System.out.println("Sei ancora in: " + getStanzaCorrente());
				}
				// Altrimenti, se il link � un mezzo di trasporto oppure � una stanza aperta
				else if ( link instanceof MezzoDiTrasposto || link instanceof StanzaLink )
				{
					spostati(link);
					break;
				}
			}
			
		}
		// Altrimenti, se la lista dei link della stanza � vuota, non ci si pu� spostare
		else
			throw new ImpossibileAndareA("Impossibile andare a: " + direzioneInCuiAndare);
	}
	

	/**
	 * Metodo privato di utilit� per spostarsi secondo un link
	 * @param link il link che permette di spostarsi
	 */
	private void spostati(Link link)
	{
		// Ricavo il nome di una delle stanze del link
		String nomeStanza = link.getStanza1();
		
		// Se il nome di tale stanza � diverso da quello della stanza corrente allora posso andarci
		if ( !nomeStanza.equals(stanzaCorrente.getNomeStanza()))
			setNewStanzaCorrente(Mondo.getStanzaByName(nomeStanza));
		
		// Altrimenti posso andare nell'altra stanza
		else
			setNewStanzaCorrente(Mondo.getStanzaByName(link.getStanza2()));
		
		// Mostro a video dove il giocatore si � spostato
		System.out.println("Ti sei spostato in: " + getStanzaCorrente());
	}
	
	
	/**
	 * Metodo che dato il nome di una stanza, se � raggiungibile dalla stanza corrente, ti ci fa entrare
	 * @param nomeStanzaInCuiAndare il nome della stanza in cui entrare
	 */
	public void entra(String nomeStanzaInCuiAndare)
	{
		try 
		{
			// Ricavo la direzione in cui � il link rispetto alla stanza corrente
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
	 * Metodo che dato il nome di un oggetto che si vuole rompere, se � nella stanza del giocatore lo rompe
	 * @param nomeOggettoDaRompere il nome dell'oggetto da rompere
	 * 
	 * @throws EntitaNonPresenteException sollevata se l'oggetto che si vuole rompere non � nella stanza del giocatore
	 * @throws ImpossibileRompereException sollevata se l'oggetto non pu� essere rotto
	 */
	public void rompi(String nomeOggettoDaRompere) 
			throws EntitaNonPresenteException, ImpossibileRompereException
	{
		// Ricavo il riferimento all'oggeto che si vuole rompere
		Entita entDaRompere = stanzaCorrente.getEntitaByName(nomeOggettoDaRompere);
		
		// Se il giocatore non ha un oggetto in grado di romperlo
		if (entDaRompere != null && !inventario.containsOggettoPotente())
			throw new ImpossibileRompereException("Per rompere questo oggetto serve uno strumento molto forte!");
		
		// Altrimenti, se l'oggetto pu� essere rotto a mani nude lo rompo
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
		// Altrimenti, se l'oggetto da rompere non � nella stanza
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
	 * @throws ImpossibileUsareSuExeption sollevata se � impossibile usare lo strumento sull'oggetto indicato
	 */
	public void usa(String nomeOggettoDaUsare, String nomeOggettoSuCuiUsarlo) throws ImpossibileUsareSuExeption
	{
		// Ricavo il riferimento all'oggetto che si vuole usare
		Entita oggDaUsare = Mondo.getOggettoByName(nomeOggettoDaUsare);
		
		// Ricavo il riferimento all'oggetto su cui usare l'oggetto che si vuole usare
		Entita oggSuCuiUsare = stanzaCorrente.getEntitaByName(nomeOggettoSuCuiUsarlo);
		
		// Ricavo il riferimento al link che si potrebbe voler usare
		Link linkDaUsare = Mondo.getLinkByName(nomeOggettoSuCuiUsarlo);
		
		
		// Se l'oggetto che si vuole usare non esiste oppure non � nell'inventario
		if (oggDaUsare == null || (oggDaUsare instanceof EntitaPrendibile && !inventario.contains((EntitaPrendibile)oggDaUsare)) )
			throw new ImpossibileUsareSuExeption("Non puoi usare: " + nomeOggettoDaUsare);
		
		
		// Se l'oggetto da usare � potente e l'oggetto su cui usarlo � fragile
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
		// Se l'oggetto da usare � nell'inventario del giocatore e l'oggetto sul quale voglio usarlo esiste
		else if (oggSuCuiUsare != null)
		{
			// Sel'oggetto su cui usarlo � uno Spegnibile
			if (oggSuCuiUsare instanceof AccendibileSpegnibile)
			{
				try
				{
					AccendibileSpegnibile ogg = (AccendibileSpegnibile) oggSuCuiUsare;
					
					// Se � acceso lo provo a spegnere
					if (ogg.isAcceso())
						ogg.spegni(nomeOggettoDaUsare);
					
					// Se � spento, lo provo a accendere
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
			// Se l'oggetto che voglio usare � un riempibile
			// --> allora lo provo a riempire
			else if (oggDaUsare instanceof RiempibileSvuotabile)
			{
				try 
				{
					RiempibileSvuotabile ogg = (RiempibileSvuotabile) oggDaUsare;
					
					// Se l'oggetto � vuoto, lo provo a riempire tramite l'oggetto dato
					if (ogg.isEmpty())
						ogg.riempiDal(nomeOggettoSuCuiUsarlo);
					
					// Se invece � pieno, provo a svuotarlo
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
		
			// Un'altro motivo: � per aprire un link (ES: il cacciavite sulla vite serve ad aprire la botola)
			else if (oggSuCuiUsare.getElencoTarget() != null)	// -> Ha almeno un target?
			{
				// Itero sui target dell'oggetto su cui voglio usare lo strumento
				// fino a che non trovo la corrispondenza che mi interessa
				for (String nomeLink : oggSuCuiUsare.getElencoTarget().keySet())
				{
					// Ottengo il riferimento al linkTarget dell'oggetto su cui usare lo strumento 
					Link link = Mondo.getLinkByName(nomeLink);
					
					// Se tale link � un apribile con strumento
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
		// Altrimenti, � impossibile usare un oggetto sull'altro
		else
			throw new ImpossibileUsareSuExeption("Impossibile usare: " + nomeOggettoDaUsare + " su " + nomeOggettoSuCuiUsarlo);
	}
	

	/**
	 * Metodo per utilizzare dei link che sono mezzi di trasporto
	 * ES: usa teletrasporto, usa navetta
	 * 
	 * @param nomeElementoDaUsare, il nome del link che si vuole usare
	 * @throws ImpossibileUsareSuExeption sollevata se l'elemento indicato non pu� essere usato
	 */
	public void usa(String nomeElementoDaUsare) throws ImpossibileUsareSuExeption
	{
		// Ricavo il riferimento al link che voglio usare
		Link link = Mondo.getLinkByName(nomeElementoDaUsare);
		
		// Se tale link esiste ed � un mezzo di trasporto (ES: teletrasporto)
		if (link != null && link instanceof MezzoDiTrasposto)
		{
			try 
			{
				// Ricavo la direzione in cui � il link rispetto alla stanza corrente
				Direzione dir = stanzaCorrente.getDirezioneByLinkName(nomeElementoDaUsare);
				
				// Provo ad andare in quella direzione
				vai(dir.name());
			} 
			catch (LinkInesistenteException | ImpossibileAndareA e) 
			{
				System.out.println(e.getMessage());
			}
			
		}
		// Se � impossibile usare l'elemento indicato
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
		// Ricavo un riferimento all'entit� nella stanza con il nome che mi interessa
		Entita pers = stanzaCorrente.getEntitaByName(nomePersonaggioConCuiParlare);
		
		// Se il personaggio esiste ed � dunque nella stessa stanza del giocatore
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
		// Ricavo il riferimento dell'entit� dal nome
		Entita animale = stanzaCorrente.getEntitaByName(nomeAnimale);
		
		// Se l'entit� � un animale allora lo accarezzo
		if (animale != null && animale instanceof Animale)
			((Animale)animale).riceviCarezza();
		
		// Altrimenti non pu� essere accarezzato
		else
			throw new OperazioneNonSupportataException("Non puoi accarezzare " + nomeAnimale);
		
	}
	
	
	/**
	 * Metodo che permette di dare un entit� ad un personaggio nella stessa stanza del gocatore
	 * 
	 * @param oggettoDaDare il nome dell'oggetto da dare
	 * @param personaggioACuiDarlo il nome del personaggio a cui darlo
	 * 
	 * @throws ImpossibileDareException sollevata se � impossibile dare l'oggetto al personaggio
	 */
	public void dai(String oggettoDaDare, String personaggioACuiDarlo) throws ImpossibileDareException
	{
		// Ricavo il riferimento all'oggetto che potrei voler dare
		Entita oggDaDare = Mondo.getOggettoByName(oggettoDaDare);
		
		// Ricavo il riferimento al personaggio che potrei voler dare
		Personaggio entDaDare = Mondo.getPersonaggioByName(oggettoDaDare);
		
		// Ricavo il riferimento al personaggio a cui voler dare l'entit�
		Entita pers = stanzaCorrente.getEntitaByName(personaggioACuiDarlo);
		
		
		// Se l'oggetto/entit� da dare non � nell'inventario, l'operazione non pu� essere
		// portata a termine. Verr�
		if ( (oggDaDare != null && oggDaDare instanceof EntitaPrendibile && !inventario.contains((EntitaPrendibile) oggDaDare))
			|| (entDaDare != null && entDaDare instanceof EntitaPrendibile && !inventario.contains((EntitaPrendibile) entDaDare)) )
		{
			throw new ImpossibileDareException("Non puoi dare " + oggettoDaDare + ". Non lo hai!");
		}
		
		// Se si sta cercando di dare qualcosa non ad un personaggio nella stanza, verr� sollevata
		// l'eccezione
		if (pers == null || !(pers instanceof Personaggio) )
			throw new ImpossibileDareException("Non puoi dare niente a:" + personaggioACuiDarlo);
		
		// Se ci� che si vuole dare lo si sta dando ad un Venditore
		if ( pers instanceof Venditore)
		{
			try 
			{
				// Il venditore vende al personaggio ci� che ha in cambio di qualcosa (soldi)
				List<EntitaPrendibile> oggComprati = ((Venditore) pers).vendi(oggettoDaDare);
				
				// Aggiungo all'inventario del giocatore gli oggetti comprati
				inventario.addAllOggetti(oggComprati);
				
				// Comunico che l'acquisto � andato a buon fine
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
			
			// Do al guardiano l'entit� prendibile che gli si vuole dare
			((Guardiano) pers).ricevi(entData);
		}
	}
	
	
}
