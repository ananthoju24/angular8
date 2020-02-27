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
import com.vts.entity.TransactionInfo;
import com.vts.entity.User;
import com.vts.respository.service.HouseRepository;
import com.vts.respository.service.OwnerRepository;
import com.vts.respository.service.TaxRepository;
import com.vts.respository.service.TransactionRepository;
import com.vts.respository.service.UserRepository;
import com.vts.util.TaxStatus;
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
	private TransactionRepository transactionRep;
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
		List<TaxInfo> partialTaxList = null;
		logger.info("getTaxDetails :: request received to get tax details for house number ::" + houseNumber);
		try {
			taxInfoList = taxRepository.findByhouseNumberandTaxStatusNot(houseNumber, TaxStatus.PARTIAL.getStatus());
			HouseInfo houseInfo = new HouseInfo();
			houseInfo.setHouseNumber(houseNumber);
			partialTaxList = taxRepository.findByhouseNumberandTaxStatus(houseNumber, TaxStatus.PARTIAL.getStatus());

			for (TaxInfo taxInfo : partialTaxList) {
				taxInfoList.add(taxInfo);
			}

			logger.info("getTaxDetails :: response from db partialTaxList :: " + partialTaxList);
		} catch (Exception e) {
			logger.error("getTaxDetails :: failed to get tax details from DB ", e);
		}
		return taxInfoList;
	}

	public List<TaxInfo> getPrevTaxDetails(String houseNumber) {

		List<TaxInfo> taxInfoList = null;
		logger.info("getTaxDetails :: request received to get tax details for house number for filling data fileds with previous one::" + houseNumber);
		try {
			taxInfoList = taxRepository.findByhouseNumberLimit1(houseNumber);
		} catch (Exception e) {
			logger.error("getTaxDetails :: failed to get tax details from DB ", e);
		}
		return taxInfoList;
	}

	public TaxInfo getTaxDetailsByTaxID(long taxID) {

		TaxInfo taxInfo = null;
		logger.info("getTaxDetailsByTaxID :: request received to get tax details for taxID ::" + taxID);
		try {
			taxInfo = taxRepository.findBytaxID(taxID);
		} catch (Exception e) {
			logger.error("getTaxDetailsByTaxID :: failed to get tax details from DB ", e);
		}
		return taxInfo;
	}

	public TaxInfo getTaxDetails(String houseNumber, String taxYear) {
		TaxInfo taxInfo = null;
		logger.info("findByhouseNumber :: request received to fetchtax details:: housenumber ::" + houseNumber + " :: taxyear " + taxYear);
		try {
			taxInfo = taxRepository.findByhouseNumber(houseNumber, taxYear);
		} catch (Exception e) {
			logger.error("getTaxDetails :: failed to get tax details from DB ", e);
		}
		return taxInfo;
	}

	public TransactionInfo addTransaction(TransactionInfo tranInfo) {
		TransactionInfo transactionInfo = null;
		logger.info("addTransaction :: request received to fetchtax details:: " + tranInfo);
		try {
			transactionInfo = transactionRep.save(tranInfo);
			logger.info("addTransaction :: response from db  " + transactionInfo);
		} catch (Exception e) {
			logger.error("getTaxDetails :: failed to get tax details from DB ", e);
			transactionInfo = null;
		}
		return transactionInfo;

	}

	public TaxInfo getTotalDueTax(String houseNumber) {
		logger.info("getTotalDueTax :: request received to get total due tax details for house number ::" + houseNumber);
		TaxInfo totalDueTax = null;
		try {
			// totalDueTax = taxRepository.groupByHouseNumber(houseNumber);
			logger.info("getTotalDueTax :: db response totalDueTax " + totalDueTax);
		} catch (Exception e) {
			logger.error("getTotalDueTax :: failed to get total tax details from DB ", e);
		}
		return totalDueTax;
	}

	public void updateTaxStatus(Long taxid, TaxStatus status) {
		logger.info("updateTaxStatus :: request taxID " + taxid + " status " + status);
		try {
			TaxInfo taxInfo = taxRepository.findBytaxID(taxid);
			taxInfo.setTaxStatus(status);
			taxRepository.save(taxInfo);
			logger.info("updateTaxStatus :: response taxID " + taxid);
		} catch (Exception e) {
			logger.error("updateTaxStatus :: exception ", e);
		}
	}

	public List<TransactionInfo> fetchTransaction(String houseNumber) {
		logger.info("fetchTransaction :: fetching transaction for house num :" + houseNumber);
		List<TransactionInfo> transactionList = null;
		try {
			transactionList = transactionRep.findBytaxID(houseNumber, TaxStatus.SUCCESS.getStatus());
			logger.info("fetchTransaction :: fetching transaction response from DB" + transactionList);
		} catch (Exception e) {
			logger.error("fetchTransaction :: exception while processing", e);
			transactionList = null;
		}

		return transactionList;
	}
	
	public TransactionInfo fetchTransaction(long transactionID) {
		logger.info("fetchTransaction :: fetching transaction for house num :" + transactionID);
		TransactionInfo transactionInfo = null;
		try {
			transactionInfo = transactionRep.findByTranID(transactionID);
			logger.info("fetchTransaction :: fetching transaction response from DB" + transactionInfo);
		} catch (Exception e) {
			logger.error("fetchTransaction :: exception while processing", e);
			transactionInfo = null;
		}

		return transactionInfo;
	}
}
