package echosystem;


// Spring requires a POJ class for each message.  In a real solution,
// we should reuse the message class defined within the interface.
public class RequestMessage {
	private String msg;
	public RequestMessage() {
	}
	public RequestMessage( String msg ) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg( String msg ) {
		this.msg = msg;
	}
}