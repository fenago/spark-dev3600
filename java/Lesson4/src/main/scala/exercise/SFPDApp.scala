/* Simple app to inspect SFPD data */
/* The following import statement is importing SparkSession*/

package exercise

import org.apache.spark.sql.SparkSession

object SFPDApp {
  def main(args: Array[String]) {

    val spark = SparkSession.builder.master("local").appName("SFPDApp").getOrCreate()

    /* MAKE SURE THE PATH TO THE DATA FILE IS CORRECT */
    val sfpdFile = "/user/user01/Data/sfpd.csv"

    //SFPD data column names
    //incidentnum,category,description,dayofweek,date,time,pddistrict,resolution,address,X,Y,pdid

    // TO DO: Build and cache the base Dataset
    // val spfdDS =

    // TO DO: Calculate total number of incidents

    // TO DO: Select distinct Categories of incidents

    // To DO: Number of incidents in each category

    // TO DO: Print to console
    // println("Total number of incidents:")
    // println("Distinct categories of incidents:" )
    // println("Number of incidents in each category:")
  }
}
