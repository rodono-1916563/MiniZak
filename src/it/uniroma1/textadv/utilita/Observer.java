package it.uniroma1.textadv.utilita;

import it.uniroma1.textadv.Stanza;

/**
 * Interfaccia utilizzata per fare in modo che le entità presenti nell'inventario dei
 * personaggi cambino stanza assieme a loro
 * 
 * @author Gabriele
 *
 */
public interface Observer 
{
	/**
	 * Metodo per aggiornare la stanza corrente
	 * @param newStanzaCorrente la nuova stanza da settare come corrente
	 */
	void updateStanzaCorrente(Stanza newStanzaCorrente);
}
