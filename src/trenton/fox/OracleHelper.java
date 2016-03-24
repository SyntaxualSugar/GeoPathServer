package trenton.fox;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import trenton.fox.model.CustomLocation;
import trenton.fox.model.CustomPath;
public class OracleHelper {
	private Connection getConnection() throws ClassNotFoundException, SQLException{
		//String driver_class = "oracle.jdbc.driver.OracleDriver";
		//Class.forName (driver_class);
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "SYSTEM";
		String pass = "livestrong";

		Connection conn = DriverManager.getConnection(url,user,pass);
		
		//Connection connection = DriverManager.getConnection(url, user, pass);
		return conn;
		
	}
	
	public void insertLoc(CustomLocation location
			) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();

		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.INSERTLOC(?,?,?,?,?,?,?,?,?,?)}");

			storedproc.setString(1, location.getLocID());
			storedproc.setDouble(2, location.getLat());
			storedproc.setDouble(3, location.getLon());
			storedproc.setString(4, location.getUserID());
			storedproc.setDate(5, new java.sql.Date(location.getTimestamp().getTime()));
			storedproc.setString(6, location.getType());
			storedproc.setString(7, location.getLabel());
			storedproc.setString(8, location.getDescription());
			storedproc.setString(9, location.getPathID());
			storedproc.setInt(10, location.getPosition());

			storedproc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertUser(String userID) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();

		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.INSERTUSER(?)}");

			storedproc.setString(1, userID);

			storedproc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertPath(CustomPath path) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();

		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.INSERTPATH(?,?,?,?)}");

			storedproc.setString(1, path.getPathID());
			storedproc.setString(2, path.getUserID());
			storedproc.setString(3, path.getLabel());
			storedproc.setString(4, path.getDescription());
			
			storedproc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeLoc(String locID) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();

		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.REMOVE_LOC(?)}");

			storedproc.setString(1, locID);
			
			storedproc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removePath(String pathID) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();

		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.REMOVE_PATH(?)}");

			storedproc.setString(1, pathID);
			
			storedproc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public CustomLocation returnLocation(String locID) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();
		CustomLocation location = new CustomLocation();
		
		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.RETURNLOCATION(?,?,?,?,?,?,?,?,?,?,?)}");
			storedproc.registerOutParameter(2, Types.VARCHAR);
			storedproc.registerOutParameter(3, Types.NUMERIC);
			storedproc.registerOutParameter(4, Types.NUMERIC);
			storedproc.registerOutParameter(5, Types.VARCHAR);
			storedproc.registerOutParameter(6, Types.TIMESTAMP_WITH_TIMEZONE);
			storedproc.registerOutParameter(7, Types.VARCHAR);
			storedproc.registerOutParameter(8, Types.VARCHAR);
			storedproc.registerOutParameter(9, Types.VARCHAR);
			storedproc.registerOutParameter(10, Types.VARCHAR);
			storedproc.registerOutParameter(11, Types.INTEGER);
			
			storedproc.setString(1, locID);
			
			storedproc.execute();
			
			location.setLocID(storedproc.getString(2));
			location.setLat(storedproc.getInt(3));
			location.setLon(storedproc.getInt(4));
			location.setUserID(storedproc.getString(5));
			location.setTimestamp(storedproc.getDate(6));
			location.setType(storedproc.getString(7));
			location.setLabel(storedproc.getString(8));
			location.setDescription(storedproc.getString(9));
			location.setPathID(storedproc.getString(10));
			location.setPosition(storedproc.getInt(11));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return location;
	}

	public List returnLocations(String type, String userID) throws ClassNotFoundException, SQLException {
		//THIS NEEDS REWORKING TO ALLOW FOR MULTIPLE RETURNS
		Connection conn = getConnection();
		CustomLocation location = new CustomLocation();
		
		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.RETURNLOCATION(?,?,?,?,?,?,?,?,?,?,?,?)}");
			storedproc.registerOutParameter(3, Types.VARCHAR);
			storedproc.registerOutParameter(4, Types.NUMERIC);
			storedproc.registerOutParameter(5, Types.NUMERIC);
			storedproc.registerOutParameter(6, Types.VARCHAR);
			storedproc.registerOutParameter(7, Types.TIMESTAMP_WITH_TIMEZONE);
			storedproc.registerOutParameter(8, Types.VARCHAR);
			storedproc.registerOutParameter(9, Types.VARCHAR);
			storedproc.registerOutParameter(10, Types.VARCHAR);
			storedproc.registerOutParameter(11, Types.VARCHAR);
			storedproc.registerOutParameter(12, Types.INTEGER);
			
			storedproc.setString(1, type);
			storedproc.setString(2, userID);
			
			storedproc.execute();
			
			location.setLocID(storedproc.getString(2));
			location.setLat(storedproc.getInt(3));
			location.setLon(storedproc.getInt(4));
			location.setUserID(storedproc.getString(5));
			location.setTimestamp(storedproc.getDate(6));
			location.setType(storedproc.getString(7));
			location.setLabel(storedproc.getString(8));
			location.setDescription(storedproc.getString(9));
			location.setPathID(storedproc.getString(10));
			location.setPosition(storedproc.getInt(11));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List list = new ArrayList();
		return list;
	}
}
