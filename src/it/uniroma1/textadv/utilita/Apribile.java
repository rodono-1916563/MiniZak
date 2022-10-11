package it.uniroma1.textadv.utilita;

import it.uniroma1.textadv.eccezioni.ImpossibileAprireException;
import it.uniroma1.textadv.eccezioni.OggettoGiaApertoException;
import it.uniroma1.textadv.eccezioni.OggettoGiaChiusoException;

/**
 * Interfaccia che modella la capacità di un istanza di essere apribile
 * 
 * @author Gabriele
 *
 */
public interface Apribile 
{
	// Per tutti gli apribili che non hanno un contenuto
	String NO_CONTENUTO = "Non ha un contenuto!";
	
	/**
	 * Metodo che ritorna lo stato di apertura dell'istanza
	 * @return lo stato di apertura dell'istanza
	 */
	StatoApertura getStatoApertura();

	/**
	 * Metodo che ritorna true se l'istanza è aperta
	 * @return true se l'istanza è aperta, false altrimenti
	 */
	boolean isOpen();

	/**
	 * Metodo per aprire l'istanza apribile
	 * 
	 * @throws OggettoGiaApertoException sollevata se l'oggetto è già aperto
	 * @throws ImpossibileAprireException sollevata se non è possibile aprirla
	 */
	void apri() throws OggettoGiaApertoException, ImpossibileAprireException;
	
	/**
	 * Metodo che ritorna una stringa con il contenuto dell'entità apribile
	 * @return una stringa con il contenuto dell'entità apribile
	 * 
	 * @throws OggettoGiaChiusoException sollevata se l'oggetto è chiuso
	 */
	String showContenuto() throws OggettoGiaChiusoException;

}
