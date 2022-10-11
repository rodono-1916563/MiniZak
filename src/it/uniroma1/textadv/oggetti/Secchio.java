package it.uniroma1.textadv.oggetti;

import it.uniroma1.textadv.Entita;
import it.uniroma1.textadv.Mondo;
import it.uniroma1.textadv.Stanza;
import it.uniroma1.textadv.eccezioni.OggettoGiaSpentoException;
import it.uniroma1.textadv.eccezioni.ImpossibileRiempireException;
import it.uniroma1.textadv.eccezioni.ImpossibileUsareSuExeption;
import it.uniroma1.textadv.eccezioni.OggettoGiaVuotoException;
import it.uniroma1.textadv.utilita.Comprabile;
import it.uniroma1.textadv.utilita.RiempibileSvuotabile;
import it.uniroma1.textadv.utilita.AccendibileSpegnibile;
import it.uniroma1.textadv.utilita.StatoRiempimento;

/**
 * Classe che modella i Secchi, ossia oggetti riempibili e svuotabili
 * Nel gioco il secchio � un'entit� che pu� essere acquistata.
 * 
 * @author Gabriele
 *
 */
public class Secchio extends Oggetto implements Comprabile, RiempibileSvuotabile
{
	/**
	 * Lo stato attuale di riempimento del secchio
	 */
	private StatoRiempimento statoRiempimento;
	
	
	/**
	 * Costruttore di un secchio inizialmente vuoto
	 * @param nome il nome del secchio
	 */
	public Secchio(String nome)
	{
		super(nome);
		this.statoRiempimento = StatoRiempimento.VUOTO;
	}
	
	
	/**
	 * Metodo che ritorna true se il secchio � vuoto, false altrimenti
	 * @return true se il secchio � vuoto, false altrimenti
	 */
	@Override
	public boolean isEmpty()
	{
		return statoRiempimento == StatoRiempimento.VUOTO;
	}
	
	
	/**
	 * Metodo che ritorna lo stato di riempimento del secchio
	 * @return lo stato di riempimento del secchio
	 */
	@Override
	public StatoRiempimento getStatoRiempimento()
	{
		return statoRiempimento;
	}

	

	@Override
	public void updateStanzaCorrente(Stanza newStanzaCorrente) 
	{
		super.setNewStanzaCorrente(newStanzaCorrente);
		
	}
	
	
	@Override
	public String toString()
	{
		return getName() + "(" + getStatoRiempimento() + ")";
	}
	
	/**
	 * Metodo che dato il nome di un oggetto da cui riempire il secchio, lo prova ad usare per riempire il secchio
	 * @param nomeOggettoDaCuiRiempire il nome dell'oggetto da cui si vuole riempire il secchio
	 * 
	 * @throws ImpossibileRiempireException sollevata se � impossibile riempire il secchio
	 */
	@Override
	public void riempiDal(String nomeOggettoDaCuiRiempire) throws ImpossibileRiempireException
	{
		// Ricavo il riferimento dell'oggetto dato in input
		Entita ogg = Mondo.getOggettoByName(nomeOggettoDaCuiRiempire);
		
		// Se l'oggetto non esiste oppure non � un pozzo
		if ( ogg == null || !(ogg instanceof Pozzo) )
			throw new ImpossibileRiempireException("Con " + nomeOggettoDaCuiRiempire + " non � possibile riempire il " + getName());
		
		// Se il secchio � vuoto e l'oggetto � un Pozzo pieno, allora riempio il secchio
		if (isEmpty() && !((Pozzo) ogg).isEmpty())
		{
			// cambio lo stato del secchio a pieno
			statoRiempimento = StatoRiempimento.PIENO;
			
			// Faccio svuotare il pozzo
			((Pozzo) ogg).svuota();
			
			System.out.println("Il secchio � stato riempito dal " + nomeOggettoDaCuiRiempire);
		}
		// Altrimenti, se il secchio � gi� pieno
		else if ( !isEmpty() )
			throw new ImpossibileRiempireException("Il secchio � gi� pieno!");
		
		// Altrimenti, vuol dire che l'oggetto da cui riempire (il pozzo) � vuoto
		else
			throw new ImpossibileRiempireException("Il " + nomeOggettoDaCuiRiempire + " � vuoto!");
	}
	
	/**
	 * Metodo per svuotare il secchio su un altro oggetto
	 * @param nomeOggettoSuCuiSvuotare il nome dell'oggetto su cui si vuole svuotare il secchio
	 * 
	 * @throws OggettoGiaVuotoException sollevata se il secchio � gi� vuoto
	 * @throws ImpossibileUsareSuExeption sollevata se il secchio non pu� essere svuotato sull'oggetto indicato
	 */
	@Override
	public void svuotaSu(String nomeOggettoSuCuiSvuotare) throws OggettoGiaVuotoException, ImpossibileUsareSuExeption
	{
		// Ricavo il riferimento all'entit� da spegnere
		Entita oggSuCuiSvuotare = Mondo.getOggettoByName(nomeOggettoSuCuiSvuotare);
		
		// Se il secchio � pieno e l'oggetto su cui svuotare il secchio � uno Spegnibile
		if ( !isEmpty( ) && oggSuCuiSvuotare != null && oggSuCuiSvuotare instanceof AccendibileSpegnibile )
		{
			// Indico che � stato svuotato il secchio
			statoRiempimento = StatoRiempimento.VUOTO;
			
			try
			{
				// Spengo l'oggetto su cui ho svuotato il secchio
				((AccendibileSpegnibile) oggSuCuiSvuotare).spegni(nomeOggettoSuCuiSvuotare);
			}
			catch (OggettoGiaSpentoException | ImpossibileUsareSuExeption e)
			{
				System.out.println(e.getMessage());
			}
		}
		// Altrimenti, se il secchio � vuoto
		else if ( isEmpty() )
			throw new OggettoGiaVuotoException("Il secchio � gi� vuoto");
		
		// Altrimenti
		else
			throw new ImpossibileUsareSuExeption("Non � possibile svuotare " + getName() + " su: " + nomeOggettoSuCuiSvuotare);
			
	}


	@Override
	public void setStatoRiempimento(StatoRiempimento newStatoRiempimento)
	{
		statoRiempimento = newStatoRiempimento;
	}
	
}
