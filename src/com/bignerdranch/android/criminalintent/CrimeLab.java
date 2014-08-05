package com.bignerdranch.android.criminalintent;

import java.util.*;

import android.content.*;
import android.util.Log;

public class CrimeLab // class start
{
	private static final String TAG = "CrimeLab";
	private static final String FILENAME = "crimes.json";
	
	private ArrayList<Crime> mCrimes;
	private CriminalIntentJSONSerializer mSerializer;
	
	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	
	private CrimeLab(Context appContext) // constructor start
	{
		mAppContext = appContext;
		mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);
		
		try
		{
			mCrimes = mSerializer.loadCrimes();
		} // end try
		catch (Exception e)
		{
			mCrimes = new ArrayList<Crime>();
			Log.e(TAG, "Error loading crimes: ", e);
		} // end catch
	} // constructor end
	
	public static CrimeLab get(Context c) // get method start
	{
		if (sCrimeLab == null)
		{
			sCrimeLab = new CrimeLab(c.getApplicationContext());
		} // end if
		
		return sCrimeLab;
	} // get method end
	
	public void addCrime(Crime c) // addCrime method start
	{
		mCrimes.add(c);
	} // addCrime method end
	
	public boolean saveCrimes() // saveCrimes method start
	{
		try
		{
			mSerializer.saveCrimes(mCrimes);
			Log.d(TAG, "crimes saved to file");
			return true;
		} // end try
		catch (Exception e)
		{
			Log.e(TAG, "Error saving crimes: ", e);
			return false;
		} // end catch
	} // saveCrimes method end
	
	public ArrayList<Crime> getCrimes() // getCrimes method start
	{
		return mCrimes;
	} // getCrimes method end
	
	public Crime getCrime(UUID id) // getCrime method start
	{
		for (Crime c : mCrimes)
		{
			if (c.getId().equals(id))
			{
				return c;
			} // end if
		} // end for
		return null;
	} // getCrime method end
} // class end
