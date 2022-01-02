package views;
import java.util.Timer;
import java.util.TimerTask;

public class Delete2 {
	private int second_passed = 0;
	
	Timer myTimer = new Timer();
	TimerTask task = new TimerTask() {
		public void run() {
			second_passed++;
			if(second_passed == 10) {
				//stop();
			}
			System.out.println("second passed:" + second_passed);
		}
	};
	
	public void startTimer() {
		
		myTimer.scheduleAtFixedRate(task,1000,1000);
	}
	
	public void stopTimer() {
		myTimer.cancel();
	}
	
	public static void main(String[] args) {
		Delete2 classi = new Delete2();
		classi.start();
		
	}


}
