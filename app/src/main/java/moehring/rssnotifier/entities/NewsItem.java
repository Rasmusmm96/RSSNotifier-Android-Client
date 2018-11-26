package moehring.rssnotifier.entities;

import java.io.Serializable;
import java.util.Date;

public class NewsItem implements Serializable{

    private String title;
    private String link;
    private Date timestamp;

    public NewsItem(String title, String link, Date timestamp) {
        this.title = title;
        this.link = link;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
