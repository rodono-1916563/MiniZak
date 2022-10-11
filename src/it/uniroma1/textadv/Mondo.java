package it.uniroma1.textadv;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import it.uniroma1.textadv.eccezioni.ConfigurazioneNonPossibileException;
import it.uniroma1.textadv.link.Link;
import it.uniroma1.textadv.oggetti.Oggetto;


/**
 * Classe che modella il mondo di gioco.
 * 
 * Implementato secondo il Singleton, pertanto può esistere una sola istanza del Mondo.
 * 
 * Il mondo mette a disposizione delle mappe statiche (poichè esisterà al massimo una sola istanza del mondo)
 * di utilità che rendeno disponibili in ogni momento (conoscendone il nome univoco) i riferimenti a 
 * stanza,link,personaggi,oggetti del mondo istanziato.
 * 
 * @author Gabriele
 *
 */
public class Mondo 
{
	
	/**
	 * Mappa che associa ad ogni nomeDellOggetto il riferimento all'oggetto istanziato
	 */
	private static Map<String, Oggetto> mappaOggettiIstanziati = new LinkedHashMap<>();
	
	
	/**
	 * Mappa che associa al nome del personaggio il riferimento al Personaggio istanziato
	 */
	private static Map<String, Personaggio> mappaPersonaggiIstanziati = new LinkedHashMap<>();
	
	
	/**
	 * Mappa che associa al nome del link il riferimento al link istanziato
	 */
	private static Map<String, Link> mappaLinkIstanziati = new LinkedHashMap<>();
	
	
	/**
	 * Mappa delle stanze del mondo, con:
	 * - chiave: il nome della stanza
	 * - valore: il riferimento alla stanza
	 */
	private static Map<String, Stanza> mappaStanzeIstanziate = new LinkedHashMap<>();
	
	
	//===========================================================================
	
	/**
	 * Il nome del mondo
	 */
	private String nomeMondo;

	/**
	 * La descrizione del mondo
	 */
	private String descrizioneMondo;
	
	/**
	 * La stanza di partenza da cui inizia il gioco
	 */
	private static Stanza stanzaDiPartenza;
	
	/**
	 * Riferimento al giocatore protagonista
	 */
	private static Giocatore player;
	
	/**
	 * Riferimento all'unica istanza del mondo
	 */
	private static Mondo mondo;
	
	
	/**
	 * Costruttore del mondo
	 * @param nomeMondo il nome del mondo
	 * @param descrizioneMondo la descrizione del mondo
	 */
	private Mondo(String nomeMondo, String descrizioneMondo)
	{
		this.nomeMondo = nomeMondo;
		this.descrizioneMondo = descrizioneMondo;
	}
	
	/**
	 * Metodo per ottenere l'unico riferimento all'istanza del mondo
	 * @param nomeMondo il nome del mondo
	 * @param descrizioneMondo la descrizione del mondo
	 * @return l'unica istanza del mondo
	 */
	public static Mondo getInstance(String nomeMondo, String descrizioneMondo)
	{
		if (mondo == null)
			mondo = new Mondo(nomeMondo, descrizioneMondo);
		
		return mondo;
	}
	
	
	/**
	 * Metodo che ritorna il nome del mondo
	 * @return il nome del mondo
	 */
	public String getNomeMondo()
	{
		return nomeMondo;
	}
	
	
	/**
	 * Metodo che ritorna la descrizione testuale del mondo
	 * @return la descrizione del mondo
	 */
	public String getDescrizioneMondo()
	{
		return descrizioneMondo;
	}
	

	/**
	 * Metodo per il caricamento del mondo dato il nome del file in formato stringa 
	 * @param nomeFile il nome del file contenente la configurazione del mondo
	 * @return l'istanza del mondo
	 */
	public static Mondo fromFile(String nomeFile) 
	{
		try 
		{
			// Richiamo il metodo di Mondo che vuole un Path in input
			return fromFile(Paths.get(nomeFile));
		}
		catch (IOException e) 
		{
			// Comunico all'utente che c'è stato un errore e forzo l'uscita dal gioco
			System.out.println("[ERROR] Impossibile giocare per un errore nella lettura del file di configurazione!");
			e.printStackTrace();
			System.exit(1);
		}
		
		// Non sarà mai raggiunto!
		return null;
	}
	
	
	/**
	 * Metodo per il caricamento del mondo dato il file di configurazione del mondo
	 * @param percorso il percorso relativo al file di configurazione del mondo
	 * @return l'istanza del mondo
	 * 
	 * @throws IOException sollevata se c'è stato un errore di IO
	 */
	public static Mondo fromFile(Path percorso) throws IOException
	{
		Mondo world = null;
		try 
		{
			// Richiamo il metodo factory della classe specializzata nella creazione del Mondo
			world =  MondoFactory.creaMondo(percorso);
		}
		catch (ConfigurazioneNonPossibileException e) 
		{	
			System.out.println("ERROR] " + e.getMessage());
			System.exit(1);
		}

		return world;
		
	}
	
	
	/**
	 * Metodo per settare il giocatore protagonista del gioco
	 * @param nomeGiocatore il nome del giocatore protagonista
	 */
	protected static void setPlayerProtagonista(String nomeGiocatore)
	{
		player = Giocatore.getInstance(nomeGiocatore);
	}
	
	
	/**
	 * Metodo per ottenere il riferimento al giocatore protagonista del mondo
	 * @return il riferimento al giocatore protagonista del mondo
	 */
	public static Giocatore getPlayer()
	{
		return player;
	}
	
	
	/**
	 * Metodo che ritorna una stringa formattata che presenta il nome e la descrizione
	 * del mondo caricato.
	 */
	@Override
	public String toString()
	{
		return "[LOADED] " + getNomeMondo() + "\n" + getDescrizioneMondo()+"\n";
	}
	

	/**
	 * Metodo statico che serve a settare la stanza di partenza del mondo
	 * @param stanzaDiPartenza la stanza da impostare come stanza di partenza
	 */
	protected static void setStanzaDiPartenza(Stanza stanzaDiPartenza)
	{
		Mondo.stanzaDiPartenza = stanzaDiPartenza;
	}
	
	/**
	 * Metodo che ritorna la stanza di partenza del mondo
	 * @return la stanza di partenza del mondo
	 */
	public static Stanza getStanzaDiPartenza()
	{
		return stanzaDiPartenza;
	}
	
	/**
	 * Metodo che mostra tutte le stanze del mondo
	 * @return l'elenco di tutte le stanze del mondo
	 */
	public static String showStanzeDelMondo()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Le stanze di questo mondo sono:\n");
		
		for (Stanza stanza : mappaStanzeIstanziate.values())
			sb.append(" - " + stanza.toString() + "\n");
		
		return sb.toString();
	}
	
	
	/**
	 * Metodo che ritorna l'elenco delle stanze del mondo
	 * @return l'elenco delle stanze del mondo
	 */
	public static Map<String, Stanza> getMappaStanzeIstanziate()
	{
		return mappaStanzeIstanziate;
	}
	
	/**
	 * Metodo per aggiungere una stanza alla mappa delle stanze istanziate
	 * @param nomeStanza il nome della stanza
	 * @param stanza il riferimento alla stanza
	 */
	public static void addStanzaToMappaStanzeIstanziate(String nomeStanza, Stanza stanza)
	{
		mappaStanzeIstanziate.putIfAbsent(nomeStanza, stanza);
	}
	
	/**
	 * Metodo per ottenere una stanza sapendone il nome
	 * @param nomeStanza il nome della stanza da ottenere
	 * @return il riferimento all'istanza della stanza cercata
	 */
	public static Stanza getStanzaByName(String nomeStanza)
	{
		return mappaStanzeIstanziate.get(nomeStanza);
	}
	
	/**
	 * Metodo che ritorna l'elenco degli oggetti nel mondo
	 * @return elenco degli oggetti nel mondo
	 */
	public static Map<String, Oggetto> getMappaOggettiIstanziati()
	{
		return mappaOggettiIstanziati;
	}
	
	/**
	 * Metodo per aggiungere un oggetto alla mappa degli oggetti istanziati
	 * @param nomeOggetto il nome dell'oggetto
	 * @param ogg il riferimento all'oggetto
	 */
	public static void addOggettoToMappaOggettiIstanziati(String nomeOggetto, Oggetto ogg)
	{
		mappaOggettiIstanziati.putIfAbsent(nomeOggetto, ogg);
	}
	
	
	/**
	 * Metodo per ottenere il riferimento ad un oggetto sapendone il nome
	 * @param nomeOgetto il nome dell'oggetto
	 * @return il riferimento ad un oggetto sapendone il nome
	 */
	public static Oggetto getOggettoByName(String nomeOgetto)
	{
		return mappaOggettiIstanziati.get(nomeOgetto);
	}
	
	/**
	 * Metodo che ritorna l'elenco dei link istanziati nel mondo
	 * @return l'elenco dei link istanziati nel mondo
	 */
	public static Map<String, Link> getMappaLinkIstanziati()
	{
		return mappaLinkIstanziati;
	}
	
	/**
	 * Metodo per aggiungere un link alla mappa dei link istanziati
	 * @param nomeLink il nome del link
	 * @param link il riferimento al link
	 */
	public static void addLinkToMappaLinkIstanziati(String nomeLink, Link link)
	{
		mappaLinkIstanziati.putIfAbsent(nomeLink, link);
	}
	
	/**
	 * Metodo per ricavare il riferimento ad un link sapendone il nome
	 * @param nomeLink il nome del link
	 * @return il riferimento ad un link sapendone il nome
	 */
	public static Link getLinkByName(String nomeLink)
	{
		return mappaLinkIstanziati.get(nomeLink);
	}
	
	/**
	 * Metodo che ritorna l'elenco dei personaggi istanziati del mondo
	 * @return l'elenco dei personaggi istanziati del mondo
	 */
	public static Map<String, Personaggio> getMappaPersonaggiIstanziati()
	{
		return mappaPersonaggiIstanziati;
	}
	
	
	/**
	 * Metodo per ottenere il riferimento al personaggio sapendone il nome
	 * @param nomePersonaggio il nome del personaggio
	 * @return il riferimento al personaggio sapendone il nome
	 */
	public static Personaggio getPersonaggioByName(String nomePersonaggio)
	{
		return mappaPersonaggiIstanziati.get(nomePersonaggio);
	}
	
	
	/**
	 * Metodo per aggiungere un personaggio alla mappa dei personaggi istanziati
	 * @param nomePersonaggio il nome del personaggio
	 * @param pers il riferimento al personaggio
	 */
	public static void addPersonaggioToMappaPersonaggiIstanziati(String nomePersonaggio, Personaggio pers)
	{
		mappaPersonaggiIstanziati.putIfAbsent(nomePersonaggio, pers);
	}
	
}
