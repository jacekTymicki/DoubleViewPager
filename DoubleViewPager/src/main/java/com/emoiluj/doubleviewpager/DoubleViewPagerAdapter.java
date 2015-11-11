package com.emoiluj.doubleviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;

public class DoubleViewPagerAdapter extends PagerAdapter {
	private static final String TAG = "DoubleViewPagerAdapter";

	private Context mContext;
	private ArrayList<PagerAdapter> mAdapters;
	private VerticalViewPager.OnPageChangeListener mOnPageChangeListener;
	private int mOnHorizontalPosition;
	private int mToVerticalPosition;
	private boolean mSmoothScroll;
	private VerticalViewPager[] mVerticalViewPagers;

	public DoubleViewPagerAdapter(Context context, ArrayList<PagerAdapter> verticalAdapters, VerticalViewPager.OnPageChangeListener
			onPageChangeListener) {
		mContext = context;
		mAdapters = verticalAdapters;
		mOnPageChangeListener = onPageChangeListener;
		mOnHorizontalPosition = -1;
		mVerticalViewPagers = new VerticalViewPager[mAdapters.size()];
	}


	@Override
	public int getCount() {
		return mAdapters.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object view) {
		container.removeView((View) view);
	}

	@Override
	public Object instantiateItem(final ViewGroup container, int position) {
		VerticalViewPager verticalViewPager = new VerticalViewPager(mContext);
		verticalViewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		verticalViewPager.setAdapter(mAdapters.get(position));
		verticalViewPager.setOnPageChangeListener(mOnPageChangeListener);
		if (position == mOnHorizontalPosition) {
			verticalViewPager.setCurrentItem(mToVerticalPosition, mSmoothScroll);
		}
		container.addView(verticalViewPager);
		mVerticalViewPagers[position] = verticalViewPager;
		return verticalViewPager;
	}

	/**
	 * Set the currently selected page vertically.
	 *
	 * @param onHorizontalPosition horizontal item index position of vertical item to select
	 * @param toVerticalPosition   vertical Item index to select
	 * @param smoothScroll         True to smoothly scroll to the new item, false to transition immediately
	 */
	public void setCurrentVerticalItem(int onHorizontalPosition, int toVerticalPosition, boolean smoothScroll) {
		Log.i(TAG, "onHorizontalPosition: " + onHorizontalPosition + " toVerticalPosition: " + toVerticalPosition);
		mOnHorizontalPosition = onHorizontalPosition;
		mToVerticalPosition = toVerticalPosition;
		mSmoothScroll = smoothScroll;
		if (mVerticalViewPagers[onHorizontalPosition] != null) {
			mVerticalViewPagers[onHorizontalPosition].setCurrentItem(mToVerticalPosition, mSmoothScroll);
		}
	}
}