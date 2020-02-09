package com.vts.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vts.beans.House;
import com.vts.beans.HouseOwnerBean;
import com.vts.beans.TaxInfoBean;
import com.vts.beans.User;
import com.vts.beans.VtsTaxInfoBean;
import com.vts.response.GeneralResponse;
import com.vts.response.VTSRespone;
import com.vts.service.VtsService;
import com.vts.util.VtsUtil;

@CrossOrigin
@RestController
@RequestMapping("/vts/")
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
				respDesc = "Please valid house number";
			}
		} catch (Exception e) {
			respCode = 400;
			respDesc = "Please try after some time";
			logger.error("login :  exception while processing request ", e);
		}
		response = new GeneralResponse(respCode, respDesc, hoBean);
		return response;
	}
	
	

	@GetMapping("/fetchtax/{housenumber}")
	public GeneralResponse fetchTax(@PathVariable String housenumber) {
		//String housenumber = house.getHouseNum();
		logger.info("searchTax : request for tax serach house number :: " + housenumber);
		GeneralResponse response = null;
		List<TaxInfoBean> taxList = null;
		int respCode = 200;
		String respDesc = "SUCCESS";
		try {
			taxList = vtsService.fetchTaxDetails(housenumber);
			logger.info("searchTax : data received from server vtsTaxInfo :: " + taxList);
			if (null == taxList) {
				respCode = 400;
				respDesc = "Please enter valid house number";
			}
		} catch (Exception e) {
			respCode = 400;
			respDesc = "Please contact technical team";
			logger.error("login :  exception while processing request ", e);
		}
		response = new GeneralResponse(respCode, respDesc, taxList);
		return response;
	}

	@PostMapping(value="/addtaxdetails", consumes = MediaType.APPLICATION_JSON_VALUE)
	public VTSRespone addTaxDetails(TaxInfoBean taxInfoBean) {
		logger.info("addTaxData :: request to add tax data" + taxInfoBean);
		VTSRespone respone = new VTSRespone();
		if (isValid(taxInfoBean)) {
			VtsTaxInfoBean vtsTaxInfoBean = vtsService.addTaxDetails(taxInfoBean);
			if (null != vtsTaxInfoBean) {
				logger.info("addTaxData ::  successfully added in db and returing response " + vtsTaxInfoBean);
				respone.setRespCode(200);
				respone.setRespDesc("saved successfully");
			} else {
				respone.setRespCode(400);
				respone.setRespDesc("Failed to add data. Please try again.");
			}
		} else {
			respone.setRespCode(400);
			respone.setRespDesc("Failed to add data. Please try again.");
		}
		return respone;
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
