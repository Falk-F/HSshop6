package shop.valueobjects;

/**
 * Klasse der Kundenadressen
 * @author Veitztanz
 *
 */

public class Adresse {
	private String strasse;
	private String hausnummer;
	private String ort;
	private String plz;

	public Adresse(String strasse, String hausnummer, String ort, String plz) {
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.ort = ort;
		this.plz = plz;
	}
	// Ab hier Accessor-Methoden
	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}
}
