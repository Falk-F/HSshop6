package shop.exceptions;

public class LoginFehlgeschlagenException extends Exception {

	public LoginFehlgeschlagenException(String name, String pwd) {
		super("Login f�r " + name + "/" + pwd + " ist fehlgeschlagen.");
	}
}
