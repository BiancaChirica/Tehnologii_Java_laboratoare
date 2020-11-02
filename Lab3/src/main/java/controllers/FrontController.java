package controllers;

import models.Languages;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Front controller class, all first requests go through this class
 * Reads cookie to initialize language variable
 */
@WebServlet(name = "FrontController", urlPatterns = "/")
public class FrontController extends HttpServlet {

    /**
     * Checks if cookie language exists to set language then forward request to input.jps page
     *
     * @param request  : user's request
     * @param response : user's response
     * @throws ServletException : exception that could be thrown
     * @throws IOException      : exception that could be thrown
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // gets language from cookie
        String languageNameFromCookie = null;
        Cookie theCookie;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                theCookie = cookie;
                if (theCookie.getName().equals("MyDictionaryLanguage")) {
                    languageNameFromCookie = theCookie.getValue();
                    break;
                }
            }
        }
        // set language from the cookie to the language bean
        Languages language = new Languages();
        if (languageNameFromCookie != null) {
            // comment out to test the wrapper in case there is no default language
            // language.setDefaultLanguage(languageNameFromCookie);
        }
        request.setAttribute("languages", language);

        //forward request to input
        RequestDispatcher dispatcher = request.getRequestDispatcher("/input.jsp?");
        dispatcher.forward(request, response);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (ServletException | IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (ServletException | IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
