package service;

import dao.QuoteDao;
import models.Quote;
import util.AlphaVantageAPI;

public class QuoteService {

    private final QuoteDao quoteDao;

    public QuoteService(QuoteDao quoteDao) {
        this.quoteDao = quoteDao;
    }

    public void fetchAndSaveQuote(String ticker) {
        Quote quote = AlphaVantageAPI.fetchQuote(ticker);
        if (quote != null) {
            quoteDao.saveQuote(quote);
            System.out.println("Quote saved: " + quote.getTicker());
        } else {
            System.out.println("Failed to fetch quote for " + ticker);
        }
    }

    public Quote getLatestQuote(String ticker) {
        return quoteDao.getQuote(ticker);
    }
}
