package exercises

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType

object SensorStream {

  def main(args: Array[String]): Unit = {
    // set up able configuration
    val spark = SparkSession.builder.master("local").appName("SensorStream").getOrCreate()

    import spark.implicits._

    // schema for sensor data
    val userSchema = new StructType().add("resid", "string").add("date", "string").add("time", "string").add("hz", "double").add("disp", "double").add("flow", "double").add("sedPPM", "double").add("psi", "double").add("chlppm", "double")

    // TO DO: parse the lines of data into sensor objects

    // TO DO: Filter sensor data for low psi (< 5.0)

    // TO DO: Start the computation and save alerts as CSV file
    println("start streaming")

    //TO DO: Wait for the computation to terminate

  }

}