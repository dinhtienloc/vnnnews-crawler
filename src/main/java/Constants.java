/**
 * Created by locdt on 11/16/2017.
 */
public class Constants {
    public static int THREAD_NUM = 5;
    public static String STOP_DATE = "15/09/2017";
    public static String VNN_PREFIX = "http://vietnamnet.vn/vn";
    public enum Category{
        THOISU("thoi-su"),
        KINHDOANH("kinh-doanh"),
        GIAITRI("giai-tri"),
        THEGIOI("the-gioi"),
        GIAODUC("giao-duc"),
        DOISONG("doi-song"),
        PHAPLUAT("phap-luat"),
        THETHAO("the-thao"),
        CONGNGHE("cong-nghe"),
        SUCKHOE("suc-khoe"),
        BATDONGSAN("bat-dong-san");
        private String alias;
        Category(String alias) {this.alias = alias;}
        public String getUrl() {return String.format(VNN_PREFIX + CATEGORY_URL, "%s", this.alias);}
    }
    public static String CATEGORY_URL = "/date/%s/%s/index.html";

    public static String CSS_TITLE = "h1.title";
    public static String CSS_TIME = "div.ArticleDateTime > span.ArticleDate";
    public static String CSS_DESC1 = "div#ArticleContent strong";
    public static String CSS_DESC2 = "div#ArticleContent b";
    public static String CSS_TAG = "div.tagBoxContent li > h3 > a";

    public static String JSON_FILE = "data.json";
}
