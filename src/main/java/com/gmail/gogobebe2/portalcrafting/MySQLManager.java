package com.gmail.gogobebe2.portalcrafting;

import code.husky.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLManager {
    private PortalCrafting plugin;
    private MySQL db;

    private String hostName;
    private String port;
    private String databaseName;
    private String username;
    private String password;

    public MySQLManager(PortalCrafting plugin) {
        this.plugin = plugin;
    }

    public void setupDB() throws SQLException {
        db = new MySQL(plugin, hostName, port, databaseName, username, password);
        try {
            db.openConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Statement statement = db.getConnection().createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS 'portals' (" +
                "'ID' int, " +
                "'PartnerID' int, " +
                "'Type' varchar(32), " +
                "'Location.X' double, " +
                "'Location.Y' double, " +
                "'Location.Z' double, " +
                "'Location.World' varchar(32))");
        statement.close();
    }

    public void closeDB() throws SQLException {
        db.closeConnection();
    }

    private ResultSet getResultSet(String column, int ID) throws SQLException, NullPointerException {
        if(!db.checkConnection()) {
            try {
                db.openConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        Statement statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT '" + column + "' FROM 'portals' WHERE 'ID' = '" + ID + "';");
        statement.close();
        if (isEmpty(rs)) {
            throw new NullPointerException();
        }
        return rs;
    }

    private boolean isEmpty(ResultSet rs) throws SQLException {
       return !rs.next();
    }

    public int getPartnerID(int ID) throws SQLException {
        String column = "PartnerID";
        ResultSet rs = getResultSet(column, ID);
        return rs.getInt(column);
    }

    public void setPartnerID(int ID) throws SQLException {
        String column = "PartnerID";
        if (!db.checkConnection()) {
            try {
                db.openConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        Statement statement = db.getConnection().createStatement();

    }

    public PortalType getType(int ID) throws SQLException {
        String column = "Type";
        ResultSet rs = getResultSet(column, ID);
        return PortalType.valueOf(rs.getString(column));
    }

    // TODO: do all sql shit.
}
