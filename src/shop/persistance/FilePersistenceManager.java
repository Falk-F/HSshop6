package shop.persistance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import shop.valueobjects.Adresse;
import shop.valueobjects.Artikel;
import shop.valueobjects.Benutzer;
import shop.valueobjects.Kunde;
import shop.valueobjects.Mitarbeiter;;

/**
 * Klasse zum schreiben in txt dateien
 * @author Veitzi
 *
 */

/**
 * to do Klasse f�r Benutzer erweitern
 * 
 *
 */
public class FilePersistenceManager implements PersistenceManager {

	private BufferedReader reader = null;
	private PrintWriter writer = null;

	public void openForReading(String datei) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(datei));
	}

	public void openForWriting(String datei) throws IOException {
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
	}

	public boolean close() {
		if (writer != null)
			writer.close();

		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				return false;
			}
		}

		return true;
	}

	/**
	 * Methode zum lesen der Artikel aus einer Txt. datei
	 */
	public Artikel ladeArtikel() throws IOException {
		// Artikeleinlesen
		String bezeichnung = liesZeile();
		if (bezeichnung == null) {
			// keine Daten mehr vorhanden
			return null;
		}
		// String zu float konvertieren
		String preisString = liesZeile();
		float preis =Float.valueOf(preisString);

		// Nummer einlesen ...
		String nummerString = liesZeile();
		// ... und von String in int konvertieren
		int nummer = Integer.parseInt(nummerString);

		String massenwareString = liesZeile();
		int  massenware = Integer.parseInt(massenwareString);
		
		// Artikel vorhanden?
		String aufLagerCode = liesZeile();
		// Codierung des aufLager in boolean umwandeln
		boolean aufLager = aufLagerCode.equals("t") ? true : false;

		String bestandString = liesZeile();
		// ... und von String in int konvertieren
		int bestand = Integer.parseInt(bestandString);



		// neues Artikel-Objekt anlegen und zurückgeben
		return new Artikel(bezeichnung, nummer, preis, bestand, aufLager, massenware);
	}

	/**
	 * Methode zum speichern der Artikel in eine Txt. Datei
	 */

	public boolean speichereArtikel(Artikel a) throws IOException {
		// Titel, Nummer und Verfügbarkeit schreiben
		schreibeZeile(a.getBezeichnung());
		schreibeZeile(a.getPreis() + "");
		schreibeZeile(a.getNummer() + "");
		schreibeZeile(a.getMassenware() + "");
		if (a.istAufLager())
			schreibeZeile("t");
		else
			schreibeZeile("f");
		schreibeZeile(a.getBestand() + "");

		return true;
	}


	/*
	 * Private Hilfsmethoden
	 */

	private String liesZeile() throws IOException {
		if (reader != null)
			return reader.readLine();
		else
			return "";
	}

	private void schreibeZeile(String daten) {
		if (writer != null)
			writer.println(daten);
	}



	public Kunde ladeKunde() throws IOException {
		String name = liesZeile();
		if (name == null) {
			// keine Daten mehr vorhanden
			return null;
		}

		// TODO entweder in eine zeile alles
		String passwort = liesZeile();

		String strasseString = liesZeile();

		String hausnummerString= liesZeile();

		String ortString= liesZeile();

		String plzString= liesZeile();

		Adresse adresse = new Adresse(strasseString, hausnummerString, ortString, plzString);
		return new Kunde(name, passwort, adresse);

	}

	@Override
	public Mitarbeiter ladeMitarbeiter() throws IOException {
		String name = liesZeile();
		if (name == null) {
			// keine Daten mehr vorhanden
			return null;
		}
		String passwort = liesZeile();

		return new Mitarbeiter(name, passwort);
	}

	@Override
	public boolean speichereMitarbeiter(Mitarbeiter m) throws IOException {
		schreibeZeile(m.getName());
		schreibeZeile(m.getPasswort());
		return false;
	}

	@Override
	public boolean speichereKunde(Kunde k) throws IOException {
		// TODO Auto-generated method stub
		schreibeZeile(k.getName());
		schreibeZeile(k.getPasswort());
		schreibeZeile(k.getAdresse().getStrasse());
		schreibeZeile(k.getAdresse().getHausnummer());
		schreibeZeile(k.getAdresse().getOrt());
		schreibeZeile(k.getAdresse().getPlz());


		return false;
	}





}
