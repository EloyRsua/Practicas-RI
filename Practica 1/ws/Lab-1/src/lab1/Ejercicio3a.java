package lab1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio3a {
    public static void main(String args[]) {
	String url = "jdbc:hsqldb:hsql://localhost";
	String user = "sa";
	String pass = "";

	// NO hace falta el finally ya que java cierra automaticamente los recursos
	try (Connection con = DriverManager.getConnection(url, user, pass); Statement st = con.createStatement();) {
	    long time = System.currentTimeMillis();

	    for (int i = 0; i <= 100; i++) {
		st.executeUpdate("UPDATE  TINVOICES set AMOUNT=AMOUNT +" + i + "WHERE STATE='PAID'");
	    }

	    time = System.currentTimeMillis() - time;
	    System.out.println(time + "ms");
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
}
