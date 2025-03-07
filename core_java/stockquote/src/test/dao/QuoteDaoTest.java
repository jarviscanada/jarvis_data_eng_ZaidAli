package test.dao;

import dao.QuoteDao;
import models.Quote;
import org.junit.jupiter.api.*;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuoteDaoTest {

    private Connection connection;
    private QuoteDao quoteDao;

    @BeforeAll
    public void setup() throws SQLException {
        connection = DatabaseConnection.getTestConnection();
        quoteDao = new QuoteDao(connection);
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS quotes (symbol VARCHAR(10) PRIMARY KEY, price DECIMAL(10,2))");
    }

    @BeforeEach
    public void cleanTable() throws SQLException {
        connection.createStatement().execute("DELETE FROM quotes");
    }

    @Test
    public void testSaveQuote() throws SQLException {
        Quote quote = new Quote("MSFT", 350.00);
        quoteDao.saveQuote(quote);

        Optional<Quote> retrieved = quoteDao.getQuote("MSFT");
        assertTrue(retrieved.isPresent());
        assertEquals(350.00, retrieved.get().getPrice());
    }

    @Test
    public void testGetNonExistingQuote() throws SQLException {
        Optional<Quote> retrieved = quoteDao.getQuote("AAPL");
        assertFalse(retrieved.isPresent());
    }

    @AfterAll
    public void teardown() throws SQLException {
        connection.close();
    }
}
