package services;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Creates new captcha image with equation and puts the result in request's session
 */
@WebServlet(name = "CAPTCHAService", urlPatterns = "/captcha")
public class CAPTCHAService extends HttpServlet {

    /**
     * Create random equation for captcha image and puts the result in request's session
     *
     * @param request : the user's request
     * @return : the equation to be drawn
     */
    private static String getRandomStringImpl(HttpServletRequest request) {
        char[] signs = {'+', '-'};
        Random random = new Random();
        int sum = 0;
        int sign = 1;

        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i % 2 != 0) {
                if (String.copyValueOf(signs, random.nextInt(2), 1).equals("+")) {
                    randomString.append("+");
                    sign = 1;
                } else {
                    randomString.append("-");
                    sign = 0;
                }
            } else {
                int nr = random.nextInt(10);
                randomString.append(nr);
                if (sign == 1) {
                    sum += nr;
                } else {
                    sum -= nr;
                }
            }
        }
        request.getSession().setAttribute("captcha", String.valueOf(sum));
        return randomString.toString();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    /**
     * Creates image for the captcha with de equation given by getRandomStringImpl method
     *
     * @param request  : user's request
     * @param response : user's response
     * @throws IOException : exception that could be thrown
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        char[] captcha = getRandomStringImpl(request).toCharArray();

        int width = 200;
        int height = 50;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();

        Font font = new Font("Verdana", Font.BOLD, 20);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(0, 0, width - 1, height - 1);

        int x = 0;
        int y;
        Random r = new Random();
        for (int i = 0; i < captcha.length; i++) {
            x += 10 + (Math.abs(r.nextInt()) % 15);
            y = 20 + Math.abs(r.nextInt()) % 20;
            graphics2D.drawChars(captcha, i, 1, x, y);
        }
        graphics2D.dispose();

        response.setContentType("image/png");
        // write the image as a png
        try (OutputStream out = response.getOutputStream()) {
            ImageIO.write(image, "png", out);
            out.flush();
        } catch (javax.imageio.IIOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
