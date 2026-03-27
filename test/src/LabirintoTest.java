package src;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;

class LabirintoTest {

	@Test
	void testStatoInizialeLabirinto() {
		
		Labirinto labirinto = new Labirinto();
		
		Stanza iniziale = labirinto.getStanzaCorrente();
		assertNotNull(iniziale, "La stanza iniziale non dovrebbe essere null");
		assertEquals("Atrio", iniziale.getNome(), "Il gioco deve iniziare nell'Atrio");
		
		Stanza vincente = labirinto.getStanzaVincente();
		assertNotNull(vincente, "La stanza vincente non dovrebbe essere null");
		assertEquals("Biblioteca", vincente.getNome(), "La stanza vincente deve essere la Biblioteca");
	
	}
}