package com.bignerdranch.android.criminalintent;

import java.io.*;
import java.util.ArrayList;

import org.json.*;

import android.content.Context;

public class CriminalIntentJSONSerializer
{
	private Context mContext;
	private String mFilename;
	
	public CriminalIntentJSONSerializer(Context c, String f)
	{
		mContext = c;
		mFilename = f;
	}
	
	public ArrayList<Crime> loadCrimes() throws IOException, JSONException
	{
		ArrayList<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader = null;
		try
		{
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				jsonString.append(line);
			} // end while
			
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
				.nextValue();
			
			for (int x = 0; x < array.length(); x++)
			{
				crimes.add(new Crime(array.getJSONObject(x)));
			} // end for
		} // end try
		catch (FileNotFoundException e)
		{
			// ignore
		} // end catch
		finally
		{
			if (reader != null)
			{
				reader.close();
			} // end if
		} // end finally
		
		return crimes;
	}
	
	public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException
	{
		JSONArray array = new JSONArray();
		for (Crime c : crimes)
		{
			array.put(c.toJSON());
		} // end for
		
		Writer writer = null;
		try
		{
			OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} // end try
		finally
		{
			if (writer != null)
			{
				writer.close();
			} // end if
		} // end finally
	}
}
