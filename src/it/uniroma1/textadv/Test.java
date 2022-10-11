package it.uniroma1.textadv;

/**
 * Classe di Test che contiene il main del gioco.
 * Commentate ci sono tutte le varie possibilità per giocare.
 * 
 * @author Gabriele
 *
 */
public class Test 
{
	public static void main(String[] args)
	{
		
		Gioco g = new Gioco();
		
		// Test su versione completa del gioco, in fast forward
		Mondo m = Mondo.fromFile("minizak.game");
		g.play(m, "minizak.ff");
		
		
		// Test su versione completa del gioco, in prima persona
//		Mondo m = Mondo.fromFile("minizak.game");
//		g.play(m);
		
		
		// Test per giocare in prima persona sul mondo con l'easter egg
//		Mondo m = Mondo.fromFile("minizak_coneasteregg.game");		
//		g.play(m);
		
		
		
//		// Test .ff su Versione basilare del gioco
//		Mondo m1 = Mondo.fromFile("minizak_18.game");
//		g.play(m1,"minizak_18.ff");
		
	}
}
