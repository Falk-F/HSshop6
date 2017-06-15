package shop.domain;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import shop.exceptions.BenutzerExistiertBereitsException;
import shop.exceptions.LoginFehlgeschlagenException;
import shop.persistance.FilePersistenceManager;
import shop.persistance.PersistenceManager;
import shop.valueobjects.Kunde;



public class KundenVerwaltung {
	private Kunde eingeloggterKunde;


	/**
	 * Klasse zur Kundenverwaltung
	 * @author Veitzi
	 *
	 */


	private List<Kunde> kundeBestand = new Vector<Kunde>();

	// datenbank zugriff wenn es klappt hoffe ich
	private PersistenceManager pm = new FilePersistenceManager(); // <----- Muesse beide noch Implementiert werden

	/*
	 * Java persistence manager /datenbankzugriff
	 */
	// test f�r den persistence manager

	public void liesDaten(String datei) throws IOException{
		pm.openForReading(datei);

		Kunde einKunde;
		do {
			//benutzer Objekt einlesen
			einKunde =pm.ladeKunde();
			if(einKunde != null){
				//benutzer in Liste einf�gen					
				try{
					einfuegen(einKunde);

				}catch (BenutzerExistiertBereitsException e1 ) {

				}

			}
		}while (einKunde != null);

		pm.close();
	}
	/**
	 * Benutzerdaten in eine Datei spechern
	 * @param datei 
	 * @throws IOException
	 */
	public void schreibeDaten(String datei) throws IOException  {
		// 
		pm.openForWriting(datei);

		if (!kundeBestand.isEmpty()) {
			Iterator iter = kundeBestand.iterator();
			while (iter.hasNext()) {
				Kunde k = (Kunde) iter.next();
				pm.speichereKunde(k);				
			}
		}

		// Persistenz-Schnittstelle wieder schließen
		pm.close();
	}




	public void einfuegen(Kunde einKunde) throws BenutzerExistiertBereitsException {

		if (!kundeBestand.contains(einKunde))
			kundeBestand.add(einKunde);
		else
			throw new BenutzerExistiertBereitsException(einKunde, " - in 'einfuegen()'");	
	}

	/**
	 * Methode zum  Benutzer suchen nach der ID
	 */

	// eventuell nach ID suchen da sie eindeutig sein wird
	public List<Kunde> sucheKunde(String name) {
		List<Kunde> suchErg = new Vector<Kunde>();



		for (Kunde aktKunde: kundeBestand) {
			if (aktKunde.getName() == (name)) {
				suchErg.add(aktKunde);
			}
		}
		return suchErg;
	}
	/**
	 * zur�ggegebener Benutzer bestand
	 * @return
	 */

	public List<Kunde> getKundeBestand() {
		// Achtung: hier wäre es sinnvoller / sicherer, eine Kopie des Vectors 
		// mit Kopien der Buch-Objekte zurückzugeben
		return kundeBestand;
	}
	public Kunde kundeEinloggen(String name, String passwort) throws LoginFehlgeschlagenException {
		//		for (Kunde k: kundenListe) {
		for (Kunde k: kundeBestand) {
			if (k.getName().equals(name) && k.getPasswort().equals(passwort)){
				eingeloggterKunde = k;
				return k; //Exception wenn Passwort oder Name falsch eingegeben
			}
		}
		throw new LoginFehlgeschlagenException(name, passwort);
	}
	public Kunde getEingeloggterKunde(){
		return eingeloggterKunde;
	}
}








