package com.bignerdranch.android.criminalintent;

import java.io.IOException;
import java.util.List;

import android.annotation.TargetApi;
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
	
	private Camera mCamera;
	private SurfaceView mSurfaceView;
	
	@Override
	@SuppressWarnings("depreciation")
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) // onCreateView method start
	{
		View v = inflater.inflate(R.layout.fragment_crime_camera, parent, false);
		
		Button takePictureButton = (Button)v.findViewById(R.id.crime_camera_takePictureButton);
		takePictureButton.setOnClickListener(new View.OnClickListener() // anonymous inner class start
		{	
			@Override
			public void onClick(View v) // onClick method start
			{
				getActivity().finish();
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
