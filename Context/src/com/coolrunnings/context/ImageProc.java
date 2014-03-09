package com.coolrunnings.context;

import magick.MagickException;
import magick.MagickImage;
import magick.util.MagickBitmap;

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
			Log.i(TAG,path);
			options.inSampleSize=4;
			Bitmap bmp = BitmapFactory.decodeFile(path,options);
			Log.i(TAG, "created bit map");
			bmp=tidyImage(bmp);
			Log.i(TAG, "tidied up bit map");
			String returnString = textFromTidyImage(bmp);
			Log.i(TAG, "converted text from tidy image");
			return returnString;
	}

	public static Bitmap tidyImage(Bitmap bmp){
		/*try {
			MagickImage mbmp = MagickBitmap.fromBitmap(bmp);
			mbmp.thresholdImage(0.5);//TODO: Check threshold value
			bmp=MagickBitmap.ToBitmap(mbmp);
		} catch (MagickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return bmp;
	}

	private static String textFromTidyImage(Bitmap bmp){
			bmp = bmp.copy(Bitmap.Config.ARGB_8888, true); //required for tess
			Log.i(TAG, "Before baseApi");
			TessBaseAPI baseApi = new TessBaseAPI();
			Log.i(TAG, "data path is" + DATA_PATH);
			baseApi.init(DATA_PATH, lang);
			Log.i(TAG, "baseApi");
			baseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "abcdefghijklmnopqrstuvwxyz");
			baseApi.setImage(bmp);
			String out=baseApi.getUTF8Text();
			Log.i(TAG, "after baseApi");
			baseApi.clear();
			if( out.indexOf('\n')!=-1)
			out.substring(0, out.indexOf('\n'));
			Log.e(TAG,out);
			return out;
	}}
