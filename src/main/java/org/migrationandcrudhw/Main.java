package org.migrationandcrudhw;

import org.flywaydb.core.Flyway;
import org.migrationandcrudhw.connection.DatabaseConnection;
import org.migrationandcrudhw.crud.ClientService;

public class Main {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "147852")
                .load();
        flyway.migrate();
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        ClientService clientService = new ClientService(databaseConnection);
        clientService.listAll();
        databaseConnection.close();
    }
}