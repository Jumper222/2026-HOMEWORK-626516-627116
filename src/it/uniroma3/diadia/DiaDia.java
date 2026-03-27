package it.uniroma3.diadia;


import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
 *
 * Questa e' la classe principale crea e istanzia tutte le altre
 *
 * @author  docente di POO 
 *         (da un'idea di Michael Kolling and David J. Barnes) 
 *          
 * @version base
 */

public class DiaDia {

	static final private String MESSAGGIO_BENVENUTO = ""+
			"Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"+
			"I locali sono popolati da strani personaggi, " +
			"alcuni amici, altri... chissa!\n"+
			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n"+
			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n" +
			"o regalarli se pensi che possano ingraziarti qualcuno.\n\n"+
			"Per conoscere le istruzioni usa il comando 'aiuto'.";

	static final private String[] elencoComandi = {"vai", "aiuto", "fine", "prendi", "posa"};

	private Partita partita;
	private IOConsole ioconsole;


	public DiaDia(IOConsole console) {
		this.partita = new Partita();
		this.ioconsole = console;
	}

	public void gioca() {
		String istruzione; 

		ioconsole.mostraMessaggio(MESSAGGIO_BENVENUTO);	
		do		
			istruzione = ioconsole.leggiRiga();
		while (!processaIstruzione(istruzione));

	}   

	/**
	 * Processa una istruzione 
	 *
	 * @return true se l'istruzione e' eseguita e il gioco continua, false altrimenti
	 */
	private boolean processaIstruzione(String istruzione) {
		Comando comandoDaEseguire = new Comando(istruzione);

		if (comandoDaEseguire.getNome().equals("fine")) {
			this.fine(); 
			return true;
		} else if (comandoDaEseguire.getNome().equals("vai"))
			this.vai(comandoDaEseguire.getParametro());
		else if (comandoDaEseguire.getNome().equals("aiuto"))
			this.aiuto();
		else if (comandoDaEseguire.getNome().equals("prendi"))
			this.prendi(comandoDaEseguire.getParametro());
		else if (comandoDaEseguire.getNome().equals("posa"))
			this.posa(comandoDaEseguire.getParametro());
		else 	
			ioconsole.mostraMessaggio("Comando sconosciuto");
		if (this.partita.vinta()) {
			ioconsole.mostraMessaggio("Hai vinto!");
			return true;
		} else
			return false;
	}   

	// implementazioni dei comandi dell'utente:

	/**
	 * Stampa informazioni di aiuto.
	 */
	private void aiuto() {
		for(int i=0; i< elencoComandi.length; i++) 
			ioconsole.mostraMessaggio(elencoComandi[i]+" ");
		
	}

	private Partita getPartita() {
		return this.partita;
	}

	/**
	 * Cerca di andare in una direzione. Se c'e' una stanza ci entra 
	 * e ne stampa il nome, altrimenti stampa un messaggio di errore
	 */
	private void vai(String direzione) {
		if(direzione==null)
			ioconsole.mostraMessaggio("Dove vuoi andare ?");
		Stanza prossimaStanza = null;
		prossimaStanza = this.getPartita().getLabirinto().getStanzaCorrente().getStanzaAdiacente(direzione);
		if (prossimaStanza == null)
			ioconsole.mostraMessaggio("Direzione inesistente");
		else {
			this.getPartita().getLabirinto().setStanzaCorrente(prossimaStanza);
			int cfu = this.partita.getGiocatore().getCfu();
			this.partita.getGiocatore().setCfu(cfu--);
		}
		ioconsole.mostraMessaggio(this.getPartita().getLabirinto().getStanzaCorrente().getDescrizione());
	}

	private void prendi(String nomeAttrezzo) {


		if (nomeAttrezzo == null) {
			ioconsole.mostraMessaggio("Specifica un attrezzo da prendere!");
			return;
		}

		Attrezzo attrezzoDaPrendere = this.getPartita().getLabirinto().getStanzaCorrente().getAttrezzo(nomeAttrezzo);

		if (attrezzoDaPrendere == null) {
			ioconsole.mostraMessaggio("L'oggetto " + nomeAttrezzo + " non è presente in questa stanza.");
			return;
		}

		this.getPartita().getLabirinto().getStanzaCorrente().removeAttrezzo(attrezzoDaPrendere);

		boolean aggiunto = partita.getGiocatore().getBorsa().addAttrezzo(attrezzoDaPrendere);

		if (aggiunto) {
			ioconsole.mostraMessaggio("Hai preso: " + nomeAttrezzo);
		} else {
			this.getPartita().getLabirinto().getStanzaCorrente().addAttrezzo(attrezzoDaPrendere);
			ioconsole.mostraMessaggio("Non puoi prendere " + nomeAttrezzo + ": borsa troppo piena o pesante!");
		}
	}		

	private void posa(String nomeAttrezzo) {


		if (nomeAttrezzo == null) {
			ioconsole.mostraMessaggio("Specifica un attrezzo da posare!");
			return;
		}
		Attrezzo attrezzoDaPosare = partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);

		if (attrezzoDaPosare == null) {
			ioconsole.mostraMessaggio("Non hai l'oggetto " + nomeAttrezzo + " nella borsa.");
			return;
		}

		boolean posato = this.getPartita().getLabirinto().getStanzaCorrente().addAttrezzo(attrezzoDaPosare);

		if (posato) {
			ioconsole.mostraMessaggio("Hai posato l'oggetto " + nomeAttrezzo + " nella stanza.");
		} else {

			partita.getGiocatore().getBorsa().addAttrezzo(attrezzoDaPosare);
			ioconsole.mostraMessaggio("Non c'è spazio nella stanza per posare " + nomeAttrezzo + "!");
		}
	}


	/**
	 * Comando "Fine".
	 */
	private void fine() {
		ioconsole.mostraMessaggio("Grazie di aver giocato!"); 
	}


	public static void main(String[] argc) {

		IOConsole console = new IOConsole();
		
		DiaDia gioco = new DiaDia(console);
	
		gioco.gioca();
	}
}