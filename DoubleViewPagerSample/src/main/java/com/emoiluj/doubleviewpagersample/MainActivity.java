package com.emoiluj.doubleviewpagersample;

import com.emoiluj.doubleviewpager.DoubleViewPager;
import com.emoiluj.doubleviewpager.DoubleViewPagerAdapter;
import com.emoiluj.doubleviewpager.HorizontalViewPager;
import com.emoiluj.doubleviewpager.VerticalViewPager;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Window;
import java.util.ArrayList;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	public static final int ONE_SEC = 1000;
	private DoubleViewPager viewpager;
	private int horizontalChildes;
	private int verticalChildes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		loadDataFromSplash();
		loadUI();
	}

	private void loadDataFromSplash() {
		horizontalChildes = getIntent().getExtras().getInt("HORIZONTAL");
		verticalChildes = getIntent().getExtras().getInt("VERTICAL");
	}

	private void loadUI() {

		ArrayList<PagerAdapter> verticalAdapters = new ArrayList<>();
		generateVerticalAdapters(verticalAdapters);

		viewpager = (DoubleViewPager) findViewById(R.id.pager);
		VerticalViewPager.OnPageChangeListener verticalListener = new VerticalViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				Log.i(TAG, "on vertical position: " + position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		};
		final DoubleViewPagerAdapter adapter = new DoubleViewPagerAdapter(getApplicationContext(), verticalAdapters, verticalListener);
		viewpager.setAdapter(adapter);

		HorizontalViewPager.OnPageChangeListener horizontalListener = new HorizontalViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				Log.i(TAG, "on horizontal position: " + position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		};
		viewpager.setOnPageChangeListener(horizontalListener);

		adapter.setCurrentVerticalItem(0, 1, false);

		new CountDownTimer(verticalChildes * ONE_SEC, ONE_SEC) {
			private int toVerticalPosition = 0;

			public void onTick(long millisUntilFinished) {
				Log.i(TAG, "attempt to selected page after millisUntilFinished: " + millisUntilFinished);
				adapter.setCurrentVerticalItem(0, ++toVerticalPosition, false);
			}

			public void onFinish() {
				adapter.setCurrentVerticalItem(0, 0, false);
			}
		}.start();
	}

	private void generateVerticalAdapters(ArrayList<PagerAdapter> verticalAdapters) {
		for (int i = 0; i < horizontalChildes; i++) {
			verticalAdapters.add(new VerticalPagerAdapter(this, i, verticalChildes));
		}
	}
}