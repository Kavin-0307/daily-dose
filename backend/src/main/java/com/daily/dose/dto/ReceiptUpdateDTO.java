package com.daily.dose.dto;
//ReceiptUpdateDTO.java
public class ReceiptUpdateDTO {

 private String receiptDescription;
 private String receiptPaymentMethod;
 private Boolean receiptPaid;

 public void setReceiptDescription(String receiptDescription) {
	 this.receiptDescription=receiptDescription;
 }
 public void setReceiptPaymentMethod(String receiptPaymentMethod) {
	 this.receiptPaymentMethod=receiptPaymentMethod;
 }
 public void setReceiptPaid(boolean receiptPaid) {
	 this.receiptPaid=receiptPaid;
 }
 public String getReceiptDescription() {
	 return receiptDescription;
 }
 public String getReceiptPaymentMethod() {
	 return receiptPaymentMethod;
 }
 public Boolean getReceiptPaid() {
	 return receiptPaid;
 }
 }
