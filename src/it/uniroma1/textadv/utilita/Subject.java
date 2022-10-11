package it.uniroma1.textadv.utilita;


/**
 * Interfaccia di utilit� che permette ad un Personaggio di aggiungere delle entit�
 * nel proprio inventario e di imporre che le entit� cambino stanza assieme ad essi.
 * 
 * 
 * Usata in coppia con l'interfaccia {@link Observer} per modellare l'Observer Pattern.
 * 
 * @author Gabriele
 *
 */
public interface Subject 
{

	/**
	 * Aggiunge all'inventario del Personaggio tutte le entit� in input
	 * @param oggetti gli oggetti da aggiungere
	 */
	void addToInventario(EntitaPrendibile...oggetti);
	
	
	/**
	 * Rimuove dall'inventario del Personaggi gli oggetti forniti in input
	 * @param oggetti gli oggetti da rimuovere
	 */
	void removeToInventario(EntitaPrendibile...oggetti);
	
	
	/**
	 * Invia la notifica a tutti gli observer
	 */
	void notifica();
}
