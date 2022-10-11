package it.uniroma1.textadv.utilita;

import it.uniroma1.textadv.Entita;
import it.uniroma1.textadv.eccezioni.ImpossibileAprireException;

/**
 * Interfaccia che modella la capacit� di un oggetto di essere aperto mediante uno strumento
 * Essere apribile tramite uno strumento implica che l'oggetto stesso � apribile.
 * 
 * @author Gabriele
 *
 */
public interface ApribileConStrumento extends Apribile
{

	/**
	 * Metodo per aprire l'entit� usando uno strumento
	 * @param oggDaUsarePerAprire lo strumento che si prova ad usare
	 * 
	 * @throws ImpossibileAprireException sollevata se � impossibile aprire l'entit� con lo strumento indicato
	 */
	void apri(Entita oggDaUsarePerAprire) throws ImpossibileAprireException;
	
	
	/**
	 * Metodo utile a settare che un entit� pu� essere aperta solo con uno strumento
	 */
	void setAperturaConStrumento();
}
