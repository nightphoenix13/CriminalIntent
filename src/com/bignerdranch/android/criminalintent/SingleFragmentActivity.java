package com.bignerdranch.android.criminalintent;

import android.os.*;
import android.support.v4.app.*;

public abstract class SingleFragmentActivity extends FragmentActivity
{
	protected abstract Fragment createFragment();
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null)
		{
			fragment = createFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}
}
