package com.dffc.wp;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.dffc.wp.util.ConfigUtil;

public class MainActivity extends FragmentActivity implements MainFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        ConfigUtil.initConfig();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(android.R.id.content, MainFragment.newInstance(null, null));
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
