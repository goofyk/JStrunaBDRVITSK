package UMainPack;

import java.sql.*;
import java.time.LocalDate;

public class UBDRV {

    private Connection conn = null;

    UBDRV(){
        conn = getConnection();
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            String ServerDB = UProperties.getProperty("ServerNameBDRV");
            String PortDB = UProperties.getProperty("ServerPortBDRV");
            String NameDB = UProperties.getProperty("NameDbBDRV");
            String UserDB = UProperties.getProperty("UsernameBDRV");
            String PswdDB = UProperties.getProperty("PasswordBDRV");
            String connectionUrl = "jdbc:sqlserver://" + ServerDB + ":" + PortDB + ";" +
                                    "databaseName=" + NameDB + ";" +
                                    "user=" + UserDB + ";" +
                                    "password=" +  PswdDB;
            connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException se) {
            ULogger.log.error(se.getMessage());
            se.printStackTrace();
        } catch (Exception e) {
            ULogger.log.error(e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public void sp_msr_value_send(ResultSet DataStruna){
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            conn.setAutoCommit(false);
            int countLoaded = 0;
            String ffcId = UProperties.getProperty("IdTZK");
            while(DataStruna.next()) {
               String msdId = rs.getString("P_MSD_ID");
               String msrValue = rs.getString("P_VALUE");
               Date msrTime = rs.getDate("DATTIM");

               cs = conn.prepareCall("{call dbo.Sp_msr_value_send(?,?,?,?)}");
               cs.setInt(1, Integer.valueOf(ffcId));
               cs.setInt(2, Integer.valueOf(msdId));
               cs.setFloat(3, Float.valueOf(msrValue));
               cs.setDate(4, msrTime);

               cs.execute();
               countLoaded++;
            }
            conn.commit();
            ULogger.log.info("В базу БДРВ из базы Струна загружено " + countLoaded + " записей!");
        }catch (NullPointerException npe){
            ULogger.log.error(npe.getMessage());
            npe.printStackTrace();
        }catch (SQLException se){
            ULogger.log.error(se.getMessage());
            se.printStackTrace();
        }
    }
}
