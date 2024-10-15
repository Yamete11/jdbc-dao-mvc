package solvd.inc.config;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.sql.Connection;

public class ConnectionPool {
    private static ConnectionPool instance;
    private final BlockingQueue<Connection> availableConnections;
    private final int poolSize = 5;
    private final AtomicInteger connectionIdGenerator = new AtomicInteger(1);

    private static final String URL = "jdbc:mysql://localhost:3306/taxi_company";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "8Nlcjqlh";

    private ConnectionPool() {
        this.availableConnections = new ArrayBlockingQueue<>(poolSize);
        try {
            for (int i = 0; i < poolSize; i++) {
                availableConnections.add(createConnection());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing the connection pool", e);
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public CompletableFuture<Connection> acquireConnection() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return availableConnections.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Error acquiring connection from the pool", e);
            }
        });
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                availableConnections.put(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Failed to release connection: " + e.getMessage());
            }
        }
    }


    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void closePool() {
        for (Connection connection : availableConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

