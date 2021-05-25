/**
 * Created by Ed0 in 14 feb. 2019
 */

public class Pojo {
	
	public String name = "pojo class";
	public Integer integer = 4;
	private Float f = 8.9f;
	private boolean xd = false;
	
	public Route[] routes = null;
	
	private char[] chars;
	
	public Pojo() {
		chars = new char[] {'p', 'o', 'j', 'o'};
	}
	
	
	public float getF() {
		return f;
	}
	
	public void setF(Float f) {
		this.f = f;
	}


	public char[] getChars() {
		return chars;
	}


	public void setChars(char[] chars) {
		this.chars = chars;
	}
	
	public boolean isXd() { return xd; }
	public boolean setXd(boolean xd) { return this.xd = xd; }
	
	
	

}
