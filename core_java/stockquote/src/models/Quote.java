package models;

import java.sql.Timestamp;
import java.util.Date;

public class Quote {

    private String ticker;
    private double open;
    private double high;
    private double low;
    private double price;
    private int volume;
    private Date latestTradingDay;
    private double previousClose;
    private double change;
    private String changePercent;
    private Timestamp timestamp;

    public Quote(String ticker, double open, double high, double low, double price, int volume, Date latestTradingDay,
            double previousClose, double change, String changePercent, Timestamp timestamp) {
        this.ticker = ticker;
        this.open = open;
        this.high = high;
        this.low = low;
        this.price = price;
        this.volume = volume;
        this.latestTradingDay = latestTradingDay;
        this.previousClose = previousClose;
        this.change = change;
        this.changePercent = changePercent;
        this.timestamp = timestamp;
    }

    public String getTicker() {
        return ticker;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public Date getLatestTradingDay() {
        return latestTradingDay;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public double getChange() {
        return change;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
