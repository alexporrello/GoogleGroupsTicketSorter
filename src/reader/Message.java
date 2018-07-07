package reader;

public class Message {

	String poster;
	String body;

	public Message(String poster, String body) {
		this.poster = poster;
		this.body   = body;
	}

	public Message(String message) {
		try {
			parseMessage(message);
		} catch (Exception e) {
			System.err.println("I failed to parse a message.");
		}
	}

	private void parseMessage(String message) throws Exception {
		String[] split = message.split("\n");

		if(split.length > 0) {
			poster = split[1];

			body = "";
			for(int i = 3; i < split.length; i++) {
				body = body + split[i] + "\n";
				body = body.replace("\\n", "\n");
			}
		} else {
			throw new Exception();
		}
	}

	public String getBody() {
		return body;
	}

	public String getPoster() {
		return poster;
	}

	public Boolean hasPoster() {
		return !poster.equals("");
	}

	public Boolean hasBody() {
		return !body.equals("");
	}

	@Override
	public String toString() {
		return poster + "\n" + body;
	}
}
