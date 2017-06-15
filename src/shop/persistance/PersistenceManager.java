package shop.persistance;

import java.io.IOException;
import shop.valueobjects.Artikel;
import shop.valueobjects.Benutzer;
import shop.valueobjects.Kunde;
import shop.valueobjects.Mitarbeiter;

/**
 * schnittstelle für externe Speicher medien in diesem fall die txt dateien
 * 		"shop_Artikel.txt"
		"shop_Benutzer.txt"
 * @author Veitzi
 *
 */
public interface PersistenceManager {

	public void openForReading(String datenquelle) throws IOException;

	public void openForWriting(String datenquelle) throws IOException;

	public boolean close();


	/**
	 * Methode zum einlesen der Artikel
	 * @return
	 * @throws IOException
	 */
	public Artikel ladeArtikel() throws IOException;

	public Kunde ladeKunde() throws IOException;
	/**
	 * Methode zum einlesen der Artikel
	 * @return
	 * @throws IOException
	 */
	public Mitarbeiter ladeMitarbeiter() throws IOException;

	/**
	 * methode zum speichern von Artikle
	 * 
	 */
	public boolean speichereArtikel(Artikel a) throws IOException;
	/**
	 * methode zum speichern von Artikle
	 * 
	 */
	public boolean speichereMitarbeiter(Mitarbeiter m) throws IOException;
	/**
	 * methode zum speichern von Kunden
	 * 
	 */
	public boolean speichereKunde(Kunde k) throws IOException;


}