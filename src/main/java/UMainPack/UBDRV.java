package UMainPack;

import java.sql.*;
import java.time.LocalDate;

public class UBDRV {

    public Connection getConnection(String connectionUrl) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);
            System.out.println("Connection is open!");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void sp_msr_value_send(Connection connection, int p_ffc_id, int p_msd_id, float p_msr_value, Date p_msr_time){
        CallableStatement cs = null;
        boolean fail = false;
        ResultSet rs = null;
        if(p_msr_time == null) p_msr_time = Date.valueOf(LocalDate.now());
        try {
            connection.setAutoCommit(false);
            //for (int i = 1; i <= 1; i++){
            cs = connection.prepareCall("{call dbo.Sp_msr_value_send(?,?,?,?)}");
            cs.setInt(1, p_ffc_id);
            cs.setInt(2, p_msd_id);
            cs.setFloat(3, p_msr_value);
            cs.setDate(4, p_msr_time);
            cs.execute();
            //}
            connection.commit();
        }catch (NullPointerException npe){
            npe.printStackTrace();
        }catch (SQLException se){
            se.printStackTrace();
        }
    }
}
