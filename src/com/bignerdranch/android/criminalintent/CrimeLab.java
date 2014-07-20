package com.bignerdranch.android.criminalintent;

import java.util.*;
import android.content.*;

public class CrimeLab
{
	private ArrayList<Crime> mCrimes;
	
	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	
	private CrimeLab(Context appContext)
	{
		mAppContext = appContext;
		mCrimes = new ArrayList<Crime>();
		for (int x = 0; x < 100; x++)
		{
			Crime c = new Crime();
			c.setTitle("Crime #" + x);
			c.setSolved(x % 2 == 0);
			mCrimes.add(c);
		}
	}
	
	public static CrimeLab get(Context c)
	{
		if (sCrimeLab == null)
		{
			sCrimeLab = new CrimeLab(c.getApplicationContext());
		}
		
		return sCrimeLab;
	}
	
	public ArrayList<Crime> getCrimes()
	{
		return mCrimes;
	}
	
	public Crime getCrime(UUID id)
	{
		for (Crime c : mCrimes)
		{
			if (c.getId().equals(id))
			{
				return c;
			}
		}
		return null;
	}
}
