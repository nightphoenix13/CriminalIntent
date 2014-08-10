package com.bignerdranch.android.criminalintent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.*;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.Button;

public class CrimeCameraFragment extends Fragment // class start
{
	private static final String TAG = "CrimeCameraFragment";
	
	public static final String EXTRA_PHOTO_FILENAME = "com.bignerdranch.android.criminalintent.photo_filename";
	
	private Camera mCamera;
	private SurfaceView mSurfaceView;
	private View mProgressContainer;
	
	private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() // anonymous inner class start
	{
		@Override
		public void onShutter() // onShutter method start
		{
			mProgressContainer.setVisibility(View.VISIBLE);
		} // onShutter method end
	}; // anonymous inner class end
	
	private Camera.PictureCallback mJpegCallback = new Camera.PictureCallback() // anonymous inner class start
	{
		@Override
		public void onPictureTaken(byte[] data, Camera camera) // onPictureTaken method start
		{
			String filename = UUID.randomUUID().toString() + ".jpg";
			FileOutputStream os = null;
			boolean success = true;
			
			try
			{
				os = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
				os.write(data);
			} // end try
			catch (Exception e)
			{
				Log.e(TAG, "Error writing to file ", e);
				success = false;
			} // end catch
			finally
			{
				try
				{
					if (os != null)
					{
						os.close();
					} // end if
				} // end try
				catch (Exception e)
				{
					Log.e(TAG, "Error closing file ", e);
					success = false;
				} // end catch
			} // end finally
			
			if (success)
			{
				Intent i = new Intent();
				i.putExtra(EXTRA_PHOTO_FILENAME, filename);
				getActivity().setResult(Activity.RESULT_OK, i);
			} // end if
			else
			{
				getActivity().setResult(Activity.RESULT_CANCELED);
			} // end else
			
			getActivity().finish();
		} // onPictureTaken method end
	}; // anonymous inner class end
	
	@Override
	@SuppressWarnings("deprecation")
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) // onCreateView method start
	{
		View v = inflater.inflate(R.layout.fragment_crime_camera, parent, false);
		
		mProgressContainer = v.findViewById(R.id.crime_camera_progressContainer);
		mProgressContainer.setVisibility(View.INVISIBLE);
		
		Button takePictureButton = (Button)v.findViewById(R.id.crime_camera_takePictureButton);
		takePictureButton.setOnClickListener(new View.OnClickListener() // anonymous inner class start
		{	
			@Override
			public void onClick(View v) // onClick method start
			{
				if (mCamera != null)
				{
					mCamera.takePicture(mShutterCallback, null, mJpegCallback);
				} // end if
			} // onClick method end
		}); // anonymous inner class end
		
		mSurfaceView = (SurfaceView)v.findViewById(R.id.crime_camera_surfaceView);
		SurfaceHolder holder = mSurfaceView.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		holder.addCallback(new SurfaceHolder.Callback() // anonymous inner class start
		{
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) // surfaceDestroyed method start
			{
				if (mCamera != null)
				{
					mCamera.stopPreview();
				} // end if
			} // surfaceDestroyed method end
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) // surfaceCreated method start
			{
				try
				{
					if (mCamera != null)
					{
						mCamera.setPreviewDisplay(holder);
					} // end if
				} // end try
				catch (IOException exception)
				{
					Log.e(TAG, "Error setting up preview display", exception);
				} // end catch
			} // surfaceCreated method end
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) // surfaceChanged method start
			{
				if (mCamera == null)
				{
					return;
				} // end if
				
				Camera.Parameters parameters = mCamera.getParameters();
				Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes(), w, h);
				parameters.setPreviewSize(s.width, s.height);
				s = getBestSupportedSize(parameters.getSupportedPictureSizes(), w, h);
				parameters.setPictureSize(s.width, s.height);
				mCamera.setParameters(parameters);
				try
				{
					mCamera.startPreview();
				} // end try
				catch (Exception e)
				{
					Log.e(TAG, "Could not start preview", e);
					mCamera.release();
					mCamera = null;
				} // end catch
			} // surfaceChanged method end
		}); // anonymous inner class end
		
		return v;
	} // onCreateView method end
	
	@TargetApi(9)
	@Override
	public void onResume() // onResume method start
	{
		super.onResume();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
		{
			mCamera = Camera.open(0);
		} // end if
		else
		{
			mCamera = Camera.open();
		} // end else
	} // onResume method end
	
	@Override
	public void onPause() // onPause method start
	{
		super.onPause();
		
		if (mCamera != null)
		{
			mCamera.release();
			mCamera = null;
		} // end if
	} // onPause method end
	
	private Size getBestSupportedSize(List<Size> sizes, int width, int height) // getBestSupportedSize method start
	{
		Size bestSize = sizes.get(0);
		int largestArea = bestSize.width * bestSize.height;
		for (Size s : sizes)
		{
			int area = s.width * s.height;
			if (area > largestArea)
			{
				bestSize = s;
				largestArea = area;
			} // end if
		} // end for
		
		return bestSize;
	} // getBestSupportedSize method end
} // class end
