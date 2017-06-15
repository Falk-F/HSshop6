package shop.exceptions;

public class LoginFehlgeschlagenException extends Exception {

	public LoginFehlgeschlagenException(String name, String pwd) {
		super("Login für " + name + "/" + pwd + " ist fehlgeschlagen.");
	}
}
