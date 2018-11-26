package moehring.rssnotifier.activities;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import moehring.rssnotifier.R;
import moehring.rssnotifier.adapters.NewsListAdapter;
import moehring.rssnotifier.datalogic.NewsManager;
import moehring.rssnotifier.datalogic.NotificationHandler;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsManager newsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsManager = NewsManager.getInstance(this);
        NotificationHandler.createNotificationChannel(this);

        refreshLayout = findViewById(R.id.swiperefresh);
        mRecyclerView = findViewById(R.id.listNews);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new NewsListAdapter(newsManager.getNewsList());
        System.out.println(newsManager.getNewsList().size());
        mRecyclerView.setAdapter(mAdapter);

        // Subscribe to topic
        FirebaseMessaging.getInstance().subscribeToTopic("efb").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Subscribed to topic", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed subscription", Toast.LENGTH_SHORT).show();
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter = new NewsListAdapter(newsManager.getNewsList());
                mRecyclerView.swapAdapter(mAdapter, false);
                refreshLayout.setRefreshing(false);
            }
        });
    }

}
