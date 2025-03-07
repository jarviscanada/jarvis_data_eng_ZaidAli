package dao;

import models.Quote;
import util.DatabaseConnection;

import java.sql.*;

public class QuoteDao {

    private Connection connection;

    public QuoteDao() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed!", e);
        }
    }

    public void saveQuote(Quote quote) {
        String sql = "INSERT INTO quotes (ticker, open, high, low, price, volume, latest_trading_day, previous_close, change, change_percent, timestamp) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, quote.getTicker());
            pstmt.setDouble(2, quote.getOpen());
            pstmt.setDouble(3, quote.getHigh());
            pstmt.setDouble(4, quote.getLow());
            pstmt.setDouble(5, quote.getPrice());
            pstmt.setInt(6, quote.getVolume());
            pstmt.setDate(7, new java.sql.Date(quote.getLatestTradingDay().getTime()));
            pstmt.setDouble(8, quote.getPreviousClose());
            pstmt.setDouble(9, quote.getChange());
            pstmt.setString(10, quote.getChangePercent());
            pstmt.setTimestamp(11, quote.getTimestamp());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Quote getQuote(String ticker) {
        String sql = "SELECT * FROM quotes WHERE ticker = ? ORDER BY timestamp DESC LIMIT 1";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ticker);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Quote(
                        rs.getString("ticker"),
                        rs.getDouble("open"),
                        rs.getDouble("high"),
                        rs.getDouble("low"),
                        rs.getDouble("price"),
                        rs.getInt("volume"),
                        rs.getDate("latest_trading_day"),
                        rs.getDouble("previous_close"),
                        rs.getDouble("change"),
                        rs.getString("change_percent"),
                        rs.getTimestamp("timestamp")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
