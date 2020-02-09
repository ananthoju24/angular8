package com.vts.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.vts.beans.HouseOwnerBean;
import com.vts.beans.OwnerBean;
import com.vts.beans.TaxInfoBean;
import com.vts.beans.TransactionBean;
import com.vts.beans.VtsTaxInfoBean;
import com.vts.response.VTSRespone;
import com.vts.service.VtsService;
import com.vts.util.VtsUtil;

@Controller
// @RequestMapping(value = "/vts")
@SessionAttributes("userName")
@CrossOrigin
public class VTSController {

	private static final Logger logger = LogManager.getLogger(VTSController.class);

	@Autowired
	private VtsService vtsService;

	@Autowired
	VtsUtil vtsUtil;

	@GetMapping({ "/", "login" })
	public String mainPage() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String processLogin(ModelMap model, @RequestParam String username, @RequestParam String password,
			HttpServletRequest request) {
		boolean isValidUser = vtsService.validateUser(username, password);
		logger.info("processLogin :: username " + username + " date " + vtsUtil.getDate() + " Year "
				+ vtsUtil.getCurrentYear());
		if (isValidUser) {
			HttpSession session = request.getSession(false);
			if (null != session) {
				session.invalidate();
			}
			session = request.getSession();
			session.setAttribute("username", username);
			session.setMaxInactiveInterval(30 * 60); // 30 mins
			model.put("userName", username);
			return "redirect:/home";
		}
		model.put("errorMsg", "Invalid username/password");
		return "login";
	}

	@GetMapping("/logout")
	public String logoutUser(HttpServletRequest httpServletRequest) {
		logger.info("logoutUser :: processing logout ");
		HttpSession httpSession = httpServletRequest.getSession(false);
		if (null != httpSession) {
			logger.info("logoutUser :: invalidating the session " + httpSession.getAttribute("username"));
			httpSession.invalidate();
		}
		return "login";
	}

	@GetMapping("/home")
	public String vtsHomePage() {
		return "home";
	}

	@GetMapping("/enroll")
	public String enrollOwnerRedirect(ModelMap model) {
		model.addAttribute("owner", new OwnerBean());
		return "enroll";
	}

	@PostMapping("/enroll")
	public String enrollOwner(ModelMap model, @ModelAttribute OwnerBean owner) {
		VTSRespone vtsResponse = null;
		logger.info("enrollOwner :: request to add new owner details " + owner);
		vtsResponse = vtsService.addOwner(owner);
		model.addAttribute("vtsResponse", vtsResponse);
		logger.info("enrollOwner :: vtsResponse " + vtsResponse);
		return enrollOwnerRedirect(model);
	}

	@GetMapping("/search/tax")
	public String searchPage(ModelMap model) {
		return "taxDetails";
	}

	@GetMapping("/taxDetails")
	public String fetchTaxData(ModelMap model, @RequestParam String houseno) {
		logger.info("fetchTaxData :: Request recevied for fetch tax data for houseno : " + houseno);
		if (null == houseno) {
			logger.info("fetchTaxData :: invalid house no");
			model.put("searchErrMsg", "Please enter valid house number");
		} else {
			HouseOwnerBean houseOwnerDetails = vtsService.getOnwerDetails(houseno.trim());
			if (null != houseOwnerDetails) {
				logger.info("fetchTaxData :: received data ");
				model.put("hoBean", houseOwnerDetails);
				return searchResult(model);
			} else {
				logger.info("fetchTaxData :: invalid house number");
				model.put("searchErrMsg", "Please enter valid house number");
			}
		}
		return "taxDetails";
	}

	@GetMapping("/searchresult")
	public String searchResult(ModelMap model) {
		return "searchresult";
	}

	@GetMapping("/viewTax")
	public String viewTaxDetails(ModelMap model, @RequestParam String houseNumber) {
		logger.info("viewTaxDetails :: Get request to handle back button");
		VtsTaxInfoBean vtsTaxInfoBean = vtsService.getTaxDetails(houseNumber);
		model.put("response", "SUCCESS");
		model.put("vtsTaxInfoBean", vtsTaxInfoBean);
		model.put("taxInfoBean", new TaxInfoBean());
		return "viewtaxdetails";
	}

	/*
	 * @PostMapping("/viewTax") public String getTaxDetails(ModelMap
	 * model, @RequestParam String houseNumber) {
	 * logger.info("getTaxDetails :: request fetch the tax details for housenumber "
	 * + houseNumber); VtsTaxInfoBean vtsTaxInfoBean =
	 * vtsService.getTaxDetails(houseNumber); model.put("response", "SUCCESS");
	 * logger.info("getTaxDetails :: response back " + vtsTaxInfoBean);
	 * model.put("vtsTaxInfoBean", vtsTaxInfoBean); model.put("taxInfoBean", new
	 * TaxInfoBean()); return "viewtaxdetails"; }
	 */

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/addtaxdetails")
	@ResponseBody
	public VTSRespone addTaxDetails(@ModelAttribute @Valid TaxInfoBean taxInfoBean, BindingResult result) {
		logger.info("addTaxData :: request to add tax data" + taxInfoBean);
		VTSRespone respone = new VTSRespone();
		if (isValid(taxInfoBean)) {
			VtsTaxInfoBean vtsTaxInfoBean = vtsService.addTaxDetails(taxInfoBean);
			if (null != vtsTaxInfoBean) {
				logger.info("addTaxData ::  successfully added in db and returing response " + vtsTaxInfoBean);
				/* model.put("houseNumber", taxInfoBean.getHno()); */
				// model.put("taxInfoBean", new TaxInfoBean());
				/* model.put("taxBeanList", taxBeanList); */
				// model.put("vtsTaxInfoBean", vtsTaxInfoBean);
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
		// model.put("vtsresponse", respone);
		// return "viewtaxdetails";
		// return getTaxDetails(model, taxInfoBean.getHno());
		// return "redirect:viewTax?houseNumber=" + taxInfoBean.getHno();
		return respone;
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/submitPayment")
	@ResponseBody
	public VTSRespone submitPayment(@ModelAttribute @Valid TransactionBean transactionBean, BindingResult result) {
		logger.info("submitPayment :: request for tax payment" + transactionBean);
		VTSRespone respone = new VTSRespone();
		if (isValidTransaction(transactionBean)) {
//			if (null != vtsTaxInfoBean) {
				logger.info("submitPayment :: tax payment success  " + transactionBean);
				respone.setRespCode(200);
				respone.setRespDesc("saved successfully");
	//		} else {
				respone.setRespCode(400);
				respone.setRespDesc("Failed to add data. Please try again.");
		//	}
		} else {
			respone.setRespCode(400);
			respone.setRespDesc("Failed to add data. Please try again.");
		}
		return respone;
	}

	private boolean isValidTransaction(@Valid TransactionBean transactionBean) {

		try {
			if (null == transactionBean) {
				return false;
			} else if (null == transactionBean.getHouseNum()) {
				return false;
			}
		} catch (Exception e) {
			logger.error(" Exception at isValidTransaction : ", e);
			return false;
		}

		return true;
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/savePayment")
	@ResponseBody
	public VTSRespone savePayment(@RequestParam String houseNum) {
		logger.info("savePayment :: request to save transation " + houseNum);
		VTSRespone respone = new VTSRespone();
		respone.setRespCode(200);
		respone.setRespDesc("Payment success");
		logger.info("savePayment :: request to save transation respone :: " + respone);
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
	 * @RequestMapping(value = "/loginFailed", method = RequestMethod.GET) public
	 * String loginError(ModelMap model) { logger.info("Invalid credentials ");
	 * model.put("errorMsg", "Invalid username/password"); return "login"; }
	 * 
	 * if we use spring security == private String getName() { String userName =
	 * null; Object principal =
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); if
	 * (principal instanceof UserDetails) { userName = ((UserDetails)
	 * principal).getUsername(); } else { userName = principal.toString(); } return
	 * userName; }
	 */
}
