package com.jpmorgan.stock.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Trade {
	
	private LocalDateTime timestampOfTrade;
	private String tradeType;
	private Integer quantity;
	private BigDecimal price;

	public String getType() {
		return tradeType;
	}

	public void setType(String type) {
		this.tradeType = type;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Trade(String tradeType, Integer quantity, BigDecimal price) {
		this.tradeType = tradeType;
		this.quantity = quantity;
		this.price = price;
	}

	/**
	 * @return the timestampOfTrade
	 */
	public LocalDateTime getTimestampOfTrade() {
		return timestampOfTrade;
	}

	/**
	 * @param timestampOfTrade the timestampOfTrade to set
	 */
	public void setTimestampOfTrade(LocalDateTime timestampOfTrade) {
		this.timestampOfTrade = timestampOfTrade;
	}

}
