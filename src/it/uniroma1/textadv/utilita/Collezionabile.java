package it.uniroma1.textadv.utilita;

/**
 * => Implementata per l'EASTEREGG
 * 
 * Interfaccia che modella la capacit� che un'entit� � un collezionabile.
 * Essere un collezionabile implica che l'entit� pu� essere presa.
 * 
 * @author Gabriele
 *
 */
public interface Collezionabile extends EntitaPrendibile
{

	/**
	 * Metodo per settare la descrizione del collezionabile
	 * @param descrizione la sua descrizione
	 */
	void setDescrizione(String descrizione);
	
}
