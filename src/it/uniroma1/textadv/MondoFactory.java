package it.uniroma1.textadv;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.uniroma1.textadv.eccezioni.ClasseJavaNonEsistenteException;
import it.uniroma1.textadv.eccezioni.ConfigurazioneNonPossibileException;
import it.uniroma1.textadv.link.Link;
import it.uniroma1.textadv.link.StanzaLink;
import it.uniroma1.textadv.oggetti.Chiave;
import it.uniroma1.textadv.oggetti.Oggetto;
import it.uniroma1.textadv.oggetti.Tesoro;
import it.uniroma1.textadv.oggetti.Tesoro.StatoSicurezza;
import it.uniroma1.textadv.personaggi.Guardiano;
import it.uniroma1.textadv.utilita.ApribileConStrumento;
import it.uniroma1.textadv.utilita.Collezionabile;
import it.uniroma1.textadv.utilita.Direzione;
import it.uniroma1.textadv.utilita.Target;


/**
 * Classe specializzata nell'istanziare il Mondo e tutte le sue componenti
 * 
 * @author Gabriele
 *
 */
public class MondoFactory 
{
	// Stringhe usate nel file di configurazione per distinguere oggetti,stanze,link, ecc
	public final static String OBJECTS = "objects";
	public final static String CHARACTERS = "characters";
	public final static String LINKS = "links";
	public final static String PLAYER = "player";
	public final static String ROOM = "room";
	public final static String WORLD = "world";
	public final static String DESCRIPTION = "description";
	public final static String START = "start";
	
	// Usato per l'easter Egg
	public final static String COLLEZIONABILI = "collezionabili";
	
	// Package in cui si trovano le classi relative
	public final static String PACKAGE_LINK = "it.uniroma1.textadv.link.";
	public final static String PACKAGE_OGGETTI = "it.uniroma1.textadv.oggetti.";
	public final static String PACKAGE_PERSONAGGI = "it.uniroma1.textadv.personaggi.";
	
	// Messaggio di errore nel caso in cui il file di configurazione ha degli errori
	public final static String FILE_CONFIG_ERRATO = "Il formato del file di configurazione è errato!";
	public final static String FILE_CONFIG_ELEMENTO_DUPLICATO = "Esistono due elementi con lo stesso nome";
	public static final String FILE_CONFIG_STANZE_DUPLICATE = "Ci sono due stanze con lo stesso nome!";
	
	public static final String CLASS_NOT_FOUND = "Non è stata trovata la classe: ";
	
	
	
	/**
	 * Riferimento al mondo di gioco che viene creato.
	 */
	private static Mondo mondo;
	
	/**
	 * Per salvare il nome della stanza di partenza quando viene trovata l'informazione nel file
	 */
	private static String stanzaDiPartenza;
	
	
	// =================== MAPPE DI CONFIGURAZIONE ===========================
	
	/**
	 * Mappa delle stanze del mondo, che contiene come:
	 * - chiave: il nome della stanza
	 * - valori: una mappa che riporta come chiave il dettaglio interessato (es: objects, links, ecc),
	 *           e come valori i nomi di ciò che la stanza deve avere
	 */
	private static Map<String, Map<String, String>> mappaStanze = new LinkedHashMap<>();
	
	
	/**
	 * Mappa degli oggetti presenti nel mondo, che contiene:
	 * - come chiavi: i nomi degli oggetti
	 * - come valori: la lista contenente i nomi della classeJava corrispondente e i target dell'oggetto
	 */
	private static Map<String, List<String>> mappaOggetti = new LinkedHashMap<>();
	
	
	/**
	 * Mappa dei link del mondo, che contiene:
	 * - come chiavi: il nome del link
	 * - come valori: l'elenco dei nomi delle stanze che collega
	 */
	private static Map<String, List<String>> mappaLink = new LinkedHashMap<>();
	
	
	
	/**
	 * Mappa dei personaggi del mondo, contenente:
	 * - come chiavi: i nomi dei personaggi
	 * - come valori: l'elenco con i nomi dei target del personaggio
	 */
	private static Map<String, List<String>> mappaPersonaggi = new LinkedHashMap<>();

	
	/**
	 * Introdotta per l' EASTEREGG
	 * 
	 * Mappa che preserva il nome degli oggetti collezionabili (inseriti come easter egg)
	 * - come chiave: il nome dell'oggetto collezionabile
	 * - come valore: la lista con i suoi target e la sua descrizione
	 */
	private static Map<String, List<String>> mappaCollezionabili = new LinkedHashMap<>();
	
	
	/**
	 * Metodo che dato il percorso di un file di configurazione, crea il mondo
	 * 
	 * @param percorso il percorso del file di configurazione
	 * @return il riferimento all'istanza del mondo creata
	 * 
	 * @throws IOException sollevata se si è verificato un errore di IO
	 * @throws ConfigurazioneNonPossibileException sollevata se è stato riscontrato un errore 
	 * 											   nel file di configurazione
	 */
	public static Mondo creaMondo(Path percorso) throws IOException, ConfigurazioneNonPossibileException
	{
		// Verifico che sia stato dato il formato corretto del file di configurazione
		if ( !percorso.toString().endsWith(".game") )
			throw new ConfigurazioneNonPossibileException("Il formato del file non è .game!");

		// Se il formato è corretto, leggo tutto il file e ne ottengo un'unica stringa
		String file = Files.readString(percorso);
		
		// Splitto il file in blocchi secondo le parentesi quadre aperte "["
		// Esse sanciscono l'inizio di un nuovo blocco dedicato alla configurazione di un certo elemento
		String[] blocchi = file.split("\\[");
		
		// Itero su ogni blocco per mapparne i dati
		// Salto il blocco[0] perchè non contiene dati utili (è generato dallo split)
		for (int i = 1; i <blocchi.length; i++)
			
				// Dato il blocco, ne mappo le informazioni in modo più organizzato
				mappaFromBlocco(blocchi[i]);
		

		// Riempio il mondo con tutto ciò che deve contenere
		try 
		{
			// Creo gli oggetti del mondo
			creaOggetti();
			
			// Creo i personaggi del mondo
			creaPersonaggi();
			
			// Creo i Link del mondo
			creaLink();
			
			// Per ogni entità, sistemo l'elenco dei loro target
			aggiustaTargetPerEntita();
			
			// Creo le stanze del mondo
			creaStanze();
			
		} 
		catch (ClasseJavaNonEsistenteException e) 
		{
			throw new ConfigurazioneNonPossibileException(e.getMessage());
		}
		
		
		// Ricavo il riferimento alla stanza di partenza
		Stanza stanzaDiStart = Mondo.getStanzaByName(stanzaDiPartenza);
		
		if (stanzaDiStart != null)
		{
			// Imposto la stanza di partenza del mondo sia per il mondo stesso che per il giocatore
			Mondo.setStanzaDiPartenza(stanzaDiStart);
			Mondo.getPlayer().setNewStanzaCorrente(stanzaDiStart);
		}
		else 
			throw new ConfigurazioneNonPossibileException("Non è stata trovata la stanza di partenza!");
		
		// ritorno il riferimento all'istanza del mondo
		return mondo;
	}
	
	
	
	/**
	 * Data una stringa che rappresenta un intero blocco del file di configurazione, mappo nell'apposita
	 * mappa i dati che riguardano questo blocco di configurazione (es: le room, gli objects, ecc)
	 * 
	 * @param blocco la stringa che rappresenta il blocco che si vuole mappare
	 * @throws ConfigurazioneNonPossibileException sollevata se è impossibile configurare qualcosa
	 */
	private static void mappaFromBlocco(String blocco) throws ConfigurazioneNonPossibileException
	{
		// Verranno usate nel seguito
		String classe, nome = null;
		
		// Splitto il blocco per riga secondo il separatore dell'OS corrente
		String[] lineeDelBlocco = blocco.split(System.lineSeparator());
	
		// Verifico che la prima riga del blocco sia compresa tra quadre [...]
		// Altrimenti è un errore del file di configurazione.
		// NOTA: è sufficente verificare se sia presente la "]" perchè il blocco è già stato splittato sul "["
		if ( !lineeDelBlocco[0].endsWith("]") )
			throw new ConfigurazioneNonPossibileException(FILE_CONFIG_ERRATO);
		
		
		// Poichè lineeDelBlocco[0] ha sempre la riga di configurazione compresa tra "[...]", e 
		// in alcuni casi al suo interno è compreso il ":", identifico in quale di questi casi ricadere.
		int indexDuePunti = lineeDelBlocco[0].indexOf(":");
		int endSeparator = lineeDelBlocco[0].indexOf("]");
		
		// Se non sono presenti i due punti ":" nella prima riga del blocco, allora non è
		// una stanza e neanche un mondo
		if (indexDuePunti == -1) 
		{
			// Ricavo il nome della classe che devo configurare
			classe = lineeDelBlocco[0].substring(0, endSeparator);
			
			// Se sto esaminando gli oggetti
			if (classe.equals(OBJECTS))	
					configuraElementi(mappaOggetti, lineeDelBlocco);
					
			// Se sto esaminando i personaggi
			else if (classe.equals(CHARACTERS))				
					configuraElementi(mappaPersonaggi, lineeDelBlocco);

			// Se sto esaminando i Link
			else if (classe.equals(LINKS))		
					configuraElementi(mappaLink, lineeDelBlocco);
			
			//===================== PER EASTEREGG ====================================
			else if (classe.equals(COLLEZIONABILI))
			{
				configuraElementi(mappaCollezionabili, lineeDelBlocco);
			}
			//==================================================================

			// Se è il giocatore protagonista
			else if (classe.equals(PLAYER))
			{
				// In lineeDelBlocco[1] vengono specificati i dati del Giocatore
				// Se presenti, rimuovo i commenti dalla linea
				lineeDelBlocco[1] = removeCommenti(lineeDelBlocco[1]);
				
				// Splitto i dati sul tab
				// In dettagli[0] = nome del Giocatore
				// In dettagli[1] = classeJava del Giocatore
				String[] dettagli = lineeDelBlocco[1].split("\\t");
				
				// Imposto il giocatore protagonista
				Mondo.setPlayerProtagonista(dettagli[0]);
			}
			
		}
		// Altrimenti, la prima linea del blocco è della forma [_classe_:_nomeIstanza_]
		else
		{
			// Prendo il nome della classe a cui si riferisce il blocco e il nome dell'istanza della classe
			classe = lineeDelBlocco[0].substring(0, indexDuePunti);
			nome = lineeDelBlocco[0].substring(indexDuePunti+1, endSeparator);
			
			
			// Definisco delle variabili utili in seguito
			String description = null, objects = null, start = null, links = null, characters = null;
			
			// Esamino ogni riga del blocco
			for (int i = 1; i < lineeDelBlocco.length; i++)
			{
				// Se presenti, rimuovo i commenti dalla linea
				lineeDelBlocco[i] = removeCommenti(lineeDelBlocco[i]);
				
				// Se non ce li ha, continuo splittando la linea sul tab
				// In dettagli[0] = la parola chiave che indica cosa rappresenta la linea
				// In dettagli[1] = stringa che elenca i dati che riguardano la parola chiave
				String[] dettagli = lineeDelBlocco[i].split("\\t");
				
				
				// Se un elemento della configurazione non ha argomenti, allora lo salto
				// ES: nel file è presente la linea characters senza nessun personaggio specificato
				if (dettagli.length < 2)
					continue;						
				
				// Altrimenti posso raccogliere gli elementi di configurazione
				switch(dettagli[0]) 
				{			
					case DESCRIPTION -> description = dettagli[1];
					case OBJECTS -> objects = dettagli[1];
					case START -> start = dettagli[1];
					case LINKS -> links = dettagli[1];
					case CHARACTERS -> characters = dettagli[1];
					default -> throw new ConfigurazioneNonPossibileException("Non è previsto: " + dettagli[0]);			
				}
				
			}
			
			// Se è una stanza
			if (classe.equals(ROOM))
			{
				// Mappo la parola chiave alla stringa che rappresenta i dati
				Map<String, String> map = new HashMap<>();
		
				// Controllo quali dati è stato possibile raccogliere
				if (description != null)
					map.put(DESCRIPTION, description);
				if (objects != null)
					map.put(OBJECTS, objects);
				if (characters != null)
					map.put(CHARACTERS, characters);
				if (links != null)
					map.put(LINKS, links);
				
				// Aggiungo la configurazione della stanza
				// Se esistono due stanze con stesso nome è un errore nel file di configurazione
				if ( mappaStanze.putIfAbsent(nome, map) != null)
					throw new ConfigurazioneNonPossibileException(FILE_CONFIG_STANZE_DUPLICATE);
			}
			// Altrimenti, se è un mondo
			else if (classe.equals(WORLD))
			{
				// Se nel blocco del mondo non sono presenti il nome e la descrizione, il mondo
				// non può essere istanziato
				if (nome == null || description == null)
					throw new ConfigurazioneNonPossibileException("Non sono stati trovati nome e descrizione del mondo!");
				
				// Creo l'unica istanza del mondo
				MondoFactory.mondo = Mondo.getInstance(nome, description);
				
				// Se è presente l'informazione sulla stanza di partenza la salvo
				if (start != null)
					stanzaDiPartenza = start;
			}
			// Altrimenti non è supportata la configurazione
			else
				throw new ConfigurazioneNonPossibileException("Non è prevista la configurazione per: " + classe);
		
		}
		
	}
	
	
	/**
	 * Metodo di utilità per rimuovere i commenti da una linea del file
	 * @param lineaDelBlocco la linea da cui eliminare i commenti
	 * @return la linea senza commenti
	 */
	private static String removeCommenti(String lineaDelBlocco)
	{
		// Controllo se la linea ha un commento
		int indexCommenti = lineaDelBlocco.indexOf(" //");
		
		// Se ce l'ha, allora lo elimino prendendo la sottostringa che va dall'inizio della riga
		// all'inizio del commento
		if (indexCommenti != -1)
			lineaDelBlocco = lineaDelBlocco.substring(0, indexCommenti);
		
		// ritorno la linea senza commenti
		return lineaDelBlocco;
	}
	
	
	
	/**
	 * Metodo di utilità usato per configurare la mappa specificata secondo i dati che vengono raccolti
	 * nella prima fase di parsing del file di configurazione
	 * 
	 * @param mappaDaConfigurare la mappa che si vuole popolare
	 * @param lineeDelBlocco l'array contenente tutte le linee di un blocco estratto dal file di configurazione
	 * 
	 * @throws ConfigurazioneNonPossibileException sollevata se nel file di configurazione si trovano
	 * 											   due elementi con lo stesso nome
	 */
	private static void configuraElementi(Map<String, List<String>> mappaDaConfigurare, String[] lineeDelBlocco) 
															throws ConfigurazioneNonPossibileException
	{
		// Esamino separatamente ogni riga del blocco
		for (int i = 1; i < lineeDelBlocco.length; i++) 
		{
			// Se presenti, rimuovo i commenti dalla linea
			lineeDelBlocco[i] = removeCommenti(lineeDelBlocco[i]);
			
			// Splitto la linea sul tab
			List<String> datiElemento = Arrays.asList(lineeDelBlocco[i].split("\\t"));
			
			// Prendo il nome dell'elemento dai dati che lo riguardano.
			// Esso è sempre in prima posizione
			String nomeElemento = datiElemento.get(0);
			
			// Ricavo la sottolista contenente il nome della ClasseJava e i target, 
			// che sono nelle posizioni >= 1 della lista dei dati
			datiElemento = datiElemento.subList(1, datiElemento.size());
			
			// Alla mappa indicata aggiungo [chiave = nomeElemento] e [valore = i suoi dati]
			// Altrimenti, se l'elemento è stato già inserito, c'è un errore nel file di configurazione
			if ( mappaDaConfigurare.putIfAbsent(nomeElemento, datiElemento) != null)
				throw new ConfigurazioneNonPossibileException(FILE_CONFIG_ELEMENTO_DUPLICATO);
		}
	}
	
	
	/**
	 * Metodo che si occupa della creazione dei link nel mondo
	 * 
	 * @throws ClasseJavaNonEsistenteException sollevata se un link non è associato ad una classeJava
	 * @throws ConfigurazioneNonPossibileException sollevata se nel file di configurazione il link non ha
	 * 											   tutti i dati che ci si aspettano
	 */
	private static void creaLink() throws ClasseJavaNonEsistenteException, ConfigurazioneNonPossibileException
	{
		// Per ogni (nome di) link presente nella mappa dei link configurati
		for (String nomeLink : mappaLink.keySet())
		{
			// Ne ricavo la lista dei dati
			List<String> datiDelLink = mappaLink.get(nomeLink);
			
			// Controllo che ci siano tutti i dati del link
			if (datiDelLink.size() < 3)
				throw new ConfigurazioneNonPossibileException("Errore di configurazione nel link: " + nomeLink);
			
			// Estraggo il nome della classeJava associato al link, che è sempre in posizione 0
			String nomeClasseJava = datiDelLink.get(0);
			
			// Ricavo i nomi delle due stanze che il link collega,
			// che sono sempre in posizione 1 e 2
			String nomeStanza1 = datiDelLink.get(1);
			String nomeStanza2 = datiDelLink.get(2);
			
			try 
			{
				// Uso la reflection per capire il link a quale classeJava appartiene
				Class<?> classeJava = Class.forName(PACKAGE_LINK + nomeClasseJava);
				
				// Ne ricavo il costruttore con 3 parametri
				Constructor<?> costruttore = classeJava.getConstructor(String.class, String.class, String.class);
				
				// Lo istanzio
				Link linkRif = (Link) costruttore.newInstance(nomeLink, nomeStanza1, nomeStanza2);
				
				// Aggiungo il riferimento al link creato nella mappa dei link del mondo
				Mondo.addLinkToMappaLinkIstanziati(nomeLink, linkRif);
			}
			catch (ClassNotFoundException e) 
			{
				throw new ClasseJavaNonEsistenteException(CLASS_NOT_FOUND + nomeClasseJava);
			}
			catch (NoSuchMethodException |
					SecurityException | InstantiationException |
					IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
			{
				e.printStackTrace();
			}
			
		}
	}
	
	
	
	/**
	 * Metodo che si occupa della creazione delle stanze del mondo
	 */
	private static void creaStanze()
	{
		// Itero sui nomi delle stanze presenti nella mappa che le configura
		for (String nomeStanza : mappaStanze.keySet())
		{
			// Per ogni stanza ne ricavo tutti i dati raccolti
			Map<String, String> datiDellaStanza = mappaStanze.get(nomeStanza);
			
			// Ricavo la descrizione della stanza
			String descrizione = datiDellaStanza.get(DESCRIPTION);
			
			// Creo la stanza e la aggiungo al mondo
			Stanza stanza = new Stanza(nomeStanza, descrizione);
			
			// Aggiungo alla mappa delle stanze istanziate la coppia (nomeStanza, suoRiferimento)
			Mondo.addStanzaToMappaStanzeIstanziate(nomeStanza, stanza);
		
			// Ricavo dalla mappa la stringa con tutti i nomi degli oggetti che sono nella stanza
			String strDiOggettiNellaStanza = datiDellaStanza.get(OBJECTS);
			
			// Se ci devono essere oggetti nella stanza
			if (strDiOggettiNellaStanza != null)
			{
				// Creo una lista con i nomi (isolati) di tutti gli oggetti nella stanza
				// Splitto su ", " (virgola<SPAZIO>) oppure su "," (virgola)
				List<String> listaDiOggettiNellaStanza = Arrays.asList(strDiOggettiNellaStanza.split(", |,"));
					
				// Ogni oggetto che deve stare nella stanza lo metto nella stanza
				for (String nomeOggetto : listaDiOggettiNellaStanza)
				{
					// Dalla mappa degli oggetti istanziati del mondo ricavo l'oggetto che mi interessa
					Oggetto obj = Mondo.getOggettoByName(nomeOggetto);
					
					// E lo metto nella stanza
					stanza.addEntitaNellaStanza(nomeOggetto, obj);
				}
			}
			
			// Ricavo i nomi dei personaggi presenti nella stanza
			String nomiPersonaggiNellaStanza = datiDellaStanza.get(CHARACTERS);
			
			// Se la stanza ha personaggi, allora li metto nella stanza
			if (nomiPersonaggiNellaStanza != null)
			{
				// Creo una lista con i nomi (isolati) di tutti i personaggi nella stanza
				List<String> listaNomiPersonaggi = Arrays.asList(nomiPersonaggiNellaStanza.split(","));
				
				// Ogni oggetto che deve stare nella stanza lo metto nella stanza
				for (String nomePersonaggio : listaNomiPersonaggi)
				{
					// Ricavo il riferimento al personaggio dalla mappa dei personaggi istanziati del mondo
					Personaggio pers = Mondo.getPersonaggioByName(nomePersonaggio);
					
					// Lo aggiungo alla stanza
					stanza.addEntitaNellaStanza(nomePersonaggio, pers);
				}
			}
			
			// Ricavo dalla mappa la stringa con tutti i nomi dei link della stanza
			String strDiLinkNellaStanza = datiDellaStanza.get(LINKS);
			
			// Se ci sono link, allora li metto nella stanza
			if (strDiLinkNellaStanza != null)
			{
				// Creo una lista con i nomi (isolati) di tutti i link della stanza
				List<String> listaDiLinkNellaStanza = Arrays.asList(strDiLinkNellaStanza.split(","));
				
				// Per ogni link che deve stare nella stanza
				for (String link : listaDiLinkNellaStanza)
				{
					// In datiLink[0] = la direzione
					// In dettagli[1] = il nome del Link (oppure il nome della stanza che fa da link)
					String[] datiLink = link.split(":");
					
					// Ottengo l'istanza associata alla direzione che mi interessa
					Direzione dir = Direzione.valueOf(Direzione.class, datiLink[0]);
					
					// Dalla mappa dei link, ricavo il riferimento al link stesso
					Link rifLink = Mondo.getLinkByName(datiLink[1]);
					
					// Se non esiste il link, allora è perchè è la stanza stessa a fare da collegamento
					// Pertanto creo una StanzaLink che unisce le due stanze
					if (rifLink == null)
						rifLink = new StanzaLink(nomeStanza, datiLink[1]);
					
					// Aggiungo alla stanza la coppia (direzione, link)
					stanza.addLinkDellaStanza(dir, rifLink);
					
				}
		
			}
			
			// Passo alla prossima stanza
		
		}
		
	}
	
	
	
	/**
	 * Metodo che si occupa della creazione degli oggetti presenti nel mondo
	 * 
	 * @throws ClasseJavaNonEsistenteException sollevata se un oggetto non è associato ad una classe Java
	 */
	private static void creaOggetti() throws ClasseJavaNonEsistenteException
	{
		// Per ogni oggetto presente nella mappaDiCofigurazione degli oggetti
		for (String nomeOggettoDaCreare : mappaOggetti.keySet())
		{
			// Ricavo dalla mappa degli oggetti i dati che lo riguardano
			List<String> datiDellOggetto = mappaOggetti.get(nomeOggettoDaCreare);
			
			// In datiDellOggetto[0] ho il nome della classeJava associata all'oggetto
			String nomeClasseJava = datiDellOggetto.get(0);
			
			
			// In tutte le posizioni successive possono esserci i target con cui l'oggetto può interagire
			// Se oltre al nomeDellaClasseJava ha anche dei target, allora li considero
			if (datiDellOggetto.size() > 1)
				datiDellOggetto = datiDellOggetto.subList(1, datiDellOggetto.size());
			
			
			// Uso la reflection per capire quale oggetto istanziare
			try 
			{
				// Sapendo il nome della ClasseJava, ne ricavo il Class
				Class<?> classeJava = Class.forName(PACKAGE_OGGETTI + nomeClasseJava );
				
				// Ottengo un array con i costruttori della classe
				Constructor<?>[] arrCostruttori = classeJava.getConstructors();
				
				// Inizializzo a null il riferimento all'oggetto che voglio creare
				Oggetto obj = null;
				
				// Esamino i costruttori per trovare quello corretto e istanzio l'oggetto
				for(Constructor<?> costruttore : arrCostruttori)
				{
					// Se l'oggetto non ha target
					if (costruttore.getParameterCount() == 0)
					{
						obj = (Oggetto) costruttore.newInstance();
						break;
					}
					// Se l'oggetto ha un target
					else if (costruttore.getParameterCount() == 1)
					{
						obj = (Oggetto) costruttore.newInstance(nomeOggettoDaCreare);
						break;
					}
					
				}
				
				// Salvo per l'oggetto il nome delle sue entità target
				for (String target : datiDellOggetto)
					obj.addTarget(target, null);
				
				
				//=================== PER EASTEREGG ============================
				
				// Se l'oggetto è un collezionabile
				if (obj instanceof Collezionabile)
				{
					// Ricavo la lista delle sue descrizioni dalla mappa apposita
					List<String> descrizione = mappaCollezionabili.get(nomeOggettoDaCreare);
					
					// E setto la sua descrizione
					((Collezionabile) obj).setDescrizione(descrizione.get(0));
				}
				//==================================================================
				
				// Aggiungo il riferimento all'oggetto creato alla mappa degli oggetti istanziati
				Mondo.addOggettoToMappaOggettiIstanziati(nomeOggettoDaCreare, obj);
			} 
			catch (ClassNotFoundException e) 
			{
				throw new ClasseJavaNonEsistenteException(CLASS_NOT_FOUND + nomeClasseJava);
			} 
			catch (InstantiationException| IllegalAccessException|
				   SecurityException|IllegalArgumentException|InvocationTargetException e) 
			{
				e.printStackTrace();
			} 
		}
	}
	
	
	/**
	 * Metodo che si occupa della creazione dei personaggi del mondo
	 * 
	 * @throws ClasseJavaNonEsistenteException sollevata se il personaggio non è associato ad alcuna classeJava
	 */
	private static void creaPersonaggi() throws ClasseJavaNonEsistenteException
	{
		
		// Esamino ogni personaggio nella mappa di configurazione dei personaggi
		for (String nomePersonaggio : mappaPersonaggi.keySet())
		{
			// Estraggo i dati che riguardano il personaggio
			List<String> datiDelPersonaggio = mappaPersonaggi.get(nomePersonaggio);
			
			// Ricavo la classeJava associata al personaggio
			String nomeClasseJava = datiDelPersonaggio.get(0);
			
			// Se il personaggio ha dei target, allora li ricavo 
			if(datiDelPersonaggio.size() > 1)
				datiDelPersonaggio = datiDelPersonaggio.subList(1, datiDelPersonaggio.size());
				
			try 
			{
				// Uso la reflection per ottenere il .class della classeJava associata al personaggio
				Class<?> classeJava = Class.forName(PACKAGE_PERSONAGGI + nomeClasseJava);
				
				// Ottengo il costruttore che mi interessa, che prende in input solo una stringa (il nome)
				Constructor<?> costruttore = classeJava.getConstructor(String.class);
				
				// Inizializzo il personaggio dandogli il suo nome
				Personaggio pers = (Personaggio) costruttore.newInstance(nomePersonaggio);
				
				// Per ogni target del personaggio
				for (String nomeTarget : datiDelPersonaggio)
				{	
					// Controllo se il target è un oggetto che è stato istanziato
					Entita rifOgg = Mondo.getOggettoByName(nomeTarget);
					
					if (rifOgg != null)
					{
						pers.addTarget(nomeTarget, rifOgg);
						continue;
					}
					
					// Controllo se il target è un personaggio prendibile che è stato istanziato
					rifOgg = Mondo.getPersonaggioByName(nomeTarget);
					if (rifOgg != null)
					{
						pers.addTarget(nomeTarget, rifOgg);
						continue;
					}

				}
				
				// Aggiungo alla mappa dei personaggi istanziati la coppia (nomePersonaggio, suo riferimento)
				Mondo.addPersonaggioToMappaPersonaggiIstanziati(nomePersonaggio, pers);
			}
			catch (ClassNotFoundException e) 
			{
				throw new ClasseJavaNonEsistenteException(CLASS_NOT_FOUND + nomeClasseJava);
			} 
			catch (InstantiationException|IllegalAccessException| 
					IllegalArgumentException| SecurityException| InvocationTargetException| 
					NoSuchMethodException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Metodo che si occupa di assegnare a ogni Personaggio e oggetto
	 * i riferimenti ai propri target
	 */
	private static void aggiustaTargetPerEntita()
	{
		// Per ogni personaggio del mondo
		for (Personaggio personaggio : Mondo.getMappaPersonaggiIstanziati().values())
			
			// Gli aggiungo i suoi target
			aggiungiTargetTo(personaggio);
		
		
		// Per ogni oggetto presente nel mondo
		for (Oggetto oggetto : Mondo.getMappaOggettiIstanziati().values())
			
			// Gli aggiungo i suoi target
			aggiungiTargetTo(oggetto);
		
	}
	
	
	
	/**
	 * Metodo che dato il riferimento ad un'entità, esamina tutti i suoi target e associa ad ogni
	 * nome del target il proprio riferimento.
	 * 
	 * I riferimenti ai target vengono ricavati dalle mappe del mondo che mantengono tali dati.
	 * 
	 * @param entita l'entità a cui aggiungere i target
	 */
	private static void aggiungiTargetTo(Entita entita)
	{
		// Per ogni (nome di) target dell'entità
		for (String nomeTarget : entita.getElencoTarget().keySet())
		{
			// Ricavo il riferimento all'eventuale oggetto target 
			Target obj = Mondo.getOggettoByName(nomeTarget);
			
			// Ricavo il riferimento all'eventuale personaggio target
			Target pers = Mondo.getPersonaggioByName(nomeTarget);
			
			// Ricavo il riferimento all'eventuale link target
			Target link = Mondo.getLinkByName(nomeTarget);
			
			// Se il target è un oggetto
			if (obj != null)
				configuraTarget(entita, nomeTarget, obj);
			
			// Se il target è un personaggio (Es: il gatto)
			else if (pers != null)
				configuraTarget(entita, nomeTarget, pers);
			
			// Se il target è un link
			else if (link != null)
				configuraTarget(entita, nomeTarget, link);
		}

	}
		

	/**
	 * Metodo che data un'entità, e la coppia (nomeDelTarget, riferimentoAlTarget),
	 * la aggiunge all'elenco dei target dell'entità
	 * 
	 * @param entita l'entità a cui aggiungere il target
	 * @param nomeTarget il nome del target da aggiungere
	 * @param target il riferimento del target da aggiungere
	 */
	private static void configuraTarget(Entita entita, String nomeTarget, Target target)
	{
		// Aggiungo ai target dell'entita la coppia (nometarget, riferimentoTarget)
		entita.addTarget(nomeTarget, target);
		
		// Se l'entita è una chiave e il target è un'apribile con strumento
		if (entita instanceof Chiave && target instanceof ApribileConStrumento)
		{
			// Imposto che il target può essere aperto solo con la chiave
			((ApribileConStrumento) target).setAperturaConStrumento();
		}
		// Se l'entità è un guardiano ed ha come target il tesoro, allora il tesoro
		// è sotto controllo
		else if (entita instanceof Guardiano && target instanceof Tesoro)
		{
			((Tesoro) target).setStatoSicurezza(StatoSicurezza.CONTROLLATO);
		}
	}
	
}