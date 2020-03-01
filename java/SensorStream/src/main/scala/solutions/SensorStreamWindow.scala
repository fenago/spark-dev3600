/* 
*  
*   
*/

package solutions

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

object SensorStreamWindow {

  def main(args: Array[String]): Unit = {

    // Create SparkSession
    val spark = SparkSession.builder.master("local").appName("SensorStreamWindow").getOrCreate()

    // Import package spark.implicits
    import spark.implicits._

    // Schema for sensor, pump and maint data
    val Sensor = new StructType().add("resid", "string").add("date", "string").add("time", "string").add("hz", "double").add("disp", "double").add("flow", "double").add("sedPPM", "double").add("psi", "double").add("chlppm", "double")

    // parse the lines of data into sensor objects
    val sensorDF = spark.readStream.option("sep", ",").schema(Sensor).csv("/user/user01/stream/")
    sensorDF.createTempView("sensor")

    // Start computation
    println("start streaming")
    val res = sensorDF.groupBy($"resid", $"date", window($"time", "10 minutes", "5 minutes")).agg(count("resid").alias("Total"))
    val resStream = res.writeStream.outputMode("complete").format("console").start()

    //val res2 = sensorDF.groupBy($"resid", $"date", window($"time", "10 minutes", "5 minutes")).agg(max("psi").alias("maxpsi"), min("psi").alias("minpsi"), avg("psi").alias("avgpsi"))
    val res2 = spark.sql("SELECT resid, date, MAX(psi) as maxpsi, min(psi) as minpsi, avg(psi) as avgpsi FROM sensor GROUP BY resid, date, window(time, '10 minutes', '5 minutes')")
    val res2Stream = res2.writeStream.outputMode("complete").format("console").start()

    // Wait for the computation to terminate
    resStream.awaitTermination()
    res2Stream.awaitTermination()

  }

}