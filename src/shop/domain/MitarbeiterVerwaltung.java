package shop.domain;


import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import shop.exceptions.BenutzerExistiertBereitsException;
import shop.exceptions.LoginFehlgeschlagenException;
import shop.persistance.FilePersistenceManager;
import shop.persistance.PersistenceManager;
//import shop.valueobjects.Benutzer;
//import shop.valueobjects.Kunde;
import shop.valueobjects.Mitarbeiter;




/**
 * Klasse zur Benutzerverwaltung
 * @author Veitzi
 *
 */
public class MitarbeiterVerwaltung {

	private List<Mitarbeiter> mitarbeiterBestand = new Vector<Mitarbeiter>();

	// datenbank zugriff wenn es klappt hoffe ich
	private PersistenceManager pm = new FilePersistenceManager(); // <----- Muesse beide noch Implementiert werden

	/*
	 * Java persistence manager /datenbankzugriff
	 */
	// test f¸r den persistence manager

	public void liesDaten(String datei) throws IOException{
		pm.openForReading(datei);

		Mitarbeiter einMitarbeiter;
		do {
			//benutzer Objekt einlesen
			einMitarbeiter =pm.ladeMitarbeiter();
			if(einMitarbeiter != null){
				//benutzer in Liste einf¸gen					
				try{
					einfuegen(einMitarbeiter);

				}catch (BenutzerExistiertBereitsException e1 ) {

				}

			}
		}while (einMitarbeiter != null);

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

		if (!mitarbeiterBestand.isEmpty()) {
			Iterator iter = mitarbeiterBestand.iterator();
			while (iter.hasNext()) {
				Mitarbeiter m = (Mitarbeiter) iter.next();
				pm.speichereMitarbeiter(m);				
			}
		}

		// Persistenz-Schnittstelle wieder schlie√üen
		pm.close();
	}




	public void einfuegen(Mitarbeiter einMitarbeiter) throws BenutzerExistiertBereitsException {//throws BenutzerExistiertBereitsException fehlt

		if (!mitarbeiterBestand.contains(einMitarbeiter))
			mitarbeiterBestand.add(einMitarbeiter);
		else
			throw new BenutzerExistiertBereitsException(einMitarbeiter, " - in 'einfuegen()'");	//<----- Exeption fehlt
	}

	/**
	 * Methode zum  Benutzer suchen nach der ID
	 */

	// eventuell nach ID suchen da sie eindeutig sein wird
	public List<Mitarbeiter> sucheMitarbeiter(String name) {
		List<Mitarbeiter> suchErg = new Vector<Mitarbeiter>();



		for (Mitarbeiter aktMitarbeiter: mitarbeiterBestand) {
			if (aktMitarbeiter.getName().equals(name)) { //<---- == ist das wirklich gut?
				suchErg.add(aktMitarbeiter);
			}
		}

		return suchErg;
	}
	/**
	 * zur¸ggegebener Benutzer bestand
	 * @return
	 */

	public List<Mitarbeiter> getMitarbeiterBestand() {
		// Achtung: hier w√§re es sinnvoller / sicherer, eine Kopie des Vectors 
		// mit Kopien der Buch-Objekte zur√ºckzugeben
		return mitarbeiterBestand;
	}
	public Mitarbeiter mitarbeiterEinloggen(String name, String passwort)throws LoginFehlgeschlagenException{
		for (Mitarbeiter m : mitarbeiterBestand){
			if(m.getName().equals(name) && m.getPasswort().equals(passwort))
				return m;
		}
		return null;

	}





}

