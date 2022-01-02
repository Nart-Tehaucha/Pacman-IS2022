package models;

import java.io.Serializable;

public class Player implements Serializable{

	private String nickname; //player nickname
	private String password; //player password
//--------------------------------------Constructor-------------------------------------------------------------------//	
	public Player(String nickname, String password) {
		super();
		this.nickname = nickname;
		this.password = password;
	}
//-----------------------------------------------------------------------------------------------------------------------//
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
