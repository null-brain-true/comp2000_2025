import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Clientteseting2 {

    private static final int SAMPLE_SIZE = 500;
    private static final long TIMEOUT_MS = 10_000;
    private static final int UPDATE_INTERVAL = 50;

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://13.238.167.130/rockyou"))
                .header("Accept", "text/event-stream")
                .build();

        HttpResponse<java.io.InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        long startTime = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body(), StandardCharsets.UTF_8))) {

            
            List<String> passwords = reader.lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> line.startsWith("data:") ? line.substring(5).trim() : line)
                .filter(Clientteseting2::isAscii)
                .takeWhile(line -> (System.currentTimeMillis() - startTime) < TIMEOUT_MS) 
                .limit(SAMPLE_SIZE)  
                .peek(pwd -> System.out.println("Sampled password: " + pwd)) 
                .collect(Collectors.toList());

            if (passwords.isEmpty()) {
                System.out.println("No passwords sampled.");
                return;
            }

            // --- histogram lengths ---
            Map<Integer, Long> lengthHistogram = passwords.stream()
                    .collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.counting()));

            // --- histogram character types ---
            Map<String, Long> charTypeHistogram = passwords.stream()
                    .flatMapToInt(String::chars)
                    .mapToObj(c -> classifyChar((char) c))
                    .filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(type -> type, Collectors.counting()));

            // Print results
            printHistograms(lengthHistogram, charTypeHistogram, passwords.size());
        }
    }

    private static boolean isAscii(String s) {
        return s.chars().allMatch(c -> c <= 127);
    }

    private static String classifyChar(char c) {
        if (Character.isUpperCase(c)) return "UPPER";
        if (Character.isLowerCase(c)) return "LOWER";
        if (Character.isDigit(c)) return "DIGIT";
        if (!Character.isLetterOrDigit(c) && c >= 33 && c <= 126) return "PUNCT";
        return null;
    }

    private static void printHistograms(Map<Integer, Long> lengthHist, Map<String, Long> charTypeHist, int totalPasswords) {
        System.out.println("\n--- Histograms after " + totalPasswords + " passwords ---");

        System.out.println("Password Length Histogram:");
        lengthHist.forEach((len, count) -> System.out.printf("Length %2d -> %d%n", len, count));

        System.out.println("\nCharacter Type Histogram:");
        System.out.printf("Uppercase   : %d%n", charTypeHist.getOrDefault("UPPER", 0L));
        System.out.printf("Lowercase   : %d%n", charTypeHist.getOrDefault("LOWER", 0L));
        System.out.printf("Digits      : %d%n", charTypeHist.getOrDefault("DIGIT", 0L));
        System.out.printf("Punctuation : %d%n", charTypeHist.getOrDefault("PUNCT", 0L));
    }
}
