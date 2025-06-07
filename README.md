# 🌍 Top 10 Earthquakes (1900–2023) – Hadoop MapReduce

## 🎯 Objective
Use Hadoop MapReduce (Java) to analyze historical earthquake data and find the top 10 strongest earthquakes from 1900 to 2023 based on magnitude.

---

## 📁 Files Included

- **earthquakes_dataset.csv** – Input dataset containing earthquake records.
- **EarthquakeMapper.java** – Mapper class that parses records and emits magnitude as key.
- **EarthquakeReducer.java** – Reducer class that sorts and extracts the top 10 earthquakes.
- **EarthquakeDriver.java** – Driver class to configure and run the MapReduce job.

---

## 🚀 How to Run
### 1. Put the CSV file into HDFS:
```bash
hdfs dfs -mkdir -p /user/yourname/input
hdfs dfs -put earthquakes_dataset.csv /user/hadoop/input/

### 2. Compile Java files and create JAR:
```bash
javac -classpath `hadoop classpath` -d build src/Main/earthquakesjob/*.java
jar -cvf TopEarthquakes.jar -C build/ .

### 3. Run Hadoop job:
```bash
hadoop jar TopEarthquakes.jar Main.earthquakesjob.EarthquakeDriver \
  /user/hadoop/input /user/hadoop/output

### 4. Get result file:
```bash
hdfs dfs -cat /user/yourname/output/part-r-00000 > top10earthquakes.txt

## Output format
<Date> | <Location> | <Magnitude>
