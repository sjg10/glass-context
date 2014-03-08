/*
 * Main service that opens up the live card 'Context'.
 */

package com.coolrunnings.context;

//Glass Specific
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;

import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetManager;


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
		installFiles();
	} // onCreate

	public int onStartCommand(Intent intent, int flags, int startId) {
		// Where the magic happens
		mLiveCard = mTimelineManager.createLiveCard(LIVE_CARD_ID);

		Intent i = new Intent(this, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		return START_STICKY;
	}
	private void installFiles(){
		AssetManager assetManager = this.getAssets();
		String DATA_PATH=Environment
				.getExternalStorageDirectory().toString()+"/context/";
		String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };

		for (String path : paths) {
		File dir = new File(path);
		if (!dir.exists()) {
		if (!dir.mkdirs()) {
		Log.v("Context.OpenContext", "ERROR: Creation of directory " + path + " on sdcard failed");
		return;
		} else {
		Log.v("Context.OpenContext", "Created directory " + path + " on sdcard");
		}
		}

		}

		// lang.traineddata file with the app (in assets folder)
		// You can get them at:
		// http://code.google.com/p/tesseract-ocr/downloads/list
		// This area needs work and optimization
		if (!(new File(DATA_PATH + "tessdata/eng.traineddata")).exists()) {
		try {
		InputStream in = assetManager.open("tessdata/eng.traineddata");
		//GZIPInputStream gin = new GZIPInputStream(in);
		OutputStream out = new FileOutputStream(DATA_PATH
		+ "tessdata/eng" + ".traineddata");

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		//while ((lenf = gin.read(buff)) > 0) {
		while ((len = in.read(buf)) > 0) {
		out.write(buf, 0, len);
		}
		in.close();
		//gin.close();
		out.close();

		Log.v("Context.OpenContext", "Copied traineddata");
		} catch (IOException e) {
		Log.e("Context.OpenContext", "Was unable to copy traineddata " + e.toString());
		}
		}
		Log.i("Context.OpenContext", "Files installed");
	}

	
}
