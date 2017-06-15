package shop.domain;

import java.io.IOException;

import java.util.List;
import shop.exceptions.ArtikelExistiertBereitsException;
import shop.exceptions.BenutzerExistiertBereitsException;
import shop.exceptions.LoginFehlgeschlagenException;
import shop.exceptions.nichgGenugArtikelVorhandenException;
import shop.valueobjects.Adresse;
import shop.valueobjects.Artikel;
import shop.valueobjects.Kunde;
import shop.valueobjects.Mitarbeiter;
/**
 * to do Artikel suche auf Artikelnummer umstellen da eindeutig
 * 
 *
 */
public class ShopVerwaltung {
	// PrÃ¤fix fÃ¼r Namen der Dateien, in der die Bibliotheksdaten gespeichert sind
	private String datei = "";
	private ArtikelVerwaltung meineArtikel;
	private KundenVerwaltung meineKunden;
	private MitarbeiterVerwaltung meineMitarbeiter;

	/**
	 * Konstruktor, der die Basisdaten (Benutzer, Artikel) aus Dateien einliest
	 * (Initialisierung des Shops).
	 * 
	 * Namensmuster der Dateien:
	 *   "shop_Artikel.txt"
	 *   "shop_Benutzer.txt"
	 */
	public ShopVerwaltung(String datei) throws IOException {
		this.datei = datei;

		// Artikel aus Datei einlesen
		meineArtikel = new ArtikelVerwaltung();
		meineArtikel.liesDaten(datei+"shop_Artikel.txt");
		meineArtikel.schreibeDaten(datei+"shop_Artikel.txt");

		meineKunden = new KundenVerwaltung();
		meineKunden.liesDaten(datei+"shop_Kunde.txt");
		meineKunden.schreibeDaten(datei+"shop_Kunde.txt");

		meineMitarbeiter = new MitarbeiterVerwaltung();
		meineMitarbeiter.liesDaten(datei+"shop_Mitarbeiter.txt");
		meineMitarbeiter.schreibeDaten(datei+"shop_Mitarbeiter.txt");
	}


	/**
	 * Methode, die eine Liste aller im Bestand befindlichen Artikel zuruekgibt.
	 * 
	 * @return Liste aller Artikel im Bestand des Shops
	 */
	public List<Artikel> gibAlleArtikel() {
		// einfach delegieren an meineBuecher
		return meineArtikel.getArtikelBestand();
	}
	/**
	 * Methode die eine Liste aller Benutzer zurückgibt
	 * 
	 */
	//	public List<Benutzer> gibAlleBenutzer() {
	//
	//		return meineBenutzer.getBenutzerBestand(); //<----- meinung von Steffi und Tim Name ist scheisse
	//	}
	public List<Kunde> gibAlleKunde() {

		return meineKunden.getKundeBestand(); //<----- meinung von Steffi und Tim Name ist scheisse
	}
	public List<Mitarbeiter> gibAlleMitarbeiter() {

		return meineMitarbeiter.getMitarbeiterBestand(); //<----- meinung von Steffi und Tim Name ist scheisse
	}



	/**
	 * methode zum suchen der Artikel anhand des Namens
	 * @param bezeichnung
	 * 
	 */
	public List<Artikel> sucheNachBezeichnung(String bezeichnung) {  //<---- eventuel nach artikle ID suchen da eindeutig
		// einfach delegieren an meineArtikel
		return meineArtikel.sucheArtikel(bezeichnung); //<---- titel ist falsch
	}
	public List<Artikel>aendereBestand(int nr, int hinzufuegen){
		return meineArtikel.aendereBestand(nr, hinzufuegen);
	}

	/**
	 * methode zum suchen von usern anhand der ID
	 * @param id des gesuchten Users
	 * @return
	 */

	public List<Kunde> sucheNachBenutzername(String name) {  //<---- eventuel nach artikle ID suchen da eindeutig
		// einfach delegieren an meineArtikel
		return meineKunden.sucheKunde(name); 
	}
	public List<Mitarbeiter> sucheNachMitarbeitername(String name) {  //<---- eventuel nach artikle ID suchen da eindeutig
		// einfach delegieren an meineArtikel
		return meineMitarbeiter.sucheMitarbeiter(name); 
	}
	/**
	 * methode zum einfügen eines Artikels
	 * @param bezeichnung
	 * @param nummer
	 * @param preis
	 * @param bestand
	 * @param aufLager
	 */
	public void fuegeArtikelEin(String bezeichnung, int nummer, float preis, int bestand, boolean aufLager, int massenware) throws ArtikelExistiertBereitsException { // <---- exception fehlt
		Artikel a = new Artikel(bezeichnung, nummer, preis, bestand, aufLager, massenware);
		meineArtikel.einfuegen(a);
	}
	/**
	 * Methode zum einfügen eines users
	 * @param name
	 * @param passwort
	 * @param adresse 
	 * @param id
	 * @throws BenutzerExistiertBereitsException 
	 */

	public void fuegeKundeEin(String name, String passwort, Adresse adresse) throws BenutzerExistiertBereitsException  { 
		Kunde k = new Kunde(name,passwort, adresse);
		meineKunden.einfuegen(k);
	}

	public void fuegeMitarbeiterEin(String name, String passwort) throws BenutzerExistiertBereitsException  {
		Mitarbeiter m = new Mitarbeiter(name,passwort);
		meineMitarbeiter.einfuegen(m);
	}

	public Kunde kundeEinloggen(String benutzername, String passwort) throws LoginFehlgeschlagenException /* throws LoginFailedException */ {
		return meineKunden.kundeEinloggen(benutzername, passwort);
	}
	public Mitarbeiter mitarbeiterEinloggen(String benutzername, String passwort) throws LoginFehlgeschlagenException{
		return meineMitarbeiter.mitarbeiterEinloggen(benutzername, passwort);

	}
	/**
	 * methode zum löschen eines Artikels
	 * @param bezeichnung
	 * @param nummer
	 * @param preis
	 * @param bestand
	 * @param aufLager
	 */
	public void loescheArtikel(String bezeichnung, int nummer, float preis, int bestand, boolean aufLager, int massenware) {
		meineArtikel.loeschen(new Artikel(bezeichnung, nummer, preis, bestand, aufLager, massenware));
	}

	/**
	 * methode zum speichern eines Artikles
	 * @throws IOException
	 */
	public void schreibeArtikel() throws IOException {
		meineArtikel.schreibeDaten(datei+"shop_Artikel.txt");
	}
	/**
	 * methoden zum speichern eines users
	 * 
	 */

	public void schreibeMitarbeiter() throws IOException {
		meineMitarbeiter.schreibeDaten(datei+"shop_Mitarbeiter.txt");
	}
	public void schreibeKunde() throws IOException {
		meineKunden.schreibeDaten(datei+"shop_Kunde.txt");
	}


	public void artikelInWarenkorb(int nr, int ka) {
		meineKunden.getEingeloggterKunde().getCart().artikelInWarenkorb(nr, ka);
		
	}

	public List<Artikel> sucheNachArtikelNr(int nr) {
		  //<---- eventuel nach artikle ID suchen da eindeutig
			// einfach delegieren an meineArtikel
			return meineArtikel.sucheNachArtikelNr(nr);
	}
	public boolean artikelVorhanden(int nr, int ka) throws nichgGenugArtikelVorhandenException{
		return meineArtikel.artikelVorhanden(nr, ka);
	}

}
