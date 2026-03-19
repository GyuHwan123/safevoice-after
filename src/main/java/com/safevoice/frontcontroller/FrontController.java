package com.safevoice.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safevoice.controller.Command;
//import com.safevoice.controller.Alert.SendPushNotificationService;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    protected void service(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String path = getCommandPath(request);

        Command command = CommandRouter.getCommand(path);

        String view = null;

        if (command != null) {
            view = command.execute(request, response);
        }
        else if (path.startsWith("Go")) {
            // GoLogin.do → login.jsp
        	view = path.substring(2).replace(".do", ".jsp");
        }

        move(request, response, view);
    }

    private String getCommandPath(HttpServletRequest request) {

        String uri = request.getRequestURI();
        String cp = request.getContextPath();

        return uri.substring(cp.length() + 1);
    }

    private void move(HttpServletRequest request,
                      HttpServletResponse response,
                      String view)
            throws ServletException, IOException {

        if (view == null) return;

        if (view.startsWith("Go")) {
            view = view.substring(2).replace(".do", ".jsp");
        }
        
        if (view.startsWith("redirect:")) {
            response.sendRedirect(request.getContextPath() + view.substring(9));
            return;
        }

        RequestDispatcher rd =
                request.getRequestDispatcher("/WEB-INF/" + view);

        rd.forward(request, response);
    }
}
