package model;



public class Record {
	private int place;
	private String nickname;
	private int score;
	private String time;

	public Record(int place, String nickname, int score, String time) {
		super();
		this.place = place;
		this.nickname = nickname;
		this.score = score;
		this.time = time;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
}
