package com.jpmorgan.stock.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedMap;

public class StockAnalysisCalculationUtils {

	
	public static BigDecimal calculateDividendYield(Stock stock) {
		if("COMMON".equals(stock.getType())) {
			return stock.getLastDividend().divide(stock.getPrice(),2);
		} else if("PREFERRED".equals(stock.getType())) {
			return stock.getFixedDividend().multiply(stock.getParValue()).divide(stock.getPrice(),2);
		}
		return new BigDecimal("0.0");
	}
	 
	public static BigDecimal calculatePERatio(Stock stock) {
		if(stock.getLastDividend().compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		return stock.getPrice().divide(stock.getLastDividend(),2);
	}
	
	/**
	 * Calculate the Volume Weighted Stock Price 
	 * 
	 * @return The Volume Weighted Stock Price
	 */
	public static BigDecimal calculateVolumeWeightedStockPrice(Stock stock) {

		// Get trades for the last 15 minutes
		int timeSpanOfLastTrades = 15;
		SortedMap<LocalDateTime, Trade> trades = stock.getTrades().getLastTrades(timeSpanOfLastTrades);
		// Calculate
		BigDecimal volumeWeigthedStockPrice = BigDecimal.ZERO;
		int totalQuantity = 0;
		for (Trade trade: trades.values()) {
			totalQuantity += trade.getQuantity();
			volumeWeigthedStockPrice = volumeWeigthedStockPrice.add(trade.getPrice().multiply(new BigDecimal(trade.getQuantity())));
		}
		return volumeWeigthedStockPrice.divide(new BigDecimal(totalQuantity),2);
	}
	
	public static double calculateGBCEAllShareIndex(List<Stock> stockList) {		
		BigDecimal nthRoot = new BigDecimal(1).divide(new BigDecimal(stockList.size()));
		BigDecimal productOfStockPrice = new BigDecimal(1);
		for(Stock stock : stockList) {
			productOfStockPrice = productOfStockPrice.multiply(stock.getPrice());
		}	
		return Math.pow(productOfStockPrice.doubleValue(), nthRoot.doubleValue());		
	}
	

}
