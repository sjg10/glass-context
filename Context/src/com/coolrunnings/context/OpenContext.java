/*
 * Main service that opens up the live card 'Context'.
 */



package com.coolrunnings.context;

//Glass Specific
import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;

import android.os.IBinder;
import android.app.Service;
import android.content.Intent;


public class OpenContext extends Service{
	
	// Name of our live card:
	private static final String LIVE_CARD_ID = "Context_Live_Card";
	
	// The time line manager for adding cards to the time line:
	private TimelineManager mTimelineManager;
	
	// Create the live card:
	@SuppressWarnings("unused")
	private LiveCard mLiveCard;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	} // IBinder
	
	@Override
	public void onCreate() {
		super.onCreate();
		mTimelineManager = TimelineManager.from(this);
		
	} // onCreate
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Where the magic happens
		mLiveCard = mTimelineManager.createLiveCard(LIVE_CARD_ID);

		Intent i = new Intent(this, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		return START_STICKY;
	}
}
