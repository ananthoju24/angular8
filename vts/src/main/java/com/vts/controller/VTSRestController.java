package com.vts.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vts.beans.House;
import com.vts.beans.HouseOwnerBean;
import com.vts.beans.OwnerBean;
import com.vts.beans.PrintPageData;
import com.vts.beans.TaxInfoBean;
import com.vts.beans.TransactionBean;
import com.vts.beans.User;
import com.vts.entity.HouseInfo;
import com.vts.response.GeneralResponse;
import com.vts.response.VTSRespone;
import com.vts.service.VtsService;
import com.vts.util.VtsUtil;

@CrossOrigin
@RestController
@RequestMapping("/vts")
public class VTSRestController {

	private static final Logger logger = LogManager.getLogger(VTSRestController.class);

	@Autowired
	private VtsService vtsService;

	@Autowired
	private VtsUtil vtsUtil;

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public VTSRespone login(@RequestBody User user) {
		logger.info("login : request for login : user :: " + user);
		VTSRespone response = new VTSRespone();
		boolean isvalidUser = false;
		try {
			isvalidUser = vtsService.validateUser(user.getUsername(), user.getPassword());
			logger.info("login : isvalidUser :: " + isvalidUser);
		} catch (Exception e) {
			logger.error("login :  exception while processing request ", e);
			isvalidUser = false;
		}
		if (isvalidUser) {
			response.setUserName(user.getUsername());
			response.setAuthToken(vtsUtil.generateUUID());
			response.setRespCode(200);
			response.setRespDesc("SUCCESS");
		} else {
			response.setRespCode(400);
			response.setRespDesc("FAILED");
		}
		return response;
	}

	@PostMapping("/search")
	public GeneralResponse searchTax(@RequestBody House house) {
		String housenumber = house.getHouseNum();
		logger.info("searchTax : request for tax serach house number :: " + housenumber);
		GeneralResponse response = null;
		HouseOwnerBean hoBean = null;
		int respCode = 200;
		String respDesc = "SUCCESS";
		try {
			hoBean = vtsService.getOwnerDetails(housenumber);
			logger.info("searchTax : data received from server vtsTaxInfo :: " + hoBean);
			if (null == hoBean) {
				respCode = 400;
				respDesc = "please enter valid house number";
			}
		} catch (Exception e) {
			respCode = 101;
			respDesc = "Please try after some time";
			logger.error("login :  exception while processing request ", e);
		}
		response = new GeneralResponse(respCode, respDesc, hoBean);
		return response;
	}

	@PostMapping("/fetchtax")
	public GeneralResponse fetchTax(@RequestBody House house) {
		GeneralResponse response = null;
		logger.info("fetchTax : request for tax serach house number :: " + house);
		List<TaxInfoBean> taxList = null;
		int respCode = 200;
		String respDesc = "SUCCESS";
		try {
			taxList = vtsService.fetchTaxDetails(house.getHouseNum());
			logger.info("fetchTax : data received from server vtsTaxInfo :: " + taxList);
			if (null == taxList) {
				respCode = 400;
				respDesc = "Please enter valid house number";
			}
		} catch (Exception e) {
			respCode = 400;
			respDesc = "Please contact technical team";
			logger.error("fetchTax :  exception while processing request ", e);
		}
		response = new GeneralResponse(respCode, respDesc, taxList);
		return response;
	}

	@PostMapping("/fetchPrevTax")
	public GeneralResponse fetchPrevTax(@RequestBody House house) {
		GeneralResponse response = null;
		logger.info("fetchPrevTax : request for tax serach house number :: " + house);
		List<TaxInfoBean> taxList = null;
		int respCode = 200;
		String respDesc = "SUCCESS";
		try {
			taxList = vtsService.fetchPrevTaxDetails(house.getHouseNum());
			logger.info("fetchPrevTax : data received from server vtsTaxInfo :: " + taxList);
			if (null == taxList) {
				respCode = 400;
				respDesc = "Please enter valid house number";
			}
			if (taxList.isEmpty()) {
				respCode = 201;
			}
		} catch (Exception e) {
			respCode = 400;
			respDesc = "Please contact technical team";
			logger.error("fetchTax :  exception while processing request ", e);
		}
		response = new GeneralResponse(respCode, respDesc, taxList);
		return response;
	}

	@GetMapping("/fetchtax/{housenumber}")
	public GeneralResponse fetchTax(@PathVariable String housenumber) {
		// String housenumber = house.getHouseNum();
		logger.info("fetchTax : request for tax serach house number :: " + housenumber);
		GeneralResponse response = null;
		List<TaxInfoBean> taxList = null;
		int respCode = 200;
		String respDesc = "SUCCESS";
		try {
			taxList = vtsService.fetchTaxDetails(housenumber);
			logger.info("fetchTax : data received from server vtsTaxInfo :: " + taxList);
			if (null == taxList) {
				respCode = 400;
				respDesc = "Please enter valid house number";
			}
		} catch (Exception e) {
			respCode = 400;
			respDesc = "Please contact technical team";
			logger.error("fetchTax :  exception while processing request ", e);
		}
		response = new GeneralResponse(respCode, respDesc, taxList);
		return response;
	}

	@PostMapping("/addtaxdetails")
	public GeneralResponse addTaxDetails(@RequestBody TaxInfoBean taxInfoBean) {
		logger.info("addTaxData :: request to add tax data" + taxInfoBean);
		GeneralResponse gResponse = new GeneralResponse();
		if (isValid(taxInfoBean)) {
			VTSRespone response = vtsService.addTaxDetails(taxInfoBean);
			logger.info("addTaxData :: response from db for add req " + response);
			if (response.getRespCode() == 200) {
				gResponse.setRespCode(200);
				gResponse.setRespDesc("saved successfully");
			} else if (response.getRespCode() == 401) {
				gResponse.setRespCode(401);
				gResponse.setRespDesc("Already tax is register for this year.");
			} else {
				gResponse.setRespCode(400);
				gResponse.setRespDesc("Failed to add data. Please try again.");
			}
		} else {
			gResponse.setRespCode(101);
			gResponse.setRespDesc("Failed to add data. Please try again.");
		}
		logger.info("addTaxData :: respone for add tax data is " + gResponse);
		return gResponse;
	}

	@PostMapping("/tax/payment")
	public GeneralResponse taxPayment(@RequestBody TaxInfoBean taxInfoBean) {
		logger.info("taxPayment :: request to pay tax " + taxInfoBean);

		GeneralResponse respone = new GeneralResponse();
		if (null == taxInfoBean.getHouseNumber()) {
			respone.setRespCode(400);
			respone.setRespDesc("Failed to add data. Please try again.");
			return respone;
		}
		logger.info("taxPayment :: prepare tax object " + taxInfoBean);
		if (isValid(taxInfoBean)) {
			PrintPageData tranResponse = vtsService.addTransaction(taxInfoBean);
			logger.info("taxPayment :: response :: " + tranResponse);
			if (null != tranResponse) {
				respone.setPayLoad(tranResponse);
				respone.setRespCode(200);
				respone.setRespDesc("payment successfully");
			} else {
				respone.setRespCode(400);
				respone.setRespDesc("payment failed.");
			}
		} else {
			respone.setRespCode(101);
			respone.setRespDesc("payment failed . Please try again.");
		}
		return respone;
	}

	@PostMapping("/enroll")
	public VTSRespone enrollOwner(@RequestBody OwnerBean owner) {
		VTSRespone vtsResponse = null;
		logger.info("enrollOwner :: request to add new owner details " + owner);
		vtsResponse = vtsService.addOwner(owner);
		logger.info("enrollOwner :: vtsResponse " + vtsResponse);
		return vtsResponse;
	}

	@PostMapping("/fetch/transaction")
	public GeneralResponse fetchTransaction(@RequestBody House house) {
		GeneralResponse resp = new GeneralResponse();
		logger.info("fetchTransaction :: request fetch avaiable transactions for house number " + house.getHouseNum());
		try {
			List<TransactionBean> tranBeanList = vtsService.fetchTransaction(house.getHouseNum());
			if (null != tranBeanList) {
				resp.setRespCode(200);
				resp.setRespDesc("SUCCESS");
				resp.setPayLoad(tranBeanList);
			} else {
				resp.setRespCode(400);
				resp.setRespDesc("No data available for this house number");
			}
		} catch (Exception e) {
			logger.error("fetchTransaction : exception while processing data ", e);
			resp.setRespCode(401);
			resp.setRespDesc("Technical error");
		}
		return resp;
	}

	@GetMapping("/fetch/{transactionID}")
	public GeneralResponse fetchTransactionById(@PathVariable String transactionID) {
		GeneralResponse resp = new GeneralResponse();
		logger.info("fetchTransaction :: request fetch avaiable transactions for transid " + transactionID);
		try {
			if (null == transactionID || "".equals(transactionID)) {
				throw new RuntimeException("Invalid request");
			}
			PrintPageData ppData = vtsService.fetchTransactionById(transactionID);
			if (null != ppData) {
				resp.setRespCode(200);
				resp.setRespDesc("SUCCESS");
				resp.setPayLoad(ppData);
			} else {
				resp.setRespCode(400);
				resp.setRespDesc("No data available for transactionID");
			}
		} catch (Exception e) {
			logger.error("fetchTransaction :: exception while processing data ", e);
			resp.setRespCode(401);
			resp.setRespDesc("Technical error");
		}
		logger.info("fetchTransaction :: response :: " + resp);
		return resp;
	}

	private boolean isValid(TaxInfoBean taxInfoBean) {

		try {
			if (null == taxInfoBean) {
				return false;
			} else if (null == taxInfoBean.getHouseNumber()) {
				return false;
			}
		} catch (Exception e) {
			logger.error(" Exception while validating the request : ", e);
			return false;
		}

		return true;
	}

	/*
	 * VtsTaxInfoBean vtsTaxInfo = null; int respCode = 200; String respDesc =
	 * "SUCCESS"; try { vtsTaxInfo = vtsService.getTaxDetails(housenumber);
	 * logger.info("searchTax : data received from server vtsTaxInfo :: " +
	 * vtsTaxInfo); if (null == vtsTaxInfo) { respCode = 400; respDesc =
	 * "Please valid house number"; } } catch (Exception e) { respCode = 400;
	 * respDesc = "Please try after some time";
	 * logger.error("login :  exception while processing request ", e); } response =
	 * new GeneralResponse(respCode, respDesc, vtsTaxInfo);
	 */
}
