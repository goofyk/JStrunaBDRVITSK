package UMainPack;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;
import java.lang.*;

public class UStruna {

    private Connection conn = null;

    UStruna(){
        conn = getConnection();
    }

    public Connection getConnection(){
        Connection connection = null;
        try{
            String Server = UProperties.getProperty("ServerNameStruna");
            String Port = UProperties.getProperty("ServerPortStruna");
            String PathToDB = UProperties.getProperty("PathToDbStruna");
            Properties props = new Properties();
            props.setProperty("user", UProperties.getProperty("UsernameStruna"));
            props.setProperty("password", UProperties.getProperty("PasswordStruna"));
            props.setProperty("encoding", UProperties.getProperty("EncodingStruna"));
            connection = DriverManager.getConnection(
                    "jdbc:firebirdsql://" + Server + ":" + Port + "/" + PathToDB,
                    props);
            return connection;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    public ResultSet getDataStruna(Connection connection){
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            String sql = new String("SELECT FIRST 2 DATA.P_VALUE FROM GET_LAST_CONF('12-SEP-2018 00:00:00', '0') AS DATA WHERE NOT UPPER(RD.P_NAME) CONTAINING 'USER_NAME'");
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String P_VALUE  = rs.getString("P_VALUE");
                byte[] P_VALUE_b  = rs.getBytes("P_VALUE");
                String P_VALUE_b_s = new String(P_VALUE_b);
                System.out.println("P_VALUE: " + P_VALUE);
                System.out.println("P_VALUE_b: " + P_VALUE_b);
                System.out.println("P_VALUE_b_String: " + P_VALUE_b.toString());
                System.out.println("P_VALUE_b_String: " + P_VALUE_b_s);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet get_params_all(Timestamp first_dattim, Timestamp last_dattim, String type_obj){
        if(conn == null) return null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(UQuery.Q_GET_ALL_DATA_STRUNA);
            pstmt.setTimestamp(1, first_dattim);
            pstmt.setTimestamp(2, last_dattim);
            pstmt.setString(3, type_obj);
            rs = pstmt.executeQuery();
//            while(rs.next()){
//                Date DATTIM  = rs.getDate("DATTIM");
//                System.out.println("DATTIM: " + DATTIM);
//                String P_VALUE  = rs.getString("P_VALUE");
//                System.out.println("P_VALUE: " + P_VALUE);
//                String P_NAME  = rs.getString("P_NAME");
//                System.out.println("P_NAME: " + P_NAME);
//                String P_MSD_ID  = rs.getString("P_MSD_ID");
//                System.out.println("P_MSD_ID: " + P_MSD_ID);
//            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

}
