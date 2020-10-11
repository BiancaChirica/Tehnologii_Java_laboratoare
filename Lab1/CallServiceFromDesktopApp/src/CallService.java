import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

public class CallService {

    public static void main(String[] args) {
        // get input from keyboard
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter some letters : ");
        String inputLetters = keyboard.next();

        // invoke service
        URL gwServlet;
        try {
            gwServlet = new URL("http://localhost:8081/Laborator1_war_exploded/GetLetters?letters=" + inputLetters + "&submit=Submit");
            HttpURLConnection servletConnection = (HttpURLConnection) gwServlet.openConnection();
            servletConnection.setRequestMethod("GET");
            servletConnection.setDoOutput(true);
            servletConnection.setDoInput(true);

            // get response
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(servletConnection.getInputStream()));
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println("Response : " + response);
            }
            in.close();
        } catch (IOException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }
}
