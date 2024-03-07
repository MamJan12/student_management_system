package studentmanagementsystem;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MatriculeGenerator {

    private static Set<Integer> usedIds = new HashSet<>();

    public static String generateMatricule() {
        // Get the current time in seconds
        long currentTimeSeconds = System.currentTimeMillis() / 1000;

        // Format the current time as a two-digit string
        String secondsValue = String.format("%02d", currentTimeSeconds % 60);

        // Generate a unique 3-digit identifier
        int uniqueId = getUniqueThreeDigitId();

        // Combine the components to create the matricule
        return "BUB" + secondsValue + "A" + String.format("%03d", uniqueId);
    }

    private static int getUniqueThreeDigitId() {
        Random random = new Random();
        int candidate;
        do {
            // Generate a random 3-digit number
            candidate = random.nextInt(900) + 100;
        } while (usedIds.contains(candidate));

        usedIds.add(candidate);
        return candidate;
    }

}