package com.bignerdranch.android.criminalintent;

import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class Crime
{
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private Time mTime;
	private boolean mSolved;
	
	public Crime()
	{
		mId = UUID.randomUUID();
		mDate = new Date();
		mTime = new Time(mDate.getTime());
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
	
	public Time getTime() // mTime get method start
	{
		return mTime;
	} // mTime get method end
	
	public boolean isSolved() // mSolved get method start
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
		setTime(mDate.getTime());
	} // mDate set method end
	
	public void setTime(long time) // mTime set method start
	{
		mTime.setTime(time);
		mDate.setTime(mTime.getTime());
	}
	
	public void cloneTime(Time time) // set method using another Time object
	{
		mTime = time;
		mDate.setTime(mTime.getTime());
	}
	
	public void setSolved(boolean solved) // mSolved set method start
	{
		mSolved = solved;
	} // mSolved set method end
	
	@Override
	public String toString()
	{
		return mTitle;
	}
}
