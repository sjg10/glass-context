package com.coolrunnings.context;

import magick.MagickException;
import magick.MagickImage;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImageProc{
	public static final String lang = "eng";
	private static final String TAG = "context.ImageProc";
	public static String DATA_PATH;
	
	public static String convertImageToString(byte[] data){
			//DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/context/";
			//BitmapFactory.Options options = new BitmapFactory.Options();
			//Log.i(TAG,path);
			//options.inSampleSize=4;
			Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
			bmp=tidyImage(bmp);
			return textFromTidyImage(bmp);
	}

	public static Bitmap tidyImage(Bitmap bmp){
		/*try {
			MagickImage mbmp = MagickBitmap.fromBitmap(bmp);
			mbmp.thresholdImage(75);//TODO: Check threshold value
			bmp=MagickBitmap.ToBitmap(mbmp);
		} catch (MagickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return bmp;
	}

	private static String textFromTidyImage(Bitmap bmp){
			bmp = bmp.copy(Bitmap.Config.ARGB_8888, true); //required for tess
			Log.v(TAG, "Before baseApi");
			TessBaseAPI baseApi = new TessBaseAPI();
			baseApi.init(DATA_PATH, lang);
			baseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "abcdefghijklmnopqrstuvwxyz");
			baseApi.setImage(bmp);
			String out=baseApi.getUTF8Text();
			baseApi.clear();
			Log.e(TAG,out);
			return out;
	}}
