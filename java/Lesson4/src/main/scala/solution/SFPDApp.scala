/* Simple app to inspect SFPD data */
/* The following import statement is importing SparkSession*/

package solution

import org.apache.spark.sql.SparkSession

object SFPDApp {
  def main(args: Array[String]) {

    val spark = SparkSession.builder.master("local").appName("SFPDApp").getOrCreate()

    /* MAKE SURE THE PATH TO THE DATA FILE IS CORRECT */
    val sfpdFile = "/home/jovyan/work/spark-dev3600/data/sfpd.csv"

    //SFPD data column names
    //incidentnum,category,description,dayofweek,date,time,pddistrict,resolution,address,X,Y,pdid

    // TO DO: Build and cache the base Dataset
    val sfpdDS = spark.read.format("csv").option("inferSchema", true).load("/home/jovyan/work/spark-dev3600/data/sfpd.csv").toDF("incidentnum", "category", "description", "dayofweek", "date", "time", "pddistrict", "resolution", "address", "X", "Y", "pdid").cache

    // TO DO: Calculate total number of incidents
    val sfpdCount = sfpdDS.count()

    // TO DO: Select distinct Categories of incidents
    val sfpdCategory = sfpdDS.select("Category").distinct()

    // To DO: Number of incidents in each category
    val sfpdCategoryCount = sfpdDS.groupBy("Category").count()

    // TO DO: Print to console
    println("Total number of incidents: %s".format(sfpdCount))
    println("Distinct categories of incidents:" )
    sfpdCategory.show(50)
    println("Number of incidents in each category:")
    sfpdCategoryCount.show(50)
  }
}
