package it.uniroma1.textadv.personaggi;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import it.uniroma1.textadv.utilita.EntitaPrendibile;
import it.uniroma1.textadv.utilita.Potente;

/**
 * Classe che modella un inventario in grado di contenere oggetti.
 * Un'inventario è in grado di contenere solamente entità prendibili dai personaggi.
 * 
 * @author Gabriele
 *
 */
public class Inventario implements Iterable<EntitaPrendibile>
{
	
	public final static String INVENTARIO_VUOTO = "L'inventario è vuoto!";
	
	/**
	 * Inventario dei personaggi in grado di contenere oggetti
	 */
	protected Set<EntitaPrendibile> inventario;
	
	
	/**
	 * Costruttore di un inventario che presenta già una serie di oggetti
	 * @param entita le entità che fanno già parte dell'inventario
	 */
	public Inventario(EntitaPrendibile...entita)
	{
		inventario = new HashSet<>(Arrays.asList(entita));
	}
	
	
	/**
	 * Metodo per verificare se un entità è nell'inventario
	 * @param entita l'entità da verificare se è nell'inventario
	 * @return true se contiene l'entità, false altrimenti
	 */
	public boolean contains(EntitaPrendibile entita)
	{
		return inventario.contains(entita);
	}
	
	
	/**
	 * Metodo per aggiungere un entità all'inventario
	 * @param entita l'entità da aggiungere
	 */
	public void addOggetto(EntitaPrendibile entita)
	{
		addAllOggetti(entita);
	}
	
	
	/**
	 * Metodo per aggiungere più entità all'inventario
	 * @param entita le entità da aggiungere
	 */
	public void addAllOggetti(EntitaPrendibile...entita)
	{
		addAllOggetti(Arrays.asList(entita));
	}
	
	
	/**
	 * Metodo per aggiungere più entità all'inventario
	 * @param entita le entità da aggiungere
	 */
	public void addAllOggetti(List<EntitaPrendibile> entita)
	{
		inventario.addAll(entita);
	}
	
	
	/**
	 * Metodo per rimuovere un'entità dall'inventario
	 * @param entita l'entità da rimuovere
	 */
	public void removeOggetto(EntitaPrendibile entita)
	{
		if (contains(entita))
			removeAllOggetti(entita);
	}
	
	
	/**
	 * Metodo per rimuovere dall'inventario più entità alla volta
	 * @param oggetti l'elenco delle entità da eliminare dall'inventario
	 */
	public void removeAllOggetti(EntitaPrendibile...oggetti)
	{
		inventario.removeAll(Arrays.asList(oggetti));
	}
	
	
	@Override
	public Iterator<EntitaPrendibile> iterator()
	{
		return inventario.iterator();
	}
	
	
	/**
	 * Metodo per controllare se nell'inventario ci sta un oggetto potente
	 * @return true se ci sta un oggetto potente, false altrimenti
	 */
	public boolean containsOggettoPotente()
	{
		// Controllo se nell'inventario c'è un oggetto potente
		for (EntitaPrendibile entita : inventario)
			if (entita instanceof Potente)
				return true;
		
		return false;
	}
	
	
	/**
	 * Metodo che ritorna true se l'inventario è vuoto, false altrimenti
	 * @return true se l'inventario è vuoto, false altrimenti
	 */
	public boolean isEmpty()
	{
		return inventario.isEmpty();
	}
	
	
	@Override
	public String toString()
	{
		// Se l'inventario è vuoto
		if ( isEmpty() )
			return INVENTARIO_VUOTO;
		
		// Altrimenti compongo l'elenco degli elementi nell'inventario
		StringBuffer sb = new StringBuffer();
		sb.append("Nell'inventario è presente:\n");
		
		for (EntitaPrendibile ent : inventario)
			sb.append(" - " + ent.toString() + "\n");
		
		return sb.toString();
	}
	
	
}
