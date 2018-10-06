package UMainPack;

import org.apache.log4j.PropertyConfigurator;

public class UMain {

    public static void main(String[] args) {
        PropertyConfigurator.configure("ULogger.properties");
        UProperties.loadProperties();
        UFormMain.mainForm.setVisible(true);
        //((UFormMain) UFormMain.mainForm).hideProgressBar();
    }

}
