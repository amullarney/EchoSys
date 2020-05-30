package echosystem;

public class UImodel {
	private static UImodel singleton;
	private String msg;
	private String reply;
	
	public static UImodel Singleton() {
		return singleton;
	}
	
	public UImodel() {
		singleton = this;
	}
	
	public void setMsg( String msg ) {
		this.msg = msg;
	}
	
	public void setReply( String reply ) {
		this.reply = reply;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public String getReply() {
		return reply;
	}
}