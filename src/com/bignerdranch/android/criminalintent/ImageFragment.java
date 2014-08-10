package com.bignerdranch.android.criminalintent;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.*;
import android.widget.ImageView;

public class ImageFragment extends DialogFragment
{ // class start
	public static final String EXTRA_IMAGE_PATH = "com.bignerdranch.android.criminalintent.image_path";
	
	public static ImageFragment newInstance(String imagePath) // newInstance method start
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_IMAGE_PATH, imagePath);
		
		ImageFragment fragment = new ImageFragment();
		fragment.setArguments(args);
		fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
		
		return fragment;
	} // newInstance method end
	
	private ImageView mImageView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) // onCreateView method start
	{
		mImageView = new ImageView(getActivity());
		String path = (String)getArguments().getSerializable(EXTRA_IMAGE_PATH);
		BitmapDrawable image = PictureUtils.getScaledDrawable(getActivity(), path);
		
		mImageView.setImageDrawable(image);
		
		return mImageView;
	} // onCreateView method end
	
	@Override
	public void onDestroyView() // onDestroyView method start
	{
		super.onDestroyView();
		PictureUtils.cleanImageView(mImageView);
	} // onDestroyView method end
} // class end
