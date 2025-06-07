package Main.earthquakesjob;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class EarthquakeMapper extends Mapper<Object, Text, DoubleWritable, Text> {

    @Override
    protected void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        if (value.toString().startsWith("Time") || value.toString().trim().isEmpty()) return;

        String[] fields = value.toString().split(",", -1);

        try {
            String time = fields[0];
            String place = fields[1];
            String magStr = fields[5];

            if (!magStr.isEmpty()) {
                double magnitude = Double.parseDouble(magStr);

                String outputValue = "Time: " + time + " | Place: " + place + " | Magnitude: " + magnitude;
                context.write(new DoubleWritable(magnitude), new Text(outputValue));
            }
        } catch (Exception e) {
        }
    }
}