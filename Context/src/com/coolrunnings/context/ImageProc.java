package com.coolrunnings.context;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImageProc{
	public static final String lang = "eng";
	private static final String TAG = "context.ImageProc";
	public static String DATA_PATH;
	
	public static String convertImageToString(String path){
			DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/context/";
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 4;
			Bitmap bmp = BitmapFactory.decodeFile(path, options);
			bmp=tidyImage(bmp);
			return textFromTidyImage(bmp);
	}

	public static Bitmap tidyImage(Bitmap bmp){
		//TODO: Use an imageproc package to run through.
		return bmp;
	}

	private static String textFromTidyImage(Bitmap bmp){
			bmp = bmp.copy(Bitmap.Config.ARGB_8888, true); //required for tess
			Log.v(TAG, "Before baseApi");
			TessBaseAPI baseApi = new TessBaseAPI();
			baseApi.init(DATA_PATH, lang);
			baseApi.setImage(bmp);
			return baseApi.getUTF8Text();
			//TODO: Ensure only a single word passed
	}
}