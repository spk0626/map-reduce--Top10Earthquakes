package Main.earthquakesjob;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Comparator;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class EarthquakeReducer extends Reducer<DoubleWritable, Text, Text, DoubleWritable> {

    private static class EarthquakeRecord {
        double magnitude;
        String info;

        EarthquakeRecord(double magnitude, String info) {
            this.magnitude = magnitude;
            this.info = info;
        }
    }

    private PriorityQueue<EarthquakeRecord> top10 = new PriorityQueue<>(10,
            Comparator.comparingDouble(e -> e.magnitude));

    @Override
    protected void reduce(DoubleWritable key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        for (Text val : values) {
            EarthquakeRecord record = new EarthquakeRecord(key.get(), val.toString());
            top10.add(record);

            if (top10.size() > 10) {
                top10.poll();
            }
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        PriorityQueue<EarthquakeRecord> output = new PriorityQueue<>(
                (a, b) -> Double.compare(b.magnitude, a.magnitude));
        output.addAll(top10);

        while (!output.isEmpty()) {
            EarthquakeRecord r = output.poll();
            context.write(new Text(r.info), new DoubleWritable(r.magnitude));
        }
    }
}