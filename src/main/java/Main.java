import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main extends ListenerAdapter {
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static LocalDateTime now = LocalDateTime.now();
    static HololiveTools hololive = new HololiveTools();
    public static JDABuilder jdabuilder = JDABuilder.createDefault("NDI1ODgxOTE5NzAwMzM2NjQy.WrHncg.EwP_DlU_iRqciL4Kn9kn9ytytUI").addEventListeners(new Main());
    public static JDA jda;
    final String apiKey = "AIzaSyBGi44EH2qpW7_8ENH6RB32r1HyZLpe_7k";
    HttpRequestInitializer httpRequestInitializer = new HttpRequestInitializer() {
        public void initialize(HttpRequest request) throws IOException {
        }
    };
    YouTube youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), httpRequestInitializer).setApplicationName("RikoBot").build();
    public static void main(String args[]) {

        BotTool bottool = new BotTool();
        hololive.buildScheduleLinux();
        try {
            jdabuilder.addEventListeners(bottool);
            jdabuilder.addEventListeners(hololive);
            jda = jdabuilder.build();
            System.out.println(returnTimestamp() + " Bot Succsessfully Started!");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to Login");
        }

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        JDA jda = e.getJDA();
        Message message = e.getMessage();
        String msg = message.getContentDisplay();
        if(msg.startsWith("!hlsearch")){

            String s = msg.replaceAll("!hlsearch","");
             s.replaceAll("\\s+", "");

        }

        if (msg.equals("!sourcecode")) {
            e.getChannel().sendMessage("Source Code [Python and Java IDE needed]: https://drive.google.com/drive/folders/1vX1MTgExX7NerD9CvtsfgxScnayKwXWZ?usp=sharing").queue();
        }
    }

    public void logCommand(MessageReceivedEvent e, String message) {
        System.out.println(returnTimestamp() + " " + e.getAuthor() + " requested " + message);
    }

    public static String returnTimestamp() {
        now = LocalDateTime.now();
        return "[" + dtf.format(now) + "]";
    }
public int getSubcount(String id){
        try {
            BigInteger subs;
            YouTube.Channels.List search = youTube.channels().list("statistics");
            search.setId(id);
            search.setKey(apiKey);
            ChannelListResponse response = search.execute();
            List<Channel> channels = response.getItems();
            for (Channel channel : channels) {
                subs = channel.getStatistics().getSubscriberCount();
                return subs.intValue();
            }
        }catch(Exception e){

        }
        return 0;

}

}

