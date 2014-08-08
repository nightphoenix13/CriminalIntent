package com.bignerdranch.android.criminalintent;

import java.util.*;
import java.sql.Time;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.*;
import android.content.pm.*;
import android.hardware.Camera;
import android.os.*;
import android.support.v4.app.*;
import android.text.*;
import android.text.format.*;
import android.view.*;
import android.widget.*;
import android.widget.CompoundButton.*;

public class CrimeFragment extends Fragment // CrimeFragment class start
{
	public static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";
	
	private static final String DIALOG_DATE = "date";
	private static final String DIALOG_TIME = "time";
	private static final int REQUEST_DATE = 0;
	private static final int REQUEST_TIME = 1;
	
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private Button mTimeButton;
	private CheckBox mSolvedCheckBox;
	private ImageButton mPhotoButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) // onCreate method start
	{
		super.onCreate(savedInstanceState);
		UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
		
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
		
		setHasOptionsMenu(true);
	} // onCreate method end
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) // onCreateView method start
	{
		View v = inflater.inflate(R.layout.fragment_crime, parent, false);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			} // end if
		} // end if
		
		mTitleField = (EditText)v.findViewById(R.id.crime_title);
		mTitleField.setText(mCrime.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() // anonymous inner class start
		{
			public void onTextChanged(CharSequence c, int start, int before,
					int count) // onTextChanged method start
			{
				mCrime.setTitle(c.toString());
			} // onTextChanged method end
			
			public void beforeTextChanged(CharSequence c, int start, int before,
					int count) // beforeTextChanged method start
			{
				// do nothing
			} // beforeTextChanged method end
			
			public void afterTextChanged(Editable c) // afterTextChanged method start
			{
				// do nothing
			} // afterTextChanged method end
		}); // anonymous inner class end
		
		mDateButton = (Button)v.findViewById(R.id.crime_date);
		updateDate();
		mDateButton.setOnClickListener(new View.OnClickListener() // anonymous inner class start
		{
			@Override
			public void onClick(View v) // onClick method start
			{
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment.newInstance(
						mCrime.getDate());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
			} // onClick method end
		}); // anonymous inner class end
		
		mTimeButton = (Button)v.findViewById(R.id.crime_time);
		updateTime();
		mTimeButton.setOnClickListener(new View.OnClickListener() // anonymous inner class start
		{	
			@Override
			public void onClick(View v) // onClick method start
			{
				FragmentManager fm = getActivity().getSupportFragmentManager();
				TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getTime());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
				dialog.show(fm, DIALOG_TIME);
			} // onClick method end
		}); // anonymous inner class end
		
		mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
		mSolvedCheckBox.setChecked(mCrime.isSolved());
		mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() // anonymous inner class start
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) // onCheckedChanged method start
			{
				mCrime.setSolved(isChecked);
			} // onCheckedChanged method end
		}); // anonymous inner class end
		
		mPhotoButton = (ImageButton)v.findViewById(R.id.crime_imageButton);
		mPhotoButton.setOnClickListener(new View.OnClickListener() // anonymous inner class start 
		{
			@Override
			public void onClick(View v) // onClick method start
			{
				Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
				startActivity(i);
			} // onClick method end
		}); // anonymous inner class end
		
		PackageManager pm = getActivity().getPackageManager();
		boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) ||
				pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT) ||
				(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && 
				Camera.getNumberOfCameras() > 0);
		if (!hasACamera)
		{
			mPhotoButton.setEnabled(false);
		} // end if
		
		return v;
	} // onCreateView method end
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) // onActivityResult method start
	{
		if (resultCode != Activity.RESULT_OK)
		{
			return;
		} // end if
		if (requestCode == REQUEST_DATE)
		{
			Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mCrime.setDate(date);
			updateDate();
		} // end if
		if (requestCode == REQUEST_TIME)
		{
			Time time = (Time)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
			mCrime.cloneTime(time);
			updateTime();
		} // end if
	} // onActivytResult method end
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) // onOptionsItemSelected method start 
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				NavUtils.navigateUpFromSameTask(getActivity());
			} // end if
			return true;
		default:
			return super.onOptionsItemSelected(item);
		} // end switch
	} // onOptionsItemSelected method end
	
	@Override
	public void onPause() // onPause method start
	{
		super.onPause();
		CrimeLab.get(getActivity()).saveCrimes();
	} // onPause method end
	
	private void updateDate() // updateDate method start
	{
		mDateButton.setText(DateFormat.format("EEEE, MMMM dd, yyyy", mCrime.getDate()));
	} // updateDate method end
	
	private void updateTime() // updateTime method start
	{
		mTimeButton.setText(mCrime.getTime().toString());
	} // updateTime method end
	
	public static CrimeFragment newInstance(UUID crimeId) // newInstance method start
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);
		
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);
		
		return fragment;
	} // newInstance method end
} // class end
