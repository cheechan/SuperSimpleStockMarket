package com.jpmorgan.stock.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Stock {
	
	private String symbol;
	private String type;
	private BigDecimal lastDividend;
	private BigDecimal fixedDividend;
	private BigDecimal parValue;
	private BigDecimal price;
	private Trades trades;
	
	public Stock(String symbol, String type, BigDecimal lastDividend, BigDecimal fixedDividend, BigDecimal parValue, BigDecimal price) {
		this.symbol = symbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
		this.price = price;
		this.trades = new Trades();
	}
	
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}

	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(BigDecimal fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public BigDecimal getParValue() {
		return parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	/**
	 * @return the trades
	 */
	public Trades getTrades() {
		return trades;
	}


	/**
	 * @param trades the trades to set
	 */
	public void setTrades(Trades trades) {
		this.trades = trades;
	}


	public void buy(int quantity, BigDecimal tradePrice, LocalDateTime timeOfTrade) {
		trades.recordTrade(timeOfTrade, new Trade("BUY", quantity, tradePrice));
	}
	
	public void sell(int quantity, BigDecimal tradePrice, LocalDateTime timeOfTrade) {	
		trades.recordTrade(timeOfTrade, new Trade("SELL", quantity, tradePrice));		
	}

}
