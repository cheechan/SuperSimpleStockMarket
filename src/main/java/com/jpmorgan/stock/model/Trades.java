package com.jpmorgan.stock.model;

import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;

 
public class Trades {
	
	private SortedMap<LocalDateTime, Trade> tradeRecords;
	
	public Trades() {
		this.tradeRecords = new TreeMap<LocalDateTime, Trade>();
	}
	
	public void recordTrade(LocalDateTime timestamp, Trade trade) {
		tradeRecords.put(timestamp, trade);
	}
	
	public SortedMap<LocalDateTime, Trade> getLastTrades(int mins) {
		LocalDateTime fromDateTime = LocalDateTime.now().minusMinutes(mins);	
		return tradeRecords.tailMap(fromDateTime);
	}
}
