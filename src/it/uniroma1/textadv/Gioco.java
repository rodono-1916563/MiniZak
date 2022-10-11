package it.uniroma1.textadv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import it.uniroma1.textadv.eccezioni.ImpossibileGiocareException;
import it.uniroma1.textadv.eccezioni.OperazioneNonSupportataException;
import it.uniroma1.textadv.oggetti.Tesoro;
import it.uniroma1.textadv.utilita.EntitaPrendibile;


/**
 * Classe che modella il Gioco.
 * 
 * Il gioco può essere giocato in modalità .ff (ossia eseguendo i comandi presenti in file di testo)
 * oppure manualmente, digitando i comandi da tastiera.
 * 
 * Nel caso di gioco in modalità manuale, il gioco termina solamente quando il Giocatore trova l'obiettivo
 * 
 * @author Gabriele
 *
 */
public class Gioco 
{
	
	/**
	 * Enumerazione che specifica i possibili stati di gioco
	 * 
	 * @author Gabriele
	 */
	enum StatoDiGioco { BLOCKED, RUNNING, WIN }
	
	/**
	 * Il nome dell'obiettivo del gioco
	 */
	public final static String TESORO = "tesoro";
	
	
	/**
	 * Lo stato corrente del gioco. 
	 * Di default prima di iniziare è Blocked
	 */
	private StatoDiGioco statoGame = StatoDiGioco.BLOCKED;
	
	
	/**
	 * Metodo che dato un mondo, fa partire il gioco
 	 * @param world il mondo su cui giocare
	 */
	public void play(Mondo world)
	{
		// Do il benvenuto al giocatore, dando una descrizione del mondo
		welcome(world);
		
		// Inizializzo il motore testuale che si occupa del parsing dei comandi
		MotoreTestuale motoreTestuale = new MotoreTestuale();
		
		// Per la lettura dell'input da tastiera
		BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
		
		// Fino a che il gioco è in esecuzione
		while(statoGame == StatoDiGioco.RUNNING)
		{
			// Formatto l'output sul terminale
			System.out.println();
			
			try 
			{
				// Leggo l'input da tastiera
				String inputTastiera = terminalReader.readLine();
				
				// Analizzo l'input letto e invoco il comando più adeguato
				motoreTestuale.parseAndExecute(inputTastiera);
			}
			catch (IOException e)
			{
				System.out.println("[ERROR] Nella lettura dell'input! Re-inserire il comando!");
				//e.printStackTrace();
			}
			catch(OperazioneNonSupportataException e) 
			{
				System.out.println(e.getMessage());
			} 
			
			// Richiamo il metodo per verificare se il giocatore ha vinto.
			// Se cosi fosse, il gioco termina
			endGame();

		}
		
		
		try 
		{
			// Chiudo il BufferedReader usato per leggere gli input
			terminalReader.close();
		} 
		catch (IOException e) 
		{
			System.out.println("[ERROR] Nella chiusura del Lettore dell'input! ");
			//e.printStackTrace();
		}
		
	}	
	
	
	/**
	 * Metodo che dato un mondo e un file script .ff gioca nel mondo eseguendo tutti i comandi
	 * testuali del file
	 * 
	 * @param world il mondo su cui giocare
	 * @param script il file .ff contenente i comandi testuali da eseguire
	 * 
	 * @throws ImpossibileGiocareException sollevata se si verificano errori durante il gioco 
	 */
	public void play(Mondo world, Path script) throws ImpossibileGiocareException
	{
		// Verifico che il formato del file che voglio eseguire in fastforward sia corretto
		if ( !script.toString().endsWith(".ff") )
			throw new ImpossibileGiocareException("Il formato del file fastforward non è: .ff");
		
		// Do il benvenuto al giocatore, dando una descrizione del mondo
		welcome(world);
		
		// Inizializzo il motore testuale che si occuperà della traduzione dei comandi
		MotoreTestuale motoreTestuale = new MotoreTestuale();
		
		try 
		{
			// Ottengo una lista con tutte le linee (separate) del file .ff
			List<String> lineeFile = Files.readAllLines(script);
			
			// Eseguo una alla volta ogni riga del file .ff
			for (String linea : lineeFile)
			{
				// Se presenti, rimuovo i commenti dalla linea
				linea = removeCommenti(linea);

				// Analizzo ed eseguo la linea letta dal file
				motoreTestuale.parseAndExecute(linea);
			
			}
		}
		catch (IOException | SecurityException | IllegalArgumentException e)
		{
			e.printStackTrace();
		} 
		catch (OperazioneNonSupportataException e) 
		{
			// Se un operazione del file .ff non è supportata sollevo l'eccezione
			throw new ImpossibileGiocareException("Operazione non riconosciuta: " + e.getMessage());
		} 
		
		// Chiamo il metodo che si occupa di verificare se l'utente ha terminato il gioco
		endGameFastForward();
	}
	
	
	
	/**
	 * Dato il nome del file .ff e un mondo, gioco in fast forward all'avventura testuale
	 * 
	 * @param world il mondo su cui giocare
	 * @param nomeFileScript il nome del file script .ff dal quale prendere i comandi e giocare 
	 */
	public void play(Mondo world, String nomeFileScript)
	{
		
		try 
		{
			// Richiamo il metodo per giocare sapendo il percorso del file
			play(world, Paths.get(nomeFileScript));
		}
		catch (ImpossibileGiocareException e) 
		{
			System.out.println("[ERROR] " + e.getMessage());
		}
	}
	
	
	
	
	/**
	 * Metodo di utilità pre rimuovere i commenti dalle linee
	 * @param linea la linea letta dal file
	 * @return la linea senza il commento
	 */
	private String removeCommenti(String linea)
	{
		// Se presente, rimuovo dalla linea il commento
		// In lineaSplittata[0] = il comando espresso nella linea
		// In lineaSplittata[1] = il commento sulla linea
		String[] lineaSplittata = linea.split(" //|\\t//");
		
		// Ritorno solamente il comando da gestire
		return lineaSplittata[0];
	}
	
	
	
	/**
	 * Metodo che dato un riferimento al mondo di gioco, da il benvenuto al giocatore fornendo
	 * indicazioni sul gioco
	 * 
	 * @param world il mondo di gioco
	 */
	private void welcome(Mondo world)
	{
		// Cambio lo stato del gioco per indicare che il gioco è in esecuzione
		statoGame = StatoDiGioco.RUNNING;
		
		// Il gioco inizia mostrando la descrizione del mondo
		System.out.println(world);
		System.out.println("[ISTRUZIONI] Per giocare inserisci i comandi da tastiera!");
		System.out.println("[CONSIGLIO] Guardati attentamente intorno!");
		
		
		System.out.println("\n[START GAME]\nSei " + Mondo.getPlayer() + ", e ti trovi nella " + Mondo.getStanzaDiPartenza());
		System.out.println("==> L'obiettivo del gioco è trovare e prendere il tesoro! <==\n");
		
	}
	
	
	/**
	 * Metodo che si occupa di verificare se il gioco è terminato.
	 * Valido per giocare sia in .ff sia manualmente.
	 * 
	 * @return true se il giocatore ha vinto, false se è ancora in gioco
	 */
	private boolean endGame()
	{
		// Ricavo il riferimento all'entità chiamata tesoro
		Entita ogg = Mondo.getOggettoByName(TESORO);
		
		// Se tale entità è davvero un tesoro, allora faccio l'upCasting a EntitàPrendibile
		EntitaPrendibile tesoro = ogg instanceof Tesoro ? (EntitaPrendibile) ogg : null;
		
		// Se il Giocatore ha nel suo inventario il tesoro, allora ha vinto
		if (tesoro != null && Mondo.getPlayer().getInventario().contains(tesoro))
		{
			// Imposto la vittoria del giocatore
			statoGame = StatoDiGioco.WIN;
			
			System.out.println("\n\n==> <Congratulazioni!! Hai terminato il gioco!!> <==");
			return true;
		}
		// Altrimenti non è ancora finito il gioco
		else
			return false;
	}
	
	
	/**
	 * Metodo per verificare se il gioco in .ff è terminato con la vittoria o meno del giocatore
	 */
	private void endGameFastForward()
	{
		// Se il gioco in .ff è finito ma il giocatore non ha vinto, allora ha perso
		if ( !endGame() )
			System.out.println("\n\n==> <GAME OVER: non hai trovato il tesoro!> <==");
	}
	
	
}
