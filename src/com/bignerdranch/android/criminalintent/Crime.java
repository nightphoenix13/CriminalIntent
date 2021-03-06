package com.bignerdranch.android.criminalintent;

import java.sql.*;
import java.util.Date;
import java.util.*;

import org.json.*;

public class Crime
{
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private Time mTime;
	private boolean mSolved;
	private Photo mPhoto;
	
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_SOLVED = "solved";
	private static final String JSON_DATE = "date";
	private static final String JSON_PHOTO = "photo";
	
	public Crime()
	{
		mId = UUID.randomUUID();
		mDate = new Date();
		mTime = new Time(mDate.getTime());
	}
	
	public Crime(JSONObject json) throws JSONException
	{
		mId = UUID.fromString(json.getString(JSON_ID));
		if (json.has(JSON_TITLE))
		{
			mTitle = json.getString(JSON_TITLE);
		} // end if
		mSolved = json.getBoolean(JSON_SOLVED);
		mDate = new Date(json.getLong(JSON_DATE));
		if (json.has(JSON_PHOTO))
		{
			mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
		} // end if
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
	
	public Photo getPhoto() // mPhoto get method start
	{
		return mPhoto;
	} // mPhoto get method end
	
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
	
	public void setPhoto(Photo p) // mPhoto set method start
	{
		mPhoto = p;
	} // mPhoto set method end
	
	public JSONObject toJSON() throws JSONException
	{
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_SOLVED, mSolved);
		json.put(JSON_DATE, mDate.getTime());
		if (mPhoto != null)
		{
			json.put(JSON_PHOTO, mPhoto.toJSON());
		} // end if
		return json;
	}
	
	@Override
	public String toString()
	{
		return mTitle;
	}
}
