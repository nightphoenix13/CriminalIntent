package com.bignerdranch.android.criminalintent;

import java.util.*;

public class Crime
{
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSolved;
	
	public Crime()
	{
		mId = UUID.randomUUID();
		mDate = new Date();
	}
	
	// get methods
	public UUID getId() // mId get method start
	{
		return mId;
	} // mId get method end
	
	public String getTitle() // mTitle get method start
	{
		return mTitle;
	} // mTitle get method end
	
	public Date getDate() // mDate get method start
	{
		return mDate;
	} // mDate get method end
	
	public boolean getSolved() // mSolved get method start
	{
		return mSolved;
	} // mSolved get method end
	
	// set methods
	public void setTitle(String title) // mTitle set method start
	{
		mTitle = title;
	} // mTitle set method end
	
	public void setDate(Date date) // mDate set method start
	{
		mDate = date;
	} // mDate set method end
	
	public void setSolved(boolean solved) // mSolved set method start
	{
		mSolved = solved;
	} // mSolved set method end
}
