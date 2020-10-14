package moehring.rssnotifier.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import moehring.rssnotifier.R;
import moehring.rssnotifier.activities.WebActivity;
import moehring.rssnotifier.entities.NewsItem;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder>{

    private LinkedList<NewsItem> mNewsItems;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTxtTitle;
        TextView mTxtLink;
        TextView mTxtTimestamp;

        ViewHolder(View itemView, Context context) {
            super(itemView);

            mTxtTitle = itemView.findViewById(R.id.txtTitle);
            mTxtLink = itemView.findViewById(R.id.txtLink);
            mTxtTimestamp = itemView.findViewById(R.id.txtDate);

            itemView.setOnClickListener(view -> {
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mTxtLink.getText().toString()));
                //context.startActivity(browserIntent);

                Uri uri = Uri.parse(mTxtLink.getText().toString());
                CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
                customTabsIntent.launchUrl(context, uri);
                /*
                Intent intent = new Intent(view.getContext(), WebActivity.class);
                intent.putExtra("link", mTxtLink.getText().toString());

                PendingIntent pendingIntent = PendingIntent.getActivity(
                        view.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
                );

                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    System.out.println("err");
                }
                 */
            });
        }
    }

    public NewsListAdapter(LinkedList<NewsItem> newsItems) {
        mNewsItems = newsItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recycler_listitem, viewGroup, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        NewsItem newsItem = mNewsItems.get(i);

        TextView title = viewHolder.mTxtTitle;
        TextView link = viewHolder.mTxtLink;
        TextView timestamp = viewHolder.mTxtTimestamp;

        title.setText(newsItem.getTitle());
        link.setText(newsItem.getLink());

        if (newsItem.getTimestamp() != null)
            timestamp.setText(DateFormat.format("kk:mm, d. MMM yyyy", newsItem.getTimestamp()));
        else
            timestamp.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }

}
