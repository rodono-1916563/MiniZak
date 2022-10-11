package it.uniroma1.textadv.utilita;

import it.uniroma1.textadv.oggetti.Tesoro.StatoSicurezza;

/**
 * Interfaccia di utilit� che modella gli oggetti che vengono controllati
 * 
 * @author Gabriele
 *
 */
public interface Controllabile 
{

	/**
	 * Metodo per ottenere lo stato di sicurezza dell'oggetto
	 * @return lo stato di sicurezza dell'oggetto
	 */
	StatoSicurezza getStatoSicurezza();
	
	/**
	 * Metodo per controllare se l'oggetto � controllato
	 * @return true se � controllato, false altrimenti
	 */
	boolean isCheked();
	
	
	/**
	 * Metodo per settare un nuovo stato di sicurezza dell'oggetto
	 * @param newStatoSicurezza il nuovo stato di sicurezza che si vuole settare
	 */
	void setStatoSicurezza(StatoSicurezza newStatoSicurezza);
	

}
