package UMainPack;

import javax.swing.table.TableModel;
import java.sql.*;

public class UEmbeddedDB {

    public static void main(String[] args) {
        connect();
        createTableUsers();
        createTableMatchStrunaBDRV();
        //insertAdminDataToDB();
        createTableDateTimeLoad();
        getLastDateTimeLoad();
        showData();
    }

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String FILENAME = "./DB/UStrunaBDRV";
    static final String URL = "jdbc:h2:file:" + FILENAME;
    static final String USER = "sa";
    static final String PASS = "123123";
    private static Connection conn = null;
    static final String TABLE_MATCHES_ID = "MatchesIdStrunaBDRV";
    static final String TABLE_DATETIME_LOAD = "DateTimeLoad";

    public static boolean execute(String querySql){
        boolean rs = true;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.execute(querySql);
        } catch (SQLException e) {
            return false;
        }
        return rs;
    };

    public static ResultSet executeQuery(String querySql){
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(querySql);
        } catch (SQLException e) {
            ULogger.log.error(e.getMessage());
        }
        return rs;
    };

    public static void createTableUsers() {
       String sql = new String("CREATE TABLE IF NOT EXISTS Users(\n" +
                "    USER_ID INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    USERNAME VARCHAR(50) NOT NULL,\n" +
                "    PASSWORD VARCHAR(150) NOT NULL,\n" +
                "   );");
        execute(sql);
    }

    public static void createTableMatchStrunaBDRV() {
        String sql = new String("CREATE TABLE IF NOT EXISTS " + TABLE_MATCHES_ID + "(\n" +
                "    STRUNA_ID_OBJ VARCHAR(25)    NOT NULL,\n" +
                "    STRUNA_P_NAME  VARCHAR(25)    NOT NULL,\n" +
                "    BDRV_P_MSD_ID VARCHAR(25)\n" +
                "   );");
        execute(sql);
    }

    public static void createTableDateTimeLoad() {
        String sql = new String("CREATE TABLE IF NOT EXISTS " + TABLE_DATETIME_LOAD + "(\n" +
                "    ID INT    PRIMARY KEY AUTO_INCREMENT,\n" +
                "    DATETIMELOAD  TIMESTAMP    NOT NULL,\n" +
                "   );");
        execute(sql);
    }

    private static Connection connect() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static ResultSet showData(){
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            String sql = new String("SELECT * FROM Users AS Users");
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String USER_ID  = rs.getString("USER_ID");
                String USERNAME = rs.getString("USERNAME");
                String PASSWORD = rs.getString("PASSWORD");
                System.out.println("USER_ID: " + USER_ID);
                System.out.println("USERNAME: " + USERNAME);
                System.out.println("PASSWORD: " + PASSWORD);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void insertAdminDataToDB() {
        try{
            conn.setAutoCommit(false);
            String sql = "INSERT INTO USERS(USERNAME, PASSWORD) VALUES (?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            String USERNAME = "Admin";
            String PASSWORD = "123";
            pst.setString(1, USERNAME);
            pst.setString(2, UCommon.md5(PASSWORD));
            pst.addBatch();
            pst.executeBatch();
            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertDataToTableMatchesFromTable(TableModel tableModel) {
        try{
            conn.setAutoCommit(false);
            String sql = new String("INSERT INTO " + TABLE_MATCHES_ID + "(STRUNA_ID_OBJ, STRUNA_P_NAME, BDRV_P_MSD_ID) VALUES (?,?,?)");
            int rowCount = tableModel.getRowCount();
            PreparedStatement pst = conn.prepareStatement(sql);
            for(int row = 0; row < rowCount; row++){
                String STRUNA_ID_OBJ = (String)tableModel.getValueAt(row, 0);
                String STRUNA_P_NAME = (String)tableModel.getValueAt(row, 1);
                String BDRV_P_MSD_ID = (String)tableModel.getValueAt(row, 2);
                pst.setString(1, STRUNA_ID_OBJ);
                pst.setString(2, STRUNA_P_NAME);
                pst.setString(3, BDRV_P_MSD_ID);
                pst.addBatch();
            }
            pst.executeBatch();
            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertDataToTableDateTimeLoad(Timestamp dateTimeLoad) {
        try{
            conn.setAutoCommit(false);
            String sql = "INSERT INTO " + TABLE_DATETIME_LOAD + "(DATETIMELOAD) VALUES (?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setTimestamp(1, dateTimeLoad);
            pst.addBatch();
            pst.executeBatch();
            conn.commit();
        }catch(SQLException e){
            ULogger.log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void cleareTableMatchesId(){
        cleareTable(TABLE_MATCHES_ID);
    };

    private static void cleareTable(String tableName) {
        String sql = new String("DROP TABLE " + tableName + ";");
        execute(sql);
    }

    public static ResultSet getAllDataMatches() {
        String sql = "SELECT * FROM " + TABLE_MATCHES_ID;
        ResultSet rs = executeQuery(sql);
        return rs;
    }

    public static Timestamp getLastDateTimeLoad() {
        String sql = "SELECT * FROM " + TABLE_DATETIME_LOAD;
        ResultSet rs = executeQuery(sql);
        Timestamp lastDateTime = new Timestamp(System.currentTimeMillis());
        try {
            rs.last();
            lastDateTime = rs.getTimestamp(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastDateTime;
    }

    static {
        connect();
        createTableUsers();
        createTableMatchStrunaBDRV();
        createTableDateTimeLoad();
    }

}
