import java.util.ArrayList;
import java.util.List;

/**
 * Created by locdt on 11/16/2017.
 */
public class News {
    public String url;
    public String time;
    public String title;
    public String desc;
    public List<String> tags;

    public int fbShareCount;
    public int fbReactionCount;
    public int fbCommentCount;

    public News(String url) {
        this.url = url;
        this.tags = new ArrayList<String>();
    }

    public void addTag(String tag) {
        if (tag == null) return;
        this.tags.add(tag);
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getFbShareCount() {
        return fbShareCount;
    }

    public void setFbShareCount(int fbShareCount) {
        this.fbShareCount = fbShareCount;
    }

    public int getBbReactionCount() {
        return fbReactionCount;
    }

    public void setFbReactionCount(int fbReactionCount) {
        this.fbReactionCount = fbReactionCount;
    }

    public int getFbCommentCount() {
        return fbCommentCount;
    }

    public void setFbCommentCount(int fbCommentCount) {
        this.fbCommentCount = fbCommentCount;
    }

    @Override
    public String toString() {
        return "News{" +
                "url='" + url + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", tags=" + tags +
                ", fbShareCount=" + fbShareCount +
                ", fbReactionCount=" + fbReactionCount +
                ", fbCommentCount=" + fbCommentCount +
                '}';
    }
}
