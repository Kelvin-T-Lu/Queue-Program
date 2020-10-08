/**
 * Thrown if a remove method is called while line is empty.
 * @author Kelvin Lu
*/
public class NoElementException extends RuntimeException{
	/**
	 * An exception with a custom error message.
	 * @param msg A custom error message inputted by user.
	 */
	public NoElementException(String msg) {
		super(msg);
	}
	/**
	 * An exception with default error message. 
	 */
	public NoElementException() {
	}
}
