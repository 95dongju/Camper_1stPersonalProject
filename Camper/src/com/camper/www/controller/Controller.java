package com.camper.www.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camper.www.service.GDeleteReviewService;
import com.camper.www.service.GJoinService;
import com.camper.www.service.GLoginService;
import com.camper.www.service.GLogoutService;
import com.camper.www.service.GModifyService;
import com.camper.www.service.GModifyViewService;
import com.camper.www.service.GReservationCOGuestListService;
import com.camper.www.service.GReservationGuestListService;
import com.camper.www.service.GReservationService;
import com.camper.www.service.GReservationViewService;
import com.camper.www.service.GReviewListService;
import com.camper.www.service.GReviewViewService;
import com.camper.www.service.GWithdrawService;
import com.camper.www.service.GWriteReviewService;
import com.camper.www.service.GemailConfirmService;
import com.camper.www.service.GidConfirmService;
import com.camper.www.service.HCampgroundDelService;
import com.camper.www.service.HCampgroundListService;
import com.camper.www.service.HCampgroundModifyService;
import com.camper.www.service.HCampgroundModifyViewService;
import com.camper.www.service.HCampgroundRegistService;
import com.camper.www.service.HCampgroundViewService;
import com.camper.www.service.HCampsiteRegistService;
import com.camper.www.service.HRejectReservationViewService;
import com.camper.www.service.HJoinService;
import com.camper.www.service.HLoginService;
import com.camper.www.service.HLogoutService;
import com.camper.www.service.HModifyService;
import com.camper.www.service.HModifyViewService;
import com.camper.www.service.HReservationCOGuestListForHostService;
import com.camper.www.service.HReservationGuestListForHostService;
import com.camper.www.service.HReviewListService;
import com.camper.www.service.HWithdrawService;
import com.camper.www.service.HbisnumConfirmService;
import com.camper.www.service.HemailConfirmService;
import com.camper.www.service.HidConfirmService;
import com.camper.www.service.MCampNameListAppendService;
import com.camper.www.service.MSearchCampService;
import com.camper.www.service.Service;


@WebServlet("*.do")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		actionDo(request, response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String command = uri.substring(conPath.length());
		String viewPage = null;
		Service service = null;
		// ***************** 공통 요청 *****************
		if(command.equals("/main.do")) {
			viewPage = "main/main.jsp";
		}else if(command.equals("/loginView.do")) {
			viewPage = "main/login.jsp";
		}else if(command.equals("/campgroundView.do")) {
			service = new HCampgroundViewService();
			service.execute(request, response);
			viewPage = "host/campgroundView.jsp";
		}else if(command.equals("/searchCamp.do")) {
			service = new MSearchCampService();
			service.execute(request, response);
			viewPage = "main/campgroundSearchList.jsp";
		}else if(command.equals("/campNameListAppend.do")) {
			service = new MCampNameListAppendService();
			service.execute(request, response);
			viewPage = "main/campgroundAppend.jsp";
		// ***************** 게스트/호스트 로그인 *****************
		}else if(command.equals("/gidConfirm.do")) {
			service = new GidConfirmService();
			service.execute(request, response);
			viewPage = "guest/gidConfirm.jsp";
		}else if(command.equals("/gemailConfirm.do")) {
			service = new GemailConfirmService();
			service.execute(request, response);
			viewPage = "guest/gemailConfirm.jsp";
		}else if(command.equals("/guestJoin.do")) {
			service = new GJoinService();
			service.execute(request, response);
			viewPage = "guest/loginGuest.jsp";
		}else if(command.equals("/guestLogin.do")) {
			service = new GLoginService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
		}else if(command.equals("/hostJoin.do")) {
			service = new HJoinService();
			service.execute(request, response);
			viewPage = "host/loginHost.jsp";
		}else if(command.equals("/hostLoginView.do")) {
			viewPage = "host/joinHost.jsp";
		}else if(command.equals("/hostLogin.do")) {
			service = new HLoginService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
		}else if(command.equals("/hidConfirm.do")) {
			service = new HidConfirmService();
			service.execute(request, response);
			viewPage = "host/hidConfirm.jsp";
		}else if(command.equals("/hemailConfirm.do")) {
			service = new HemailConfirmService();
			service.execute(request, response);
			viewPage = "host/hemailConfirm.jsp";
		}else if(command.equals("/bisnumConfirm.do")) {
			service = new HbisnumConfirmService();
			service.execute(request, response);
			viewPage = "host/bisnumConfirm.jsp";
		// ***************** 게스트/호스트 로그아웃 *****************
		}else if(command.equals("/logoutGuest.do")) {
			service = new GLogoutService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
		}else if(command.equals("/logoutHost.do")) {
			service = new HLogoutService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
		// ********** 게스트/호스트 마이페이지 / 정보 수정 *************
		}else if(command.equals("/mypageGuest.do")) {
			viewPage = "guest/mypageGuest.jsp";
		}else if(command.equals("/mypageHost.do")) {
			viewPage = "host/mypageHost.jsp";
		}else if(command.equals("/modifyGuestView.do")) {
			service = new GModifyViewService();
			service.execute(request, response);
			viewPage = "guest/modifyGuest.jsp";	
		}else if(command.equals("/modifyHostView.do")) {
			service = new HModifyViewService();
			service.execute(request, response);
			viewPage = "host/modifyHost.jsp";	
		}else if(command.equals("/modifyGuest.do")) {
			service = new GModifyService();
			service.execute(request, response);
			viewPage = "guest/mypageGuest.jsp";	
		}else if(command.equals("/modifyHost.do")) {
			service = new HModifyService();
			service.execute(request, response);
			viewPage = "host/mypageHost.jsp";	
		}else if(command.equals("/withdrawGuestView.do")) {
			viewPage = "guest/withdrawGuest.jsp";	
		}else if(command.equals("/withdrawGuest.do")) {
			service = new GWithdrawService();
			service.execute(request, response);
			viewPage = "main/main.jsp";	
		}else if(command.equals("/withdrawHostView.do")) {
			viewPage = "host/withdrawHost.jsp";
		}else if(command.equals("/withdrawHost.do")) {
			service = new HWithdrawService();
			service.execute(request, response);
			viewPage = "main/main.jsp";	
			// ************ 호스트 캠핑장 등록 / 삭제 ***************
		}else if(command.equals("/campgroundListView.do")) {
			service = new HCampgroundListService();
			service.execute(request, response);
			viewPage = "host/campgroundList.jsp";
		}else if(command.equals("/campgroundRegistView.do")) {
			viewPage = "host/campgroundRegist.jsp";
		}else if(command.equals("/campgroundRegist.do")) {
			service = new HCampgroundRegistService();
			service.execute(request, response);
			viewPage = "host/campsiteRegist.jsp";
		}else if(command.equals("/campsiteRegist.do")) {
			service = new HCampsiteRegistService();
			service.execute(request, response);
			viewPage = "/campgroundListView.do";
		}else if(command.equals("/campgroundDelete.do")) {
			service = new HCampgroundDelService();
			service.execute(request, response);
			viewPage = "/campgroundListView.do";
		}else if(command.equals("/campgroundModifyView.do")) {
			service = new HCampgroundModifyViewService();
			service.execute(request, response);
			viewPage = "host/campgroundModify.jsp";
		}else if(command.equals("/campgroundModify.do")) {
			service = new HCampgroundModifyService();
			service.execute(request, response);
			viewPage = "/campgroundListView.do";
		// *************** 게스트 예약 페이지 ***************
		}else if(command.equals("/reservationView.do")) {
			service = new GReservationViewService();
			service.execute(request, response);
			viewPage = "guest/reservationView.jsp";
		}else if(command.equals("/reservation.do")) {
			service = new GReservationService();
			service.execute(request, response);
			viewPage = "reservationView.do";
		}else if(command.equals("/reservationGuestList.do")) {
			service = new GReservationGuestListService();
			service.execute(request, response);
			viewPage = "guest/reservationGuestList.jsp";
		}else if(command.equals("/reservationCOGuestList.do")) {
			service = new GReservationCOGuestListService();
			service.execute(request, response);
			viewPage = "guest/reservationGuestList.jsp";
		}else if(command.equals("/reservationGuestListForHost.do")) {
			service = new HReservationGuestListForHostService();
			service.execute(request, response);
			viewPage = "host/reservationGuestListForHost.jsp";
		}else if(command.equals("/reservationCOGuestListForHost.do")) {
			service = new HReservationCOGuestListForHostService();
			service.execute(request, response);
			viewPage = "host/reservationGuestListForHost.jsp";
		}else if(command.equals("/rejectReservation.do")) {
			service = new HRejectReservationViewService();
			service.execute(request, response);
			viewPage = "host/reservationGuestListForHost.jsp";
		}else if(command.equals("/writeReviewView.do")) {
			viewPage = "guest/writeReview.jsp";
		}else if(command.equals("/writeReview.do")) {
			service = new GWriteReviewService();
			service.execute(request, response);
			viewPage = "guest/reservationGuestList.jsp";
		}else if(command.equals("/reviewListforGuest.do")) {
			service = new GReviewListService();
			service.execute(request, response);
			viewPage = "guest/reviewGuestList.jsp";
		}else if(command.equals("/reviewView.do")) {
			service = new GReviewViewService();
			service.execute(request, response);
			viewPage = "main/reviewView.jsp";
		}else if(command.equals("/deleteReview.do")) {
			service = new GDeleteReviewService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
		}else if(command.equals("/reviewListforHost.do")) {
			service = new HReviewListService();
			service.execute(request, response);
			viewPage = "host/reviewGuestList.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}
}
