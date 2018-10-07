package UMainPack;

import org.apache.log4j.PropertyConfigurator;

public class UMain {

    static UFormMain mainForm;

    public static void main(String[] args) {
        PropertyConfigurator.configure("ULogger.properties");
        UProperties.loadProperties();
        mainForm = new UFormMain();
        mainForm.setVisible(true);
        //((UFormMain) UFormMain.mainForm).hideProgressBar();
    }

}
