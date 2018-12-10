package es.ed0.jsonlib;

public class JSONReader {
	
	
	private static class Token {
		
		public static enum TokenType {
			asd, ads;
		}
		
		private String text;
		
		private TokenType type;
		
		
	}
	
	private String json;
	
	
	public JSONReader(String json) {
		this.json = json;
		
	}
	
	

}
