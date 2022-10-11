package it.uniroma1.textadv.personaggi;

import it.uniroma1.textadv.Personaggio;
import it.uniroma1.textadv.oggetti.Tesoro;
import it.uniroma1.textadv.oggetti.Tesoro.StatoSicurezza;
import it.uniroma1.textadv.utilita.EntitaPrendibile;
import it.uniroma1.textadv.utilita.Target;

/**
 * Classe che modella il personaggio del Guardiano.
 * 
 * @author Gabriele
 *
 */
public class Guardiano extends Personaggio
{

	public final static String TESORO = "tesoro";
	
	
	/**
	 * Enumerazione che stabilisce il grado di attenzione del Guardiano
	 * @author Gabriele
	 *
	 */
	public enum StatoAttenzione { ATTENTO, DISTRATTO }
	
	
	/**
	 * Lo stato di attenzione corrente del guardiano
	 */
	private StatoAttenzione statoAttenzione;
	
	/**
	 * Costruttore del Guardiano con un inventario vuoto, che sta svolgendo con
	 * attenzione il suo lavoro
	 * 
	 * @param nome il nome del Guardiano
	 */
	public Guardiano(String nome) 
	{
		super(nome);
		statoAttenzione = StatoAttenzione.ATTENTO;
	}
	
	
	
	/**
	 * Metodo che ritorna true se il Guardiano è distratto, false altrimenti
	 * @return true se il Guardiano è distratto, false altrimenti
	 */
	public boolean isDistratto()
	{
		return statoAttenzione == StatoAttenzione.DISTRATTO;
	}
	
	
	/**
	 * Metodo per aggiungere al proprio inventario una serie di oggetti
	 */
	@Override
	public void addToInventario(EntitaPrendibile... oggetti) 
	{
		inventario.addAllOggetti(oggetti);
	}


	/**
	 * Metodo per ricevere un entità e aggiungerla al proprio inventario
	 * @param entRicevuta l'entità ricevuta
	 */
	public void ricevi(EntitaPrendibile entRicevuta)
	{
		if (entRicevuta != null && entRicevuta instanceof Gatto)
		{
			// Aggiungo il gatto all'inventario
			addToInventario(entRicevuta);
			
			System.out.println( "<" + getName() + ">:\nOh ma che bel gattino :o\n"
							 				+ "Ehi dove scappi?! Vieni qui!!");
			
			// Imposto che ora il guardiano è distratto
			statoAttenzione = StatoAttenzione.DISTRATTO;
			
			
			// Ricavo il riferimento al tesoro target del guardiano
			Target tesoro = getTargetByName(TESORO);
			
			// Se il tesoro esiste ed è controllato
			if (tesoro != null && tesoro instanceof Tesoro && ((Tesoro) tesoro).isCheked())
			{
				// Da ora, il tesoro sarà libero
				((Tesoro)tesoro).setStatoSicurezza(StatoSicurezza.LIBERO);
			}
		}
		else
		{
			// Aggiungo l'entità ricevuta all'inventario
			addToInventario(entRicevuta);
			
			// Mostro una frase a schermo
			System.out.println("Pff hahah pensi di distrarmi con un " + entRicevuta + "?!\nMi hai preso per un dilettante?!");
		}
	}
	
	
	/**
	 * Metodo che fa parlare il personaggio
	 */
	@Override
	public void parla(String nomePersonaggioConCuiParlare) 
	{
		String str = getName() + ":\nChi sei tu?! Cosa ci fai qui?!\n"
								 + "Sei qui per il tesoro?! Non lo avrai mai!!\n";
		
		str += "<Pensando ad alta voce>\nOh, ma cos'è questo? Un pelo? Spero di non trovarmi mai davanti un gatto...";
		
		System.out.println(str);
		
	}
	

}
