package shop.exceptions;

import shop.valueobjects.Benutzer;

public class BenutzerExistiertBereitsException extends Exception{



/**
 * exeption das ein Benutzer bereits existiert
 * 
 */
	


	/**
	 * Konstruktor
	 * benutzer existiert bereits
	 */
	public BenutzerExistiertBereitsException(Benutzer benutzer, String zusatzMsg) {
		super("Benutzer mit ID " + benutzer.getId() + " und Nummer " + benutzer.getId() 
				+ " existiert bereits" + zusatzMsg); // Statt ID -> Benutzername
		
	}
}
