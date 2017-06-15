package shop.valueobjects;
/**
 * Klasse der Kundendaten erweitert aus Benutzer
 * @author Veitztanz
 *
 */

public class Kunde extends Benutzer {

	private Adresse adresse = null;
	private Warenkorb cart = null;


	public Kunde(String name, String passwort, Adresse adresse) {
		super(name, passwort);
		this.adresse = adresse;
		this.cart = new Warenkorb();
	}
	// Ab hier Accessor-Methoden

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Warenkorb getCart() {
		return cart;
	}
}
