package moehring.rssnotifier.dataaccess;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Deque;
import java.util.LinkedList;

import moehring.rssnotifier.entities.NewsItem;

public class DeviceAccess {
    private static final String filePath = "news";

    public static boolean saveNewsList(Deque<NewsItem> newsList, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(filePath, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(newsList);
            oos.close();
            fos.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static LinkedList<NewsItem> loadNewsList(Context context) {
        try {
            FileInputStream fis = context.openFileInput(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            LinkedList<NewsItem> newsList = (LinkedList<NewsItem>) ois.readObject();
            ois.close();
            fis.close();
            return newsList;
        } catch(IOException | ClassNotFoundException ex) {
            return new LinkedList<>();
        }
    }
}
