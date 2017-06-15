package shop.ui.cui;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
//import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import shop.domain.ShopVerwaltung;
import shop.exceptions.ArtikelExistiertBereitsException;
import shop.exceptions.BenutzerExistiertBereitsException;
import shop.exceptions.LoginFehlgeschlagenException;
import shop.exceptions.nichgGenugArtikelVorhandenException;
import shop.valueobjects.Adresse;
import shop.valueobjects.Artikel;
import shop.valueobjects.Benutzer;
import shop.valueobjects.Kunde;

/**
 * 
 * klasse für einen  einfache Benutzerschnittstelle des shops.
 * die Ausgabe erfolgt über die Comandozeile (CUI Command line User Interface
 *
 * @author Veitzi
 *
 */

/*
 * to do kaufen muss eingefügt werden
 * richtige Typumwandlungen einfügen
 */
public class ShopClientCUI {
	private boolean loginSchleife = true;
	private ShopVerwaltung shop;
	private BufferedReader in;
//	private ArtikelVerwaltung meineArtikel;
	private Benutzer eingeloggterBenutzer = null;
//	private ArtikelVerwaltung artikel;


	public ShopClientCUI(String datei) throws IOException {
		// die Shop Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		shop = new ShopVerwaltung(datei);

		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	/* 
	 * 
	 * Interne (private) Methode zur Ausgabe des Menüs.
	 *
	 *
	 *
	 * ausgabe final nicht über konsole
	 */
	private void gibMitarbeiterMenueAus() {
		System.out.print("Befehle: \n  Artikel ausgeben:            'a'");
		System.out.print("         \n  Artikel loeschen:            'd'");
		System.out.print("         \n  Neuen Artikel anlegen:       'e'");
		System.out.print("         \n  Artikel suchen:              'f'");
		System.out.print("	       \n  Artikelbestand hinzufuegen:  'h'");
		System.out.print("         \n  Artikel sortieren:           'o'");
		System.out.print("         \n  Daten sichern:               's'");
		System.out.println("       \n  Beenden:                     'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}

	private void gibKundenMenueAus() {
		System.out.print("Befehle: \n  Artikel ausgeben:            'a'");
		System.out.print("         \n  Artikel suchen:              'f'");
		System.out.print("		   \n  Artikel kaufen:              'k'"); //<--- neu Nur für Kunden
		System.out.print("         \n  Artikel sortieren            'o'");
		System.out.print("         \n  Daten sichern:               's'");
		System.out.println("       \n  Beenden:                     'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}
	private void gibSortierungsmenueAus() { // Sortierungsmenü
		System.out.println(" Auf welche Art möchten Sie sortieren? ");
		System.out.println(" Nach Artikelbezeichnung sortieren: 	'b'");
		System.out.println(" Nach Artikelpreis sortieren:           'p'");
		System.out.println(" Nach Artikelnummer sortieren:          'u'");
		System.out.println(" Zurück:                                'z'");
		System.out.print("> ");
	}
	private void loginScreen(){ //Erste Methode zum einloggen(k oder m)
		System.out.println("Als Kunde einloggen:                    'k'");
		System.out.println("Als neuer Kunde registrieren:           'c'");
		System.out.println("Als Mitarbeiter einloggen:              'm'");
		System.out.println("Als neuer Mitarbeiter registrieren:     'e'");
		System.out.println("Beenden:                                'q'");
		System.out.print("> ");
	}


	/* 
	 * 
	 * Interne (private) Methode zum Einlesen von Benutzereingaben.
	 */
	private String liesEingabe() {
		// einlesen von Konsole

		String eingabe = "";
		try{
			eingabe = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return eingabe;
	}

	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zur Verarbeitung von Eingaben
	 * und Ausgabe von Ergebnissen.
	 */


	/*
	 * komplett überarbeitung für Artikel
	 * 
	 * case für kaufen muss ergänzt werden !!!
	 */
	private void verarbeiteEingabe(String line) throws IOException /*throws IOException*/ {
		String nummer;
		int nr;
		String bezeichnung;
		int be;
		String preis;
		float pr;
		String bestand;
		boolean aufLager = false;
		String massenware;
		int mw ;
//		String anzahl;
		String hinzu;
		int hinzufuegen;
		String kaufen;
		int ka;
		boolean pruef = true;

		List<Artikel> liste; //<--- map für die artikel

		// Eingabe bearbeiten:
		switch (line) {

		case "a":
			liste = shop.gibAlleArtikel();
			gibArtikellisteAus(liste);
			break;
		case "d":
			// lies die notwendigen Parameter, einzeln pro Zeile
			/*
			 * TODO Bezieht sich noch nicht auf die Liste. Muss abgeändert werden,
			 * damit bei falscher Eingabe bspw. die Meldung kommt:
			 * "Bitte vorhandenen Artikel eingeben"
			 * 
			 * Außerdem soll das Löschen nur auf die Artikelnummer bezogen sein
			 */
			pruef = true;
			do{
				try{
					System.out.print("Artikelnummer   > ");
					nummer = liesEingabe();
					nr = Integer.parseInt(nummer);
					pruef = false;
				} catch(NumberFormatException e){
					pruef=true;
					nr = 0;
					System.out.println("Bitte geben Sie eine vorhandene Nummer ein.");
				}	
			}while(pruef);

			System.out.print("Artikelbezeichnung  > ");
			bezeichnung = liesEingabe();
			pruef = true;
			do{
				try{
					System.out.print("Artikelpreis   > ");
					preis = liesEingabe();
					pr = Float.valueOf(preis);
					pruef = false;
				} catch(NumberFormatException e){
					pruef=true;
					pr = 0;
					System.out.println("Bitte geben Sie die zum Artikel passenden Preis an\nZ.B.: 1.99");
				}	
			}while(pruef);

			pruef = true;
			do{
				try{
					System.out.print("Massenwaren anzahl >   ");
					massenware = liesEingabe();
					mw = Integer.parseInt(massenware);
					pruef = false;
				} catch(NumberFormatException e){
					pruef=true;
					mw = 0;
					System.out.println("Bitte geben Sie eine anständige Zahl ein.");
				}	
			}while(pruef);

			pruef = true;
			do{
				try{
					System.out.print("Artikelbestand   > ");
					bestand = liesEingabe();
					be = Integer.parseInt(bestand);
					pruef = false;
				} catch(NumberFormatException e){
					pruef=true;
					be = 0;
					System.out.println("Bitte geben Sie eine anständige Zahl ein.");
				}
			}while(pruef);

			if(be>0){
				aufLager = true;

			}
			shop.loescheArtikel(bezeichnung, nr, pr, be, aufLager, mw);
			System.out.println("Der Artikel wurde gelöscht,");
			break;
		case "e":		
			// liest die notwendigen Parameter, einzeln pro Zeile
			//Grundgerüst für Fehlermeldungen, statt Abstürze 
			//Artikelnummer einfügen auf Fehler überprüfen
			pruef = true;
			do{
				try{
					System.out.print("Artikelnummer   > ");
					nummer = liesEingabe();
					nr = Integer.parseInt(nummer);
					pruef = false;
				} catch(NumberFormatException e){
					pruef=true;
					nr = 0;
					System.out.println("Bitte geben Sie eine anständige Zahl ein.");
				}	
			}while(pruef);
			//Grundgerüst für Fehlermeldungen, statt Abstürze

			System.out.print("Artikelbezeichnung  > ");
			bezeichnung = liesEingabe();

			//Artikelpreis einfügen auf Fehler überprüfen
			pruef = true;
			do{
				try{
					System.out.print("Artikelpreis   > ");
					preis = liesEingabe();
					pr = Float.valueOf(preis);
					pruef = false;
				} catch(NumberFormatException e){
					pruef=true;
					pr = 0;
					System.out.println("Bitte geben Sie eine anständige Zahl ein. \nZ.B.: 1.99");
				}	
			}while(pruef);

			//Artikelbestand einfügen auf Fehler überprüfen
			pruef = true;
			do{
				try{
					System.out.println("Massenwaren anzahl    ");
					System.out.print("Wenn es keine Massenware ist bitte '1' eingeben  >");
					massenware = liesEingabe();
					mw = Integer.parseInt(massenware);
					pruef = false;
				} catch(NumberFormatException e){
					pruef=true;
					mw = 0;
					System.out.println("Bitte geben Sie eine anständige Zahl ein.");
				}	
			}while (pruef);
			pruef = true;
			do{
				try{
					System.out.print("Artikelbestand muss ein vielfaches von " + mw +" sein > ");
					bestand = liesEingabe();
					be = Integer.parseInt(bestand);

					int modulowert = be % mw;     // <<----- muss in die ArtikelVW
					while (modulowert != 0){
						{
							System.out.print("Artikelbestand muss ein vielfaches von " + mw +" sein > ");
							bestand = liesEingabe();
							be = Integer.parseInt(bestand);
							modulowert = be % mw;
						}
					}
					pruef = false;
				} catch(NumberFormatException e){
					pruef=true;
					be = 0;
					System.out.println("Bitte geben Sie eine anständige Zahl ein.");
				}	
			}while(pruef);

			if(be>0){
				aufLager = true;
			}
			//In Shop einfügen Bestätigung/Fehler
			try {
				if (nr != -1){
					shop.fuegeArtikelEin (bezeichnung, nr, pr, be, aufLager, mw);
					System.out.println("Einfuegen ok");
				}
				else{
					System.out.println("Fehler");
				}
			}
			catch (ArtikelExistiertBereitsException e) {
				// Hier Fehlerbehandlung...
				System.out.println("Fehler beim Einfuegen");
				e.printStackTrace();
			}
			break;
		case "f":
			System.out.print("Artikelbbezeichnung  > ");
			bezeichnung = liesEingabe();
			
			liste = shop.sucheNachBezeichnung(bezeichnung);
			gibArtikellisteAus(liste);
			// TODO Hier muss Exception rein "dieser Artikel existiert nicht"
			break;
		case "h": // hinzufügen von vorhandener Ware
			//TODO exceptions

			liste = shop.gibAlleArtikel();
			gibArtikellisteAus(liste);
			System.out.println("Geben Sie die ArtikelNR. ein: ");
			nummer = liesEingabe();
			nr = Integer.parseInt(nummer);

			System.out.print("Hinzuzufügende Anzahl: ");
			hinzu = liesEingabe();
			hinzufuegen = Integer.parseInt(hinzu);
			
			shop.aendereBestand(nr, hinzufuegen);

			break;
		case "k":// kaufen fall wird in den warenkorb übergeben getestet mit einer system out des warenkorbs (wieder entfernt)
			//TODO: massenware abfrage muss eingefügt werden, exception für negative zahlen bei der eingabe,
			liste = shop.gibAlleArtikel();
			gibArtikellisteAus(liste);
			System.out.println("Welchen Artikel möchten Sie Kaufen?");
			System.out.println("Geben sie die ArtikelNR. des Artikels ein");

			nummer = liesEingabe();
			nr = Integer.parseInt(nummer);

			System.out.println("wieviel moechten sie davon kaufen?");
			kaufen = liesEingabe();
			ka = Integer.parseInt(kaufen);
			// TODO hier die Ware in den Warenkorb uebergeben
			hinzufuegen = ka * (-1); // in eine negative zahl umwandeln um die Ware im bestandt zu verringern
			
		
			try {
				if (shop.artikelVorhanden (nr, ka)== true){
					shop.aendereBestand (nr, hinzufuegen);
					shop.artikelInWarenkorb (nr, ka); 
					System.out.println("Artikel nr " + nr +" wurde " + ka + " mal in den Warenkorb gelegt");
				}
			} catch (nichgGenugArtikelVorhandenException e1) {
				System.err.print(e1.getMessage());
				
			}

			break;
		
		case "o":
			gibSortierungsmenueAus();
			verarbeiteSortierung();
			break;
		case "s":
			try {
				shop.schreibeArtikel();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zum Ausgeben von Artikellisten.
	 *
	 */
	private void gibArtikellisteAus(List<Artikel> liste) {
		if (liste.isEmpty()) {
			System.out.println("Die Liste ist leer.");
		} else {
			Iterator<Artikel> it = liste.iterator();
			while (it.hasNext()) {
				Artikel artikel = it.next();
				System.out.println(artikel);
			}
		}
	}

	private void verarbeiteLogin(){
		String benutzername = "";
		String passwort = "";
		Adresse adresse;
		String line = liesEingabe();
		switch (line) {

		case "k":
			System.out.print("Benutzername: ");
			benutzername = liesEingabe();
			System.out.print("Passwort: ");
			passwort = liesEingabe();
			try {
				eingeloggterBenutzer = shop.kundeEinloggen(benutzername, passwort);
				System.out.println("Herzlich willkommen, Herr/Frau " + eingeloggterBenutzer.getName());
			} catch (LoginFehlgeschlagenException e2) {
				e2.printStackTrace();
				System.err.println(e2.getMessage());
			}
			break;
		case "c":
			System.out.print("Benutzername: ");
			benutzername = liesEingabe();
			System.out.print("Passwort: ");
			passwort = liesEingabe();
			adresse = liesAdresse();
		
			try {
				shop.fuegeKundeEin(benutzername, passwort, adresse);
				// eingeloggterBenutzer = shop.kundeEinloggen(benutzername, passwort);
			} catch (BenutzerExistiertBereitsException e1) {
				System.err.println(e1.getMessage());
			}
			break;
		case "m":
			System.out.print("Benutzername: ");
			benutzername = liesEingabe();
			System.out.print("Passwort: ");
			passwort = liesEingabe();
//			eingeloggterBenutzer = shop.mitarbeiterEinloggen(benutzername, passwort);
			
			try {
				eingeloggterBenutzer = shop.mitarbeiterEinloggen(benutzername, passwort);
				System.out.println("Herzlich willkommen, Herr/Frau " + eingeloggterBenutzer.getName());
			} catch (LoginFehlgeschlagenException e2) {
				e2.printStackTrace();
				System.err.println(e2.getMessage());
			}
			break;
		

		case "e":
			System.out.print("Benutzername: ");
			benutzername = liesEingabe();
			System.out.print("Passwort: ");
			passwort = liesEingabe();
			// TODO Exception bei falscher/ungültiger Eingabe?
			try {
				shop.fuegeMitarbeiterEin(benutzername, passwort);
			} catch (BenutzerExistiertBereitsException e) {
				e.printStackTrace();
			}
			break;

		case "q":
			loginSchleife = false;
			eingeloggterBenutzer = null;
			// TODO: hier fehlt noch was!
			break;

		}
	}

	private Adresse liesAdresse() {
		String str;
		String hausnummer;
		String ort;
		String plz;

		System.out.print("Strasse: ");
		str = liesEingabe();
		System.out.print("Hausnummer: ");
		hausnummer = liesEingabe();
		System.out.print("Ort: ");
		ort = liesEingabe();
		System.out.print("PLZ: ");
		plz = liesEingabe();

		return new Adresse(str, hausnummer, ort, plz);
	}

	private void verarbeiteSortierung(){
		String sort = liesEingabe();
		switch (sort) {
		case "b":
			List<Artikel> artikelListeBez = shop.gibAlleArtikel();
			//			meineArtikel.sortArtikelBezeichnung();
			Collections.sort(artikelListeBez, new Comparator<Artikel>(){ //Comparator = Interface

				public int compare(Artikel a1, Artikel a2) {                        // Methode, die Artikel vergleicht
					return a1.getBezeichnung().compareTo(a2.getBezeichnung());
				}
			});
			gibArtikellisteAus(artikelListeBez);
			break;
		case "u":
			List<Artikel> artikelListeNr = shop.gibAlleArtikel();
			Collections.sort(artikelListeNr, new Comparator<Artikel>(){ //Comparator = Interface

				@Override
				public int compare(Artikel o1, Artikel o2) {                        // Methode, die Artikel vergleicht
					return o1.getNummer() - (o2.getNummer());
				}
			});
			gibArtikellisteAus(artikelListeNr);
			break;
		case "p":
			List<Artikel> artikelListePreis = shop.gibAlleArtikel();
			Collections.sort(artikelListePreis, new Comparator<Artikel>(){ //Comparator = Interface

				@Override
				public int compare(Artikel o1, Artikel o2) {                        // Methode, die Artikel vergleicht
					return (int) (o1.getPreis() - (o2.getPreis()));
				}
			});
			gibArtikellisteAus(artikelListePreis);
			break;
		case "z":
			if (eingeloggterBenutzer instanceof Kunde) {
				gibKundenMenueAus();
			} else {
				gibMitarbeiterMenueAus();
			}
		}
	}


	/**
	 * 
	 * Methode zur Ausführung der Hauptschleife:
	 * - Menü ausgeben
	 * - Eingabe des Benutzers einlesen
	 * - Eingabe verarbeiten und Ergebnis ausgeben
	 * (EVA-Prinzip: Eingabe-Verarbeitung-Ausgabe)
	 * @throws IOException 
	 */
	public void run() throws IOException {
		// Variable für Eingaben von der Konsole
		String input = ""; 
		do{
			loginScreen();
			verarbeiteLogin();
			// Hauptschleife der Benutzungsschnittstelle
			// mitarbeiter oder Kunde abfrage

			// login
			if(eingeloggterBenutzer != null)
				do {
					// Es wird bis jetzt nur das Mitarbeitermenü ausgegeben
					if(eingeloggterBenutzer instanceof Kunde){
						gibKundenMenueAus();
					} else{
						gibMitarbeiterMenueAus();
					}
					input = liesEingabe();
					verarbeiteEingabe(input);

				} while (!input.equals("q"));
		}while(loginSchleife);
	}


	/**
	 * Die main-Methode...
	 */
	public static void main(String[] args) {
		ShopClientCUI cui;
		try {
			cui = new ShopClientCUI("SHOP");
			cui.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


