package com.uesugi.mumen.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Environment;

/**
 * 对原来的FileUtils类，做一些改动 基本上此类用于辅助照片相关
 * 
 * @author lizhenning
 * 
 */

public class FileUtils {

	// 取得SdCard目录
	public static String getSdCardPath() {
		String path = "";

		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);

		if (!sdCardExist) {
			return path;
		}

		path = Environment.getExternalStorageDirectory().getPath();
		if (!path.endsWith("/")) {
			path = path + "/";
		}

		// Log.e(TAG, "sdcardPath = " + path);
		return path;
	}

	public static String createFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");

		return dateFormat.format(date);
	}

	public static String createFileNameWithOffset(int offset) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");

		return dateFormat.format(date) + offset;
	}

	// 递归生成父目录
	public static boolean createParentPath(String path) {
		boolean ret = true;

		File f = new File(path);
		if (!f.exists()) {
			if (!createParentPath(f.getParent())) {
				ret = false;
			} else {
				ret = f.mkdir();
			}
		}

		return ret;
	}

	public static boolean savePhoto(Bitmap bit, File file) {
		boolean ret = false;

		try {
			FileOutputStream outStream = new FileOutputStream(file);
			bit.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.close();

			return true;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return ret;
	}

	public static Bitmap scaleBitmapFromFileWithCheck(String file, int width,
			int height) {

		Bitmap bm = scaleBitmapFromFile(file, width, height);

		int degree = readPictureDegree(file);
		bm = rotaingImageView(degree, bm);

		return bm;
	}

	public static Bitmap scaleBitmapFromFile(String file, int width, int height) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		Bitmap bm = BitmapFactory.decodeFile(file, opts);

		int pixels = width * height;
		opts.inSampleSize = FileUtils.computeSampleSize(opts, -1, width
				* height);
		opts.inJustDecodeBounds = false;
		opts.inDither = false;
		opts.inPurgeable = true;
		opts.inTempStorage = new byte[(pixels / 1024 + 1) * 1024];
		opts.inPreferredConfig = Bitmap.Config.RGB_565;

		File f = new File(file);
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			bm = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, opts);
		} catch (Exception e) {
			bm = null;
		} finally {
			if (fs != null) {
				try {
					fs.close();
					fs = null;
				} catch (IOException e) {
				}
			}
		}

		return bm;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		;
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	// 动态计算图片缩放比例。
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize = 0;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	// 计算原始图片尺寸。
	public static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double width = options.outWidth;
		double height = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(width * height / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(width / minSideLength),
				Math.floor(height / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 300) { // 循环判断如果压缩后图片是否大于300kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	public static Bitmap getRoundedCornerBitmap2(Bitmap bitmap, int r) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = bitmap.getWidth() / r;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	public static Bitmap drawableToBitamp(Drawable drawable) {
		Bitmap bitmap;
		int w = 100;
		int h = 100;
		System.out.println("Drawable转Bitmap");
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		bitmap = Bitmap.createBitmap(w, h, config);
		// 注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}
	public   static  Bitmap drawableToBitmap(Drawable drawable) {    
        
        Bitmap bitmap = Bitmap    
                        .createBitmap(    
                                        drawable.getIntrinsicWidth(),    
                                        drawable.getIntrinsicHeight(),    
                                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888    
                                                        : Bitmap.Config.RGB_565);    
        Canvas canvas =  new  Canvas(bitmap);    
         //canvas.setBitmap(bitmap);    
        drawable.setBounds( 0 ,  0 , drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());    
        drawable.draw(canvas);    
         return  bitmap;    
}
}
