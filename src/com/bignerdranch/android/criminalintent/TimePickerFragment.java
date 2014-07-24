package com.bignerdranch.android.criminalintent;

import java.sql.*;
import java.util.Calendar;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.*;
import android.support.v4.app.DialogFragment;
import android.view.*;
import android.widget.*;
import android.widget.TimePicker.*;

public class TimePickerFragment extends DialogFragment
{
	public static final String EXTRA_TIME = "com.bignerdranch.android.criminalintent.time";
	
	private Time mTime;
	
	public static TimePickerFragment newInstance(Time time)
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_TIME, time);
		
		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	private void sendResult(int resultCode)
	{
		if (getTargetFragment() == null)
		{
			return;
		}
		
		Intent i = new Intent();
		i.putExtra(EXTRA_TIME, mTime);
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		mTime = (Time)getArguments().getSerializable(EXTRA_TIME);
		
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(mTime);
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
		
		TimePicker timePicker = (TimePicker)v.findViewById(R.id.dialog_time_timePicker);
		timePicker.setIs24HourView(false);
		timePicker.setCurrentHour(hours);
		timePicker.setCurrentMinute(minutes);
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener()
		{
			@Override
			public void onTimeChanged(TimePicker view, int hour, int minute)
			{
				calendar.set(year, month, day, hour, minute);
				mTime = new Time(calendar.getTimeInMillis());
				getArguments().putSerializable(EXTRA_TIME, mTime);
			}
		});
		
		return new AlertDialog.Builder(getActivity())
			.setView(v)
			.setTitle(R.string.time_picker_title)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
			{	
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					sendResult(Activity.RESULT_OK);
				}
			})
			.create();
	}
}
