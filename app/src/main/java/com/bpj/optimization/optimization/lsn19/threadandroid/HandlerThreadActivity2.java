package com.bpj.optimization.optimization.lsn19.threadandroid;

import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.bpj.optimization.optimization.R;

public class HandlerThreadActivity2 extends Activity implements Callback {

static final String TAG = "jason";
	
	Camera mCamera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	byte[] buffers;
	
	HandlerThread mHandlerThread = new HandlerThread("my_handlerthread");
	Handler subHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handler_thread2);
		
		surfaceView = (SurfaceView) findViewById(R.id.surface_view);
		surfaceHolder = surfaceView.getHolder();
		
		surfaceHolder.addCallback(this);
	}
	
	class MyTask implements Runnable, PreviewCallback{

		@Override
		public void run() {
			//打开相机
			//子线程中打开
			Log.d("jason", Thread.currentThread().getName() + "_open");
			mCamera = Camera.open(CameraInfo.CAMERA_FACING_BACK);
			try {
				mCamera.setPreviewDisplay(surfaceHolder);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Camera.Parameters parameters = mCamera.getParameters();
			//设置相机参数
			parameters.setPreviewSize(480, 320); //预览画面宽高
			mCamera.setParameters(parameters);
			//获取预览图像数据
			buffers = new byte[480 * 320 * 4];
			mCamera.addCallbackBuffer(buffers);
			mCamera.setPreviewCallbackWithBuffer(this);
			mCamera.startPreview();
			
			Log.d(TAG, Thread.currentThread().getName()+ "_run");
		}

		@Override
		public void onPreviewFrame(byte[] data, Camera camera) {
			if(mCamera != null){
				mCamera.addCallbackBuffer(buffers);
				//编码
				Log.d(TAG, Thread.currentThread().getName()+ "_onPreviewFrame");
			}
		}
		
	}
	

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mHandlerThread.start();
		subHandler = new Handler(mHandlerThread.getLooper());
		subHandler.post(new MyTask());
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
}
