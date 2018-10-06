package UServicePack;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.TimerTask;

class UServiceTaskHandler extends TimerTask {
    int interval = 5000;
    UServiceTaskHandler(int interval){
        this.interval =  interval;
    }

    public void run() {
        try {
            writeToFile(String.valueOf(this.interval));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String text) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\ULTRA\\test.txt"), "UTF-8"));
        out.write(text);
        out.close();
    }
}
