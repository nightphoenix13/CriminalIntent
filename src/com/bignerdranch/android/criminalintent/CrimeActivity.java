package com.bignerdranch.android.criminalintent;

import java.util.*;

import android.support.v4.app.*;

public class CrimeActivity extends SingleFragmentActivity
{

	@Override
	protected Fragment createFragment()
	{
		UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		
		return CrimeFragment.newInstance(crimeId);
	}

	

}
