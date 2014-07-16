package com.bignerdranch.android.criminalintent;

import android.support.v7.app.*;
import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import android.os.*;

public class CrimeActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crime);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null)
		{
			fragment = new CrimeFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		} // end if
	}

	

}
