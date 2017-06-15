package shop.valueobjects;



/**
 * Klasse der Artikel
 * @author Veitzi
 *
 */


public class Artikel implements Comparable<Artikel>{

	//Attribute zur Beschreibung eines Artikels:
	private String bezeichnung;
	private int nummer;
	private float preis;
	private int bestand;
	private boolean aufLager;
	private int massenware;



	public Artikel(String bezeichnung, int nr, float preis, int bestand, int massenware){
		this(bezeichnung, nr, preis, bestand, true,  massenware);
	}

	public Artikel(String bezeichnung, int nr, float preis, int bestand, boolean aufLager, int massenware){
		nummer = nr;
		this.bezeichnung = bezeichnung;
		this.preis = preis;
		this.bestand = bestand;
		this.aufLager = aufLager;
		this.massenware = massenware;
	}


	// ---Dienste der Artikel-Objekte---



	public String toString(){
		String verfuegbarkeit = aufLager ? "auf Lager" : "ausverkauft";
		return ("Nr: " + nummer + " / Artikel: " + bezeichnung + " / Preis: " + preis + "€" + " / " + aufLager + " / Artikel im Bestand: " + bestand + "  / Anzahl Massenware: " + massenware );
	}

	/*
	 * Standart-Methode von Object überschrieben.
	 * Methode dient Vergleich von zwei Artikel-Objekten anhand ihrer Werte,
	 * d.h.Bezeichnung, Nummer, .
	 */

	public boolean equals(Object andererArtikel){
		if (andererArtikel instanceof Artikel)
			return ((this.nummer == ((Artikel) andererArtikel).nummer)
					&& (this.bezeichnung.equals(((Artikel) andererArtikel).bezeichnung))
					//  && (this.preis == (((Artikel) andererArtikel).preis))		//<--- wieder das == prob preis abfrage kann mehrfach vorhanden sein
					&& this.bestand == (((Artikel) andererArtikel).bestand)); //	-		"		-
		else
			return false;
	}

	// Ab hier Accessor-Methoden

	public String getBezeichnung(){
		return bezeichnung;
	}

	public int getNummer(){
		return nummer;
	}

	public float getPreis(){
		return preis;
	}

	public int getBestand(){
		return bestand;
	}
	public void setBestand (int bestand){
		this.bestand = bestand;
	}

	public boolean istAufLager(){
		return aufLager;
	}
	public int getMassenware(){
		return massenware;
	}
	public void bestandErhoehen(int hinzufuegen){
		this.bestand = bestand + hinzufuegen;


	}

	@Override
	public int compareTo(Artikel anderer) {

		return nummer - anderer.nummer;
	}
}
