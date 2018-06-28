package com.dari.astro.mappers;

import org.springframework.stereotype.Component;

import com.dari.astro.bos.KPNatalHoroscope;
import com.dari.astro.bos.TransactionHistory;
import com.dari.astro.utils.TransactionBean;

@Component("transactionHistoryMapper")
public class TransactionHistoryMapper {
	
	public TransactionHistory mapTransactionHistory(String transactionDate,int id,TransactionBean transactionBean) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setOwnerNumber(transactionBean.getOwnerNumber());
		transactionHistory.setOwnerEmail(transactionBean.getOwnerEmail());
		transactionHistory.setTransaction_Amount(transactionBean.getTransaction_Amount());
		transactionHistory.setTransaction_Date(transactionDate);
		transactionHistory.setTransaction_Device_ID(transactionBean.getTransaction_Device_ID());
		transactionHistory.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
		transactionHistory.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
		transactionHistory.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
		transactionHistory.setTransaction_Id(transactionBean.getTransaction_Id());
		transactionHistory.setTransaction_Status(transactionBean.getTransaction_Status());
		transactionHistory.setTransactionChartId(id);
		
		
		return transactionHistory;
	}

}
