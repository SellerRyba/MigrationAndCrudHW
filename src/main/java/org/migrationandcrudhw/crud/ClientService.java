package org.migrationandcrudhw.crud;

import org.migrationandcrudhw.connection.DatabaseConnection;
import org.migrationandcrudhw.data.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private PreparedStatement selectStatement;
    private PreparedStatement selectByName;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement selectAllStatement;

    public ClientService(DatabaseConnection databaseConnection) {

        Connection connection = databaseConnection.getConnection();
        try {
            this.selectStatement = connection
                    .prepareStatement("select * from client where id = ?");
            this.insertStatement = connection
                    .prepareStatement("insert into client (name) VALUES (?)");
            this.updateStatement = connection
                    .prepareStatement("update client set name = ? where id = ?");
            this.deleteByIdStatement = connection
                    .prepareStatement("delete from client where id = ?");
            this.selectAllStatement = connection
                    .prepareStatement("select * from client");
            this.selectByName = connection
                    .prepareStatement("select * from client where name = ?");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public long create(Client client) {
        long id = 0;
        try {
                insertStatement.setString(1, client.getName());
                insertStatement.executeUpdate();
                selectByName.setString(1, client.getName());
                ResultSet rs = selectByName.executeQuery();
                while (rs.next()){
                    id = rs.getLong("id");
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public String getById(long id){
        String name = null;
        try {
            selectStatement.setLong(1, id);
            ResultSet rs = selectStatement.executeQuery();

            while (rs.next()){
                name = rs.getString("name");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return name;
    }
    public void setName(long id, String name){
        try {
            updateStatement.setString(1, name);
            updateStatement.setLong(2, id);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteById(long id){
        try {
            deleteByIdStatement.setLong(1, id);
            deleteByIdStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Client> listAll(){
        List<Client> result = new ArrayList<>();
        try {
            ResultSet rs = selectAllStatement.executeQuery();
            while (rs.next()){
                long id = rs.getLong("id");
                String name = rs.getString("name");
                result.add(new Client(id,name));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
