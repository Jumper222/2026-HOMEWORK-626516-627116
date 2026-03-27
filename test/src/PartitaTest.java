package src;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;

class PartitaTest {

	
	@Test
	void testVintaNull() {
		Partita partita = new Partita();
		partita.getLabirinto().setStanzaCorrente(null);
		assertFalse(partita.vinta(),"Il test null è falso");
	}
	
	@Test
	void testVintaFalso() {
		Partita partita = new Partita();
		Stanza finta = new Stanza("finta");
		partita.getLabirinto().setStanzaCorrente(finta);
		assertFalse(partita.vinta(),"Il test dove non vinci è falso");
	}
	
	@Test
	void testVintaVero() {
		Partita partita = new Partita();
		partita.getLabirinto().setStanzaCorrente(partita.getLabirinto().getStanzaVincente());
		assertTrue(partita.vinta(),"Il test dove vinci è falso");
	}
	
	@Test
	void testIsFinitaFinitaFalse() {
		Partita partita = new Partita();
		assertFalse(partita.isFinita(),"Il test non funziona quando il bool finita è falso");
	}
	
	@Test 
	void testIsFinitaCFUNon0() {
		Partita partita = new Partita();
		assertFalse(partita.isFinita(),"Il test non fuziona quando i cfu non sono 0");
	}
	
	@Test
	void testIsFinitaCFU0() {
		Partita partita = new Partita();
		partita.getGiocatore().setCfu(0);
		assertTrue(partita.isFinita(),"Il testo non funziona quando i CFU sono 0");
	}
}