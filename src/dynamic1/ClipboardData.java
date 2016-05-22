package dynamic1;

public class ClipboardData {

	private String text = new String ();

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	private String user = new String ();

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	private long timeout;

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public ClipboardData () {}
	
	public ClipboardData(String text, String user, long timeout) {
		super();
		this.text = text;
		this.user = user;
		this.timeout = timeout;
	}

	public ClipboardData (ClipboardData source) {
		this(source.getText(), source.getUser(), source.getTimeout());
	}

}
