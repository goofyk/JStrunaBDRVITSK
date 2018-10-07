package UMainPack;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.TimerTask;

class UServiceTaskHandler extends TimerTask {
    int interval = 5000;
    UServiceTaskHandler(int interval){
        this.interval =  interval;
    }

    public void run() {
//        try {
//            writeToFile(String.valueOf(this.interval));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        UStruna uStruna = new UStruna();
        java.sql.Timestamp tmstmpFirstDattim = UEmbeddedDB.getLastDateTimeLoad();
        java.sql.Timestamp tmstmpLastDattim = new Timestamp(System.currentTimeMillis());
        String typeObj = "0";
        ResultSet rsStruna = uStruna.get_params_all(tmstmpFirstDattim, tmstmpLastDattim, typeObj);
        if(rsStruna == null){
            ULogger.log.error("Не удалось получить данные из базы Струна. Проверьте подключение и попробуйте повторить попытку.");
            return;
        }
        UBDRV uBdrv = new UBDRV();
        uBdrv.runLoadDataStrunaCmd(rsStruna, tmstmpLastDattim);

    }

    public void writeToFile(String text) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\ULTRA\\test.txt"), "UTF-8"));
        out.write(text);
        out.close();
    }
}
