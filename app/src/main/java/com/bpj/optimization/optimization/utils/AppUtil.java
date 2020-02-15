package com.bpj.optimization.optimization.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class AppUtil {

	public static Uri getUriForFile(Context context, File strFile){
		Uri uri;
		if (Build.VERSION.SDK_INT < 24){
			uri = Uri.fromFile(strFile);
		}else{
			uri = FileProvider.getUriForFile(context, context.getPackageName()+".fileProvider", strFile);
		}
		return uri;
	}

	/**
	 * 判断是否有某个权限
	 */
	public static boolean hasPermission(Context context, String permission){
		if (Build.VERSION.SDK_INT >= 23) {
			if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
				return false;
			}
		}
		return true;
	}

	/**
	 * 弹出对话框请求权限
	 */
	public static void requestPermissions(Activity activity, String[] permissions, int requestCode){
		if (Build.VERSION.SDK_INT >= 23) {
			ActivityCompat.requestPermissions(activity, permissions, requestCode);
		}
	}

	/**
	 * 返回缺失的权限
	 * @return 返回缺少的权限，null 意味着没有缺少权限
	 */
	public static String[] getDeniedPermissions(Context context, String[] permissions){
		if (Build.VERSION.SDK_INT >= 23) {
			ArrayList<String> deniedPermissionList = new ArrayList<>();
			for(String permission : permissions){
//				if(permission.equals(Manifest.permission.CAMERA)){
//					if(!hasCameraPermission()){
//						deniedPermissionList.add(permission);
//					}
//				}else if(permission.equals(Manifest.permission.RECORD_AUDIO) || permission.equals(Manifest.permission.MODIFY_AUDIO_SETTINGS)){
//					if(!hasRecordPermission()){
//						deniedPermissionList.add(permission);
//					}
//				}else
					if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
					deniedPermissionList.add(permission);
				}
			}
			int size = deniedPermissionList.size();
			if(size > 0){
				return deniedPermissionList.toArray(new String[deniedPermissionList.size()]);
			}
		}
		return null;
	}

	public static String getPermissionDes(String[] permissions){
		Set<String> set = new LinkedHashSet<>();
		for(int i = 0; i < permissions.length; i++){
			if(!TextUtils.isEmpty(permissions[i])){
				if(permissions[i].equals(Manifest.permission.READ_PHONE_STATE)){
					set.add("读取手机状态");
				}if(permissions[i].equals(Manifest.permission.SEND_SMS)){
					set.add("发送短信");
				}else if(permissions[i].equals(Manifest.permission.RECORD_AUDIO)) {
					set.add("录音");
				}else if(permissions[i].equals(Manifest.permission.MODIFY_AUDIO_SETTINGS)) {
					set.add("录音");
				} else if(permissions[i].equals(Manifest.permission.CAMERA)) {
					set.add("拍照");
				}else if(permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
					set.add("写入文件");
				}else if(permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
					set.add("读取文件");
				}else if(permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
					set.add("读取位置信息");
				}else if(permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
					set.add("读取位置信息");
				}
			}
		}
		if(set != null && !set.isEmpty()){
			StringBuilder res = new StringBuilder();
			Iterator<String> it = set.iterator();
			while(it.hasNext())
				res.append(it.next()).append("、");

			return res.substring(0, res.length() - 1).toString();
		}
		return "";
	}

	/**
	 * 判断是是否有拍照权限，适配Vivo、Oppo 等手机
	 */
	public static boolean hasCameraPermission() {
		Field fieldPassword = null;
		try {
			Camera camera = Camera.open();
			fieldPassword = camera.getClass().getDeclaredField("mHasPermission");
			fieldPassword.setAccessible(true);
			Boolean result = (Boolean) fieldPassword.get(camera);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	/**
	 * 判断是是否有录音权限，适配Vivo、Oppo等手机
	 */
	public static boolean hasRecordPermission() {
		// 音频获取源
		int audioSource = MediaRecorder.AudioSource.MIC;
		// 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
		int sampleRateInHz = 44100;
		// 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
		int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
		// 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
		int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
		// 缓冲区字节大小
		int bufferSizeInBytes = 0;

		bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
		AudioRecord audioRecord = new AudioRecord(audioSource, sampleRateInHz,
				channelConfig, audioFormat, bufferSizeInBytes);
		//开始录制音频
		try {
			// 防止某些手机崩溃，例如联想
			audioRecord.startRecording();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		/**
		 * 根据开始录音判断是否有录音权限
		 */
		if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
			return false;
		}
		audioRecord.stop();
		audioRecord.release();
		audioRecord = null;

		return true;
	}

}
