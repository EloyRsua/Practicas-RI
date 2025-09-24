package lab1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ejercicio3bOracle {
    public static void main(String args[]) {
	String url = "jdbc:oracle:thin:@156.35.94.98:1521:desa19";
	String user = "UO298184";
	String pass = "pass1493";
	String sql = "UPDATE  TINVOICES set AMOUNT=AMOUNT + ? WHERE STATE='PAID'";

	// NO hace falta el finally ya que java cierra automaticamente los recursos
	try (Connection con = DriverManager.getConnection(url, user, pass); PreparedStatement pst = con.prepareStatement(sql);) {
	    long time = System.currentTimeMillis();

	    for (int i = 0; i <= 100; i++) {
		pst.setInt(1, i);
		pst.executeUpdate();
	    }
	    time = System.currentTimeMillis() - time;
	    System.out.println(time + "ms");

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
}
