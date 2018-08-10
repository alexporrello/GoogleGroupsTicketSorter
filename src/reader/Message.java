package reader;

/**
 * A message should always be an element of SupportPage. It contains...
 * <ul>
 * 	<li>A message's poster (or author).</li>
 * 	<li>The body (or content) of a message.</li>
 * </ul>
 * @author Alexander Porrello
 */
public class Message {

	/** The author (or poster) of this message **/
	String poster;
	
	/** The body (or content) of the post **/
	String body;

	/**
	 * Used to create an already-parsed message.
	 * @param poster The author (or poster) of this message
	 * @param body The body (or content) of the post
	 */
	public Message(String poster, String body) {
		this.poster = poster;
		this.body   = body;
	}

	/** Returns the message's body. **/
	public String getBody() {
		return body;
	}

	/** Returns the message's poster. **/
	public String getPoster() {
		return poster;
	}

	/**
	 * For determining if the message has a poster.
	 * @return true if the message has a poster; else, false.
	 */
	public Boolean hasPoster() {
		return !poster.equals("");
	}

	/**
	 * For determining if the message has a body.
	 * @return true if the message has a body; else, false.
	 */
	public Boolean hasBody() {
		return !body.equals("");
	}
}
