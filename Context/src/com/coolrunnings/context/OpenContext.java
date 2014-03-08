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
	public void onCreate() {
		super.onCreate();
		mTimelineManager = TimelineManager.from(this);
	} // onCreate
}
