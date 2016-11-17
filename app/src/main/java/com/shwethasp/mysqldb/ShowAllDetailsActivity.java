package com.shwethasp.mysqldb;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shwethap on 05-07-2016.
 */
public class ShowAllDetailsActivity extends AppCompatActivity {

    public static ListView mListView;
    DBHelper db;
    public static ArrayList<DetailsModel> alldetails;
    public static DetailsAdapter detailsAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_detail);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        db = new DBHelper(this);

        alldetails = db.getAllDetails();
        mListView = (ListView) findViewById(R.id.all_details_list);


        detailsAdapter = new DetailsAdapter(ShowAllDetailsActivity.this, alldetails);
        // Attach the adapter to a ListView
        mListView.setAdapter(detailsAdapter);
        detailsAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

    }

    //listview animation with modify and delete button
    public void myClickHandler(View v) {
        LinearLayout vwParentRow = (LinearLayout) v.getParent();
        ImageView iv = (ImageView) vwParentRow.getChildAt(0);

        LinearLayout lw = (LinearLayout) vwParentRow.getChildAt(1);

        if (lw.getVisibility() == View.GONE) {
            lw.setVisibility(View.VISIBLE);
            iv.setImageResource(R.drawable.arrow_right);
        } else {
            lw.setVisibility(View.GONE);
            iv.setImageResource(R.drawable.arrow_left);

        }
    }

    //after modify refresh the content of listview
    private void refreshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alldetails = db.getAllDetails();
                detailsAdapter = new DetailsAdapter(ShowAllDetailsActivity.this, alldetails);
                // Attach the adapter to a ListView
                mListView.setAdapter(detailsAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
    }
}
