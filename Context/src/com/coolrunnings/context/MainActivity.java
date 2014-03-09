/*
 * Main Activity of the 'Context' live card.
 */


package com.coolrunnings.context;

//import java.io.File;

import java.io.File;
import java.io.IOException;

import com.google.android.glass.app.Card;
import com.google.android.glass.media.CameraManager;
//import com.google.android.glass.media.CameraManager;
//import com.google.android.glass.timeline.LiveCard;
//import com.google.glass.widget.SliderView;

import android.os.Bundle;
import android.os.FileObserver;
import android.provider.MediaStore;
//import android.os.FileObserver;
//import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
//import android.content.Intent;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
//import android.view.Menu;
import android.view.View;
import android.view.WindowManager;


public class MainActivity extends Activity{
	private CameraView cameraView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		takePicture();
		createCard("Loading...");
	}

private void takePicture() {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(intent, 1);
}
/*
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 1 && resultCode == RESULT_OK) {
        String picturePath = data.getStringExtra(
                CameraManager.EXTRA_PICTURE_FILE_PATH);
        createCard(picturePath);
        processPictureWhenReady(picturePath);
    }

    super.onActivityResult(requestCode, resultCode, data);
}

private void processPictureWhenReady(final String picturePath) {
    final File pictureFile = new File(picturePath);

    if (pictureFile.exists()) {
        // The picture is ready; process it.
    } else {
        // The file does not exist yet. Before starting the file observer, you
        // can update your UI to let the user know that the application is
        // waiting for the picture (for example, by displaying the thumbnail
        // image and a progress indicator).

        final File parentDirectory = pictureFile.getParentFile();
        FileObserver observer = new FileObserver(parentDirectory.getPath()) {
            // Protect against additional pending events after CLOSE_WRITE is
            // handled.
            private boolean isFileWritten;

            @Override
            public void onEvent(int event, final String path) {
                if (!isFileWritten) {
                    // For safety, make sure that the file that was created in
                    // the directory is actually the one that we're expecting.
                    File affectedFile = new File(parentDirectory, path);
                    isFileWritten = (event == FileObserver.CLOSE_WRITE
                            && affectedFile.equals(pictureFile));

                    if (isFileWritten) {
                        stopWatching();

                        // Now that the file is ready, recursively call
                        // processPictureWhenReady again (on the UI thread).
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String result=ImageProc.convertImageToString(path);
                                createCard(result);
                            }
                        });
                    }
                }
            }
        };
        observer.startWatching();
    }
}

	*/
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == 1 && resultCode == RESULT_OK) {
	        String picturePath = data.getStringExtra(CameraManager.EXTRA_PICTURE_FILE_PATH);
	        processPictureWhenReady(picturePath);
	    }
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	private void processPictureWhenReady(final String picturePath) {
	    final File pictureFile = new File(picturePath);

	    if (pictureFile.exists()) {
	        // The picture is ready; process it.
	    } else {
	        // The file does not exist yet. Before starting the file observer, you
	        // can update your UI to let the user know that the application is
	        // waiting for the picture (for example, by displaying the thumbnail
	        // image and a progress indicator).

	        final File parentDirectory = pictureFile.getParentFile();
	        FileObserver observer = new FileObserver(parentDirectory.getPath()) {
	            // Protect against additional pending events after CLOSE_WRITE is
	            // handled.
	            private boolean isFileWritten;

	            @Override
	            public void onEvent(int event, String path) {
	                if (!isFileWritten) {
	                    // For safety, make sure that the file that was created in
	                    // the directory is actually the one that we're expecting.
	                    File affectedFile = new File(parentDirectory, path);
	                    isFileWritten = (event == FileObserver.CLOSE_WRITE
	                            && affectedFile.equals(pictureFile));

	                    if (isFileWritten) {
	                        stopWatching();

	                        // Now that the file is ready, recursively call
	                        // processPictureWhenReady again (on the UI thread).
	                        runOnUiThread(new Runnable() {
	                            @Override
	                            public void run() {
	    	                        String newWord = ImageProc.convertImageToString(picturePath);
	    	                        createCard(newWord);
	                            }
	                        });
	                    }
	                }
	            }
	        };
	        observer.startWatching();
	    }
	    
	    
	}
	
		
		
	private void createCard(String wordToShow){
		Card card1 = new Card(this);
		card1.setText(wordToShow); // Main text area
		//card1.setFootnote("..or Ma'am"); // Footer
		View card1View = card1.toView();

		// Display the card we just created
		setContentView(card1View);
	}



}
