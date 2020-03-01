package solutions

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType

object SensorStream {

  def main(args: Array[String]): Unit = {
    // set up able configuration
    val spark = SparkSession.builder.master("local").appName("SensorStream").getOrCreate()

    // schema for sensor data
    val userSchema = new StructType().add("resid", "string").add("date", "string").add("time", "string").add("hz", "double").add("disp", "double").add("flow", "double").add("sedPPM", "double").add("psi", "double").add("chlppm", "double")

    // parse the lines of data into sensor objects
    val sensorCsvDF = spark.readStream.option("sep", ",").schema(userSchema).csv("/user/user01/stream/")

    // filter sensor data for low psi
    val filterSensorDF = sensorCsvDF.filter("psi < 5.0")

    // Start the computation
    println("start streaming")
    val query = filterSensorDF.writeStream.format("csv").option("path", "/user/user01/AlertOutput/").option("checkpointLocation", "/user/user01/checkpoint").start()

    // Wait for the computation to terminate
    query.awaitTermination()

  }

}