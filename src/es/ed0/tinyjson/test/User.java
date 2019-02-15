package es.ed0.tinyjson.test;

public class User {

	private int tier;
	private String userId, email, name, firebaseToken, password;
	private boolean dirtyToken;
	
	
	public User(String userId, String email, String name, String password, String firebaseToken, int tier, boolean dirtyToken) {
		this.userId = userId;
		this.tier = tier;
		this.email = email;
		this.name = name;
		this.firebaseToken = firebaseToken;
		this.password = password;
		this.dirtyToken = dirtyToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirebaseToken() {
		return firebaseToken;
	}

	public void setFirebaseToken(String firebaseToken) {
		this.firebaseToken = firebaseToken;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDirtyToken() {
		return dirtyToken;
	}

	public void setDirtyToken(boolean dirtyToken) {
		this.dirtyToken = dirtyToken;
	}
	
	
}
