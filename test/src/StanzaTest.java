package src;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class StanzaTest {


	@Test
	 void testImpostaStanzaAdiacenteStanzaNull() {
		Stanza A = new Stanza("A");
		A.impostaStanzaAdiacente("nord", null);
		assertNull(A.getStanzaAdiacente("nord"),"La stanza NULL non funziona");
	}
	
	@Test
	void testImpostaStanzaAdiacenteDirezioneNull() {
		Stanza A = new Stanza("A");
		Stanza B = new Stanza("B");
		A.impostaStanzaAdiacente(null, B);
		assertNull(A.getStanzaAdiacente(null),"La direzione NULL non funziona");
	}
	
	@Test
	void testImpostaStanzaAdiacenteDirezioneSbagliata() {
		Stanza A = new Stanza("A");
		A.impostaStanzaAdiacente("nord", null);
		assertNull(A.getStanzaAdiacente("sud"),"Usare la direzione sbagliata non funziona");
	}
	
	@Test
	void testAddAttrezzoNull() {
		Stanza A = new Stanza("A");
		assertFalse(A.addAttrezzo(null),"Aggiungere un attrezzo null non funziona");
	}
	
	@Test
	void testAddAttrezzoPieno() {
		Stanza A = new Stanza("A");
		Attrezzo atr = new Attrezzo("Giorgio",10);
		for(int i = 0; i < 10; i++) {
			A.addAttrezzo(atr);
		}
		Attrezzo Carlo = new Attrezzo("Carlo",2);
		assertFalse(A.addAttrezzo(Carlo),"Aggiungere un attrezzo sopra il limite lo aggiunge per davvero");
		
	}
	
	@Test
	void testAddAttrezzoTrovato() {
		Stanza A = new Stanza("A");
		Attrezzo atr = new Attrezzo("Atr",2);
		assertTrue(A.addAttrezzo(atr));
	}
	
	
	@Test
	void testHasAttrezzoTrovato() {
		Stanza A = new Stanza("A");
		Attrezzo atr = new Attrezzo("Paolo",10);
		A.addAttrezzo(atr);
		assertTrue(A.hasAttrezzo("Paolo"));
	}
}
