package it.uniroma1.textadv.personaggi;


import java.util.ArrayList;
import java.util.List;

import it.uniroma1.textadv.Entita;
import it.uniroma1.textadv.Mondo;
import it.uniroma1.textadv.Personaggio;
import it.uniroma1.textadv.eccezioni.ImpossibileComprareException;
import it.uniroma1.textadv.oggetti.Soldi;
import it.uniroma1.textadv.utilita.Comprabile;
import it.uniroma1.textadv.utilita.EntitaPrendibile;
import it.uniroma1.textadv.utilita.Target;

/**
 * Classe che modella il personaggio del Venditore
 * 
 * @author Gabriele
 *
 */
public class Venditore extends Personaggio
{
	
	/**
	 * Costruttore del Venditore, con un inventario vuoto
	 * @param nome il nome del venditore
	 */
	public Venditore(String nome) 
	{
		super(nome);
	}
	
	
	/**
	 * Metodo per vendere le entità target del Venditore
	 * @param inCambioDi il nome di ciò con cui si sta pagando
	 * @return la lista delle entità acquistate
	 * 
	 * @throws ImpossibileComprareException sollevata se si prova a comprare qualcosa senza dargli i soldi
	 */
	public List<EntitaPrendibile> vendi(String inCambioDi) throws ImpossibileComprareException
	{
		// Se il venditore non ha target, allora non ci sta nulla da acquistare
		if (getElencoTarget().isEmpty())
			throw new ImpossibileComprareException("Non ci sono oggetti da acquistare!");
		
		// Altrimenti, ricavo il riferimento all'oggetto ricevuto
		Entita oggRicevuto = Mondo.getOggettoByName(inCambioDi);

		// Se tale oggetto esiste e sono dei soldi
		if (oggRicevuto != null && oggRicevuto instanceof Soldi)
		{
			// Creo una lista per contenere gli oggetti che saranno venduti
			List<EntitaPrendibile> oggDaComprare = new ArrayList<>();
			
			// Per ogni oggetto prendibile e comprabile lo aggiungo nella lista degli oggetti comprati 
			for (Target ent : getElencoTarget().values())
				if (ent instanceof Comprabile)
					oggDaComprare.add((EntitaPrendibile) ent);
			
			return oggDaComprare;
		}
		// Altrimenti, se il venditore non ha ricevuto i soldi
		else
			throw new ImpossibileComprareException("Impossibile comprare con: " + inCambioDi 
												   + "\nPer comprare qualcosa servono i soldi!");
	}


	/**
	 * Metodo che fa parlare il Venditore
	 */
	@Override
	public void parla(String nomePersonaggioConCuiParlare) 
	{
		String str = "Ciao " + nomePersonaggioConCuiParlare + ", sono " + getName();
		str+= "\nBenvenuto nel mio negozio. Guardati intorno e vedi se ti può servire qualcosa";
		
		System.out.println(str);
		
	}
	
	
}
