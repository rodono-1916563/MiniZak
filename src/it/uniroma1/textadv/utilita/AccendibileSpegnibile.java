package it.uniroma1.textadv.utilita;

import it.uniroma1.textadv.eccezioni.ImpossibileUsareSuExeption;
import it.uniroma1.textadv.eccezioni.OggettoGiaAccesoException;
import it.uniroma1.textadv.eccezioni.OggettoGiaSpentoException;

/**
 * Interfaccia che modella la capacit� di un oggetto che pu� essere acceso e spento
 * 
 * @author Gabriele
 *
 */
public interface AccendibileSpegnibile
{
	
	/**
	 * Metodo che ritorna lo stato di accensione dell'oggetto
	 * @return lo stato di accensione dell'oggetto
	 */
	StatoAccensione getStatoAccensione();
	
	/**
	 * Metodo che ritorna true se l'oggetto � acceso, false altrimenti
	 * @return true se l'oggetto � acceso, false altrimenti
	 */
	boolean isAcceso();
	
	/**
	 * Metodo che serve a spegnere l'oggetto
	 * @param nomeOggDaUsare il nome dell'oggetto che si vuole usare
	 * 
	 * @throws OggettoGiaSpentoException sollevata se l'oggetto � gi� spento
	 * @throws ImpossibileUsareSuExeption sollevata se l'oggetto indicato non pu� essere usato per spegnere l'oggetto
	 */
	void spegni(String nomeOggDaUsare) throws OggettoGiaSpentoException, ImpossibileUsareSuExeption;
	
	
	/**
	 * Metodo che serve ad accendere un oggetto
	 * @throws OggettoGiaAccesoException sollevata se l'oggetto � gi� acceso
	 */
	void accendi() throws OggettoGiaAccesoException;
	
}
