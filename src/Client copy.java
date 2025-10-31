import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://13.238.167.130/rockyou"))
                .header("Accept", "text/event-stream")
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(HttpResponse::body)
                .thenAccept(inputStream -> {
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                        List<String> passwords = reader.lines()
                                .filter(line -> line.isBlank())
                                .map(line -> line.replace("data: ", " ")).trim()
                                .filter(Client::isAscii)
                                .limit(SAMPLE_SIZE)
                                .collect(Collectors.toList());

                        Map<Integer, Long> charTypeHistgram = passwords.stream()
                                .collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.counting()));

                    } catch (IOException e) {
                        System.err.println("Error reading Server Side Event (SSE) stream: " + e.getMessage());
                    }
                })
                .join(); // Wait for the async operation to complete
    }
}