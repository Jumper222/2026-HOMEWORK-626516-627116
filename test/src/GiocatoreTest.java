package src;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.giocatore.Giocatore;

class GiocatoreTest {

	@Test
	void testCostruttoreStatoIniziale() {
		Giocatore giocatore = new Giocatore();
		
		// Verifico i CFU di partenza
		assertEquals(20, giocatore.getCfu(), "Il giocatore deve iniziare con 20 CFU");
		
		assertNotNull(giocatore.getBorsa(), "La borsa del giocatore deve essere inizializzata");
	}
}