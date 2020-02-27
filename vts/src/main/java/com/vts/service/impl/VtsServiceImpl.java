package com.vts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vts.beans.HouseOwnerBean;
import com.vts.beans.OwnerBean;
import com.vts.beans.PrintPageData;
import com.vts.beans.TaxDetails;
import com.vts.beans.TaxInfoBean;
import com.vts.beans.TransactionBean;
import com.vts.beans.VtsTaxInfoBean;
import com.vts.entity.HouseInfo;
import com.vts.entity.OwnerInfo;
import com.vts.entity.TaxInfo;
import com.vts.entity.TransactionInfo;
import com.vts.entity.User;
import com.vts.response.VTSRespone;
import com.vts.service.VtsService;
import com.vts.service.dao.TaxDao;
import com.vts.service.dao.VtsSMSService;
import com.vts.service.dao.impl.VtsDaoService;
import com.vts.util.TaxStatus;
import com.vts.util.VtsUtil;

@Component
public class VtsServiceImpl implements VtsService {

	static final Logger logger = LogManager.getLogger(VtsServiceImpl.class);

	@Autowired
	TaxDao taxDao;

	@Autowired
	VtsDaoService vtsDaoService;

	@Autowired
	VtsUtil vtsUtil;

	@Autowired
	VtsSMSService vtsSMSService;

	@Override
	public boolean validateUser(String userName, String password) {
		logger.info("VtsService :: validating user " + userName + " pwd " + password);
		Optional<User> user = vtsDaoService.getUser(userName);
		logger.info("VtsService :: user object from DB " + user.isPresent());
		if (user.isPresent()) {
			User userData = user.get();
			logger.info("VtsService :: user object from DB " + userData.getUserName());
			if (userData.getUserName().equals(userName) && userData.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public TaxDetails fetchTaxInfo(String hno) {
		logger.info("fetchTaxInfo :: reqeust to fetch tax fo hno :: " + hno);
		TaxDetails taxDetails = taxDao.geTaxDetails(hno);
		return taxDetails;
	}

	@Override
	public VTSRespone addOwner(OwnerBean owner) {
		logger.info("vtsService ::owner data from controller " + owner);
		VTSRespone vtsResponse = new VTSRespone();
		logger.info("addOnwerDetails :: request to add new onwer to system :: " + owner);
		try {
			HouseInfo houseData = vtsDaoService.fetchHouseInfo(owner.getHouseNumber());
			if (null != houseData) {
				vtsResponse.setRespCode(404);
				vtsResponse.setRespDesc("House Number registered already");
				return vtsResponse;
			}

			OwnerInfo ownerInfo = new OwnerInfo();
			ownerInfo.setOwnerName(owner.getOwnerName());
			ownerInfo.setMobileNumber(owner.getMobileNumber());
			ownerInfo.setFatherOrHusband(owner.getFhName());
			HouseInfo houseInfo = new HouseInfo();
			houseInfo.setHouseNumber(owner.getHouseNumber());
			houseInfo.setOwnerInfo(ownerInfo);

			String response = vtsDaoService.saveOwnerandHouseInfo(ownerInfo, houseInfo);
			if (null != response && "SUCCESS".equalsIgnoreCase(response)) {
				vtsResponse.setRespCode(200);
				vtsResponse.setRespDesc("successfully added");
			} else {
				vtsResponse.setRespCode(400);
				vtsResponse.setRespDesc("add failed.Please try again");
			}
		} catch (Exception e) {
			logger.error("addOnwerDetails :: failed to add owner to DB ", e);
			vtsResponse.setRespCode(101);
			vtsResponse.setRespDesc("add failed.Please try again");
		}
		logger.info("addOnwerDetails :: vtsResponse : " + vtsResponse);
		return vtsResponse;
	}

	public HouseOwnerBean getOnwerDetails(String hno) {
		HouseOwnerBean hoBean = null;
		logger.info("getDetails :: Request to fetch house details for :: " + hno);
		if (null == hno) {
			logger.info("getDetails :: houseNumber is empty returing ");
			return hoBean;
		} else {
			HouseInfo hInfo = vtsDaoService.fetchHouseInfo(hno);
			logger.info("getDetails :: received data from dao  :: " + hInfo);

			if (null != hInfo) {
				hoBean = prepareOwnerBean(hInfo);
				logger.info("getDetails :: returning resoponse  :: " + hoBean);
			}
		}
		return hoBean;
	}

	@Override
	public VTSRespone addTaxDetails(TaxInfoBean taxInfoBean) {
		logger.info("addTaxDetails :: Request add tax details :: " + taxInfoBean);
		VTSRespone vtsRespone = new VTSRespone();
		TaxInfo isTaxAlreadyThere = vtsDaoService.getTaxDetails(taxInfoBean.getHouseNumber(), taxInfoBean.getTaxYear());
		logger.info("addTaxDetails :: response from db isTaxAlreadyThere :: " + isTaxAlreadyThere);
		if (null == isTaxAlreadyThere) {
			TaxInfo taxInfoDBObj = prepareDBObject(taxInfoBean);
			TaxInfo taxInfoResp = vtsDaoService.addTaxDetails(taxInfoDBObj);
			logger.info("addTaxDetails :: response from db :: " + taxInfoResp);
			if (null != taxInfoResp) {
				vtsRespone.setRespCode(200);
			} else {
				vtsRespone.setRespCode(400);
			}
		} else {
			vtsRespone.setRespCode(401);
		}
		return vtsRespone;
	}

	/*
	 * Need to change this method fetch data by once
	 * 
	 */

	@Override
	public VtsTaxInfoBean getTaxDetails(String houseNumber) {
		logger.info("getTaxDetails :: Request get tax details :: " + houseNumber);
		VtsTaxInfoBean vtsTaxBean = new VtsTaxInfoBean();
		List<TaxInfo> taxInfoList = vtsDaoService.getTaxDetails(houseNumber);
		logger.info("getTaxDetails :: taxInfoList " + taxInfoList);
		vtsTaxBean.setHouseNumber(houseNumber);
		vtsTaxBean.setCurrentDate(vtsUtil.getDate());
		vtsTaxBean.setTaxYear(vtsUtil.getPreviousYear() + "-" + vtsUtil.getCurrentYear());
		HouseInfo hInfo = vtsDaoService.fetchHouseInfo(houseNumber);
		if (null != hInfo) {
			vtsTaxBean.setHouseOwnerBean(prepareOwnerBean(hInfo));
		}
		if (null != taxInfoList) {
			logger.info("getTaxDetails :: found data in db for  " + houseNumber);
		}
		logger.info("getTaxDetails :: returning response as :  " + vtsTaxBean);
		return vtsTaxBean;
	}

	@Override
	public TaxInfo getTaxDetails(String houseNumber, String taxYear) {
		TaxInfo taxInfo = null;
		logger.error("getTaxDetails :: house number and tax year :: houseNumber : " + houseNumber + " taxYear " + taxYear);
		try {
			taxInfo = vtsDaoService.getTaxDetails(houseNumber, taxYear);
		} catch (Exception e) {
			logger.error("getTaxDetails :: with housenumber and taxYear failed to add ", e);
		}
		return taxInfo;
	}

	private HouseOwnerBean prepareOwnerBean(HouseInfo hInfo) {
		HouseOwnerBean hoBean = new HouseOwnerBean();
		hoBean.setHouseNumber(hInfo.getHouseNumber());
		hoBean.setOwnerName(hInfo.getOwnerInfo().getOwnerName());
		hoBean.setMobileNumber(hInfo.getOwnerInfo().getMobileNumber());
		hoBean.setFatherOrHusband(hInfo.getOwnerInfo().getFatherOrHusband());
		return hoBean;
	}

	@Override
	public HouseOwnerBean getOwnerDetails(String houseNumber) {
		HouseOwnerBean hoBean = null;
		HouseInfo hInfo = vtsDaoService.fetchHouseInfo(houseNumber);
		if (null != hInfo) {
			hoBean = prepareOwnerBean(hInfo);
		}
		return hoBean;
	}

	@Override
	public List<TaxInfoBean> fetchTaxDetails(String houseNumber) {
		logger.info("fetchTaxDetails :: fetch list of tax details");
		List<TaxInfoBean> listTax = null;
		try {
			List<TaxInfo> taxList = vtsDaoService.getTaxDetails(houseNumber);
			listTax = prepareTaxListObj(taxList);
		} catch (Exception e) {
			logger.error("fetchTaxDetails :: exception while ", e);
		}
		return listTax;
	}

	@Override
	public List<TaxInfoBean> fetchPrevTaxDetails(String houseNumber) {
		logger.info("fetchTaxDetails :: fetch list of tax details " + houseNumber);
		List<TaxInfoBean> listTax = null;
		try {
			List<TaxInfo> taxList = vtsDaoService.getPrevTaxDetails(houseNumber);
			logger.info("fetchTaxDetails :: resp from dao " + taxList);

			listTax = preparePrevTaxListObj(taxList);
		} catch (Exception e) {
			logger.error("fetchTaxDetails :: exception while ", e);
		}
		return listTax;
	}

	@Override
	public PrintPageData addTransaction(TaxInfoBean taxInfoBean) {
		logger.info("addTransaction :: adding transaction data");
		TransactionBean tranBean = null;
		HouseOwnerBean hoBean = null;
		PrintPageData ppData = new PrintPageData();
		TransactionInfo transactionInfo = null;
		try {
			boolean flag = isValidTransaction(taxInfoBean);
			logger.info("addTransaction :: isValidTransaction " + flag);
			if (!flag) {
				return null;
			}
			TaxInfo taxInfo = vtsDaoService.getTaxDetailsByTaxID(taxInfoBean.getTaxId());
			if (null != taxInfo) {
				transactionInfo = prepareTransactionBean(taxInfo);
				transactionInfo.setTaxStatus(TaxStatus.SUCCESS);
			} else {
				return null;
			}
			TransactionInfo transactionInfoResp = vtsDaoService.addTransaction(transactionInfo);
			if (null != transactionInfoResp) {
				if ("totalPayment".equalsIgnoreCase(taxInfoBean.getPaymentType())) {
					logger.info("addTransaction :: processing totalPayment");
					vtsDaoService.updateTaxStatus(taxInfoBean.getTaxId(), TaxStatus.SUCCESS);
				} else {
					logger.info("addTransaction :: processing partial Payment");
					vtsDaoService.updateTaxStatus(taxInfoBean.getTaxId(), TaxStatus.PARTIAL);
				}
				tranBean = prepareTranUIObj(transactionInfoResp);
				hoBean = getOwnerDetails(taxInfoBean.getHouseNumber());
				long remainingTax = taxInfo.getTotalTax() - tranBean.getTotalTax();
				logger.info("addTransaction :: hoBean " + hoBean);
				String msg = "నూత్ పల్లి,నందిపేట్‌,నిజామాబాద్‌\n మొత్తం పన్ను:" + taxInfo.getTotalTax() + " \n చెల్లించిన పన్ను:" + tranBean.getTotalTax() + "మిగిలిన  పన్నులు :" + remainingTax
						+ "\nఇంటి పన్ను సకాలంలో చెల్లించి గ్రామ అభివృద్ధి కి సహకరించండి \n ధన్యవాదాలు";
				vtsSMSService.sendCampaign(hoBean.getMobileNumber(), msg);
				createPrintData(taxInfoBean.getTaxYear(), tranBean, hoBean, ppData);
			}
			logger.info("addTransaction :: resposne from dao " + transactionInfoResp);
		} catch (Exception e) {
			logger.error("addTransaction :: exception while ", e);
		}
		return ppData;
	}

	@Override
	public List<TransactionBean> fetchTransaction(String houseNumber) {
		List<TransactionBean> tranBeanList = null;
		logger.info("fetchTransaction :: request houseNumber ::" + houseNumber);
		try {
			List<TransactionInfo> tranDBList = vtsDaoService.fetchTransaction(houseNumber);
			if (null != tranDBList) {
				tranBeanList = preapreTranUIList(tranDBList);
			}
			logger.info("fetchTransaction :: response" + tranBeanList);
		} catch (Exception e) {
			logger.error("fetchTransaction :: exception ", e);
			tranBeanList = null;
		}

		return tranBeanList;
	}

	@Override
	public PrintPageData fetchTransactionById(String transactionID) {
		PrintPageData ppData = null;
		logger.info("fetchTransactionById :: request transactionID :: " + transactionID);
		try {
			TransactionInfo tranInfo = vtsDaoService.fetchTransaction(Long.parseLong(transactionID));
			if (null != tranInfo) {
				ppData = new PrintPageData();
				createPrintData(tranInfo.getTaxInfo().getTaxYear(), prepareTransactionBean(tranInfo), prepareOwnerBean(tranInfo.getTaxInfo().getHouseInfo()), ppData);
			}
			logger.info("fetchTransactionById :: response" + ppData);
		} catch (Exception e) {
			logger.error("fetchTransactionById :: exception ", e);
			ppData = null;
		}
		return ppData;
	}

	private void createPrintData(String taxYear, TransactionBean tranBean, HouseOwnerBean hoBean, PrintPageData ppData) {
		int year = Integer.parseInt(taxYear);
		ppData.setHouseInfo(hoBean);
		ppData.setTranID(tranBean.getTranID());
		if (Integer.parseInt(vtsUtil.getCurrentYear()) == year) {
			ppData.setCurrentTax(tranBean);
			ppData.setDueTax(createDummyObj());
		} else {
			ppData.setDueTax(tranBean);
			ppData.setCurrentTax(createDummyObj());
		}
		ppData.setData(vtsUtil.getDate());
		ppData.setTaxYear(year - 1 + " - " + year);
	}

	private List<TransactionBean> preapreTranUIList(List<TransactionInfo> tranDBList) {
		List<TransactionBean> tranUIList = new ArrayList<TransactionBean>();
		for (TransactionInfo tInfo : tranDBList) {
			logger.info(" here preparing transaction data ");
			/* PrintPageData ppData = new PrintPageData(); */
			TransactionBean tBean = prepareTransactionBean(tInfo);
			/*
			 * createPrintData(tInfo.getTaxInfo().getTaxYear(), tBean,
			 * prepareOwnerBean(tInfo.getTaxInfo().getHouseInfo()),ppData);
			 * tBean.setPpData(ppData);
			 */
			tranUIList.add(tBean);

		}
		return tranUIList;
	}

	private TransactionBean prepareTransactionBean(TransactionInfo tranEntity) {
		logger.info("prepareTransactionBean :: preparing entity object");
		TransactionBean tranUIBean = new TransactionBean();
		tranUIBean.setTranID(tranEntity.getTranID());
		tranUIBean.setHouseTax(tranEntity.getHouseTax());
		tranUIBean.setLibraryTax(tranEntity.getLibraryTax());
		tranUIBean.setLightTax(tranEntity.getLightTax());
		tranUIBean.setDrainageTax(tranEntity.getDrainageTax());
		tranUIBean.setWaterTax(tranEntity.getWaterTax());
		tranUIBean.setKulaiNelaVariFee(tranEntity.getKulaiNelaVariFee());
		tranUIBean.setKulaiDeposit(tranEntity.getKulaiDeposit());
		tranUIBean.setLicenseFee(tranEntity.getLicenseFee());
		tranUIBean.setHouseConstructionFee(tranEntity.getHouseConstructionFee());
		tranUIBean.setDakhalaFee(tranEntity.getDakhalaFee());
		tranUIBean.setBandhelaDoddi(tranEntity.getBandhelaDoddi());
		tranUIBean.setBuildingRents(tranEntity.getBuildingRents());
		tranUIBean.setOthersKey(tranEntity.getOthersKey());
		tranUIBean.setOtherValue(tranEntity.getOtherValue());
		tranUIBean.setTotalTax(tranEntity.getTotalTax());
		tranUIBean.setTaxYear(tranEntity.getTaxYear());
		logger.info(" transaction status " + tranEntity.getTaxStatus());
		tranUIBean.setTaxStatus(tranEntity.getTaxStatus().getStatus());
		return tranUIBean;
	}

	private boolean isValidTransaction(TaxInfoBean taxInfoBean) {
		boolean isPartialPayment = false;
		logger.info("isValidTransaction ::validating transaction request  ");
		if (taxInfoBean.getHouseTax() < 0) {
			logger.info("isValidTransaction :: HouseTax");
			return false;
		} else if (taxInfoBean.getLibraryTax() < 0) {
			logger.info("isValidTransaction :: library tax");
			return false;
		} else if (taxInfoBean.getLightTax() < 0) {
			logger.info("isValidTransaction :: light tax");
			return false;
		} else if (taxInfoBean.getDrainageTax() < 0) {
			logger.info("isValidTransaction :: drainage");
			return false;
		} else if (taxInfoBean.getWaterTax() < 0) {
			logger.info("isValidTransaction :: water tax");
			return false;
		} else if (taxInfoBean.getKulaiNelaVariFee() < 0) {
			logger.info("isValidTransaction :: kulai nela vari fee");
			return false;
		} else if (taxInfoBean.getKulaiDeposit() < 0) {
			logger.info("isValidTransaction :: kulai deposit");
			return false;
		} else if (taxInfoBean.getLicenseFee() < 0) {
			logger.info("isValidTransaction :: license fee");
			return false;
		} else if (taxInfoBean.getHouseConstructionFee() < 0) {
			logger.info("isValidTransaction :: construction");
			return false;
		} else if (taxInfoBean.getDakhalaFee() < 0) {
			logger.info("isValidTransaction :: Dakhala fee");
			return false;
		} else if (taxInfoBean.getBandhelaDoddi() < 0) {
			logger.info("isValidTransaction :: Bandhela doddi");
			return false;
		} else if (taxInfoBean.getBuildingRents() < 0) {
			logger.info("isValidTransaction :: building rents");
			return false;
		} else if (taxInfoBean.getOtherValue() < 0) {
			logger.info("isValidTransaction :: other value ");
			return false;
		}
		return true;
	}

	private TransactionBean createDummyObj() {
		return new TransactionBean(000l, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "", 0, 0, "NA", "NA");
	}

	private TransactionBean prepareTranUIObj(TransactionInfo tranInfo) {
		logger.info("prepareTransactionBean :: preparing entity object");
		TransactionBean tranBean = new TransactionBean();
		tranBean.setTranID(tranInfo.getTranID());
		tranBean.setHouseTax(tranInfo.getHouseTax());
		tranBean.setLibraryTax(tranInfo.getLibraryTax());
		tranBean.setLightTax(tranInfo.getLightTax());
		tranBean.setDrainageTax(tranInfo.getDrainageTax());
		tranBean.setWaterTax(tranInfo.getWaterTax());
		tranBean.setKulaiNelaVariFee(tranInfo.getKulaiNelaVariFee());
		tranBean.setKulaiDeposit(tranInfo.getKulaiDeposit());
		tranBean.setLicenseFee(tranInfo.getLicenseFee());
		tranBean.setHouseConstructionFee(tranInfo.getHouseConstructionFee());
		tranBean.setDakhalaFee(tranInfo.getDakhalaFee());
		tranBean.setBandhelaDoddi(tranInfo.getBandhelaDoddi());
		tranBean.setBuildingRents(tranInfo.getBuildingRents());
		tranBean.setOthersKey(tranInfo.getOthersKey());
		tranBean.setOtherValue(tranInfo.getOtherValue());
		tranBean.setTotalTax(tranInfo.getTotalTax());
		tranBean.setTaxYear(tranInfo.getTaxYear());
		tranBean.setTaxStatus(tranInfo.getTaxStatus().equals(TaxStatus.SUCCESS) ? "SUCCESS" : "CANCEL");
		return tranBean;
	}

	// Preparing TransactionInfo DB object from tax ui object
	private TransactionInfo prepareTransactionBean(TaxInfoBean taxInfoBean) {
		logger.info("prepareTransactionBean :: preparing entity object");
		TransactionInfo tranInfo = new TransactionInfo();
		TaxInfo taxInfo = new TaxInfo();
		taxInfo.setTaxID(taxInfoBean.getTaxId());
		tranInfo.setTaxInfo(taxInfo);
		tranInfo.setHouseTax(taxInfoBean.getHouseTax());
		tranInfo.setLibraryTax(taxInfoBean.getLibraryTax());
		tranInfo.setLightTax(taxInfoBean.getLightTax());
		tranInfo.setDrainageTax(taxInfoBean.getDrainageTax());
		tranInfo.setWaterTax(taxInfoBean.getWaterTax());
		tranInfo.setKulaiNelaVariFee(taxInfoBean.getKulaiNelaVariFee());
		tranInfo.setKulaiDeposit(taxInfoBean.getKulaiDeposit());
		tranInfo.setLicenseFee(taxInfoBean.getLicenseFee());
		tranInfo.setHouseConstructionFee(taxInfoBean.getHouseConstructionFee());
		tranInfo.setDakhalaFee(taxInfoBean.getDakhalaFee());
		tranInfo.setBandhelaDoddi(taxInfoBean.getBandhelaDoddi());
		tranInfo.setBuildingRents(taxInfoBean.getBuildingRents());
		tranInfo.setOthersKey(taxInfoBean.getOthersKey());
		tranInfo.setOtherValue(taxInfoBean.getOtherValue());
		tranInfo.setTotalTax(taxInfoBean.getTotalTax());
		tranInfo.setTaxYear(taxInfoBean.getTaxYear());
		return tranInfo;
	}

	// Preparing TransactionInfo DB object from tax info db object
	private TransactionInfo prepareTransactionBean(TaxInfo taxInfo) {
		logger.info("prepareTransactionBean :: preparing entity object");
		TransactionInfo tranInfo = new TransactionInfo();
		TaxInfo taxInfo1 = new TaxInfo();
		taxInfo1.setTaxID(taxInfo.getTaxID());
		tranInfo.setTaxInfo(taxInfo);
		tranInfo.setHouseTax(taxInfo.getHouseTax());
		tranInfo.setLibraryTax(taxInfo.getLibraryTax());
		tranInfo.setLightTax(taxInfo.getLightTax());
		tranInfo.setDrainageTax(taxInfo.getDrainageTax());
		tranInfo.setWaterTax(taxInfo.getWaterTax());
		tranInfo.setKulaiNelaVariFee(taxInfo.getKulaiNelaVariFee());
		tranInfo.setKulaiDeposit(taxInfo.getKulaiDeposit());
		tranInfo.setLicenseFee(taxInfo.getLicenseFee());
		tranInfo.setHouseConstructionFee(taxInfo.getHouseConstructionFee());
		tranInfo.setDakhalaFee(taxInfo.getDakhalaFee());
		tranInfo.setBandhelaDoddi(taxInfo.getBandhelaDoddi());
		tranInfo.setBuildingRents(taxInfo.getBuildingRents());
		tranInfo.setOthersKey(taxInfo.getOthersKey());
		tranInfo.setOtherValue(taxInfo.getOtherValue());
		tranInfo.setTotalTax(taxInfo.getTotalTax());
		tranInfo.setTaxYear(taxInfo.getTaxYear());
		return tranInfo;
	}

	private List<TaxInfoBean> prepareTaxListObj(List<TaxInfo> taxInfoList) {
		logger.info("prepareTaxListObj :: preparing ui object for get Tax details");
		List<TaxInfoBean> taxBeanList = new ArrayList<TaxInfoBean>();
		// vtsTaxBean.setTaxBeanList(taxBeanList);
		for (TaxInfo taxInfo : taxInfoList) {
			TaxInfoBean taxBeanObj = perpareTaxInfoBean(taxInfo);
			taxBeanList.add(taxBeanObj);
			/*
			 * if (taxInfo.getTaxYear().equals(vtsUtil.getCurrentYear())) {
			 * vtsTaxBean.setCurrentTax(taxBeanObj); }
			 */
		}
		return taxBeanList;
	}

	private List<TaxInfoBean> preparePrevTaxListObj(List<TaxInfo> taxInfoList) {
		logger.info("prepareTaxListObj :: preparing ui object for get Tax details");
		List<TaxInfoBean> taxBeanList = new ArrayList<TaxInfoBean>();
		for (TaxInfo taxInfo : taxInfoList) {
			TaxInfoBean taxBeanObj = perparePrevTaxInfoBean(taxInfo);
			taxBeanList.add(taxBeanObj);
		}
		return taxBeanList;
	}

	private TaxInfoBean perpareTaxInfoBean(TaxInfo taxInfo) {
		TaxInfoBean taxBeanObj = null;
		try {
			if (null != taxInfo && null != taxInfo.getHouseInfo()) {
				taxBeanObj = new TaxInfoBean();
				taxBeanObj.setHouseNumber(taxInfo.getHouseInfo().getHouseNumber());
				taxBeanObj.setTaxId(taxInfo.getTaxID());
				taxBeanObj.setHouseTax(taxInfo.getHouseTax());
				taxBeanObj.setLibraryTax(taxInfo.getLibraryTax());
				taxBeanObj.setLightTax(taxInfo.getLightTax());
				taxBeanObj.setDrainageTax(taxInfo.getDrainageTax());
				taxBeanObj.setWaterTax(taxInfo.getWaterTax());
				taxBeanObj.setKulaiNelaVariFee(taxInfo.getKulaiNelaVariFee());
				taxBeanObj.setKulaiDeposit(taxInfo.getKulaiDeposit());
				taxBeanObj.setLicenseFee(taxInfo.getLicenseFee());
				taxBeanObj.setHouseConstructionFee(taxInfo.getHouseConstructionFee());
				taxBeanObj.setDakhalaFee(taxInfo.getDakhalaFee());
				taxBeanObj.setBandhelaDoddi(taxInfo.getBandhelaDoddi());
				taxBeanObj.setBuildingRents(taxInfo.getBuildingRents());
				taxBeanObj.setOthersKey(taxInfo.getOthersKey());
				taxBeanObj.setOtherValue(taxInfo.getOtherValue());
				taxBeanObj.setTotalTax(taxInfo.getTotalTax());
				taxBeanObj.setTaxYear(taxInfo.getTaxYear());
				taxBeanObj.setTaxStatus(vtsUtil.getTaxStatus(taxInfo.getTaxStatus()));
			} else {
				taxBeanObj = new TaxInfoBean();
			}
		} catch (Exception e) {
			logger.error("perpareTaxInfoBean :: preparing ui object excpetion ", e);
			taxBeanObj = new TaxInfoBean();
		}
		return taxBeanObj;
	}

	private TaxInfoBean perparePrevTaxInfoBean(TaxInfo taxInfo) {
		TaxInfoBean taxBeanObj = null;
		try {
			if (null != taxInfo && null != taxInfo.getHouseInfo()) {
				taxBeanObj = new TaxInfoBean();
				taxBeanObj.setHouseNumber(taxInfo.getHouseInfo().getHouseNumber());
				taxBeanObj.setTaxId(taxInfo.getTaxID());
				// Adding 5% to the tax
				double dobleHouseTax = (double) (taxInfo.getHouseTax());

				dobleHouseTax = (dobleHouseTax) + (dobleHouseTax / 100.0) * 5.0;
				taxBeanObj.setHouseTax((long) Math.floor(dobleHouseTax));
				taxBeanObj.setLibraryTax(taxInfo.getLibraryTax());
				taxBeanObj.setLightTax(taxInfo.getLightTax());
				taxBeanObj.setDrainageTax(taxInfo.getDrainageTax());
				taxBeanObj.setWaterTax(taxInfo.getWaterTax());
				taxBeanObj.setKulaiNelaVariFee(taxInfo.getKulaiNelaVariFee());
				taxBeanObj.setKulaiDeposit(taxInfo.getKulaiDeposit());
				taxBeanObj.setLicenseFee(taxInfo.getLicenseFee());
				taxBeanObj.setHouseConstructionFee(taxInfo.getHouseConstructionFee());
				taxBeanObj.setDakhalaFee(taxInfo.getDakhalaFee());
				taxBeanObj.setBandhelaDoddi(taxInfo.getBandhelaDoddi());
				taxBeanObj.setBuildingRents(taxInfo.getBuildingRents());
				taxBeanObj.setOthersKey(taxInfo.getOthersKey());
				taxBeanObj.setOtherValue(taxInfo.getOtherValue());
				taxBeanObj.setTotalTax(taxInfo.getTotalTax());
				// Incrementing Year by 1
				long lognYear = Long.parseLong(taxInfo.getTaxYear()) + 1;
				taxBeanObj.setTaxYear(lognYear + "");
				taxBeanObj.setTaxStatus(vtsUtil.getTaxStatus(taxInfo.getTaxStatus()));
			} else {
				taxBeanObj = new TaxInfoBean();
			}
		} catch (Exception e) {
			logger.error("perpareTaxInfoBean :: preparing ui object excpetion ", e);
			taxBeanObj = new TaxInfoBean();
		}
		return taxBeanObj;
	}

	private TaxInfo prepareDBObject(TaxInfoBean taxBeanObj) {
		logger.info("prepareDBObject :: preparing entity object");
		TaxInfo taxInfo = new TaxInfo();
		HouseInfo houseInfo = new HouseInfo();
		houseInfo.setHouseNumber(taxBeanObj.getHouseNumber());
		taxInfo.setHouseInfo(houseInfo);
		taxInfo.setTaxID(taxBeanObj.getTaxId());
		taxInfo.setHouseTax(taxBeanObj.getHouseTax());
		taxInfo.setLibraryTax(taxBeanObj.getLibraryTax());
		taxInfo.setLightTax(taxBeanObj.getLightTax());
		taxInfo.setDrainageTax(taxBeanObj.getDrainageTax());
		taxInfo.setWaterTax(taxBeanObj.getWaterTax());
		taxInfo.setKulaiNelaVariFee(taxBeanObj.getKulaiNelaVariFee());
		taxInfo.setKulaiDeposit(taxBeanObj.getKulaiDeposit());
		taxInfo.setLicenseFee(taxBeanObj.getLicenseFee());
		taxInfo.setHouseConstructionFee(taxBeanObj.getHouseConstructionFee());
		taxInfo.setDakhalaFee(taxBeanObj.getDakhalaFee());
		taxInfo.setBandhelaDoddi(taxBeanObj.getBandhelaDoddi());
		taxInfo.setBuildingRents(taxBeanObj.getBuildingRents());
		taxInfo.setOthersKey(taxBeanObj.getOthersKey());
		taxInfo.setOtherValue(taxBeanObj.getOtherValue());
		taxInfo.setTotalTax(taxBeanObj.getTotalTax());
		taxInfo.setTaxYear(taxBeanObj.getTaxYear());
		taxInfo.setTaxStatus(TaxStatus.PENDING);
		logger.info("prepareDBObject :: preparing entity object taxInfo " + taxInfo);
		return taxInfo;
	}

}
