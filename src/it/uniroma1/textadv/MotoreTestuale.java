package it.uniroma1.textadv;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import it.uniroma1.textadv.eccezioni.OperazioneNonSupportataException;


/**
 * Classe che si occupa di gestire la parte testuale del gioco.
 * 
 * Grazie al metodo parseAndExecute() è in grado di interpretare e eseguire 
 * comandi che ammettono in input fino a due argomenti.
 * 
 * @author Gabriele
 *
 */
public class MotoreTestuale 
{
	/**
	 * Elenco delle stopwords.
	 * Seguono il formato: 			<SPAZIO>stopword<SPAZIO>|<SPAZIO>stopword<SPAZIO>|...
	 * E' cosi specificato perchè segue la sintassi delle regex (ossia | sta per OR)
	 */
	public final static String STOPWORDS = " il | lo | la | i | gli | in | da | per | ad | con | su | a | nella ";
	
	

	/**
	 * Metodo che si occupa di interpretare e eseguire un comando testuale
	 * @param lineaDaParsare la linea che va interpretata e eseguita
	 * 
	 * @throws OperazioneNonSupportataException sollevata se l'operazione non è supportata
	 */
	public void parseAndExecute(String lineaDaParsare) throws OperazioneNonSupportataException
	{

		// Estraggo il comando da eseguire, che è sempre in prima posizione
		String comando = Arrays.stream(lineaDaParsare.split(" ")).findFirst().orElse(null);

		// Mostro il comando che sto eseguendo
		System.out.println("\nComando: " + lineaDaParsare);
		
		// Riduco la lineaDaParsare ai soli argomenti del comando
		String argomenti = lineaDaParsare.substring(comando.length());
		
		
		// Splitto gli argomenti secondo le stopword e tengo tutti quelli diversi da "" e " "
		// Può capitare infatti che nello splittare sulle stopword rimangano "" e " " dovuto
		// alla formattazione del comando in input
		List<String> listaArg = Arrays.stream(argomenti.split(STOPWORDS))
									  .filter( x -> !(x.equals("") || x.equals(" ")) )
									  .collect(Collectors.toList());
		
		// Rimuovo lo " " all'inzio di tutti gli argomenti che ci iniziano.
		for (int i = 0; i < listaArg.size(); i++)
		{
			String el = listaArg.get(i);
			if (el.startsWith(" "))
				listaArg.set(i, el.substring(1));
		}

		
		try 
		{
			// Scopro quanti argomenti ha in input il comando
			int argomentiComando = listaArg.size();
			
			// Se sono 0, invoco il comando con 0 argomenti
			if (argomentiComando == 0)
			{
				Method metodo = Giocatore.class.getMethod(comando);
				metodo.invoke(Mondo.getPlayer());
			}
			// Se l'argomento è 1, allora invoco il comando con 1 argomento
			else if (argomentiComando == 1)
			{
				Method metodo = Giocatore.class.getMethod(comando, String.class);
				metodo.invoke(Mondo.getPlayer(), listaArg.get(0));
			}
			// Se gli argomenti sono 2 invoco il comando con 2 argomenti
			else if (argomentiComando == 2 ) 
			{
				Method metodo = Giocatore.class.getMethod(comando, String.class, String.class);
				metodo.invoke(Mondo.getPlayer(), listaArg.get(0), listaArg.get(1));
			}
			// Altrimenti, l'operazione non è supportata
			else
				throw new OperazioneNonSupportataException(comando);
				
		}
		catch (NoSuchMethodException e)
		{
			// Se il comando non esiste lancio l'eccezione che comunica che l'operazione non è supportata
			throw new OperazioneNonSupportataException(comando);
		}
		// Gestisco le eccezioni lanciate dai metodi richiamati tramite reflection
		catch(InvocationTargetException e)
		{
			System.out.println(e.getCause().getMessage());
		}
		catch(OperazioneNonSupportataException e)
		{
			System.out.println("[ERROR] Operazione non supportata: " + e.getMessage());
		}
		catch(SecurityException | IllegalAccessException | IllegalArgumentException  e)
		{
			System.out.println("[ERROR] Nel parsing del motore testuale!");
			e.printStackTrace();
		}
	}
	
}
