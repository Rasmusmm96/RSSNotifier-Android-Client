package moehring.rssnotifier.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

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
        Bundle b = getIntent().getExtras();

        if (b != null && b.getString("link") != null) {
            Uri uri = Uri.parse(b.getString("link"));
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);
            builder.setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right);
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, uri);
        }

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
        FirebaseMessaging.getInstance().subscribeToTopic("efb").addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(MainActivity.this, "Failed subscription", Toast.LENGTH_SHORT).show();
            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            mAdapter = new NewsListAdapter(newsManager.getNewsList());
            mRecyclerView.swapAdapter(mAdapter, false);
            refreshLayout.setRefreshing(false);
        });
    }

}
