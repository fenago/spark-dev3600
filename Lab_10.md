<img align="right" src="./logo-small.png">

# Lab : Build a Simple Apache Spark Application

#### Pre-reqs:
- Google Chrome (Recommended)

#### Lab Environment
There is no requirement for any setup.

**Note:** 
- Spark Labs (powered by Jupyter) will be accessible at the port given to you by your instructor. Password for jupyterLab : `1234`

All labs are present in `work/spark-dev3600` folder. To copy and paste: use **Control-C** and to paste inside of a terminal, use **Control-V**

You can access jupyter lab at `<host-ip>:<port>/lab/workspaces/lab10`

<h4><span style="color:red;">Lab 6.5a: Load and Inspect Data Using the Spark Shell</span></h4>

Estimated time to complete: 20 minutes

1. Launch the Spark interactive shell, if it is not already running `spark-shell`
2. Import package:

```
import org.apache.spark.sql.types.StructType
```

NOTE: A sparkSession object, which is the entry point for spark, is instantiated on its own.
3. Before we can load data from sensordata.csv, we will create a schema for the csv file so that
we can process the csv file directly into a dataframe:

```
val userSchema = new StructType().add("resid", "string").add("date",
"string").add("time", "string").add("hz", "double").add("disp",
"double").add("flow", "double").add("sedPPM", "double").add("psi",
"double").add("chlppm", "double")
```
4. Load the sensor data for this lab from the sensordata.csv file into a DataFrame:

```
val csvDF = spark.read.format("csv").option("header",
"false").schema(userSchema).load("/home/jovyan/work/spark-dev3600/data/sensordata.csv")
```

5. View the data. Retrieve the first few rows of the dataframe:

```
csvDF.show(20)
```

Verify that the data is displayed and formatted correctly.
6. Return the number of elements in the DataFrame:

```
csvDF.count()
```

7. Create an alert DF for when the PSI is low, and print some results:

```
val sensorDF = csvDF.filter(col("psi")<0.5)
```

8. Print the schema, and show the first 20 rows of the DataFrame:

```
sensorDF.printSchema()
sensorDF.take(20).foreach(println)
```
9. Explore the data set with some queries. First, group by the sensor ID and date, and retrieve the
average PSI:
```
csvDF.groupBy("resid", "date").agg(avg(col("psi"))).show()
```

10. Register sensorDF as a temporary table that you can query:
```
sensorDF.createTempView("sensor")
```

<h4><span style="color:red;">6.5b: Use Spark Streaming with the Spark Shell</span></h4>

**Important!** In this lab, you will have two terminal windows open: one that is running
the Spark shell, and another that is running commands at the UNIX command prompt. The lab
exercises will refer to these as the "Spark window" and the "UNIX window," respectively.

Follow instructions closely to make sure you are in the correct terminal window for each step.

1. Launch the spark shell in the terminal window you already have open, if it is not already running.
This will be the Spark window for the remainder of this lab.
2. If you relaunched the spark shell, import the following package:

```
import org.apache.spark.sql.types.StructType
```

3. Open a second terminal window (the UNIX window).
4. Create a directory named stream that the streaming application will read from:
`mkdir -p ~/work/spark-dev3600/stream`
5. Back in the Spark window, create a schema for the csv file so that we can process the csv stream
directly into an unbounded dataframe:
```
val userSchema = new StructType().add("resid", "string").add("date",
"string").add("time", "string").add("hz", "double").add("disp",
"double").add("flow", "double").add("sedPPM", "double").add("psi",
"double").add("chlppm", "double")
```

6. Next, create an input stream:
```
val sensorCsvDF = spark.readStream.option("sep",
",").schema(userSchema).csv("/home/jovyan/work/spark-dev3600/stream/")
```
7. Finally, use the writeStream.format("console").start()method to display the
contents of the stream on screen:

```
val query = sensorCsvDF.writeStream.format("console").start()
```

**Note:** It is safe to ignore the following errors:

```
Failed to delete path <path>, error: No such file or directory (2)
```

8. Run the following command to prevent the program from exiting, so you can view the active
stream:

```
query.awaitTermination()
```
9. From the UNIX Window, copy sensordata.csv to the stream directory you created. This is
the directory from which the streaming application will read. Be sure to specify the correct paths
to the sensordata.csv file and the stream directory, if yours are different:

```
cp ~/work/spark-dev3600/data/sensordata.csv ~/work/spark-dev3600/stream/file1.csv
```

The Spark window will print out the data.
10. When it completes, use CTRL+C to terminate the query and exit the scala shell.

