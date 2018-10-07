package UMainPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class UDialogSettings extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField ServerNameBDRV;
    private JPanel panel1;
    private JPanel panel2;
    private JTextField NameDbBDRV;
    private JTextField UsernameBDRV;
    private JTextField ServerPortBDRV;
    private JPasswordField PasswordBDRV;
    private JTextField ServerNameStruna;
    private JTextField PathToDbStruna;
    private JTextField UsernameStruna;
    private JPasswordField PasswordStruna;
    private JTextField ServerPortStruna;
    private JPanel pnlBDRV;
    private JPanel pnlStruna;
    private JTextField NameService;
    private JTextField IntervalScheduler;
    private JButton createEXEButton;
    private JTextField PathToFileOfService;
    private JTextField EncodingStruna;
    private JButton btnCheckConnectBDRV;
    private JButton btnCheckConnectStruna;
    private JTextField IdTZK;

    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int sizeWidth = 800;
    private static int sizeHeight = 600;
    private static int locationX = (screenSize.width - sizeWidth) / 2;
    private static int locationY = (screenSize.height - sizeHeight) / 2;
    private String root = System.getProperty("user.dir");

    public UDialogSettings() {

        //setLocationRelativeTo(UMain.mainForm);
        setBounds(locationX, locationY, this.getWidth(), this.getHeight());
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(buttonOK);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        loadProperties();

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        createEXEButton.addActionListener(e -> createExeService());

        btnCheckConnectBDRV.addActionListener(e -> checkConnect(new UBDRV().getConnection()));
        btnCheckConnectStruna.addActionListener(e -> checkConnect(new UStruna().getConnection()));
    }

    private void onOK() {
        saveProperties();
        this.dispose();
    };

    private void onCancel() {
        this.dispose();
    };

    private void createExeService(){
        try {
            String command = "";
//                    String javaHome = System.getProperty("java.home");
//                    System.out.println(javaHome);
//                    String sysdir = System.getenv("WINDIR") + "\\system32\\";
//                    String nameOfServiceJar = "StrunaBdrvService.jar";
//                    String pathToServicePack = root + "\\src\\main\\java\\UServicePack\\";
//
//                    String command = sysdir + "javac.exe " + pathToServicePack + "UServiceTask.java";
//                    Runtime.getRuntime().exec(command);
//
//                    command = sysdir + "java.exe cvmf .\\" + pathToServicePack + nameOfServiceJar + ".\\src\\main\\java\\META-INF\\MANIFEST.MF *.class";
//                    Runtime.getRuntime().exec(command);
//
//                    makeJarFile(pathToServicePack + nameOfServiceJar);

            command = root + "\\launch4j\\launch4j.exe " + root + "\\launch4j\\config\\config.xml";
            Runtime.getRuntime().exec(command);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    };

    private void saveProperties() {

        // id TZK
        UProperties.setProperty("IdTZK", IdTZK.getText());

        // BDRV
        UProperties.setProperty("ServerNameBDRV", ServerNameBDRV.getText());
        UProperties.setProperty("ServerPortBDRV", ServerPortBDRV.getText());
        UProperties.setProperty("NameDbBDRV", NameDbBDRV.getText());
        UProperties.setProperty("UsernameBDRV", UsernameBDRV.getText());
        String strPasswordBDRV  = new String(PasswordBDRV.getPassword());
        if(!strPasswordBDRV.isEmpty()) UProperties.setProperty("PasswordBDRV", strPasswordBDRV);

        // Struna
        UProperties.setProperty("ServerNameStruna", ServerNameStruna.getText());
        UProperties.setProperty("ServerPortStruna", ServerPortStruna.getText());
        UProperties.setProperty("PathToDbStruna", PathToDbStruna.getText());
        UProperties.setProperty("UsernameStruna", UsernameStruna.getText());
        UProperties.setProperty("EncodingStruna", EncodingStruna.getText());
        String strPasswordStruna  = new String(PasswordStruna.getPassword());
        if(!strPasswordStruna.isEmpty()) UProperties.setProperty("PasswordStruna", strPasswordStruna);

        // Service
        UProperties.setProperty("NameService", NameService.getText());
        UProperties.setProperty("IntervalScheduler", IntervalScheduler.getText());
        UProperties.setProperty("PathToFileOfService", PathToFileOfService.getText());

    };

    private void loadProperties(){

        // ID TZK
        IdTZK.setText(UProperties.getProperty("IdTZK"));

        // BDRV
        ServerNameBDRV.setText(UProperties.getProperty("ServerNameBDRV"));
        ServerPortBDRV.setText(UProperties.getProperty("ServerPortBDRV"));
        NameDbBDRV.setText(UProperties.getProperty("NameDbBDRV"));
        UsernameBDRV.setText(UProperties.getProperty("UsernameBDRV"));

        // Struna
        ServerNameStruna.setText(UProperties.getProperty("ServerNameStruna"));
        ServerPortStruna.setText(UProperties.getProperty("ServerPortStruna"));
        PathToDbStruna.setText(UProperties.getProperty("PathToDbStruna"));
        UsernameStruna.setText(UProperties.getProperty("UsernameStruna"));
        EncodingStruna.setText(UProperties.getProperty("EncodingStruna"));

        // Service
        NameService.setText(UProperties.getProperty("NameService"));
        IntervalScheduler.setText(UProperties.getProperty("IntervalScheduler"));
        PathToFileOfService.setText(UProperties.getProperty("PathToFileOfService"));

    };

    private void checkConnect(Connection connection){
        String textConfirm = "Connection successfully established";
        int gradeMess = JOptionPane.INFORMATION_MESSAGE;
        if(connection == null){
            textConfirm = "Connection not established!";
            gradeMess = JOptionPane.ERROR_MESSAGE;
        }else {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(this, textConfirm, "Message",gradeMess);
    };

}
