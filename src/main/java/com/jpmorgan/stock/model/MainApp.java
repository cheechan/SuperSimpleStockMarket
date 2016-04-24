package com.jpmorgan.stock.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainApp {

	public static void main(String[] args) {
		
		StockData stockData = new StockData();
		Stock tea = stockData.getStockData("TEA");
		Stock pop = stockData.getStockData("POP");
		Stock ale = stockData.getStockData("ALE");
		Stock gin = stockData.getStockData("GIN");
		Stock joe = stockData.getStockData("JOE");
		List<Stock> listOfStocks = new ArrayList<Stock>();
		listOfStocks.add(tea);
		listOfStocks.add(pop);
		listOfStocks.add(ale);
		listOfStocks.add(gin);
		listOfStocks.add(joe);
		
		tea.buy(10, stockData.generatePrice(), LocalDateTime.now());
		pop.buy(10, stockData.generatePrice(), LocalDateTime.now());
		ale.buy(10, stockData.generatePrice(), LocalDateTime.now());
		gin.buy(10, stockData.generatePrice(), LocalDateTime.now());
		joe.buy(10, stockData.generatePrice(), LocalDateTime.now());
		
		tea.sell(10, stockData.generatePrice(), LocalDateTime.now());
		pop.sell(10, stockData.generatePrice(), LocalDateTime.now());
		ale.sell(10, stockData.generatePrice(), LocalDateTime.now());
		gin.sell(10, stockData.generatePrice(), LocalDateTime.now());
		joe.sell(10, stockData.generatePrice(), LocalDateTime.now());
		
		
		
		
		
		System.out.println("***Dividend Yield***");
		for(Stock stock : listOfStocks) {
			System.out.println("Stock (" + stock.getSymbol() + ") -> " + StockAnalysisCalculationUtils.calculateDividendYield(stock).doubleValue());
		}
		
		System.out.println("***P/E Ratio***");
		for(Stock stock : listOfStocks) {
			if(null != StockAnalysisCalculationUtils.calculatePERatio(stock)) {
				System.out.println("Stock (" + stock.getSymbol() + ") -> " + StockAnalysisCalculationUtils.calculatePERatio(stock).doubleValue());
			} else {
				System.out.println("Stock (" + stock.getSymbol() + ") -> INFINITE");
			}
			
		}
		
		System.out.println("***Volume Weighted Stock Price***");
		for(Stock stock : listOfStocks) {
			System.out.println("Stock (" + stock.getSymbol() + ") -> " + StockAnalysisCalculationUtils.calculateVolumeWeightedStockPrice(stock).doubleValue());
		}
		
		System.out.println("***GBCE All Share Index***");
		System.out.println("Stock Index: " + StockAnalysisCalculationUtils.calculateGBCEAllShareIndex(listOfStocks));

		
	}

}
