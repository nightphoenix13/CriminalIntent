package com.bignerdranch.android.criminalintent;

import org.json.*;

public class Photo
{ // class start
	private static final String JSON_FILENAME = "filename";
	
	private String mFilename;
	
	public Photo(String filename) // String constructor
	{
		setFilename(filename);
	} // constructor end
	
	public Photo(JSONObject json) throws JSONException // JSONObject constructor
	{
		setFilename(json.getString(JSON_FILENAME));
	} // constructor end
	
	public JSONObject toJSON() throws JSONException // toJSON method start
	{
		JSONObject json = new JSONObject();
		json.put(JSON_FILENAME, mFilename);
		return json;
	} // toJSON method end
	
	public String getFilename() // mFilename get method start
	{
		return mFilename;
	} // mFilename get method end
	
	public void setFilename(String filename) // mFilename set method start
	{
		mFilename = filename;
	} // mFilename set method end
} // class end
