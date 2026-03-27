package src;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Borsa;

class BorsaTest {

	// --------------------------------------------------------
	// 1. TEST DI STATO INIZIALE
	// --------------------------------------------------------
	@Test
	void testBorsaVuotaInizialmente() {
		Borsa borsa = new Borsa();
		assertTrue(borsa.isEmpty(), "Una borsa appena creata dovrebbe essere vuota");
		assertEquals(0, borsa.getPeso(), "Il peso di una borsa vuota deve essere 0");
		assertEquals(10, borsa.getPesoMax(), "Il peso massimo di default deve essere 10");
	}

	// --------------------------------------------------------
	// 2. TEST SULL'INSERIMENTO (addAttrezzo)
	// --------------------------------------------------------
	@Test
	void testAddAttrezzo_Valido() {
		Borsa borsa = new Borsa();
		Attrezzo spada = new Attrezzo("spada", 5);
		
		assertTrue(borsa.addAttrezzo(spada), "Dovrebbe essere possibile aggiungere un attrezzo se c'è spazio e peso");
		assertFalse(borsa.isEmpty(), "La borsa non dovrebbe più essere vuota");
		assertEquals(5, borsa.getPeso(), "Il peso totale dovrebbe aggiornarsi a 5");
	}

	@Test
	void testAddAttrezzo_TroppoPesante() {
		Borsa borsa = new Borsa(10); // Borsa con max 10kg
		Attrezzo incudine = new Attrezzo("incudine", 15);
		
		assertFalse(borsa.addAttrezzo(incudine), "Non dovrebbe inserire un attrezzo che supera il peso massimo");
		assertTrue(borsa.isEmpty(), "La borsa dovrebbe rimanere vuota se l'inserimento fallisce");
	}

	@Test
	void testAddAttrezzo_SuperaNumeroMassimo() {
		Borsa borsa = new Borsa(100); // Borsa enorme (100kg), il peso non sarà un problema
		
		// Riempio la borsa con 10 attrezzi leggerissimi (1kg l'uno)
		for (int i = 0; i < 10; i++) {
			Attrezzo piuma = new Attrezzo("piuma" + i, 1);
			assertTrue(borsa.addAttrezzo(piuma));
		}
		
		// Provo ad aggiungere l'undicesimo attrezzo
		Attrezzo extra = new Attrezzo("extra", 1);
		assertFalse(borsa.addAttrezzo(extra), "Non dovrebbe inserire l'undicesimo attrezzo, l'array è di 10");
	}

	@Test
	void testAddAttrezzo_Null() {
		Borsa borsa = new Borsa();
		
		// ATTENZIONE: Nel tuo codice attuale questo test PASSERÀ perché andrà in crash!
		// Se guardi il tuo addAttrezzo fai: attrezzo.getPeso(). Se attrezzo è null -> NullPointerException!
		assertThrows(NullPointerException.class, () -> {
			borsa.addAttrezzo(null);
		}, "Inserire null causa una NullPointerException. Dovresti aggiungere un if(attrezzo == null) in addAttrezzo!");
	}

	// --------------------------------------------------------
	// 3. TEST SULLA RICERCA (hasAttrezzo / getAttrezzo)
	// --------------------------------------------------------
	@Test
	void testHasAttrezzo_Esistente() {
		Borsa borsa = new Borsa();
		Attrezzo torcia = new Attrezzo("torcia", 2);
		borsa.addAttrezzo(torcia);
		
		assertTrue(borsa.hasAttrezzo("torcia"), "Dovrebbe trovare l'attrezzo inserito");
		assertEquals(torcia, borsa.getAttrezzo("torcia"), "Il getAttrezzo dovrebbe restituire esattamente la torcia");
	}

	@Test
	void testHasAttrezzo_Inesistente() {
		Borsa borsa = new Borsa();
		assertFalse(borsa.hasAttrezzo("chiave"), "Non dovrebbe trovare un attrezzo mai inserito");
		assertNull(borsa.getAttrezzo("chiave"), "getAttrezzo dovrebbe restituire null se non lo trova");
	}

	// --------------------------------------------------------
	// 4. TEST SULLA RIMOZIONE (TDD - Fallirà finché non implementi removeAttrezzo)
	// --------------------------------------------------------
	@Test
	void testRemoveAttrezzo() {
		Borsa borsa = new Borsa();
		Attrezzo libro = new Attrezzo("libro", 2);
		borsa.addAttrezzo(libro);
		
		// Rimuovo l'attrezzo
		Attrezzo rimosso = borsa.removeAttrezzo("libro");
		
		// Verifiche
		assertEquals(libro, rimosso, "Il metodo dovrebbe restituirmi il libro rimosso");
		assertFalse(borsa.hasAttrezzo("libro"), "Il libro non dovrebbe più essere nella borsa");
		assertEquals(0, borsa.getPeso(), "Il peso dovrebbe tornare a 0");
		assertTrue(borsa.isEmpty(), "La borsa dovrebbe tornare vuota");
	}
}