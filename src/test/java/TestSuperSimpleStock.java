import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.stock.model.Stock;
import com.jpmorgan.stock.model.StockAnalysisCalculationUtils;
import com.jpmorgan.stock.model.StockData;
import com.jpmorgan.stock.model.Trade;
import com.jpmorgan.stock.model.Trades;


public class TestSuperSimpleStock {
	final static long TWENTY_MINUTES = 20;
	final static int FIFTEEN_MINUTES = 15;
	private StockData stockData;

	@Before
	public void setUp() throws Exception {
		stockData = new StockData();
	}
	
	@Test
	public void whenRecordATradeCalledThenShouldStoreTradeDetailsCorrectly() {
		Trades trades = new Trades();
		Trade trade = new Trade("BUY", 12, new BigDecimal("123.00"));
		LocalDateTime tradeTimestamp = LocalDateTime.now();
		trades.recordTrade(tradeTimestamp, trade);
		Map<LocalDateTime, Trade> lastTrade = trades.getLastTrades(FIFTEEN_MINUTES);
		assertEquals(1, lastTrade.size());
		for(Trade t : lastTrade.values()) {
			assertEquals(new BigDecimal("123.00"), t.getPrice());
			assertEquals("BUY", t.getType());
			assertEquals(12, t.getQuantity().intValue());
 		}
	}
	
	@Test
	public void whenGetLastTradeCalledForLast15MinThenShouldReturnCorrectTrades() {
		Trades trades = new Trades();
		Trade trade1 = new Trade("BUY", 12, new BigDecimal("123.00"));
		Trade trade2 = new Trade("BUY", 9, new BigDecimal("10.00"));
		LocalDateTime tradeTimestampWithinTime = LocalDateTime.now();

		LocalDateTime tradeTimestampOutwithTime = LocalDateTime.now().minusMinutes(TWENTY_MINUTES);
		trades.recordTrade(tradeTimestampWithinTime, trade1);
		trades.recordTrade(tradeTimestampOutwithTime, trade2);
		Map<LocalDateTime, Trade> lastTrade = trades.getLastTrades(FIFTEEN_MINUTES);
		assertEquals(1, lastTrade.size());
		for(Trade t : lastTrade.values()) {
			assertEquals(new BigDecimal("123.00"), t.getPrice());
			assertEquals("BUY", t.getType());
			assertEquals(12, t.getQuantity().intValue());
 		}
	}
	
	@Test
	public void whenBuyCalledThenRecordCorrectTrade() {
		Stock pop = stockData.getStockData("POP");
		BigDecimal stockPrice = stockData.generatePrice();
		pop.buy(123, stockPrice, LocalDateTime.now());		
		Map<LocalDateTime, Trade> trades = pop.getTrades().getLastTrades(FIFTEEN_MINUTES);
		assertEquals(1, trades.size());
		for(Trade t : trades.values()) {
			assertEquals(stockPrice, t.getPrice());
			assertEquals("BUY", t.getType());
			assertEquals(123, t.getQuantity().intValue());
 		}
	}
	
	@Test
	public void whenSellCalledThenRecordCorrectTrade() {
		Stock pop = stockData.getStockData("ALE");
		BigDecimal stockPrice = stockData.generatePrice();
		pop.sell(5, stockPrice, LocalDateTime.now());		
		Map<LocalDateTime, Trade> trades = pop.getTrades().getLastTrades(FIFTEEN_MINUTES);
		assertEquals(1, trades.size());
		for(Trade t : trades.values()) {
			assertEquals(stockPrice, t.getPrice());
			assertEquals("SELL", t.getType());
			assertEquals(5, t.getQuantity().intValue());
 		}
	}
	
	@Test
	public void whenCalculateDividendYieldCalledForCOMMONTypeStockThenReturnCorrectAnswer() {
		
		Stock pop = stockData.getStockData("POP");
		BigDecimal stockPrice = stockData.generatePrice();
		pop.setPrice(stockPrice);
		BigDecimal dividendYield = pop.getLastDividend().divide(stockPrice,2);
		BigDecimal calculatedDividendYield = StockAnalysisCalculationUtils.calculateDividendYield(pop);
		assertEquals(dividendYield, calculatedDividendYield);	
	}
	
	@Test
	public void whenCalculateDividendYieldCalledForPREFERREDTypeStockThenReturnCorrectAnswer() {
		
		Stock gin = stockData.getStockData("GIN");
		BigDecimal stockPrice = stockData.generatePrice();
		gin.setPrice(stockPrice);
		BigDecimal dividendYield = gin.getFixedDividend().multiply(gin.getParValue()).divide(stockPrice,2);
		BigDecimal calculatedDividendYield = StockAnalysisCalculationUtils.calculateDividendYield(gin);
		assertEquals(dividendYield, calculatedDividendYield);	
	}
	
	@Test
	public void whenCalculatePERatioCalledThenReturnNullForAZeroLastDividend() {
		Stock tea = stockData.getStockData("TEA");
		BigDecimal stockPrice = stockData.generatePrice();
		tea.setPrice(stockPrice);
		
		assertEquals(null, StockAnalysisCalculationUtils.calculatePERatio(tea));
	}
	
	@Test
	public void whenCalculatePERatioCalledThenReturnCorrectAnswer() {
		Stock pop = stockData.getStockData("POP");
		BigDecimal stockPrice = stockData.generatePrice();
		pop.setPrice(stockPrice);
		BigDecimal peRation = stockPrice.divide(pop.getLastDividend(),2);
		assertEquals(peRation, StockAnalysisCalculationUtils.calculatePERatio(pop));
	}
	
	@Test
	public void whenCalculateVolumeWeightedStockPriceThenReturnCorrectAnswer() {
		
		Stock joe = stockData.getStockData("JOE");
		BigDecimal tradePrice1 = stockData.generatePrice();
		BigDecimal tradePrice2 = stockData.generatePrice();
		BigDecimal tradePrice3 = stockData.generatePrice();
		BigDecimal tradePrice4 = stockData.generatePrice();
		
		joe.buy(1, tradePrice1, LocalDateTime.now());
		joe.buy(10, tradePrice2, LocalDateTime.now().minusMinutes(5));
		joe.buy(100, tradePrice3, LocalDateTime.now().minusMinutes(10));
		
		// A trade placed more than 15 mins ago
		joe.buy(1000, tradePrice4, LocalDateTime.now().minusMinutes(TWENTY_MINUTES));
		
		// calculate expectedVWStockPrice
		int totalQuantityOfTrade = 1 + 10 + 100;
		BigDecimal totalProductOfTradePriceAndQuantity = 
				((tradePrice1.multiply(new BigDecimal(1))
						.add(tradePrice2.multiply(new BigDecimal(10)))
						.add(tradePrice3.multiply(new BigDecimal(100)))));

		BigDecimal expectedVWStockPrice = totalProductOfTradePriceAndQuantity.divide(new BigDecimal(totalQuantityOfTrade),2);
		BigDecimal actualVWStockPrice = StockAnalysisCalculationUtils.calculateVolumeWeightedStockPrice(joe);
		
		assertEquals(expectedVWStockPrice, actualVWStockPrice);		
	}
	
	@Test
	public void whenCalculateGBCEAllShareIndexCalledThenReturnCorrectAnswer() {
		
		Stock tea = stockData.getStockData("TEA");
		Stock pop = stockData.getStockData("POP");
		Stock ale = stockData.getStockData("ALE");
		Stock gin = stockData.getStockData("GIN");
		Stock joe = stockData.getStockData("JOE");
		
		List<Stock> stockList = new ArrayList<Stock>();
		stockList.add(tea);
		stockList.add(pop);
		stockList.add(ale);
		stockList.add(gin);
		stockList.add(joe);
		
		double teaStockPrice = tea.getPrice().doubleValue();
		double popStockPrice = pop.getPrice().doubleValue();
		double aleStockPrice = ale.getPrice().doubleValue();
		double ginStockPrice = gin.getPrice().doubleValue();
		double joeStockPrice = joe.getPrice().doubleValue();
		
		double numberOfStock = 5;
		double nthRoot = 1 / numberOfStock;
		double productOfAllStockPrice = teaStockPrice * popStockPrice * aleStockPrice * ginStockPrice * joeStockPrice;
		double actualShareIndex = StockAnalysisCalculationUtils.calculateGBCEAllShareIndex(stockList);		
		double expectedShareIndex = Math.pow(productOfAllStockPrice, nthRoot);
		
		assertEquals(new BigDecimal(expectedShareIndex), new BigDecimal(actualShareIndex));	
	}

}
