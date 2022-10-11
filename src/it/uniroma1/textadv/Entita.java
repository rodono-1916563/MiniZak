package it.uniroma1.textadv;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.uniroma1.textadv.oggetti.Oggetto;
import it.uniroma1.textadv.utilita.EntitaPrendibile;
import it.uniroma1.textadv.utilita.Target;

/**
 * Classe madre di tutte le entità che esistono nel mondo (come {@link Personaggio}, {@link Oggetto})
 * Ogni entità dispone di una propria posizione nel mondo e di un proprio nome univoco.
 * 
 * Inoltre, un'entità può essere un target per un altra entità.
 * 
 * @author Gabriele
 *
 */
public abstract class Entita implements Target
{
	
	/**
	 * Il nome dell'entità
	 */
	protected String nome;

	/**
	 * La stanza corrente in cui si trova l'entità
	 */
	protected Stanza stanzaCorrente;
	
	/**
	 * La mappa contenente i target dell'entità
	 * - chiave: il nome del target
	 * - valore: il riferimento al target
	 */
	protected Map<String, Target> elencoTarget = new LinkedHashMap<>();
	
	
	/**
	 * Costruttore delle entità
	 * @param nome il nome dell'entità
	 */
	public Entita(String nome)
	{
		this.nome = nome;
	}
	
	
	/**
	 * Costruttore dell'entità
	 * @param nome il nome dell'entità
	 * @param entita l'elenco delle entità target dell'entità stessa
	 */
	public Entita(String nome, EntitaPrendibile...entita)
	{
		// Mi occupo dei target dell'entità
		List<EntitaPrendibile> listaEntita = Arrays.asList(entita);
		
		// Per ogni entita prendibile data in input, la metto nell'elenco dei target dell'entita
		for (EntitaPrendibile entPrendibile : listaEntita)
		{
			Entita ent = (Entita) entPrendibile;
			
			elencoTarget.put(ent.getName(), ent);
			
		}
	}
	
	/**
	 * Ritorna il nome dell'entità
	 * @return il nome dell'entità
	 */
	public String getName() { return nome; }
	
	
	/**
	 * Metodo per settare la nuova stanza corrente di un entità
	 * @param newStanzaCorrente la nuova stanza da settare come corrente
	 */
	public void setNewStanzaCorrente(Stanza newStanzaCorrente)
	{
		this.stanzaCorrente = newStanzaCorrente;	
	}
	
	
	/**
	 * Metodo che ritorna la stanza corrente dell'entità
	 * @return la stanza corrente dell'entità
	 */
	public Stanza getStanzaCorrente() { return stanzaCorrente; }
	
	
	/**
	 * Metodo per aggiungere un target all'elenco dei target dell'entità
	 * @param nomeTarget il nome del target
	 * @param target il riferimento al target
	 */
	public void addTarget(String nomeTarget, Target target)
	{
		elencoTarget.putIfAbsent(nomeTarget, target);
	}

	
	/**
	 * Sapendo il nome del target, ottengo il suo riferimento
	 * 
	 * @param nomeTarget il nome del target che si vuole ottenere
	 * @return il riferimento al target desiderato
	 */
	public Target getTargetByName(String nomeTarget)
	{		
		return elencoTarget.get(nomeTarget);
	}
	
	
	/**
	 * Metodo per ottenere l'elenco dei target dell'entità
	 * @return l'elenco dei target dell'entità
	 */
	public Map<String, Target> getElencoTarget()
	{
		return elencoTarget;
	}
		
}
