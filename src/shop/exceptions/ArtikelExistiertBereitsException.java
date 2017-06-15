package shop.exceptions;

import shop.valueobjects.Artikel;

public class ArtikelExistiertBereitsException extends Exception {



	/**
	 * exeption das ein Artikel bereits existiert
	 * 
	 */

	/**
	 * to do: Artikel auf ID umstellen
	 */

	/**
	 * Konstruktor
	 * Artikel existiert bereits
	 */
	public ArtikelExistiertBereitsException(Artikel artikel, String zusatzMsg) {
		super("Artikel mit Bezeichnung " + artikel.getBezeichnung() + " und Nummer " + artikel.getNummer() 
		+ " existiert bereits" + zusatzMsg);


	}
}
