package corporation;

import java.io.*;
import java.util.*;

public class BobReleaseScheduler {

        static class Release {
        int startDay;
        int duration;
        int endDay;

        public Release(int startDay, int duration) {
            this.startDay = startDay;
            this.duration = duration;
            this.endDay = startDay + duration - 1;
        }
    }

    public static void main(String[] args) {
       
        List<Release> releases = new ArrayList<>();

       
        String inputFilePath = "releases.txt";  
        String outputFilePath = "./files/solution.txt";  

        // Read input from releases.txt file
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int startDay = Integer.parseInt(parts[0]);
                int duration = Integer.parseInt(parts[1]);
                releases.add(new Release(startDay, duration));
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            e.printStackTrace();
            return; 
        }

        
        releases.sort((r1, r2) -> {
            if (r1.startDay != r2.startDay) {
                return Integer.compare(r1.startDay, r2.startDay);
            } else {
                return Integer.compare(r1.duration, r2.duration);
            }
        });

        
        List<Release> selectedReleases = new ArrayList<>();
        int currentDay = 1;

       
        for (Release release : releases) {
            if (release.startDay >= currentDay && release.endDay <= 10) {
                selectedReleases.add(release);
                currentDay = release.endDay + 1;  // Move to the next available day after this release
            }
        }

        // Ensure the output directory exists
        File outputFile = new File(outputFilePath);
        File outputDir = outputFile.getParentFile();
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                System.err.println("Failed to create output directory: " + outputDir.getPath());
                return;  
            }
        }

        // Write output to solution.txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(selectedReleases.size() + "\n");
            for (Release release : selectedReleases) {
                writer.write(release.startDay + " " + release.endDay + "\n");
            }
            System.out.println("Solution written to file: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

