package echosystem;


// Spring requires a POJ class for each message.  In a real solution,
// we should reuse the message class defined within the interface.
public class ReplyMessage {
	private String msg;
	public ReplyMessage() {
	}
	public ReplyMessage( String msg ) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg( String msg ) {
		this.msg = msg;
	}
}