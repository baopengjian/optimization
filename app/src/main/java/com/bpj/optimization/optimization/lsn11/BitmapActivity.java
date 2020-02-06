package com.bpj.optimization.optimization.lsn11;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bpj.optimization.optimization.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Ray on 2020-1-27.
 */
public class BitmapActivity extends AppCompatActivity {

    private File imageFile;
    private File sdFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmap_activity);

        sdFile = Environment.getExternalStorageDirectory();
        imageFile = new File(sdFile, "Chrysanthemum2.jpg");
    }


    /**
     * 1.质量压缩
     * 原理：通过算法抠掉(同化)了图片中的一些某个些点附近相近的像素，达到降低质量介绍文件大小的目的。
     * 减小了图片质量
     * 注意：它其实只能实现对file的影响，对加载这个图片出来的bitmap内存是无法节省的，还是那么大。
     * 因为bitmap在内存中的大小是按照像素计算的，也就是width*height，对于质量压缩，并不会改变图片的真实的像素（像素大小不会变）。
     * <p>
     * 使用场景：
     * 将图片压缩后保存到本地，或者将图片上传到服务器。根据实际需求来。
     */
    // BitmapFactory.decodeFile(pathName)
    // BitmapFactory.decodeResource(res,id )
    //BitmapFactory.decodeStream(is)
    public void qualitCompress(View v) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        //压缩图片
        compressImageToFile(bitmap, new File(sdFile, "qualityCompress.jpeg"));
    }

    private void compressImageToFile(Bitmap bitmap, File file) {
        //0 ~ 100
        int quality = 50;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality,bao );
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bao.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sizeCompress(View v) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        //压缩图片
        compressBitmapToFileBySize(bitmap, new File(sdFile, "sizeCompress.jpeg"));
    }


    /**
     * 2.尺寸压缩
     * 通过减少单位尺寸的像素值，正真意义上的降低像素。1020*8880--
     * 使用场景：缓存缩略图的时候（头像处理）
     *
     * @param bmp
     * @param file
     */
    public static void compressBitmapToFileBySize(Bitmap bmp, File file) {
        //压缩尺寸倍数，值越大，图片的尺寸就越小
        int ratio = 4;

        Bitmap result = Bitmap.createBitmap(bmp.getWidth() / ratio, bmp.getHeight() / ratio, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        RectF rect = new RectF(0,0,bmp.getWidth()/ratio,bmp.getHeight()/ratio);
        canvas.drawBitmap(bmp,null ,rect ,null );

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        result.compress(Bitmap.CompressFormat.JPEG,100 ,bao );

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bao.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compressBitmap(View v) {
        compressBitmap(imageFile.getAbsolutePath(),  new File(sdFile, "CompressBitmap.jpeg"));
    }


    /**
     * 3. 设置图片的采样率，降低图片像素
     * @param filePath
     * @param file
     */
    public static void compressBitmap(String filePath, File file){
        // 数值越高，图片像素越低
        int inSampleSize = 8;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
//	        options.inJustDecodeBounds = true;//为true的时候不会真正加载图片，而是得到图片的宽高信息。
        //采样率
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 ,baos);
        try {
            if(file.exists())
            {
                file.delete();
            }
            else {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
