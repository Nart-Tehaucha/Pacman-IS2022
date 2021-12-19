
package controllers;

import java.io.Serializable;
import java.util.Objects;

public class RecordWinner implements Serializable, Comparable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
    private int points;
	private double time;
	private boolean did_Earn_Trophy; //yes or no
	
	public RecordWinner(String userName, int pinots, double time, boolean did_Earn_Trophy) {
		super();
		this.userName = userName;
		this.points = pinots;
		this.time = time;
		this.did_Earn_Trophy = did_Earn_Trophy;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(did_Earn_Trophy, points, time, userName);
	}
 

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isDid_Earn_Trophy() {
		return did_Earn_Trophy;
	}
	public void setDid_Earn_Trophy(boolean did_Earn_Trophy) {
		this.did_Earn_Trophy = did_Earn_Trophy;
	}
	
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	
	@Override
	public String toString() {
		return "RecordWinner [userName=" + userName + ", points=" + points + ", time=" + time + ", did_Earn_Trophy="
				+ did_Earn_Trophy + "]";
	}

	@Override
	public int compareTo(Object o) {
		RecordWinner rw = (RecordWinner) o;
		if (this.points > rw.getPoints())
			return -1;
		if (this.points < rw.getPoints())
			return 1;
		else {
			if (this.time < rw.getTime())
				return -1;
			if (this.time < rw.getTime())
				return 1;
			return 0;
			}
		}

	

	
	
	}
    
    

	

