package it.uniroma1.textadv;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.uniroma1.textadv.eccezioni.LinkInesistenteException;
import it.uniroma1.textadv.link.Link;
import it.uniroma1.textadv.utilita.Direzione;


/**
 * Classe che modella una stanza, intesa come un qualsiasi ambiente (sia interno che esterno)
 * 
 * @author Gabriele
 *
 */
public class Stanza 
{	
	// Messaggio di errore
	public static final String DIREZIONE_NON_VALIDA = "Non ci sono collegamenti validi a ";
	
	/**
	 * Il nome della stanza
	 */
	private String nomeStanza;
	
	/**
	 * La descrizione testuale della stanza
	 */
	private String descrizioneStanza;
	
	/**
	 * Elenco delle stanze raggiungibili dalla stanza corrente,
	 * organizzate secondo una direzione
	 */
	private Map<Direzione, List<Link>> elencoCollegamenti = new LinkedHashMap<>();
	
	/**
	 * Mappa delle entità contenute nella stanza:
	 * - chiave: il nome dell'entità
	 * - valore: il riferimento all'entità
	 */
	private Map<String, Entita> elencoEntitaNellaStanza = new LinkedHashMap<>();
	
	/**
	 * Costruttore delle Stanze
	 * @param nomeStanza il nome della stanza
	 * @param descrizioneStanza la descrizione testuale della stanza
	 */
	public Stanza(String nomeStanza, String descrizioneStanza)
	{
		this.nomeStanza = nomeStanza;
		this.descrizioneStanza = descrizioneStanza;
	}
	
	/**
	 * Metodo per ottenere il nome della stanza
	 * @return il nome della stanza
	 */
	public String getNomeStanza() { return nomeStanza; }
	
	
	/**
	 * Metodo che ritorna la descrizione della stanza
	 * @return la descrizione della stanza
	 */
	public String getDescrizione()
	{
		return descrizioneStanza;
	}
	
	
	/**
	 * Metodo per aggiungere un'entità nella stanza
	 * @param nomeEntita il nome dell'entità da aggiungere nella stanza
	 * @param entita l'entita da aggiungere nella stanza
	 */
	public void addEntitaNellaStanza(String nomeEntita, Entita entita)
	{
		// Aggiungo nella mappa delle entità nella stanza la coppia (nome, riferimento)
		elencoEntitaNellaStanza.putIfAbsent(nomeEntita, entita);
		
		// Posiziono l'entità nella stanza
		entita.setNewStanzaCorrente(this);
	}
	
	
	/**
	 * Metodo per aggiungere un link alla stanza
	 * @param dir la direzione in cui è il link
	 * @param link il link da aggiungere
	 */
	public void addLinkDellaStanza(Direzione dir, Link link)
	{
		// Nella mappa dei Link, aggiungo una lista contenente solo il link se la chiave dir non è presente,
		// altrimenti unisco il nuovo link alla lista dei link precedente
		elencoCollegamenti.merge(dir, Arrays.asList(link), 
								(oldList, newList) -> Stream.concat(oldList.stream(), newList.stream())
															.collect(Collectors.toList()));
	}
	
	/**
	 * Metodo per verificare se due stanze sono uguali
	 * @return true se sono la stessa stanza, false se sono stanze diverse
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o == null || getClass() != o.getClass())
			return false;
		
		Stanza other = (Stanza) o;
		
		// Ogni stanza ha il proprio nome e la propria descrizione univoca
		return nomeStanza.equals(other.nomeStanza) && descrizioneStanza.equals(other.descrizioneStanza);
	}
	
	
	/**
	 * Metodo che restituisce l'hashCode associato alla stanza
	 * @return l'intero associato alla stanza
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(nomeStanza, descrizioneStanza);
	}
	
	
	/**
	 * Metodo che ritorna una stringa che elenca tutte le entità presenti nella stanza
	 * @return l'elenco delle entità nella stanza
	 */
	public String showEntitaNellaStanza()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Ci sono le seguenti entità:\n");
		
		for (Entita ent : elencoEntitaNellaStanza.values())
			sb.append(" - " + ent.toString() + "\n");
		
		return sb.toString();
	}
	
	
	/**
	 * Metodo che data una Direzione, ritorna la stringa raffigurante i link in quella direzione
	 * @param dir la direzione da controllare
	 * @return la stringa rappresentante i link in quella direzione
	 */
	public String showCollegamentiByDirezione(Direzione dir)
	{
		// Se nella direzione data non ci sono link
		if (getCollegamentiByDirezione(dir) == null)
			return DIREZIONE_NON_VALIDA + dir;
		
		// Altrimenti compongo la stringa con tutti i link della stanza
		StringBuffer sb = new StringBuffer();
		sb.append("Ad " + dir + " ci sono:\n");
		
		for (Link link : getCollegamentiByDirezione(dir))
			sb.append(" - " + link.toString() + "\n");
		
		return sb.toString();
	}
	
	/**
	 * Data una direzione, ritorna l'elenco dei collegamenti in quella direzione
	 * @param dir la direzione che ci interessa
	 * @return la lista dei collegamenti in quella direzione
	 */
	public List<Link> getCollegamentiByDirezione(Direzione dir)
	{
		return elencoCollegamenti.get(dir);
	}
	
	/**
	 * Metodo per ottenere la mappa completa dei collegamenti di una stanza
	 * @return la mappa dei collegamenti della stanza
	 */
	public Map<Direzione, List<Link>> getElencoCollegamenti()
	{
		return elencoCollegamenti;
	}
	
	/**
	 * Metodo che ritorna l'elenco delle entità nella stanza
	 * @return l'elenco delle entità nella stanza
	 */
	public Map<String, Entita> getElencoEntitaNellaStanza()
	{
		return elencoEntitaNellaStanza;
	}
	
	
	/**
	 * Sapendo il nome dell'entità, otteniamo il suo riferimento
	 * @param nomeEntita il nome dell'entità che vogliamo ottenere
	 * @return il riferimento all'entità desiderata
	 */
	public Entita getEntitaByName(String nomeEntita)
	{
		return elencoEntitaNellaStanza.get(nomeEntita);
	}
	
	
	/**
	 * Metodo che dato il nome di un link/stanza, ritorna la sua direzione
	 * @param nomeLink il nome della stanza/link di cui si vuole conoscere la direzione
	 * @return la direzione della stanza/link
	 * 
	 * @throws LinkInesistenteException sollevata se non esiste alcun link/stanza con il nome dato
	 */
	public Direzione getDirezioneByLinkName(String nomeLink) throws LinkInesistenteException
	{
		// Ricavo il riferimento al link con il nome dato
		Link link = Mondo.getLinkByName(nomeLink);
		
		// Ricavo il riferimento alla stanza con il nome dato
		Stanza stanza = Mondo.getStanzaByName(nomeLink);
		
		// Se è un link esistente nel mondo
		if (link != null)
		{
			// Prendo tutte le coppie Chiave:Valore dell'elenco dei collegamenti della stanza
			for (Entry<Direzione, List<Link>> entry : getElencoCollegamenti().entrySet())
			{
				// Se il link richiesto è nella stanza,
				// -> allora ne ritorno la direzione
				if (entry.getValue().contains(link))
					return entry.getKey();
			}
		}
		// Altrimenti, se il link cercato è in realtà una stanza vicina della stanzaCorrente
		else if (stanza != null)
		{
			// Prendo tutte le coppie Chiave:Valore dell'elenco dei collegamenti della stanza
			for (Entry<Direzione, List<Link>> entry : getElencoCollegamenti().entrySet())
			{
				// Se esiste un link con lo stesso nome della stanza
				// -> allora ne ritorno la direzione
				if (entry.getValue().stream().map(Link::getStanza2).anyMatch(l -> l.equals(stanza.getNomeStanza())))
					return entry.getKey();
			}
		}
		else
			// Sollevo l'eccezione che il link non esiste
			throw new LinkInesistenteException("Non esiste link per questa stanza: " + nomeLink);
		
		return null;
	}
	
	
	/**
	 * Metodo che ritorna una stringa che elenca tutti i collegamenti della stanza corrente
	 * @return una stringa che riporta l'elenco dei collegamenti della stanza
	 */
	public String showCollegamenti()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("I collegamenti di <" + getNomeStanza() + "> sono:\n");
		
		for (Entry<Direzione, List<Link>> entry : elencoCollegamenti.entrySet())
				if (entry.getValue() != null)
					sb.append(" - Ad " + entry.getKey() + ": " + entry.getValue() + "\n");
		
		return sb.toString();
	}
	
	
	
	/**
	 * Metodo che dato il nome di un link, restituisce il riferimento al link se esso 
	 * appartiene alla stanza
	 * 
	 * @param nomeLink il nome del link di cui interessa ricavare il riferimento
	 * @return il riferimento al link se esso appartiene alla stanza
	 * 
	 * @throws LinkInesistenteException sollevata se il link non è nella stanza
	 */
	public Link getLinkByName(String nomeLink) throws LinkInesistenteException
	{
		Link link = Mondo.getLinkByName(nomeLink);
		
		// Se il link esiste nel mondo
		if (link != null )
		{
			// Per ogni link della stanza verifico se il proprio nome è uguale a quello in input
			// Se true -> ne ritorno il riferimento
			for (List<Link> lista : elencoCollegamenti.values())
				for (Link l : lista)
					if (l.getName().equals(nomeLink))
						return l;
		}
		// Altrimenti, il link non esiste
		else
			throw new LinkInesistenteException("Il link non esiste: " + nomeLink);
		
		// Se il link esiste nel mondo ma non è un link della stanza, ritorno null
		return null;
	}
	
	
	/**
	 * Metodo che ritora il nome e la descrizione di base della stanza
	 * @return una stringa che riporta nome e descrizione della stanza
	 */
	@Override
	public String toString()
	{
		return "<" + getNomeStanza() + "> (" + getDescrizione() + ")";
			
	}
	
}
