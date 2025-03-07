
import controller.StockQuoteController;

public class Main {

    public static void main(String[] args) {
        QuoteService quoteService = new QuoteService();
        PositionService positionService = new PositionService();
        StockQuoteController controller = new StockQuoteController(quoteService, positionService);
        controller.initClient();
    }
}
