package robot.util;

/**
 * @author Mitchell
 * 
 * A timer that can be used to wait for a given time asynchronously
 *
 */
public class AsyncTimer {
	private long nextTime = -1;

	/**
	 * wait a specified time from the current system time
	 * @param time
	 */
	public void startWait(long time){
		nextTime = System.currentTimeMillis()+time;
	}

	/**
	 * Wait a specified time from the last time the timer was set to expire
	 * if the timer has not yet been set then the wait period will end the given time from the current system time
	 * @param time
	 */
	public void startWaitFromPrevious(long time){
		if(nextTime==-1)
			startWait(time);
		else
			nextTime = nextTime+time;
	}
	
	/**
	 * @return true if the timer has reached the end of the wait period
	 */
	public boolean isComplete(){
		return System.currentTimeMillis()>=nextTime;
	}
	
	/**
	 * Wait for the timer to reach the end of the wait period
	 * Will return true immediatly if the timer has not yet been set
	 * @return true to confirm that the timer has reached the end of the period
	 */
	public boolean waitComplete(){
		if(nextTime==-1)
			return true;
		long timeToWait = nextTime-System.currentTimeMillis();
		if(timeToWait<=0)
			return true;
		RobotUtil.sleep(timeToWait);
		return isComplete();
	}
}
