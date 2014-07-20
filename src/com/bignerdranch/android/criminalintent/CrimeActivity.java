package com.bignerdranch.android.criminalintent;

import android.support.v7.app.*;
import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import android.os.*;

public class CrimeActivity extends SingleFragmentActivity
{

	@Override
	protected Fragment createFragment()
	{
		return new CrimeFragment();
	}

	

}
