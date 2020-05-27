package echosystem;


// Spring requires a POJ class for each message.  In a real solution,
// we should reuse the message class defined within the interface.
public class ReplyMessage {
	private String content;
	public ReplyMessage() {
	}
	public ReplyMessage( String content ) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
}