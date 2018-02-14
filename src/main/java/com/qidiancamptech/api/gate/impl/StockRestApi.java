package com.qidiancamptech.api.gate.impl;


import com.qidiancamptech.api.IStockRestApi;
import com.qidiancamptech.api.gate.HttpUtilManager;
import com.qidiancamptech.api.gate.MD5Util;
import com.qidiancamptech.api.gate.StringUtil;
import org.apache.http.HttpException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class StockRestApi implements IStockRestApi {

	private String secret_key;
	
	private String api_key;
	
	private String url_prex;
	
	public StockRestApi(String url_prex, String api_key, String secret_key){
		this.api_key = api_key;
		this.secret_key = secret_key;
		this.url_prex = url_prex;
	}
	
	public StockRestApi(String url_prex){
		this.url_prex = url_prex;
	}
	


	public final String PAIRS_URL = "/api2/1/pairs";


	public final String MARKETINFO_URL = "/api2/1/marketinfo";


	public final String MARKETLIST_URL = "/api2/1/marketlist";


	public final String TICKERS_URL = "/api2/1/tickers";

	
	public final String TICKER_URL = "/api2/1/ticker";

	
	public final String ORDERBOOK_URL = "/api2/1/orderBook";


	public final String BALANCE_URL = "/api2/1/private/balances";

	
	private final String DEPOSITADDRESS_URL = "/api2/1/private/depositAddress";

	
	private final String DEPOSITESWITHDRAWALS_URL = "/api2/1/private/depositsWithdrawals";

	
	private final String BUY_URL = "/api2/1/private/buy";

	
	private final String SELL_URL = "/api2/1/private/sell";

	
	private final String CANCELORDER_URL = "/api2/1/private/cancelOrder";

	
	private final String CANCELALLORDERS_URL = "/api2/1/private/cancelAllOrders";

	
	private final String GETORDER_URL = "/api2/1/private/getOrder";


	private final String OPENORDERS_URL = "/api2/1/private/openOrders";

	
	private final String TRADEHISTORY_URL = "/api2/1/tradeHistory";


	private final String WITHDRAW_URL = "/api2/1/private/withdraw";
	

	private final String MYTRADEHISTORY_URL = "/api2/1/private/tradeHistory";









	@Override
	public String pairs() throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		String result = httpUtil.requestHttpGet(url_prex, PAIRS_URL, param);
	    return result;
	}

	@Override
	public String marketinfo() throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		String result = httpUtil.requestHttpGet(url_prex, MARKETINFO_URL, param);
		return result;
	}

	@Override
	public String marketlist() throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		String result = httpUtil.requestHttpGet(url_prex, MARKETLIST_URL, param);
		return result;
	}

	@Override
	public String tickers() throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		String result = httpUtil.requestHttpGet(url_prex, TICKERS_URL, param);
		return result;
	}

	@Override
	public String ticker(String symbol) throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		
		param += "/"+symbol;
		
		String result = httpUtil.requestHttpGet(url_prex, TICKER_URL + param, "");
		return result;
	}

	@Override
	public String orderBook(String symbol) throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		if(!StringUtil.isEmpty(symbol)) {
			if(param.equals("")) {
				param += "/";
			}
			param += symbol;
		}
		String result = httpUtil.requestHttpGet(url_prex, ORDERBOOK_URL + param, param);
		return result;
	}

	@Override
	public String tradeHistory(String symbol) throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		if(!StringUtil.isEmpty(symbol )) {
			if(param.equals("")) {
				param += "/";
			}
			param += symbol;
		}
		System.out.println(TRADEHISTORY_URL+param);
		String result = httpUtil.requestHttpGet(url_prex, TRADEHISTORY_URL + param, "");
		return result;
	}

	@Override
	public String balance() throws HttpException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("api_key", api_key);
		String sign = MD5Util.buildMysignV1(params, this.secret_key);
		params.put("sign", sign);

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();

		String result = httpUtil.doRequest( "data", "post", url_prex+BALANCE_URL, params );
		return result;
	}
	

	@Override
	public String depositAddress(String symbol) throws HttpException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("currency", api_key);

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.doRequest( "data", "post", url_prex+DEPOSITADDRESS_URL, params );
		return result;
	}
	

	@Override
	public String depositsWithdrawals(String startTime,String endTime) throws HttpException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("start", startTime);
		params.put("end", endTime);


		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.doRequest( "data", "post", url_prex+ DEPOSITESWITHDRAWALS_URL, params );
		return result;
	}
	
	@Override
	public String buy(String currencyPair,String rate, String amount) throws HttpException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("currencyPair", currencyPair);
		params.put("rate", rate);
		params.put("amount", amount);

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.doRequest( "data", "post", url_prex+ BUY_URL, params );
		return result;
	}
	
	@Override
	public String sell(String currencyPair,String rate, String amount) throws HttpException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("currencyPair", currencyPair);
		params.put("rate", rate);
		params.put("amount", amount);

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.doRequest( "data", "post", url_prex+ SELL_URL, params );
		return result;
	}
	
	
	@Override
	public String cancelOrder(String orderNumber,String currencyPair) throws HttpException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderNumber", orderNumber);
		params.put("currencyPair", currencyPair);

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.doRequest( "data", "post", url_prex+ CANCELORDER_URL, params );
		return result;
	}
	
	@Override
	public String cancelAllOrders(String type,String currencyPair) throws HttpException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", type);
		params.put("currencyPair", currencyPair);

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.doRequest( "data", "post", url_prex+ CANCELALLORDERS_URL, params );
		return result;
	}
	
	@Override
	public String getOrder(String orderNumber,String currencyPair) throws HttpException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderNumber", orderNumber);
		params.put("currencyPair", currencyPair);

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.doRequest( "data", "post", url_prex+ GETORDER_URL, params );
		return result;
	}
	
	
	@Override
	public String openOrders() throws HttpException, IOException {
		Map<String, String> params = new HashMap<String, String>();


		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.doRequest( "data", "post", url_prex+ OPENORDERS_URL, params );
		return result;
	}
	
	
	@Override
	public String myTradeHistory(String currencyPair,String orderNumber) throws HttpException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("currencyPair", currencyPair);
		params.put("orderNumber", orderNumber);

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.doRequest( "data", "post", url_prex+ MYTRADEHISTORY_URL, params );
		return result;
	}

	
	@Override
	public String withdraw(String currency,String amount, String address) throws HttpException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("currency", currency);
		params.put("amount", amount);
		params.put("address", address);

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.doRequest( "data", "post", url_prex+ WITHDRAW_URL, params );
		return result;
	}








	public String getSecret_key() {
		return secret_key;
	}

	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}


	public String getApi_key() {
		return api_key;
	}

	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}

	public String getUrl_prex() {
		return url_prex;
	}

	public void setUrl_prex(String url_prex) {
		this.url_prex = url_prex;
	}

}
