package lab1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio1 {
    public static void main(String args[]) {
	String url = "jdbc:hsqldb:hsql://localhost";
	String user = "sa";
	String pass = "";
	String sql = "SELECT name,surname FROM TMECHANICS";

	// NO hace falta el finally ya que java cierra automaticamente los recursos
	try (Connection con = DriverManager.getConnection(url, user, pass); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql);) {

	    while (rs.next()) {
		System.out.println(rs.getString("name") + ", " + rs.getString("surname"));

	    }

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
