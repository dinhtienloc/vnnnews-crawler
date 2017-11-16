import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * Created by locdt on 11/16/2017.
 */
public class Crawler implements Runnable {
    private String date;
    public Crawler(String date) {
        this.date = date;
    }

    @Override
    public void run() {
        crawl(this.date);
    }

    public void crawl(String date) {
        final Calendar cal = Calendar.getInstance();
        try {
            int articleCount = 0;
            Date stopDate = new SimpleDateFormat("dd/MM/yyyy").parse(Constants.STOP_DATE);
            Date iteratorDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            cal.setTime(iteratorDate);


            while (iteratorDate.after(stopDate)) {
                String dateParam = Utils.convertDateToString(iteratorDate)  ;
                for (Constants.Category cat : Constants.Category.values()) {
                    String url = String.format(cat.getUrl(), dateParam);
                    while (true) {
                        try {
                            System.out.println("[CRAWLER] " + url);
                            Document document = Jsoup.connect(url).get();
                            Elements articles = document.select("ul.ListArticle li");
                            int length = articles.size();

                            for (int i = 0; i < length; i++) {
                                Element link = articles.get(i).select("a").first();
                                News news = new News(link.attr("abs:href"));
                                visitNews(news);
                                articleCount++;
                            }
                            break;
                        } catch (ConnectException e) {
                            e.printStackTrace();
                            continue;
                        } catch (SocketTimeoutException e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
                cal.add(Calendar.DATE, Constants.THREAD_NUM*-1);
                iteratorDate = cal.getTime();
            }
            System.out.println("[FINISH] Total: " + articleCount);
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void visitNews(News news) throws Exception {
        while(true) {
            try {
                String url = news.getUrl();
//                System.out.println("> [VISIT]: " + url);
                Document document = Jsoup.connect(url).get();

                Element detail = document.select("div.ArticleDetail").first();
                Elements eTitle = detail.select(Constants.CSS_TITLE);
                if (eTitle == null)
                    return;
                String title = eTitle.first().text().replace(" ", " ").trim();

                Element eTime = detail.select(Constants.CSS_TIME).first();
                if (eTime == null)
                    return;

                String time = eTime.text().replace(" ", " ").trim();
                Element eDesc = detail.select(Constants.CSS_DESC1).first();
                if (eDesc == null)
                    eDesc = detail.select(Constants.CSS_DESC2).first();

                if (eDesc == null)
                    return;

                String desc = eDesc.text().replace(" ", " ").trim();
                news.setTitle(title);
                news.setTime(time);
                news.setDesc(desc);
                Elements tags = detail.select(Constants.CSS_TAG);
                for (Element tag : tags) news.addTag(tag.text().trim());

                //Graph FB
                int[] fbInfo = getFbInfo(news.getUrl());
                news.setFbShareCount(fbInfo[0]);
                news.setFbReactionCount(fbInfo[1]);
                news.setFbCommentCount(fbInfo[2]);

                // save to json file
                ObjectMapper mapper = new ObjectMapper();
                String data = mapper.writeValueAsString(news);
                Utils.appendDataToFile(Constants.JSON_FILE, data + ",");
                break;
            } catch (ConnectException e) {
                e.printStackTrace();
                continue;
            }
            catch (SocketTimeoutException e) {
                e.printStackTrace();
                continue;
            }
            catch (NullPointerException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public static String FACEBOOK_API = "https://graph.facebook.com/v2.11/?id=";
    public static String TOKEN = "&fields=engagement&access_token=EAACEdEose0cBAAmZCW8tXEqOqromUWCLKVumrXKnWrjTfzlvGMKAK4OVL3YZAKvEeJjR8pTUb0c6Xak6AcFchz6iD1cJO7wZCXc6ZAcPoWQHTo3xN8yVriWXvCErbZBVowLqPnlTg9sieVgo1BP1t0YdOpzh0pqikFSmL4ZCqYIXVOwT4EyqukhLnLoDkTlJtYKZBPKSHNMfQZDZD";
    public static int[] getFbInfo(String id) throws Exception {
        URL url = new URL(FACEBOOK_API + id + TOKEN);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode != 400) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            if (!"".equals(response)) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.toString());
                int commentCount = root.at("/engagement/comment_count").intValue();
                int reactionCount = root.at("/engagement/reaction_count").intValue();
                int shareCount = root.at("/engagement/share_count").intValue();
                //
                return new int[]{shareCount, reactionCount, commentCount};
            }
        }
        return new int[]{0, 0, 0};
    }
}
