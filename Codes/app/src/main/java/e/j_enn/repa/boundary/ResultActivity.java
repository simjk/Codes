package e.j_enn.repa.boundary;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

import e.j_enn.repa.R;

public class ResultActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private BrowseFragment browseFragment;
    private DashboardFragment dashboardFragment;
    private SellFragment sellFragment;
    private CalculatorFragment calculatorFragment;
    private MeFragment meFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){

                case R.id.nav_browse:
                    setFragment(browseFragment);
                    return true;

                case R.id.nav_dashboard:
                    setFragment(dashboardFragment);
                    return true;

                case R.id.nav_sell:
                    setFragment(sellFragment);
                    return true;

                case R.id.nav_calculator:
                    setFragment(calculatorFragment);
                    return true;

                case R.id.nav_me:
                    setFragment(meFragment);
                    return true;

                /*default:
                    //setFragment(browseFragment);
                    return false;*/
            }
            return false;
        }
    };


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        mMainNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        String email = getIntent().getStringExtra("email");
        Bundle condition = getIntent().getExtras();
        Bundle bundle = new Bundle();
        bundle.putString("email", email);

        //When the app starts, BrowseFragment will be displayed
        setTitle("Browse");
        ResultFoundFragment bf = new ResultFoundFragment();
        bf.setArguments(bundle);
        bf.setArguments(condition);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame, bf);
        ft.commit();

        browseFragment = new BrowseFragment();
        browseFragment.setArguments(bundle);

        dashboardFragment = new DashboardFragment();
        dashboardFragment.setArguments(bundle);

        sellFragment = new SellFragment();
        //Pass data:email into Fragment
        sellFragment.setArguments(bundle);

        calculatorFragment = new CalculatorFragment();
        calculatorFragment.setArguments(bundle);

        meFragment = new MeFragment();
        meFragment.setArguments(bundle);
        /*android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(android.R.id.content, browseFragment);
        ft.commit();*/

        //getSupportFragmentManager().beginTransaction().add(android.R.id.content, browseFragment ).commit();
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) mMainNav.getChildAt(0);




        try {

            Field shiftingMode = menuView.getClass()
                    .getDeclaredField("mShiftingMode");

            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {

                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                //To update view, set the checked value again
                item.setChecked(item.getItemData().isChecked());
            }


        } catch (NoSuchFieldException e) {
            e.printStackTrace();

        } catch (IllegalAccessException e) {
            e.printStackTrace();

        } catch (SecurityException e) {
            e.printStackTrace();
        }


    }


    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

}
