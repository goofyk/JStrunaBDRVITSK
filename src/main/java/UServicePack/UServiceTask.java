package UServicePack;

import UMainPack.UProperties;

import java.io.*;
import java.util.*;

public class UServiceTask {

    private static Timer timer = new Timer();
    private static boolean shedulerIsRun = false;
    private static int intervalScheduler = 60000;

    public static void main(String[] args) {

        UProperties.loadProperties();

        //if(timer != null) timer.cancel();

        int intervalPropScheduler = intervalScheduler;
        if(!UProperties.getProperty("IntervalScheduler").isEmpty()){
            intervalPropScheduler = Integer.valueOf(UProperties.getProperty("IntervalScheduler"));
//            try {
//                writeToFile("UProp.intervalPropScheduler = " + intervalPropScheduler, "test2");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

        //if(args.length > 0) intervalScheduler = Integer.valueOf(args[0]);

        if(intervalScheduler == 0) return;

        if(intervalPropScheduler != intervalScheduler){
            intervalScheduler = intervalPropScheduler;
            runScheduler();
            shedulerIsRun = true;
        }
    }

    public static void runScheduler() {
        timer.schedule(new UServiceTaskHandler(intervalScheduler), 0, intervalScheduler);
//        writeToFile("Sheduler runned with interval = " + String.valueOf(intervalScheduler), "test3");
    }

    public static void writeToFile(String text, String  nameFile) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\ULTRA\\" + nameFile + ".txt"), "UTF-8"));
        out.write(text);
        out.close();
    }

    UServiceTask(int interval) {
        if(interval == 0) return;
        timer.schedule(new UServiceTaskHandler(interval), 0, interval);
    }
}

