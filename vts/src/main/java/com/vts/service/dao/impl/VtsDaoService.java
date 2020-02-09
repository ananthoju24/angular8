package com.vts.service.dao.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.entity.HouseInfo;
import com.vts.entity.OwnerInfo;
import com.vts.entity.TaxInfo;
import com.vts.entity.User;
import com.vts.respository.service.HouseRepository;
import com.vts.respository.service.OwnerRepository;
import com.vts.respository.service.TaxRepository;
import com.vts.respository.service.UserRepository;
import com.vts.util.VtsUtil;

@Service
public class VtsDaoService {

	private static final Logger logger = LogManager.getLogger(VtsDaoService.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private HouseRepository houseRepository;
	@Autowired
	private TaxRepository taxRepository;

	@Autowired
	VtsUtil vtsUtil;

	public Optional<User> getUser(String username) {
		logger.info("getUser :: loading data from DB for reqested user " + username);
		Optional<User> user = userRepository.findById(username);
		return user;
	}

	public String saveOwnerandHouseInfo(OwnerInfo ownerInfo, HouseInfo houseInfo) {
		String response = "SUCCESS";
		logger.info("saveOwnerandHouseInfo :: Request to save new owner and house info to system");
		try {
			ownerInfo = ownerRepository.save(ownerInfo);
			logger.info("saveOwnerandHouseInfo :: is owner saved to DB ? :: " + ownerInfo);
			houseInfo = houseRepository.save(houseInfo);
			logger.info("saveOwnerandHouseInfo :: is house saved to DB ? :: " + ownerInfo);
		} catch (Exception e) {
			logger.error("saveOwnerandHouseInfo :: failed to add owner to DB ", e);
			response = "FAILED";
		}
		return response;
	}

	public HouseInfo fetchHouseInfo(String houseNumber) {
		HouseInfo houseInfo = null;
		logger.info("fetchHouseInfo :: Request to fetch house details for :: " + houseNumber);
		logger.info("fetchHouseInfo :: calling repo to fetch house info");
		Optional<HouseInfo> houseObj = houseRepository.findById(houseNumber);
		if (houseObj.isPresent()) {
			houseInfo = houseObj.get();
			logger.info("fetchHouseInfo :: Data found in db :: " + houseInfo);
		}
		return houseInfo;
	}

	public TaxInfo addTaxDetails(TaxInfo taxInfo) {
		TaxInfo taxInfoDBObj = null;
		logger.info("addTaxDetails :: adding tax details to DB taxInfo ::" + taxInfo);
		try {
			taxInfoDBObj = taxRepository.save(taxInfo);
		} catch (Exception e) {
			logger.error("addTaxDetails :: failed to add tax details to DB ", e);
		}
		return taxInfoDBObj;
	}

	public List<TaxInfo> getTaxDetails(String houseNumber) {
		List<TaxInfo> taxInfoList = null;
		logger.info("getTaxDetails :: request received to get tax details for house number ::" + houseNumber);
		try {
			taxInfoList = taxRepository.findByhouseNumber(houseNumber);
		} catch (Exception e) {
			logger.error("getTaxDetails :: failed to get tax details from DB ", e);
		}
		return taxInfoList;
	}

	public TaxInfo getTotalDueTax(String houseNumber) {
		logger.info("getTotalDueTax :: request received to get total due tax details for house number ::" + houseNumber);
		TaxInfo totalDueTax = null;
		try {
		//	totalDueTax = taxRepository.groupByHouseNumber(houseNumber);
			logger.info("getTotalDueTax :: db response totalDueTax "+totalDueTax);
		} catch (Exception e) {
			logger.error("getTotalDueTax :: failed to get total tax details from DB ", e);
		}
		return totalDueTax;
	}
}
