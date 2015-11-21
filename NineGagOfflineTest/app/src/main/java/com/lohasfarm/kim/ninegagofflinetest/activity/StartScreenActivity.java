package com.lohasfarm.kim.ninegagofflinetest.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lohasfarm.kim.ninegagofflinetest.R;
import com.lohasfarm.kim.ninegagofflinetest.adapter.GagAdapter;
import com.lohasfarm.kim.ninegagofflinetest.fakeRemoteService.FakeRemoteService;
import com.lohasfarm.kim.ninegagofflinetest.unit.Gag;
import com.lohasfarm.kim.ninegagofflinetest.unitcontroller.GagController;
import com.lohasfarm.kim.ninegagofflinetest.unitstorage.GagStorage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

public class StartScreenActivity extends ActionBarActivity implements ActionBar.TabListener  {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    private static GagStorage _gagStorage = new GagStorage();
    private static LinkedList<Gag> _gagList = new LinkedList<Gag>();
    private final String _TAG = "StartScreenActivity";

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        GagController.getInstance().initGagStorage(_gagStorage, this);
        //FileManager.getInstance().deleteGagJson(this);

        /**
        for(int i = 0; i< 50; i++) {
            Gag newGag = GagController.getInstance().createRandomPost(i);
            GagController.getInstance().addGag(newGag, this);
        }
        **/


        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle("9GAG");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_start_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);

            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int _page;
        private LinkedList<Gag> _gagList = new LinkedList<Gag>();
        private AsyncTask<Void, Void, ArrayList<Gag>> _fetchAsyncTask;
        private FakeRemoteService _fakeRemoteService;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            _page = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if(_page == 1) {
                //hot page
                View rootView = inflater.inflate(R.layout.fragment_hot_screen, container, false);
                return getHotScreenView(rootView);
            } else if(_page == 2) {
                //trending page
                View rootView = inflater.inflate(R.layout.fragment_trending_screen, container, false);
                return rootView;
            } else if(_page == 3) {
                //fresh page
                View rootView = inflater.inflate(R.layout.fragment_fresh_screen, container, false);
                return rootView;
            } else if(_page == 4) {
                //top page
                View rootView = inflater.inflate(R.layout.fragment_top_screen, container, false);
                return rootView;
            }
            return null;
        }

        private View getHotScreenView(View rootView) {
            ArrayList<Gag> array = new ArrayList<Gag>();
            _gagList = GagController.getInstance().getEntireList();
            for(Gag newGag: _gagList) {
                array.add(newGag);
            }

            GagAdapter adapter = new GagAdapter(this.getActivity(), R.layout.custom_row_gag, array);
            ListView listView = (ListView)rootView.findViewById(R.id.verticalList);
            listView.setAdapter(adapter);

            return rootView;
        }


    }

}
