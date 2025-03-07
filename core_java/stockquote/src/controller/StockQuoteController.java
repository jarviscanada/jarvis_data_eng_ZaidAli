
import java.util.Scanner;

public class StockQuoteController {

    private QuoteService quoteService;
    private PositionService positionService;
    private Scanner scanner;

    public StockQuoteController(QuoteService quoteService, PositionService positionService) {
        this.quoteService = quoteService;
        this.positionService = positionService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Initializes the user interface for our application.
     */
    public void initClient() {
        System.out.println("Welcome to the Stock Trading Application!");

        while (true) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. View stock quote");
            System.out.println("2. Buy stock");
            System.out.println("3. Sell stock");
            System.out.println("4. View portfolio");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewStockQuote();
                    break;
                case "2":
                    buyStock();
                    break;
                case "3":
                    sellStock();
                    break;
                case "4":
                    viewPortfolio();
                    break;
                case "5":
                    System.out.println("Exiting application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid input! Please enter a valid option.");
            }
        }
    }

    /**
     * Fetches and displays stock quote information.
     */
    private void viewStockQuote() {
        System.out.print("Enter stock ticker symbol: ");
        String ticker = scanner.nextLine().toUpperCase();
        Quote quote = quoteService.getQuote(ticker);

        if (quote != null) {
            System.out.println("\nStock Quote for: " + ticker);
            System.out.println("Open Price: " + quote.getOpenPrice());
            System.out.println("Current Price: " + quote.getCurrentPrice());
            System.out.println("Change Percent: " + quote.getChangePercent());
        } else {
            System.out.println("Error fetching stock data. Please check the symbol and try again.");
        }
    }

    /**
     * Handles buying stocks and adds them to the portfolio.
     */
    private void buyStock() {
        System.out.print("Enter stock ticker symbol: ");
        String ticker = scanner.nextLine().toUpperCase();
        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        boolean success = positionService.buyStock(ticker, quantity);
        if (success) {
            System.out.println("Successfully purchased " + quantity + " shares of " + ticker);
        } else {
            System.out.println("Error buying stock. Please check the ticker symbol and try again.");
        }
    }

    /**
     * Handles selling stocks from the portfolio.
     */
    private void sellStock() {
        System.out.print("Enter stock ticker symbol: ");
        String ticker = scanner.nextLine().toUpperCase();
        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        boolean success = positionService.sellStock(ticker, quantity);
        if (success) {
            System.out.println("Successfully sold " + quantity + " shares of " + ticker);
        } else {
            System.out.println("Error selling stock. Check if you have enough shares.");
        }
    }

    /**
     * Displays the user's portfolio and calculates profit/loss.
     */
    private void viewPortfolio() {
        System.out.println("\nYour Portfolio:");
        positionService.viewPortfolio();
    }
}
