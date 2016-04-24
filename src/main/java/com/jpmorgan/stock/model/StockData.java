package com.jpmorgan.stock.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class StockData {
	
	private Map<String, Stock> stockMap;
	private Map<String, Trades> tradeMap;
	
	public StockData() {
		this.stockMap = new HashMap<String, Stock>();
		stockMap.put("TEA", new Stock("TEA", "COMMON", new BigDecimal("0.0"), new BigDecimal("0.0"), new BigDecimal("100.0"), generatePrice()));
		stockMap.put("POP", new Stock("POP", "COMMON", new BigDecimal("8.0"), new BigDecimal("0.0"), new BigDecimal("100.0"), generatePrice()));
		stockMap.put("ALE", new Stock("ALE", "COMMON", new BigDecimal("23.0"), new BigDecimal("0.0"), new BigDecimal("60.0"), generatePrice()));
		stockMap.put("GIN", new Stock("GIN", "PREFERRED", new BigDecimal("8.0"), new BigDecimal("0.2"), new BigDecimal("100.0"), generatePrice()));
		stockMap.put("JOE", new Stock("JOE", "COMMON", new BigDecimal("13.0"), new BigDecimal("0.0"), new BigDecimal("250.0"), generatePrice()));
		
		this.tradeMap = new HashMap<String, Trades>();
		tradeMap.put("TEA", new Trades());
		tradeMap.put("POP", new Trades());
		tradeMap.put("ALE", new Trades());
		tradeMap.put("GIN", new Trades());
		tradeMap.put("JOE", new Trades());
		
		
	}
	
	
	public Stock getStockData(String stockName) {	
		return stockMap.get(stockName);
	}
	
	public BigDecimal generatePrice() {
		return new BigDecimal((int) (Math.random() * 9000) / 100.0);
	}

}
