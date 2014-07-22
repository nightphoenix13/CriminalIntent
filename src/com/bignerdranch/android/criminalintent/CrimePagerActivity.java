package com.bignerdranch.android.criminalintent;

import java.util.*;

import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.*;

public class CrimePagerActivity extends FragmentActivity
{
	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mCrimes = CrimeLab.get(this).getCrimes();
		
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm)
		{
			@Override
			public int getCount()
			{
				return mCrimes.size();
			}
			
			@Override
			public Fragment getItem(int pos)
			{
				Crime crime = mCrimes.get(pos);
				return CrimeFragment.newInstance(crime.getId());
			}
		});
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{	
			@Override
			public void onPageSelected(int pos)
			{
				Crime crime = mCrimes.get(pos);
				if (crime.getTitle() != null)
				{
					setTitle(crime.getTitle());
				}
			}
			
			@Override
			public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {}
			
			@Override
			public void onPageScrollStateChanged(int state) {}
		});
		
		UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		for (int x = 0; x < mCrimes.size(); x++)
		{
			if (mCrimes.get(x).getId().equals(crimeId))
			{
				mViewPager.setCurrentItem(x);
				break;
			}
		}
	}
}
