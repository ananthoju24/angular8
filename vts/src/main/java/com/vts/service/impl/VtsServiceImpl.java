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
import com.vts.beans.TaxDetails;
import com.vts.beans.TaxInfoBean;
import com.vts.beans.VtsTaxInfoBean;
import com.vts.entity.HouseInfo;
import com.vts.entity.OwnerInfo;
import com.vts.entity.TaxInfo;
import com.vts.entity.User;
import com.vts.response.VTSRespone;
import com.vts.service.VtsService;
import com.vts.service.dao.TaxDao;
import com.vts.service.dao.impl.VtsDaoService;
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
			ownerInfo.setFatherOrHusband(owner.getFatherOrHusband());
			HouseInfo houseInfo = new HouseInfo();
			houseInfo.setHouseNumber(owner.getHouseNumber());
			houseInfo.setOwnerInfo(ownerInfo);

			String response = vtsDaoService.saveOwnerandHouseInfo(ownerInfo, houseInfo);
			if (null != response && "SUCCESS".equalsIgnoreCase(response)) {
				vtsResponse.setRespCode(201);
				vtsResponse.setRespDesc("successfully added");
			} else {
				vtsResponse.setRespCode(404);
				vtsResponse.setRespDesc("add failed.Please try again");
			}
		} catch (Exception e) {
			logger.error("addOnwerDetails :: failed to add owner to DB ", e);
			vtsResponse.setRespCode(404);
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
	public VtsTaxInfoBean addTaxDetails(TaxInfoBean taxInfoBean) {
		logger.info("addTaxDetails :: Request add tax details :: " + taxInfoBean);
		VtsTaxInfoBean vtsTaxBean = null;
		TaxInfo taxInfoDBObj = prepareDBObject(taxInfoBean);
		TaxInfo taxInfo2 = vtsDaoService.addTaxDetails(taxInfoDBObj);
		if (null != taxInfo2) {
			vtsTaxBean = getTaxDetails(taxInfoBean.getHouseNumber());
			logger.info("addTaxDetails :: response from db :: " + taxInfo2);
		}
		return vtsTaxBean;
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
		// TaxInfo totalDueTax = vtsDaoService.getTotalDueTax(houseNumber);
		// logger.info("getTaxDetails :: total due tax :: " + totalDueTax);
		vtsTaxBean.setHouseNumber(houseNumber);
		vtsTaxBean.setCurrentDate(vtsUtil.getDate());
		vtsTaxBean.setTaxYear(vtsUtil.getPreviousYear() + "-" + vtsUtil.getCurrentYear());

		HouseInfo hInfo = vtsDaoService.fetchHouseInfo(houseNumber);
		if (null != hInfo) {
			vtsTaxBean.setHouseOwnerBean(prepareOwnerBean(hInfo));
		}
		/*
		 * if(null != totalDueTax) { totalDueTax.setHouseInfo(hInfo); TaxInfoBean dueTax
		 * = perpareTaxInfoBean(totalDueTax); vtsTaxBean.setDueTax(dueTax); }
		 */
		if (null != taxInfoList) {
			logger.info("getTaxDetails :: found data in db for  " + houseNumber);
			// prepareTaxListObj(taxInfoList, vtsTaxBean);
		}
		logger.info("getTaxDetails :: returning response as :  " + vtsTaxBean);
		return vtsTaxBean;
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
				taxBeanObj.setTaxStatus(taxInfo.getTaxStatus());
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
		taxInfo.setTaxStatus(taxBeanObj.getTaxStatus());
		taxInfo.setTotalTax(taxBeanObj.getTotalTax());
		taxInfo.setTaxYear(taxBeanObj.getTaxYear());
		taxInfo.setTaxStatus(taxBeanObj.getTaxStatus());
		return taxInfo;
	}
}
