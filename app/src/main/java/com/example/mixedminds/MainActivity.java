package com.example.mixedminds;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements SimpleGestureFilter.SimpleGestureListener {

    private SimpleGestureFilter detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.app_name);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_affluenza);

        // Detect touched area
        detector = new SimpleGestureFilter(this,this);

        ///
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.replace(R.id.fragment_conteiner, new OrariETariffeFragment(), "AFFLUENZA");
// grFrag is about to become the current fragment, with the tag "GRAPH_FRAGMENT"
        tr.addToBackStack("AFFLUENZA");
// 'addToBackStack' also takes a string, which can be null, but this is not the tag
        tr.commit();
        ///
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner,
//                new HomeFragment()).commit();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            if (this != null) {
                this.onBackPressed();
            }
            return true;
        };
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    String t = "";

                    switch (item.getItemId()) {
                        case R.id.nav_gallery:
                            t = "HOME";
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_prenotazioni:
                            t = "PRENOTAZIONI";
                            selectedFragment = new PrenotazioniFragment();
                            break;
//                        case R.id.nav_orarie_e_tariffe:
//                            t = "ORARI";
//                            selectedFragment = new OrariETariffeFragment();
//                            break;
                        case R.id.nav_affluenza:
                            t = "AFFLUENZA";
                            selectedFragment = new OrariETariffeFragment();
                            break;
                    }
                    FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
                    tr.replace(R.id.fragment_conteiner, selectedFragment, t);
                    tr.addToBackStack("ORARI");
                    tr.commit();

//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner,
//                            selectedFragment, t).commit();
                    return true;
                }
            };

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onSwipe(int direction) {

        switch (direction) {
            case SimpleGestureFilter.SWIPE_DOWN :
                ContattiFragment myFragmentCont = (ContattiFragment) getSupportFragmentManager().findFragmentByTag("CONTATTI");
                if(myFragmentCont != null && myFragmentCont.isVisible()){
                    FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
                    tr.replace(R.id.fragment_conteiner, new OrariETariffeFragment(), "AFFLUENZA");
                    tr.addToBackStack("CONTATTI");
                    tr.commit();
                }
                break;
            case SimpleGestureFilter.SWIPE_UP :
                OrariETariffeFragment myFragmentHome = (OrariETariffeFragment) getSupportFragmentManager().findFragmentByTag("AFFLUENZA");
                if(myFragmentHome != null && myFragmentHome.isVisible()){
                    FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
                    tr.replace(R.id.fragment_conteiner, new ContattiFragment(), "CONTATTI");
                    tr.addToBackStack("AFFLUENZA");
                    tr.commit();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner,
//                            new ContattiFragment()).commit();
                }
                break;
        }

    }

    @Override
    public void onDoubleTap() {
        //Nothing
    }


    ImageView imageView;
    float startXValue = 1;
    private Integer[] mImageIds = { R.drawable.ariccia_ext, R.drawable.ariccia_ext2, R.drawable.ariccia_ext3, R.drawable.ariccia_int, R.drawable.ariccia_int2,
            R.drawable.ariccia_int3, R.drawable.ariccia_int4, R.drawable.ariccia_int5, R.drawable.ariccia_tot, R.drawable.chigi1, R.drawable.chigi2, R.drawable.parco1}; //, R.drawable.parco2};
    private int index = 0;



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float endXValue = 0;
        float x1 = event.getAxisValue(MotionEvent.AXIS_X);
        int action = MotionEventCompat.getActionMasked(event);
        imageView = (ImageView) findViewById(R.id.iv_background);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                startXValue = event.getAxisValue(MotionEvent.AXIS_X);

                return true;

            case (MotionEvent.ACTION_UP):
                endXValue = event.getAxisValue(MotionEvent.AXIS_X);
                if (endXValue > startXValue) {
                    if (endXValue - startXValue > 100) {
                        if (index - 1 >= 0) index -= 1;
                        else index =  mImageIds.length - 1;
                        System.out.println("Left-Right");
                        imageView.setImageResource(mImageIds[index]);
                    }
                }else {
                    if (startXValue -endXValue> 100) {
                        if (index + 1 < mImageIds.length) index += 1;
                        else index = 0;
                        System.out.println("Right-Left");
                        imageView.setImageResource(mImageIds[index]);

                    }
                }
                return true;


            default:
                return super.onTouchEvent(event);
        }

    }

}
