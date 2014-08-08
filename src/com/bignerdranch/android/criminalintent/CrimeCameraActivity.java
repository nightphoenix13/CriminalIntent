package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

public class CrimeCameraActivity extends SingleFragmentActivity // class start
{
	@Override
	public void onCreate(Bundle savedInstanceState) // onCreate method start
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		super.onCreate(savedInstanceState);
	} // onCreate method end

	@Override
	protected Fragment createFragment() // createFragment method start
	{
		return new CrimeCameraFragment();
	} // createFragment method end
} // class end
