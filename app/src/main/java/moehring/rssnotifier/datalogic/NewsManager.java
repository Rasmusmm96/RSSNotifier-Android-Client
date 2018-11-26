package moehring.rssnotifier.datalogic;

import android.content.Context;

import java.util.LinkedList;

import moehring.rssnotifier.dataaccess.DeviceAccess;
import moehring.rssnotifier.entities.NewsItem;

public class NewsManager {
    private static NewsManager instance;
    private LinkedList<NewsItem> newsList;

    public static NewsManager getInstance(Context context) {
        if (instance == null)
            instance = new NewsManager(context);
        return instance;
    }

    private NewsManager(Context context) {
        newsList = DeviceAccess.loadNewsList(context);
    }

    public LinkedList<NewsItem> getNewsList() {
        return newsList;
    }

    public void addNews(Context context, NewsItem newsItem) {
        newsList.addFirst(newsItem);

        if (newsList.size() > 30)
            newsList.removeLast();
        
        DeviceAccess.saveNewsList(newsList, context);
    }

    public void removeNews(Context context, int position) {
        newsList.remove(position);
        DeviceAccess.saveNewsList(newsList, context);
    }


}
