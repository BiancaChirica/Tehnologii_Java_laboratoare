import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CallService {
    static List<String> testLetters = Arrays.asList("java", "aafgbdf", "andjdfvghg", "dffdgvgrsdf", "dfdgsjf", "jb",
            "dshfvfh", "azfgzdgfva", "oracle", "studzfgyskdh");

    public static void main(String[] args) {
        // get input from keyboard
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter some letters : ");
        String inputLetters = keyboard.next();

        // invoke service
        sendRequestAndPrintAnswer(inputLetters);

        for (int count = 0; count < 10; count++) {
            System.out.println("\nInput letters : " + testLetters.get(count));
            long startTime = System.nanoTime();
            sendRequestAndPrintAnswer(testLetters.get(count));
            long endTime = System.nanoTime();
            System.out.println("Milliseconds time : " + ((endTime - startTime) / 1000000));
        }
    }

    private static void sendRequestAndPrintAnswer(String inputLetters) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://localhost:8081/Laborator1_war_exploded/GetLetters?letters=%s&submit=Submit", inputLetters)))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(CallService::getResponse)
                .join();
    }

    private static void getResponse(String s) {
        System.out.print("Response : " + s);
        System.out.println("Response length : " + s.trim().length());
    }
}
