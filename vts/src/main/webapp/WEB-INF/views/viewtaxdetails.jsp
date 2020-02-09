<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<meta charset="utf-8">
<style>
.btn {
	margin-top: 5px;
}


@media print {
	.no-print, .no-print * {
		display: none !important;
	}
}
</style>
<div id="page-wrapper" class="no-print" style="margin-top: 85px;">
	<div class="container" class="no-print">
		<div class="row">
			<div class="form-group 	col-md-3">
				<button type="submit" name="addTax"
					class="btn btn-primary btn-block" data-toggle="modal"
					data-target="#addTaxModal" value="Add Tax">Add Tax</button>
			</div>
			<div class="form-group 	col-md-3">
				<button type="submit" name="payment"
					class="btn btn-primary btn-block" data-toggle="modal"
					data-target="#paymentTaxModal" value="total tax payment">total
					tax payment</button>
				<input type="submit" name="printReceipt" class="btn btn-primary btn-block" data-toggle="modal" data-target="#printModel" value="receipt print">&nbsp;
			</div>
		</div>
		<div>
			<table class="table table-responsive  table-striped">
				<thead>
					<tr>
						<th><label>e</label><label>O</label><label>ti</label> Tax </th>
						<th>Village Tax</th>
						<th>Water Tax</th>
						<th>Lighting Tax</th>
						<th>cleanliness Tax</th>
						<th>Total Tax Amount</th>
						<th>Tax Year</th>
						<th>Payment Status</th>
						<th>Pay</th>
						<!-- class="notranslate" -->
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty vtsTaxInfoBean.taxBeanList}">
						<tr>
							<td colspan="8" align="center">tax details add.</td>
						</tr>
					</c:if>
					<c:forEach items="${vtsTaxInfoBean.taxBeanList}" var="taxBeanList">
						<tr>
							<td class="align-middle"><c:out
									value="${taxBeanList.houseTax}"></c:out></td>
							<td class="align-middle"><c:out
									value="${taxBeanList.villageTax}" /></td>
							<td class="align-middle"><c:out
									value="${taxBeanList.waterTax}" /></td>
							<td class="align-middle"><c:out
									value="${taxBeanList.lightingTax}" /></td>
							<td class="align-middle"><c:out
									value="${taxBeanList.cleaningTax}" /></td>
							<td class="align-middle"><c:out
									value="${taxBeanList.totalTax}" /></td>
							<td class="align-middle"><c:out
									value="${taxBeanList.taxYear}" /></td>
							<td class="notranslate align-middle"><c:out
									value="${taxBeanList.taxStatus}" /></td>
							<td><Button type="submit" name="taxPayment"
									class="btn btn-primary" data-toggle="modal" data-target="#paymentModel"
									value="<c:out value="${taxBeanList.taxId}" />">Pay</Button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div id="response">
			<div class="form-group">
				<label for="response" id="addResp" class="text-success"></label> <label
					for="response" id="failedResp" class="text-danger"></label>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<div class="modal fade" id="printModel" tabindex="-1" role="dialog"
	aria-labelledby="printModel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-body table-responsive">
				<br>
				<div class="col-12 text-center">
					Grama Panchayati Office, Noothpally<br> Mandal Nandipet, Dist.
					Nijamabad <br> <span class="notranslate">Receipt</span>
					<hr>
				</div>
				<div class="row">
					<div class="col-sm-6 notranslate">
						Receipt No. :
						<c:out value="${vtsTaxInfoBean.currentTax.taxId}" />
					</div>
					<div class="col-sm-6">
						Date :
						<c:out value="${vtsTaxInfoBean.currentDate}" />
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						Mrs/Mr :
						<c:out value="${vtsTaxInfoBean.houseOwnerBean.ownerName}" />
					</div>

					<div class="col-sm-6 .text-dark">
						Father/Husband :
						<c:out value="${vtsTaxInfoBean.houseOwnerBean.fatherOrHusband}" />
					</div>
				</div>
				<div class="row">
					<div class="col-sm-4">
						House No. :
						<c:out value="${vtsTaxInfoBean.houseNumber}" />
					</div>
					<div class="col-sm-4"><span>Tap</span> No :</div>
					<div class="col-sm-4">
						Year :
						<c:out value="${vtsTaxInfoBean.taxYear}" />
					</div>
				</div>
				<div class="table-responsive">
					<table class="table table-bordered">
						<tr>
							<td>s.no</td>
							<td>Tax Details</td>
							<td>Dues</td>
							<td>Present</td>
							<td>Total</td>
						</tr>
						<tr>
							<td>1</td>
							<td><label>e</label><label>O</label><label>ti</label> Tax </td>
							<td><c:out value="${vtsTaxInfoBean.dueTax.houseTax}" /></td>
							<td><c:out value="${vtsTaxInfoBean.currentTax.houseTax}" /></td>
							<td><c:out
									value="${vtsTaxInfoBean.dueTax.houseTax+vtsTaxInfoBean.currentTax.houseTax}" /></td>
						</tr>
						<tr>
							<td>2</td>
							<td>Library tax</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>3</td>
							<td>Lighting Tax</td>
							<td><c:out value="${vtsTaxInfoBean.dueTax.lightingTax}" /></td>
							<td><c:out value="${vtsTaxInfoBean.currentTax.lightingTax}" /></td>
							<td><c:out
									value="${vtsTaxInfoBean.dueTax.lightingTax+vtsTaxInfoBean.currentTax.lightingTax}" /></td>
						</tr>
						<tr>
							<td>4</td>
							<td>Drainage Tax</td>
							<td><c:out value="${vtsTaxInfoBean.dueTax.cleaningTax}" /></td>
							<td><c:out value="${vtsTaxInfoBean.currentTax.cleaningTax}" /></td>
							<td><c:out
									value="${vtsTaxInfoBean.dueTax.cleaningTax +vtsTaxInfoBean.currentTax.cleaningTax}" /></td>
						</tr>
						<tr>
							<td>5</td>
							<td>Water Tax</td>
							<td><c:out value="${vtsTaxInfoBean.dueTax.waterTax}" /></td>
							<td><c:out value="${vtsTaxInfoBean.currentTax.waterTax}" /></td>
							<td><c:out
									value="${vtsTaxInfoBean.dueTax.waterTax+vtsTaxInfoBean.currentTax.waterTax}" /></td>
						</tr>
						<tr>
							<td>6</td>
							<td><span>Tap</span> <span>monthly</span><span>Fiju</span></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>7</td>
							<td><span>Tap</span> Deposit</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>8</td>
							<td>License Fee</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>9</td>
							<td>House Construction Feeju</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>10</td>
							<td>Dhakala Fee</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>11</td>
							<td>Banddela Doddi</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>12</td>
							<td>Building Rents</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>13</td>
							<td>Others</td>
							<td><c:out value="${vtsTaxInfoBean.dueTax.villageTax}" /></td>
							<td><c:out value="${vtsTaxInfoBean.currentTax.villageTax}" /></td>
							<td><c:out
									value="${vtsTaxInfoBean.dueTax.villageTax + vtsTaxInfoBean.currentTax.villageTax}"></c:out></td>
						</tr>
						<tr>
							<td></td>
							<td>Total Amt</td>
							<td><c:out value="${vtsTaxInfoBean.dueTax.totalTax}" /></td>
							<td><c:out value="${vtsTaxInfoBean.currentTax.totalTax}" /></td>
							<td><c:out
									value="${vtsTaxInfoBean.dueTax.totalTax + vtsTaxInfoBean.currentTax.totalTax}"></c:out></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="modal-footer no-print">
				<button type="button" class="btn btn-secondary notranslate"
					data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary notranslate"
					onclick="window.print()">Print</button>
			</div>
		</div>
	</div>
</div>

<!-- Adding Tax -->
<div class="modal fade" id="addTaxModal" tabindex="-1" role="dialog"
	aria-labelledby="addTaxModalTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header no-print" align="center">
				<h4 class="modal-title no-print" id="addTaxModalTitle">Add Tax</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<!-- <form id="addTaxForm"> -->
			<form id="addTaxForm" name="addTaxForm"
				class="form-horizontal border needs-validation"
				novalidate="novalidate">
				<div class="modal-body">
					<input type="text" class="form-control" id="hno" name="hno"
						hidden="hidden" value="${vtsTaxInfoBean.houseNumber}" />
					<div class="form-group">
						<label for="houseTax"><label>e</label><label>O</label><label>ti</label> Tax </label> <input type="text"
							name="houseTax" class="form-control"
							onkeypress="return event.charCode >= 48 && event.charCode <= 57"
							required="required" minlength="1" maxlength="10"
							pattern="[1-9]{1}[0-9]*" />
						<div class="invalid-feedback notranslate">Please enter valid
							house tax</div>
					</div>
					<div class="form-group">
						<label for="inputVillageTax">Village Tax</label> <input
							type="text" class="form-control"
							onkeypress="return event.charCode >= 48 && event.charCode <= 57"
							id="villageTax" name="villageTax" required="required"
							minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" />
						<div class="invalid-feedback notranslate">Please enter valid
							village tax</div>
					</div>
					<div class="form-group">
						<label for="inputWaterTax">Water Tax</label> <input type="text"
							class="form-control" id="waterTax" name="waterTax"
							required="required" minlength="1" maxlength="10"
							onkeypress="return event.charCode >= 48 && event.charCode <= 57"
							pattern="[1-9]{1}[0-9]*" />
						<div class="invalid-feedback notranslate">Please enter valid
							water tax</div>
					</div>
					<div class="form-group">
						<label for="lightingTax">Lighting Tax</label> <input type="text"
							class="form-control" id="lightingTax" name="lightingTax"
							required="required" pattern="[1-9]{1}[0-9]*"
							onkeypress="return event.charCode >= 48 && event.charCode <= 57"
							minlength="1" maxlength="10" />
						<div class="invalid-feedback notranslate">Please enter
							lighting tax</div>
					</div>
					<div class="form-group">
						<label for="cleanlinessTax">Cleanliness Tax</label> <input
							type="text" class="form-control" pattern="[1-9]{1}[0-9]*"
							id="cleaningTax" name="cleaningTax" required="required"
							onkeypress="return event.charCode >= 48 && event.charCode <= 57"
							minlength="1" maxlength="10" />
						<div class="invalid-feedback notranslate">Please enter
							cleanliness tax</div>
					</div>
					<div class="form-group date">
						<label for="inputcleanlinessTax">Tax Year <span
							class="notranslate">(YYYY)</span></label> <input type="text"
							class="form-control"
							onkeypress="return event.charCode >= 48 && event.charCode <= 57"
							id="taxYear" name="taxYear" required="required"
							pattern="[2]{1}[0]{1}[1-2]{1}[0-9]{1}" />
						<div class="invalid-feedback notranslate">Please enter valid
							year tax</div>
					</div>

				</div>
				<div class="modal-footer no-print">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">
						<span class="notranslate">Close</span>
					</button>
					<button type=submit id="addTaxService"
						class="btn btn-primary notranslate">Save</button>
				</div>
			</form>
		</div>
	</div>
</div>

<!-- paymentModel -->
<div class="modal fade" id="paymentModel" tabindex="-1" role="dialog" aria-labelledby="paymentModel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header no-print" align="center">
				<h4 class="modal-title no-print" id="paymentModelTitle">Tax Payment</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body table-responsive">
				<br>
				<div class="table-responsive">
					<form class="form-horizontal border needs-validation" novalidate="novalidate" id ="paymentForm">
						<input name="houseNum" id="houseNum" type="text" class="form-control" id="houseNum" hidden="hidden" value="${vtsTaxInfoBean.houseNumber}" />
					<table class="table ">
						<thead>
						<tr>
							<th>s.no</th>
							<th>Tax Details</th>
							<th>Amount Paying</th>
							</tr>
						</thead>
						<tbody>
						<tr>
							<td>1</td>
							<td style="margin: 0; display: inline-block; float: left;"><label>e</label><label>O</label><label>ti</label> Tax </td>
							<td><input type="text" name="houseTax" id="houseTax" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required="required" minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" /></td>
						</tr>
						<tr>
							<td>2</td>
							<td>Library Tax</td>
							<td><input type="text" name="libraryTax" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required="required" minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" /></td>
							
						</tr>
						<tr>
							<td>3</td>
							<td>Lighting Tax</td>
							<td><input type="text" name="lightTax" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required="required" minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" /></td>
						</tr>
						<tr>
							<td>4</td>
							<td>Drainage Tax</td>
							<td><input type="text" name="drainageTax" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required="required" minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" /></td>
						</tr>
						<tr>
							<td>5</td>
							<td>Water Tax</td>
							<td><input type="text" name="waterTax" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required="required" minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" /></td>
						</tr>
						<tr>
							<td>6</td>
							<td><span>Tap</span> <span>monthly</span><span>Fee</span></td>
							<td><input type="text" name="kulaiTax" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required="required" minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" /></td>
						</tr>
						<tr>
							<td>7</td>
							<td><span>Tap</span> Deposit</td>
							<td><input type="text" name="kulaiDeposit" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required="required" minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" /></td>
						</tr>
						<tr>
							<td>8</td>
							<td>License Fee</td>
							<td><input type="text" name="licenseFee" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required="required" minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" /></td>
						</tr>
						<tr>
							<td>9</td>
							<td>House Construction Feeju</td>
							<td><input type="text" name="houseFee" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required="required" minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" /></td>
						</tr>
						<tr>
							<td>10</td>
							<td>Dhakala Fee</td>
							<td><input type="text" name="dhakalFee" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required="required" minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" /></td>
						</tr>
						<tr>
							<td>11</td>
							<td>Bandhela Doddi</td>
							<td><input type="text" name="bandhelaDoddi" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required="required" minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" /></td>
						</tr>
						<tr>
							<td>12</td>
							<td>Building Rents</td>
							<td><input type="text" name="buildingRents" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required="required" minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" /></td>
						</tr>
						<tr>
							<td>13</td>
							<td><input type="text" class="form-control" id="waterTax" name="waterTax" required="required" minlength="1" maxlength="10" value="Others" /></td>
							<td><input type="text" name="others" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required="required" minlength="1" maxlength="10" pattern="[1-9]{1}[0-9]*" /></td>
						</tr>
						<tr>
							<td></td>
							<td>Total Amount</td>
							<td></td>
						</tr>
						</tbody>
					</table>
					</form>
				</div>
			</div>
			<div class="modal-footer no-print">
				<button type="button" class="btn btn-secondary notranslate" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary notranslate" id="paymentSubmit">Pay</button>
			</div>
		</div>
	</div>
</div>

<!-- END paymentModel -->


<!-- Payment Model -->
<div class="modal fade" id="paymentTaxModal" tabindex="-1" role="dialog"
	aria-labelledby="paymentTaxModalTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header no-print" align="center">
				<h4 class="modal-title" id="paymentTaxModalTitle">Tax Payment</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<form class="form-horizontal border needs-validation" novalidate="novalidate">
				<div class="modal-body">
					<input name="houseNum" type="text" class="form-control" id="houseNum" hidden="hidden" value="${vtsTaxInfoBean.houseNumber}" />
					<div class=" form-group radio">
						<label class="radio-inline"> <input type="radio"
							name="paid" checked>Total Tax <c:out
								value="${vtsTaxInfoBean.dueTax.totalTax + vtsTaxInfoBean.currentTax.totalTax}"></c:out>rs.
						</label>
						<div class="invalid-feedback notranslate">Please select
							total tax</div>
					</div>
					<div class=" form-group radio">
						<label class="radio-inline"> <input type="radio"
							name="optradio" checked>Paid
						</label>
						<div class="invalid-feedback notranslate">Please select paid</div>
					</div>
				</div>
				<div class="modal-footer no-print">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">
						<span class="notranslate">Close</span>
					</button>
					<button type="submit" id="pay" class="btn btn-primary notranslate">Submit</button>
				</div>
			</form>
		</div>
	</div>
</div>
<script src="/resources/assets/js/formValidation.js"></script>
<script type="text/javascript">
	$(function() {
		/*  Submit form using Ajax */
		$("#addResp").html("");
		$("#failedResp").html("");
		$('#addTaxService').click(function(e) {
			//Prevent default submission of form
			e.preventDefault();
			var submitTaxData = $('form[name=addTaxForm]').serialize();
			//alert(submitTaxData);
			$.post({
				url : './addtaxdetails',
				data : submitTaxData,
				success : function(resp) {
				$("#addTaxModal").modal("hide");
					if (resp.respCode == 200) {
						$("#addResp").html(resp.respDesc);
					} else {
						$("#failedResp").html("Please enter valid house number.");
					}
				}
			});
			setTimeout(function() {location.reload(true);}, 2000);
		});

		$('#paymentSubmit').click(function(e) {
			//Prevent default submission of form paymentForm
			e.preventDefault();
			var submitPaymentData = $('form[name=paymentForm]').serialize();
			alert(submitPaymentData);
			/* $.post({
				url : './submitPayment',
				data : submitPaymentData,
				success : function(resp) {
				$("#paymentModel").modal("hide");
					if (resp.respCode == 200) {
						$("#addResp").html(resp.respDesc);
					} else {
						$("#failedResp").html("Please enter valid house number.");
					}
				}
			});
			setTimeout(function() {location.reload(true);}, 2000); */
		});
		
		$('#pay').click(function(e) {
			//Prevent default submission of form
			e.preventDefault();
			var houseNum = $('#houseNum').val();
			//alert(houseNum);
			$.post({
				url : './savePayment?houseNum=' + houseNum,
				success : function(resp) {
					$("#paymentTaxModal").modal("hide");
					if (resp.respCode == 200) {
						$("#addResp").html(resp.respDesc);
					} else {
						$("#failedResp").html(resp.respDesc);
					}
				}
			});
			setTimeout(function() {
				location.reload(true);
			}, 3000);

		});

	});
</script>
