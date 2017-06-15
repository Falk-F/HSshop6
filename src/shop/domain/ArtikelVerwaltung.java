package shop.domain;

import java.io.IOException;

import java.util.Collections;
//import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import shop.exceptions.ArtikelExistiertBereitsException;
import shop.exceptions.nichgGenugArtikelVorhandenException;
import shop.persistance.FilePersistenceManager;
import shop.persistance.PersistenceManager;
import shop.valueobjects.Artikel;
//import shop.valueobjects.Warenkorb;



/**
 * Klasse zur Verwaltung von Artikel.
 * 
 * (Verwaltung der Artikel in Liste/Vector mit Generics)
 */
public class ArtikelVerwaltung {

	// Verwaltung des Artikelbestands in Vector
	private List<Artikel> artikelBestand = new Vector<Artikel>();
	
	// Persistenz-Schnittstelle, die für die Details des Dateizugriffs verantwortlich ist
	private PersistenceManager pm = new FilePersistenceManager();

	/**
	 * methode zum einlesen der Artikle aus einer datei
	 * @param datei
	 * @throws IOException
	 */
	public void liesDaten(String datei) throws IOException {
		// PersistenzManager für Lesevorgänge öffnen
		pm.openForReading(datei);

		Artikel einArtikel;
		do {
			// Artikel-Objekt einlesen
			einArtikel = pm.ladeArtikel();
			if (einArtikel != null) {
				// Artikel in Liste einfügen
				try {
					einfuegen(einArtikel);					
				} catch (ArtikelExistiertBereitsException e1) { //<-- eigentlich(ArtikelExistiertBereitsException e1)
					// Kann hier eigentlich nicht auftreten,
					// daher auch keine Fehlerbehandlung...
				}
			}
		} while (einArtikel != null);

		// Persistenz-Schnittstelle wieder schließen
		pm.close();
	}

	/**
	 * Methode zum Schreiben der Artikeldaten in eine Datei.
	 * 
	 * @param datei Datei, in die der Artikelbestand geschrieben werden soll
	 * @throws IOException
	 */
	public void schreibeDaten(String datei) throws IOException  {
		// PersistenzManager für Schreibvorgänge öffnen
		pm.openForWriting(datei);

		if (!artikelBestand.isEmpty()) {
			Iterator<Artikel> iter = artikelBestand.iterator();
			while (iter.hasNext()) {
				Artikel a = (Artikel) iter.next();
				pm.speichereArtikel(a);				
			}
		}

		// Persistenz-Schnittstelle wieder schließen
		pm.close();
	}

	/**
	 * Methode, die ein Artikel an das Ende der Artikelliste einfügt.
	 * 
	 * @param einArtikel der einzufügende Artikel
	 * @throws Exception 
	 * @throws ArtikelExistiertBereitsException wenn der Artikel bereits existiert
	 */
	public void einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
		if (!artikelBestand.contains(einArtikel))
			artikelBestand.add(einArtikel);
		else
			throw new ArtikelExistiertBereitsException(einArtikel, " - in 'einfuegen()'"); 
	}

	/**
	 * Methode zum Löschen eines Artikels aus dem Bestand. 
	 * 
	 * @param einArtikel der löschende Artikel
	 */
	public void loeschen(Artikel einArtikel) {
		artikelBestand.remove(einArtikel);
	}

	/**
	 * Methode, die anhand einer Bezeichnung nach Artikeln sucht. Es wird eine Liste von Artikeln
	 * zurückgegeben, die alle Artikel mit exakt übereinstimmendem Bezeichnung enthält.
	 * 
	 * @param bezeichnung Bezeichnung des gesuchten Artikels
	 * @return Liste der Artikel mit gesuchter Bezeichnung (evtl. leer)
	 */
	public List<Artikel> sucheArtikel(String bezeichnung) {
		List<Artikel> suchErg = new Vector<Artikel>();


		for (Artikel aktArtikel: artikelBestand) {
			if (aktArtikel.getBezeichnung().equals(bezeichnung)) {
				suchErg.add(aktArtikel);
			}
		}
		Collections.sort(suchErg);
		return suchErg;
	}
	public List<Artikel> aendereBestand (int nr, int hinzufuegen){
		List<Artikel> liste = sucheNachArtikelNr(nr);
		if (hinzufuegen >= 0){
			liste.get(0).bestandErhoehen(hinzufuegen);
			return artikelBestand;

		}
		if (hinzufuegen < 0){
			liste.get(0).bestandErhoehen(hinzufuegen);
			return artikelBestand;
		}
		// bestand verrringer (kaufen) als if
		else { // negative zahl exception
			//throws IOException
			return null;  // <----- nur zum testen
		}
	}

	public boolean artikelVorhanden (int nr, int ka)throws nichgGenugArtikelVorhandenException{
		List<Artikel> liste = sucheNachArtikelNr(nr);
		if (liste.get(0).getBestand() >= ka){
			return true;
		}
		else
			throw new nichgGenugArtikelVorhandenException () ;
			
	}
	/**
	 * Methode, die den Artikelbestand zurückgibt.
	 */
	public List<Artikel> getArtikelBestand() {
		// Achtung: hier wäre es sinnvoller / sicherer, eine Kopie des Vectors 
		// mit Kopien der Artikel-Objekte zurückzugeben
		Collections.sort(artikelBestand);

		return artikelBestand;
	}

	public List<Artikel> sucheNachArtikelNr(int nr) {
		List<Artikel> suchErg = new Vector<Artikel>();


		for (Artikel aktArtikel: artikelBestand) {
			if (aktArtikel.getNummer()==(nr)) {
				suchErg.add(aktArtikel);
			}
		}
		Collections.sort(suchErg);
		return suchErg;
	}

}
