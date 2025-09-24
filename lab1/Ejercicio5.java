package lab1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

public class Ejercicio5 {

    private static final String URL = "jdbc:hsqldb:hsql://localhost";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static void main(String[] args) throws Exception {
	int operaciones = 1000;

	// Escenario 1: sin pool de conexiones
	long inicioNoPool = System.currentTimeMillis();
	for (int i = 0; i < operaciones; i++) {
	    Connection con = DriverManager.getConnection(URL, USER, PASS);
	    Statement stmt = con.createStatement();
	    stmt.executeUpdate("UPDATE TINVOICES set AMOUNT=AMOUNT+" + i + " WHERE STATE='PAID'");
	    stmt.close();
	    con.close();
	}
	long finNoPool = System.currentTimeMillis();
	System.out.println("Tiempo sin pool: " + (finNoPool - inicioNoPool) + " ms");

	// Escenario 2: con pool de conexiones (c3p0)
	DataSource ds_unpooled = DataSources.unpooledDataSource(URL, USER, PASS);
	Map<String, Object> overrides = new HashMap<>();
	overrides.put("minPoolSize", 3);
	overrides.put("maxPoolSize", 15);
	overrides.put("initialPoolSize", 3);
	DataSource ds_pooled = DataSources.pooledDataSource(ds_unpooled, overrides);

	long inicioPool = System.currentTimeMillis();
	for (int i = 0; i < operaciones; i++) {
	    Connection con = ds_pooled.getConnection();
	    Statement stmt = con.createStatement();
	    stmt.executeUpdate("UPDATE TINVOICES set AMOUNT=AMOUNT+" + i + " WHERE STATE='PAID'");
	    stmt.close();
	    con.close(); // importante -> devuelve al pool
	}
	long finPool = System.currentTimeMillis();
	System.out.println("Tiempo con pool: " + (finPool - inicioPool) + " ms");
    }
}
