import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.ReadyEvent;

import java.time.LocalDate;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

public class Bot extends ListenerAdapter {
   public void onReady(ReadyEvent event) {
      int[] track = { 0, 0, 0, 0, 0, 0, 0 };
      String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", };
      int nToday = java.time.LocalDate.now().getDayOfWeek().getValue();
      String today = "";
      File file = new File("keepTrack.txt");
      boolean updated = false;
      
      try {
         Scanner fileReader = new Scanner(file);
         if (fileReader.hasNext() && fileReader.next().equals(days[nToday - 1])) {
            updated = true;
         }
         else if (fileReader.hasNext()) {
            for (int i = 0; i < 7; i++) {
               track[i] = Integer.parseInt(fileReader.next());
            }
         }
         fileReader.close();
      } catch (IOException e) {
         System.out.println("bruh IO problem");
      }
      
      if (!updated) {
         try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            
            if (nToday == 1) today = "> Monkey Monday";
            else if (nToday == 2) today = (track[nToday - 1] % 3 != 0) ? "> Toerangatang Tuesday" : "> Toe Touching Tuesday";
            else if (nToday == 3) today = "> Wetsack Wednesday";
            else if (nToday == 4) today = (track[nToday - 1] % 3 != 0) ? "> Testicle Touching Thursday" : "> Toetastic Thursday";
            else if (nToday == 5) today = "> Funky Monkey Friday";
            else if (nToday == 6) today = "> Super Monkey Saturday";
            else if (nToday == 7) today = "> Sleepy Monkey Sunday";
            
            printWriter.println(days[nToday - 1]);
            track[nToday - 1]++;
            for (int i = 0; i < 7; i++) {
               printWriter.println(track[i]);
            }
            printWriter.close();
         } catch (IOException e) {
            System.out.println("bruh writing problem");
         }
         event.getJDA().getTextChannelsByName("monkey-schedule", true).get(0).sendMessage(today).queue();
      }
      
      if (event.getJDA() != null) event.getJDA().shutdown();
   }

   public static void main(String[] args) throws LoginException {
      JDA jda = new JDABuilder(AccountType.BOT).setToken("NzY3OTA0NzA2OTk5NDg0NDI2.X44s_Q.LmobQm-Aa0XH7JWtLYdHNvK5rmk").build();
      jda.getPresence().setActivity(Activity.playing("cheeks spread"));
      jda.addEventListener(new Bot());
   }
}