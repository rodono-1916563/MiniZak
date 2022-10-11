package it.uniroma1.textadv.utilita;

import it.uniroma1.textadv.eccezioni.ImpossibileRiempireException;
import it.uniroma1.textadv.eccezioni.ImpossibileUsareSuExeption;
import it.uniroma1.textadv.eccezioni.OggettoGiaVuotoException;

/**
 * Interfaccia che modella la capacità di un oggetto di poter essere riempito e svuotato
 * 
 * @author Gabriele
 *
 */
public interface RiempibileSvuotabile 
{

	/**
	 * Metodo che ritorna true se l'istanza è vuota, false altrimenti
	 * @return true se l'istanza è vuota, false altrimenti
	 */
	boolean isEmpty();
	
	/**
	 * Metodo che ritorna lo stato di riempimento dell'istanza
	 * @return lo stato di riempimento dell'istanza
	 */
	StatoRiempimento getStatoRiempimento();
	
	
	/**
	 * Metodo per settare un nuovo stato di riempimento
	 * @param newStatoRiempimento il nuovo stato da settare
	 */
	void setStatoRiempimento(StatoRiempimento newStatoRiempimento);
	
	/**
	 * Metodo per riempire un istanza Riempibile tramite un'altra istanza
	 * @param nomeOggettoDaCuiRiempire  il nome dell'istanza da cui riempire il Riempibile
	 * 
	 * @throws ImpossibileRiempireException sollevata se l'istanza non può essere riempita
	 */
	void riempiDal(String nomeOggettoDaCuiRiempire) 
			throws ImpossibileRiempireException;
	
	/**
	 * Metodo per svuotare l'istanza su di un'altra
	 * @param nomeOggettoSuCuiSvuotare il nome dell'istanza su cui svuotarla
	 * 
	 * @throws OggettoGiaVuotoException sollevata se l'istanza da svuotare è già vuota
	 * @throws ImpossibileUsareSuExeption sollevata se non può essere svuotato sull'oggetto indicato
	 */
	void svuotaSu(String nomeOggettoSuCuiSvuotare)
			throws OggettoGiaVuotoException, ImpossibileUsareSuExeption;
	
}
