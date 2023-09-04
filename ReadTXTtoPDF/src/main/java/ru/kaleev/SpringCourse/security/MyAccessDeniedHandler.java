//package ru.kaleev.SpringCourse.security;
//
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.access.AccessDeniedHandlerImpl;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@Component
//public class MyAccessDeniedHandler extends AccessDeniedHandlerImpl {
//
//	@Override
//	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
//			throws IOException, ServletException {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (auth != null) {
//			request.getSession().setAttribute("checkAuthor", "Tai Khoan ban khong co quyen, ban dang nhap tk khac");//
//			response.sendRedirect(request.getContextPath() + "/login/signin");
//
//		} else {
//			super.handle(request, response, exception);
//		}
//	}
//}
