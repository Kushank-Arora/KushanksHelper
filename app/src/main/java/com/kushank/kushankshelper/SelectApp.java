package com.kushank.kushankshelper;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kushank.kushankshelper.adapters.ApplicationAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.util.Collections.sort;

public class SelectApp extends AppCompatActivity {

    List<Pair<String, Drawable>> listOfApps;
    List<Pair<String, Drawable>> listOfAppsToShow;
    Map<String, String> packageNameToAppName;
    private ApplicationAdapter appAdapter;

    @BindView(R.id.tvPleaseWait)
    TextView pleaseWait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_app);
        ButterKnife.bind(this);

        listOfApps = new ArrayList<>();
        listOfAppsToShow = new ArrayList<>();
        packageNameToAppName = new HashMap<>();

        listOfAppsToShow.addAll(listOfApps);
        appAdapter = new ApplicationAdapter(listOfAppsToShow, new ApplicationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String appName) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("appName", appName);
                resultIntent.putExtra("appPackage", packageNameToAppName.get(appName));
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        RecyclerView rlListApps = findViewById(R.id.rvListApps);
        rlListApps.setLayoutManager(llm);
        rlListApps.setAdapter(appAdapter);

        EditText etAppName = findViewById(R.id.etAppName);
        etAppName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                modifyList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        new GetAndFillAppsList().execute();
    }

    public void modifyList(String appName) {
        appName = appName.toUpperCase();
        listOfAppsToShow.clear();
        for(Pair<String, Drawable> pair: listOfApps){
            if (pair.first.toUpperCase().contains(appName)) {
                listOfAppsToShow.add(pair);
            }
        }
        appAdapter.notifyDataSetChanged();
    }

    private class GetAndFillAppsList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pleaseWait.setVisibility(View.INVISIBLE);
            listOfAppsToShow.addAll(listOfApps);
            appAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fillListOfApps();
            return null;
        }


        private void fillListOfApps() {
            final PackageManager pm = getPackageManager();
            //get a list of installed apps.
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

            for (ApplicationInfo packageInfo : packages) {
                if (!isSystemPackage(packageInfo)) {
                    listOfApps.add(new Pair<>(packageInfo.loadLabel(pm).toString(), packageInfo.loadIcon(pm)));
                    packageNameToAppName.put(packageInfo.loadLabel(pm).toString(), packageInfo.packageName);
                }
            }
            sort(listOfApps, new Comparator<Pair<String, Drawable>>() {
                @Override
                public int compare(Pair<String, Drawable> o1, Pair<String, Drawable> o2) {
                    return o1.first.compareTo(o2.first);
                }
            });
        }

        private boolean isSystemPackage(ApplicationInfo applicationInfo) {
            return ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
        }
    }

}
