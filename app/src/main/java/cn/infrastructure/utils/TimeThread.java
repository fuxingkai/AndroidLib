package cn.infrastructure.utils;

import android.os.Handler;

public class TimeThread extends Thread {
	public static final int MSG_TIME_TRIGGER = 10000;
	public static final int MSG_TIME_DONE = 10001;

	private volatile boolean isRunning = false;

	private long beginTime;
	private long overTime; // overTime=-1 never time over
	private long sleepTime;
	private Handler handler;

	public TimeThread(Handler handler, long beginTime, long overTime,
			long sleepTime) {
		this.handler = handler;
		this.beginTime = beginTime;
		this.overTime = overTime;
		this.sleepTime = sleepTime;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		isRunning = true;
		boolean isNotTimeOver = (overTime == -1) ? true : (System
				.currentTimeMillis() - beginTime <= overTime);
		while (isNotTimeOver && isRunning) {
			isNotTimeOver = (overTime == -1) ? true : (System
					.currentTimeMillis() - beginTime <= overTime);
			try {
				sleep(sleepTime);
				handler.sendEmptyMessage(MSG_TIME_TRIGGER);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		handler.sendEmptyMessage(MSG_TIME_DONE);
		isRunning = false;
	}

	public Boolean getIsRunning() {
		return isRunning;
	}

	public void stopRunning() {
		this.isRunning = false;
	}

	public synchronized void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}
}
