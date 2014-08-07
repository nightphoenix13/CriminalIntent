package com.bignerdranch.android.criminalintent;

import java.util.*;

import android.annotation.TargetApi;
import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.*;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class CrimeListFragment extends ListFragment // class start
{
	private ArrayList<Crime> mCrimes;
	private boolean mSubtitleVisible;
	private static final String TAG = "CrimeListFragment";
	
	@Override
	public void onCreate(Bundle savedInstanceState) // onCreate method start
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);
		mSubtitleVisible = false;
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();
		
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);
	} // onCreate method end
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) // onCreateView method start
	{
		View v = super.onCreateView(inflater, parent, savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			if (mSubtitleVisible)
			{
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
			} // end if
		} // end if
		
		ListView listView = (ListView)v.findViewById(android.R.id.list);
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
		{
			registerForContextMenu(listView);
		} // end if
		else
		{
			listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE_MODAL);
			listView.setMultiChoiceModeListener(new MultiChoiceModeListener() // anonymous inner class start
			{
				public void onItemCheckedStateChanged(ActionMode mode, int position,
						long id, boolean checked) // onItemCheckedStateChanged method start
				{
					// not used
				} // onItemCheckedStateChanged method end
				
				public boolean onCreateActionMode(ActionMode mode, Menu menu) // onCreateActionMode method start
				{
					MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.crime_list_item_context, menu);
					return true;
				} // onCreateActionMode method end
				
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) // onActionItemClicked method start
				{
					switch (item.getItemId())
					{
					case R.id.menu_item_delete_crime:
						CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
						CrimeLab crimeLab = CrimeLab.get(getActivity());
						for (int x = adapter.getCount() - 1; x >= 0; x--)
						{
							if (getListView().isItemChecked(x))
							{
								crimeLab.deleteCrime(adapter.getItem(x));
							} // end if
						} // end for
						
						mode.finish();
						adapter.notifyDataSetChanged();
						return true;
					default:
						return false;
					} // end switch
				} // onActionItemClicked method end

				@Override
				public void onDestroyActionMode(ActionMode arg0) // onDestroyActionMode method start
				{
					// not used
				} // onDestroyActionMode method end

				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) // onPrepareActionMode method start
				{
					return false;
				} // onPrepareActionMode method end
			}); // anonymous inner class end
		} // end else
		
		return v;
	} // onCreateView method end
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) // onListItemClick method start
	{
		Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);

		// start CrimePagerActivity for this crime
		Intent i = new Intent(getActivity(), CrimePagerActivity.class);
		i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		startActivity(i);
	} // onListItemClick method end
	
	@Override
	public void onResume() // onResume method start
	{
		super.onResume();
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
	} // onResume method end
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) // onCreateOptionsMenu method start
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
		if (mSubtitleVisible && showSubtitle != null)
		{
			showSubtitle.setTitle(R.string.subtitle);
		} // end if
	} // onCreateOptionsMenu method end
	
	@TargetApi(11)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) // onOptionsItemSelected method start
	{
		switch (item.getItemId())
		{
		case R.id.menu_item_new_crime:
			Crime crime = new Crime();
			CrimeLab.get(getActivity()).addCrime(crime);
			Intent i = new Intent(getActivity(), CrimePagerActivity.class);
			i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
			startActivityForResult(i, 0);
			return true;
		case R.id.menu_item_show_subtitle:
			if (getActivity().getActionBar().getSubtitle() == null)
			{
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
				mSubtitleVisible = true;
				item.setTitle(R.string.hide_subtitle);
			} // end if
			else
			{
				getActivity().getActionBar().setSubtitle(null);
				mSubtitleVisible = false;
				item.setTitle(R.string.show_subtitle);
			} // end else
			return true;
		default:
			return super.onOptionsItemSelected(item);
		} // end switch
	} // onOptionsItemSelected method end
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) // onCreateContextMenu method start
	{
		getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
	} // onCreateContextMenu method end
	
	@Override
	public boolean onContextItemSelected(MenuItem item) // onContextItemSelected method start
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		int position = info.position;
		CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
		Crime crime = adapter.getItem(position);
		
		switch (item.getItemId())
		{
		case R.id.menu_item_delete_crime:
			CrimeLab.get(getActivity()).deleteCrime(crime);
			adapter.notifyDataSetChanged();
			return true;
		} // end switch
		
		return super.onContextItemSelected(item);
	} // onContextItemSelected method end
	
	private class CrimeAdapter extends ArrayAdapter<Crime> // internal class start
	{
		public CrimeAdapter(ArrayList<Crime> crimes) // constructor
		{
			super(getActivity(), 0, crimes);
		} // constructor end
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) // getView method start
		{
			if (convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_crime, null);
			} // end if
			
			Crime c = getItem(position);
			
			TextView titleTextView = (TextView)convertView.findViewById(
					R.id.crime_list_item_titleTextView);
			titleTextView.setText(c.getTitle());
			TextView dateTextView = (TextView)convertView.findViewById(
					R.id.crime_list_item_dateTextView);
			dateTextView.setText(c.getDate().toString());
			CheckBox solvedCheckBox = (CheckBox)convertView.findViewById(
					R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.isSolved());
			
			return convertView;
		} // getView method end
	} // internal class end
} // class end
