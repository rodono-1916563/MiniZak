package it.uniroma1.textadv.utilita;

import it.uniroma1.textadv.eccezioni.OggettoGiaRottoException;

/**
 * Interfaccia che modella la capacit� di un oggetto di essere rompibile senza
 * bisogno di altri oggetti
 * 
 * @author Gabriele
 *
 */
public interface Fragile 
{

	/**
	 * Metodo per rompere l'oggetto
	 * @throws OggettoGiaRottoException sollevata se l'oggetto � gi� rotto
	 */
	void rompi() throws OggettoGiaRottoException;
}
