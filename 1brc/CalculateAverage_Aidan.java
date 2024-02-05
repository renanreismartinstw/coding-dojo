// Written Aaliyah, Aidan, Ashley, David, Jas   :)

package dev.morling.onebrc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CalculateAverage__Wise {

    public static class Result{
        Double min;
        Double max;
        Double sum;
        int count;

        public Result(Double min, Double max, Double sum, int count) {
            this.min = min;
            this.max = max;
            this.sum = sum;
            this.count = count;
        }
    }
    public static Map<String,Result> outputResults = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Path p = Path.of("./data/weather_stations.csv");
        File f = p.toFile();

        var groups = Files.lines(p).map(CalculateAverage__Wise::foo);

        groups.forEach( group -> {

            var name = group.getKey();
            var value = group.getValue();

            // check if in map
            if (outputResults.containsKey(group.getKey())) {

                var existing = outputResults.get(group.getKey());
                existing.count++;
                existing.sum+=value;
                if (value < existing.min) {
                    existing.min = value;
                }
                if (value < existing.max) {
                    existing.max = value;
                }
            } else {
                outputResults.put(name, new Result(value, value, value, 1));
            }
        });

        outputResults.keySet().stream().sorted().forEach(CalculateAverage__Wise::printString);

    }

    private static String printString(String key) {
        var result = outputResults.get(key);
        var average = result.sum / Double.valueOf(result.count);
        return key + result.min + '/' + (((average * 10) % 1) / 10.0) + '/' + result.max;
    }




    private static Map.Entry<String, Double> foo(String line) {
        String[] outputs = line.split(";");
        return Map.entry(outputs[0], Double.parseDouble(outputs[1]));
    }

}
