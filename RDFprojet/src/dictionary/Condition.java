package dictionary;

public class Condition {
	private String p;
	private String o;
	
	public Condition(String p, String o) {
		super();
		this.p = p;
		this.o = o;
	}
	
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public String getO() {
		return o;
	}
	public void setO(String o) {
		this.o = o;
	}

	@Override
	public String toString() {
		return "Condition [p=" + p + ", o=" + o + "]";
	}	
	
	
}