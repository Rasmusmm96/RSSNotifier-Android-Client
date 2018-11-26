package moehring.rssnotifier.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

import moehring.rssnotifier.datalogic.NewsManager;
import moehring.rssnotifier.datalogic.NotificationHandler;
import moehring.rssnotifier.entities.NewsItem;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getData().get("title");
        String link = remoteMessage.getData().get("link");
        Date timestamp = new Date();

        NotificationHandler.showNotification(getApplicationContext(), title, link);
        NewsManager newsManager = NewsManager.getInstance(getApplicationContext());

        NewsItem newsItem = new NewsItem(title, link, timestamp);
        newsManager.addNews(this, newsItem);
    }
}
