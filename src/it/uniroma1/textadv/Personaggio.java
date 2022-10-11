package it.uniroma1.textadv;

import java.util.Objects;

import it.uniroma1.textadv.personaggi.Inventario;
import it.uniroma1.textadv.utilita.EntitaPrendibile;
import it.uniroma1.textadv.utilita.Subject;

/**
 * Classe astratta che modella i personaggi che vivono nel mondo.
 * 
 * Sono dei Subject {@link Subject} in quanto le entità nel loro inventario devono cambiare stanza
 * assieme al personaggio stesso.
 * 
 * Ogni personaggio ha una propria frase canonica, implementata secondo il Template Pattern.
 * 
 * @author Gabriele
 *
 */
public abstract class Personaggio extends Entita implements Subject
{
		
	/**
	 * L'inventario del personaggio, di default vuoto
	 */
	protected Inventario inventario = new Inventario();
	
	
	
	/**
	 * Costruttore del Personaggio, con un inventario vuoto
	 * @param nome il nome del ersonaggio
	 */
	public Personaggio(String nome)
	{
		super(nome);
	}
	
	
	/**
	 * Metodo per settare la nuova stanza corrente del personaggio e di tutti gli oggetti
	 * presenti nel suo inventario.
	 * 
	 * @param newStanzaCorrente la stanza che si vuole settare come corrente
	 */
	@Override
	public void setNewStanzaCorrente(Stanza newStanzaCorrente) 
	{ 
		this.stanzaCorrente = newStanzaCorrente; 
		
		// Mando la notifica a tutti gli oggetti dell'inventario che sta cambiando la stanza corrente
		notifica();
	}
	
	/**
	 * Metodo che notifica a tutti gli oggetti dell'inventario che sta cambiando la loro stanza corrente
	 */
	@Override
	public void notifica() 
	{
		for (EntitaPrendibile obj : inventario)
			obj.updateStanzaCorrente(stanzaCorrente);
		
	}
	
	
	/**
	 * Metodo per aggiungere una serie di oggetti all'inventario
	 */
	@Override
	public void addToInventario(EntitaPrendibile... oggetti) 
	{
		inventario.addAllOggetti(oggetti);
	}

	/**
	 * Metodo per rimuovere una serie di oggetti dall'inventario
	 */
	@Override
	public void removeToInventario(EntitaPrendibile... oggetti)
	{
		inventario.removeAllOggetti(oggetti);	
	}
	
	
	@Override
	public boolean equals(Object o)
	{
		if ( o == null || getClass() != o.getClass())
			return false;
		
		
		Personaggio other = (Personaggio) o;
		
		// Ogni personaggio si distingue per il suo nome univoco
		return nome.equals(other.getName());
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(nome);
	}
	
	
	/**
	 * Metodo che mostra la rappresentazione a stringa dell'inventario del personaggio
	 * @return la rappresentazione a stringa dell'inventario
	 */
	public String showInventario() 
	{
		return inventario.toString();
		
	}
	
	
	/**
	 * Metodo che restituisce l'inventario del personaggio
	 * @return l'inventario del personaggio
	 */
	public Inventario getInventario()
	{
		return inventario;
	}
	
	
	/**
	 * Metodo che ritorna una rappresentazione a stringa dell'identità del personaggio
	 * @return una rappresentazione a stringa dell'identità del personaggio
	 */
	@Override
	public String toString()
	{
		return getName() + " (" + getClass().getSimpleName() + ")";
	}
	
	
	/**
	 * Metodo che permette a un personaggio di parlare con un altro.
	 * Ogni personaggio ha la sua frase canonica.
	 * 
	 * @param nomePersonaggioConCuiParlare il nome del personaggio con cui parlare
	 */
	public abstract void parla(String nomePersonaggioConCuiParlare);


}
