package filters;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "ResponseFilter", urlPatterns = {"/*"})
public class ResponseFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        System.out.println("Response Filter");

        ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);
        //Send the decorated object as a replacement for the original response
        chain.doFilter(request, wrapper);
        //Get the dynamically generated content from the decorator
        String content = wrapper.toString();
        // Modify the content
        Document doc = Jsoup.parse(content);
        // put every paragraph in a div with background
        for (Element element : doc.getElementsByTag("p")) {
            element.wrap("<div style=\"background-color:powderblue;\"></div>");
        }

        // set the font of the first paragraph
       if( doc.getElementsByTag("p").first() != null ){
           doc.getElementsByTag("p").first().attr("style", "font-family:courier;");
           doc.getElementsByTag("p").first().attr("style", "color:red;");
       }

        System.out.println("Response Filter after");

        PrintWriter out = response.getWriter();
        out.write(doc.toString());
        out.flush();
        out.close();
    }
}

