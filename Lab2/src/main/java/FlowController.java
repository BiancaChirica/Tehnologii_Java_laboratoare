import models.ErrorBean;
import models.WordBean;
import models.WordBeanList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controls the flow and the logic of the application
 */
@WebServlet(name = "FlowController", urlPatterns = "/FlowController")
public class FlowController extends HttpServlet {

    public static final String ERROR_ATTRIBUTE_NAME = "error";
    public static final String ERROR_NULL_FIELDS_MESSAGE = "Fields can't be null.";
    public static final String ERROR_WRONG_CAPTCHA_MESSAGE = "Captcha didn't match. <br/> Correct answer : ";
    public static final String YOUR_ANSWER_MESSAGE = "<br/> Your answer :";
    public static final String ERROR_DUPLICATE_WORD_MESSAGE = "Word already exists in the database.";

    /**
     * Handles Post method, submitted by form
     *
     * @param request  : the user's request
     * @param response : the user's response
     * @throws ServletException : exception thrown
     * @throws IOException      : exception thrown
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get parameters]
        String language = request.getParameter("language");
        String word = request.getParameter("word");
        String definition = request.getParameter("definition");
        String captchaInput = request.getParameter("captchaInput");

        log(definition);
        // validate params
        if (language == null || language.trim().length() == 0 || word == null || word.trim().length() == 0
                || definition == null || definition.trim().length() == 0
                || captchaInput == null || captchaInput.trim().length() == 0) {
            // if null send to error page
            sendToErrorPage(ERROR_NULL_FIELDS_MESSAGE, request, response);
            return;
        }

        String validCaptcha = request.getSession().getAttribute("captcha").toString().trim();
        // validate captcha
        if (!captchaInput.trim().equals(validCaptcha)) {
            // if null send to error page
            sendToErrorPage(ERROR_WRONG_CAPTCHA_MESSAGE + validCaptcha + YOUR_ANSWER_MESSAGE + captchaInput, request, response);
            return;
        }

        // create new word bean to send to result
        WordBean wordBean = new WordBean();
        wordBean.setLanguage(language);
        wordBean.setWord(word);
        wordBean.setDefinition(definition);

        // check if is duplicate word
        DatabaseServices databaseServices = new DatabaseServices();
        // if it's not duplicate,add word and redirect to result page
        if (!databaseServices.isDuplicateWord(wordBean)) {
            databaseServices.addNewWord(wordBean);
            WordBeanList wordList = databaseServices.getAllWordsForALanguage(wordBean.getLanguage());
            request.setAttribute("wordList", wordList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/result.jsp");
            dispatcher.forward(request, response);
        } else {
            sendToErrorPage(ERROR_DUPLICATE_WORD_MESSAGE, request, response);
        }
    }

    /**
     * Handles GET method
     * Saves language cookie
     *
     * @param req  : the user's request
     * @param resp : the user's response
     * @throws ServletException : exception thrown
     * @throws IOException      : exception thrown
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // if request has language parameter save cookie
        String cookieLang = req.getParameter("lang");
        if (cookieLang != null) {
            // request from changing the language, save cookie
            Cookie theCookie = new Cookie("MyDictionaryLanguage", cookieLang);
            theCookie.setMaxAge(9999999 * 99);
            resp.addCookie(theCookie);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/input.jsp");
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Method to redirect user to error page.
     *
     * @param errorMessage : the error to display to the user
     * @param request      : the user's request
     * @param response     : the response sent to the user
     * @throws ServletException : exception thrown
     * @throws IOException      : exception thrown
     */
    private void sendToErrorPage(String errorMessage, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
        ErrorBean errorBean = new ErrorBean();
        errorBean.setErrorText(errorMessage);
        request.setAttribute(ERROR_ATTRIBUTE_NAME, errorBean);
        dispatcher.forward(request, response);
    }
}
