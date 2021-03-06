package com.example.testapp;

import com.example.datamodel.Message;
import com.example.datamodel.User;
import com.example.http.Httphandler;
import com.example.utils.Constants;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	private static final String TAG = "MainActivity";
	public static Context mContext;
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = getApplicationContext();
		startApplicationComponents();
		checkGCMRegistrationId();
		
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33B5E5")));
		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		final Intent intent = getIntent();
	    final Bundle extras = intent.getExtras();
	    Log.d("MainActivity", "going insude");
	    if(extras != null){
//		    String screenName = (String) extras.get(Constants.PAGE);
		    Log.d("MainActivity", "going insude");
		    Log.d("MainActivity", "going insude"+extras.toString());
		    
		    boolean cameFromNotification = extras.getBoolean(Constants.NOTIFICATION, false);
		    Log.d("MainActivity", "going insude" + cameFromNotification);
//		    if(screenName != null && screenName.equals(OfferRideFragment.class.getName())){
//		    	FragmentManager fragmentManager = getSupportFragmentManager();
//	            fragmentManager
//	            	.beginTransaction()
//	            	.replace(R.id.container,
//	            			OfferRideFragment.newInstance(2, "Offer a ride")).commit();
//		    } else
		    	if(cameFromNotification) {
		    		
		    	Message gcmMessage1 = (Message)intent.getSerializableExtra(Constants.SOME_NAME_FOR_GCM_OBJECT);
		    	intent.replaceExtras((Bundle)null);
		    	Log.d("MainActivity", gcmMessage1.toString());
		    	Fragment fragment = UserDetailFragment.newInstance(2, "User details", gcmMessage1, null, null);
			 	
		        
			 	FragmentManager fragmentManager = getSupportFragmentManager();
		        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		        fragmentTransaction.replace(R.id.container, fragment);
		        fragmentTransaction.addToBackStack(null);
		        fragmentTransaction.commit();
		    	
		    }
	    }
	    
	    
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		switch(position){
			case 0:
				fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							FeedsFragment.newInstance(1, "My rides"))
					.commit();
				break;
			
			case 1:
				fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							OfferRideFragment.newInstance(2, "Offer a ride"))
					.addToBackStack(null)
					.commit();
				mTitle = getString(R.string.offer_ride);
				break;
			case 2:
				mTitle = getString(R.string.offered_rides);
				
		}
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.my_rides);
			break;
		case 2:
			mTitle = getString(R.string.offer_ride);
			break;
		case 3:
			mTitle = getString(R.string.offered_rides);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
//	public static class PlaceholderFragment extends Fragment {
//		/**
//		 * The fragment argument representing the section number for this
//		 * fragment.
//		 */
//		private static final String ARG_SECTION_NUMBER = "section_number";
//
//		/**
//		 * Returns a new instance of this fragment for the given section number.
//		 */
//		public static PlaceholderFragment newInstance(int sectionNumber) {
//			PlaceholderFragment fragment = new PlaceholderFragment();
//			Bundle args = new Bundle();
//			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//			fragment.setArguments(args);
//			return fragment;
//		}
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_main, container,
//					false);
//			return rootView;
//		}
//
//		@Override
//		public void onAttach(Activity activity) {
//			super.onAttach(activity);
//			((MainActivity) activity).onSectionAttached(getArguments().getInt(
//					ARG_SECTION_NUMBER));
//		}
//	}
	
	public void startApplicationComponents(){
		Httphandler.setSharedInstance(new Httphandler());
	}
	
	private boolean checkGCMRegistrationId(){
		boolean registrationSucsess = User.getSharedInstance().getRegistrationStatus();
		if (!registrationSucsess) {
			//Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
			Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
			startActivity(intent);
			Log.d(TAG, "Registration not done");
			return false;
		}
		
		return true;
//		int registeredVersion = getSharedPreferences(Constants.APP_SETTINGS, MODE_PRIVATE).getInt(Constants.APP_VERSION, Integer.MIN_VALUE);
//		int currentVersion = getAppVersion(getApplicationContext());
//		if (registeredVersion != currentVersion) {
//			Log.i(TAG, "App version changed.");
//			return "";
//		}
		
//		try {
//			PackageInfo packageInfo = context.getPackageManager()
//					.getPackageInfo(context.getPackageName(), 0);
//			return packageInfo.versionCode;
//		} catch (NameNotFoundException e) {
//			Log.d("RegisterActivity",
//					"I never expected this! Going down, going down!" + e);
//			throw new RuntimeException(e);
//		}
	}
}
