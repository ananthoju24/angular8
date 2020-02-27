package com.vts.service.dao;

import java.io.BufferedReader;
import java.io.DataOutputStream; 
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.vts.service.impl.VtsServiceImpl;

@Component
public class VtsSMSService {
	static final Logger logger = LogManager.getLogger(VtsSMSService.class);
	static String url = "https://www.sms4india.com";
//OTYBVYPKFMYGUQVL4JT1DVHYRAKWIJDZ
	//68ODLB0398RRW3GA
	
	String apiKey = "RGUHIUK0TZM1PV8PIL36ABN6QG0PI9EB";
	String secretKey = "GK0UQACTCKUWK6RD";
	String useType = "stage";
	String senderId ="VTS";

	public String sendCampaign(String phone, String message) {
		try {
			logger.info("sendCampaign :: sending sms phone "+phone+" message "+message);
			if(null == phone) {
				phone = "8019692762";
			}
			// construct dataa
			JSONObject urlParameters = new JSONObject();
			urlParameters.put("apikey", apiKey);
			urlParameters.put("secret", secretKey);
			urlParameters.put("usetype", useType);
			urlParameters.put("phone", phone);
			urlParameters.put("message", URLEncoder.encode(message, "UTF-8"));
			urlParameters.put("senderid", senderId);
			URL obj = new URL(url + "/api/v1/sendCampaign");
			// send data
			logger.info("sendCampaign :: sending sms "+urlParameters);
			HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
			wr.write(urlParameters.toString().getBytes());
			// get the response
			BufferedReader bufferedReader = null;
			if (httpConnection.getResponseCode() == 200) {
				bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			} else {
				bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
			}
			StringBuilder content = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line).append("\n");
			}
			bufferedReader.close();
			logger.info("sendCampaign :: sending sms "+content);
			return content.toString();
		} catch (Exception ex) {
			logger.error("sendCampaign :: exception "+ex);
			return "{'status':500,'message':'Internal Server Error'}";
		}

	}
}
