/*
*
*
*/

package solutions

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

object SensorStreamSQL {

  def main(args: Array[String]): Unit = {

    // Create SparkSession
    val spark = SparkSession.builder.master("local").appName("SensorStreamSQL").getOrCreate()

    // Import package spark.implicits
    import spark.implicits._

    // Schema for sensor data
    val Sensor = new StructType().add("resid", "string").add("date", "string").add("time", "string").add("hz", "double").add("disp", "double").add("flow", "double").add("sedPPM", "double").add("psi", "double").add("chlppm", "double")

    // Load pump and maint data
    val pumpDF = spark.read.option("inferSchema", "true").csv("/user/user01/Data/sensorvendor.csv").toDF("resid", "pumpType", "purchaseDate", "serviceDate", "vendor", "longitude", "lattitude")
    println("pumpRDD take 5")
    pumpDF.show(5)

    val maintDF = spark.read.option("inferSchema", "true").csv("/user/user01/Data/sensormaint.csv").toDF("resid", "eventDate", "technician", "description")
    println("maintRDD take 5")
    maintDF.show(5)

    // create views for maint and pump Datasets
    maintDF.createTempView("maint")
    pumpDF.createTempView("pump")

    // parse the lines of data into sensor objects
    val sensorDF = spark.readStream.option("sep", ",").schema(Sensor).csv("/user/user01/stream/")
    sensorDF.createTempView("sensor")

    val res = spark.sql("SELECT resid, date,MAX(hz) as maxhz, min(hz) as minhz, avg(hz) as avghz, MAX(disp) as maxdisp, min(disp) as mindisp, avg(disp) as avgdisp, MAX(flow) as maxflow, min(flow) as minflow, avg(flow) as avgflow,MAX(sedPPM) as maxsedPPM, min(sedPPM) as minsedPPM, avg(sedPPM) as avgsedPPM, MAX(psi) as maxpsi, min(psi) as minpsi, avg(psi) as avgpsi,MAX(chlPPM) as maxchlPPM, min(chlPPM) as minchlPPM, avg(chlPPM) as avgchlPPM FROM sensor GROUP BY resid,date")
    println("sensor max, min, averages ")
    // start computation
    val dataAvgStream = res.writeStream.outputMode("complete").format("console").start()

    // Filter sensor data for low psi
    val filterSensorDF = sensorDF.filter("psi < 5.0")
    filterSensorDF.createTempView("alert")
    println("low pressure alert")
    // Start the computation
    val filterStream = filterSensorDF.writeStream.outputMode("append").format("console").start()

    val alertpumpmaintViewDF = spark.sql("select s.resid, s.date, s.psi, p.pumpType, p.purchaseDate, p.serviceDate, p.vendor, m.eventDate, m.technician, m.description from alert s join pump p on s.resid = p.resid join maint m on p.resid=m.resid")
    println("alert pump maintenance data")
    val pumpMaintStream = alertpumpmaintViewDF.writeStream.format("console").start() // Default output mode is Append

    // Wait for the computation to terminate
    filterStream.awaitTermination()
    dataAvgStream.awaitTermination()
    pumpMaintStream.awaitTermination()

  }

}