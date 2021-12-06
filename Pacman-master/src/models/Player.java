package models;

public class Player {

	private String nickname;
	private String password;
	
	public Player(String nickname, String password) {
		super();
		this.nickname = nickname;
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
