package UMainPack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;
import java.lang.*;

public class UStruna {

    public Connection getConnection(){
        try{
            String Server = UProperties.getProperty("ServerNameStruna");
            String Port = UProperties.getProperty("ServerPortStruna");
            String PathToDB = UProperties.getProperty("PathToDbStruna");
            Properties props = new Properties();
//            props.setProperty("user", "SYSDBA");
//            props.setProperty("password", "masterkey");
//            props.setProperty("encoding", "ASCII");//
            props.setProperty("user", UProperties.getProperty("UsernameStruna"));
            props.setProperty("password", UProperties.getProperty("PasswordStruna"));
            props.setProperty("encoding", UProperties.getProperty("EncodingStruna"));
            Connection connection = DriverManager.getConnection(
                    "jdbc:firebirdsql://" + Server + ":" + Port + "/" + PathToDB,
                    props);
            return connection;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getDataStruna(Connection connection){
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            String sql = "SELECT FIRST 2 rd.P_VALUE FROM GET_LAST_CONF('12-SEP-2018 00:00:00', '0') AS RD WHERE NOT UPPER(RD.P_NAME) CONTAINING 'USER_NAME'";
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

}
