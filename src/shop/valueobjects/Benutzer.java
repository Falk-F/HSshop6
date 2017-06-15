package shop.valueobjects;

/**
 * Klasse der Benutzerdaten
 * @author Veitztanz
 *
 */
public abstract class Benutzer {

	private static int NUMMERN_ZAEHLER = 1;

	protected String name;
	protected String passwort;
	protected int id;


	public Benutzer(String name, String passwort) {
		this(name, passwort, NUMMERN_ZAEHLER++);
	}

	public Benutzer(String name, String passwort, int id) {
		this.name = name;
		this.passwort = passwort;
		this.id = id;
		if (id > NUMMERN_ZAEHLER)
			NUMMERN_ZAEHLER = id+1;
	}

	// Ab hier Accessor-Methoden
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public int getId() {
		return id;
	}

}