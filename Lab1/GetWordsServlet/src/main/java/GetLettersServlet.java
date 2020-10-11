import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "GetLetters", urlPatterns = {"/GetLetters"})
public class GetLettersServlet extends HttpServlet {
    /**
     * Reader used from reading the file containing the words.
     */
    private Scanner mReader = null;

    /**
     * Opens the file containing the words.
     *
     * @throws ServletException : exception produced by opening the file
     */
    @Override
    public void init() throws ServletException {

        File myDatabase = new File("C:\\Users\\bianc\\Desktop\\Tehnologii_Java_laboratoare\\Lab1\\GetWordsServlet\\wordsDatabaseFile.txt");
        try {
            mReader = new Scanner(myDatabase);
        } catch (IOException e) {
            throw new UnavailableException(e.getMessage());
        }
    }

    /**
     * Responds to "get" methods.
     *
     * @param request  : the request from the client
     * @param response : the response to send back
     * @throws IOException : exception thrown
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (request == null)
            return;
        init();
        // get client information from response
        ServletContext context = this.getServletContext();

        // get ip address
        context.log("Ip address : " + request.getRemoteAddr());
        //Get method used
        context.log("Method : " + request.getMethod());
        //Get headers, user-agent
        context.log("User-agent : " + printEnumeration(request.getHeaders("User-Agent")));
        // Get language
        context.log("Languages : " + request.getLocale().getLanguage());
        // Get parameters
        context.log("Parameters : ");
        Map<String, String[]> map = request.getParameterMap();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String elem = enumeration.nextElement();
            context.log("Name : " + elem + " Value : " + Arrays.toString(map.get(elem)));
        }

        // get word with the requested letters
        String letters = request.getParameter("letters");
        if (mReader == null) {
            log("Scanner is null");
            return;
        }
        List<String> resultList = new ArrayList<>();
        while (mReader.hasNextLine()) {
            String returnedWord = mReader.nextLine();
            String copyLetters = letters.toLowerCase();
            char[] returnedLettersArray = returnedWord.toLowerCase().toCharArray();
            boolean ok = true;
            for (char c : returnedLettersArray)
                if (copyLetters.contains(String.valueOf(c))) {
                    copyLetters = copyLetters.replaceFirst(String.valueOf(c), "");
                } else {
                    ok = false;
                    break;
                }
            if (ok) {
                resultList.add(returnedWord);
            }
        }
        //send response back
        PrintWriter out = new PrintWriter(response.getWriter());
        response.setContentType("text/html");

        // if user agent is desktop app send text, else send html page
        if (printEnumeration(request.getHeaders("User-Agent")).equals("Java/11.0.2")) {
            out.println(resultList.toString());
        } else {

            out.println("<html><head><title>Result</title></head>");
            out.println("<body><h2>List of words :</h2><ul>");
            for (String s : resultList) {
                out.println("<li>" + s + "</li>");
            }
            out.println("</ul></body></html>");
            out.close();
        }
    }

    @Override
    public void destroy() {
        //mReader.close();
    }

    private String printEnumeration(Enumeration<String> enumeration) {
        StringBuilder result = new StringBuilder();
        while (enumeration.hasMoreElements()) {
            result.append(enumeration.nextElement());
        }
        return result.toString();
    }
}
