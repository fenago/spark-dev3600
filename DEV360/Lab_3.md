<img align="right" src="../logo-small.png">

# Lab : Apply Operations on Datasets

#### Pre-reqs:
- Google Chrome (Recommended)

#### Lab Environment
There is no requirement for any setup.



**Note:** Elev8ed Notebooks (powered by Jupyter) will be accessible at the port given to you by your instructor. Password for jupyterLab : `1234`

All Notebooks are present in `work/spark-dev3600` folder. To copy and paste: use **Control-C** and to paste inside of a terminal, use **Control-V**

You can access jupyter lab at `<host-ip>:<port>/lab/workspaces/lab3`

## Lab 3.1: Explore and Save SFPD Data
Estimated time to complete: 20 minutes
In this activity, you will use Dataset operations and SQL to explore the data in the Datasets. Use Dataset
operations or SQL queries to answer the questions below.
If you want to try out your queries, log in to the cluster as user01. You can start the spark shell and run
your answers to test them.
1. What are the five districts with the most number of incidents? Hint: Use groupBy; count;
sort. You can use show(5) to show five elements.
val incByDist = sfpdDS._____________________________________________
____________________________________________________________________

Note: Pass in the SQL query within the parentheses. For example:
spark.sql("SELECT <column>, count(incidentnum)
AS inccount FROM <Dataset Table> GROUP BY <column>
ORDER BY inccount <SORT ORDER> LIMIT <number of records>")

val incByDistSQL = spark.sql("_________________________________
__________________________________________________________________")
2. What are the top 10 resolutions?
val top10Res = sfpdDS.______________________________________________
____________________________________________________________________
val top10ResSQL = spark.sql("__________________________________
__________________________________________________________________")
3. What are the top three categories of incidents?
val top3Cat = sfpdDS.______________________________________________
___________________________________________________________________
val top3CatSQL = spark.sql("__________________________________
__________________________________________________________________")

4. Save the top 10 resolutions to a JSON file in the folder /home/jovyan/work/spark-dev3600/output in the cluster
file system.

Hint: Use: DS.write.format("json").mode("<mode type>").save("<path
to file>")
If the path to the output directory exists, you will get an error. Delete it first or add
logic to remove the directory (if it exists) before saving.

top10ResSQL.________________________________________________________

Note: You may encounter the following message: “ERROR MapRFileSystem:
Failed to delete path maprfs:/… error: No such file or
directory (2)”. This is a known issue in Spark 2.1 that you can ignore; Spark is
attempting to remove temporary files without first making sure they exist.

5. Open an additional terminal window. At the Linux command prompt, verify that the data was
saved to the file:

`cd /mapr/<cluster name>/home/jovyan/work/spark-dev3600/output`

`cat part-00000...`

## Lab 3.3: Create and Use User-Defined Functions (UDFs)
Estimated time to complete: 20 minutes
The date field in this Dataset is a string of the form mm/dd/yy. You are going to create a function to
extract the year from the date field. There are two ways to use a UDF with Spark Datasets. You can
define it inline and use it within Dataset operations or use it in SQL queries.

Q: What do you need to do to use this function in SQL queries?

A: Register this function as a UDF. Use spark.udf.register.

Hint: Function_Definition:
def getyear(s:String):String = {
val year = ___________________________________________

year
}

1. Register the UDF and define function that extracts characters after the last ‘/’ from the string:
spark.udf.register.("<function_name>",<function_definition>)
2. Using the registered the UDF in a SQL query, find the count of incidents by year and show the
results:
val incyearSQL = spark.sql("SELECT getyear(date),
count(incidentnum) AS countbyyear FROM sfpd
GROUP BY getyear(date) ORDER BY countbyyear DESC")
3. Find and display the category, address, and resolution of incidents reported in 2014:
val inc2014 = spark.sql("_______________________________________")
4. Find and display the addresses and resolutions of vandalism incidents in 2015:
val van2015 = spark.sql("_______________________________________")
Try creating other functions. For example, a function that extracts the month, and use this to see which
month in 2014 had the most incidents.

## Lab 3.4 Analyze Data Using UDFs and Queries
Estimated time to complete: 30 minutes

Now that you have explored DataFrames and created simple user-defined functions, build a standalone
application using DataFrames. High-level steps are listed below:
1. Load the data in sfpd.csv (use Lab 2.3).
2. Create the Dataset (schema inferred by reflection) and register it as a table (use Lab 2.3).
3. Get the top three categories and print them to the console (refer to Lab 3.1).
4. Find the address, resolution, date, and time for burglaries in 2015 (refer to Lab 3.1).
5. Save this to a JSON file in a folder /home/jovyan/work/spark-dev3600/appoutput in the cluster (refer to Lab 3.1).

Note: You may encounter the following message: “ERROR MapRFileSystem:
Failed to delete path maprfs:/… error: No such file or
directory (2)”. This is a known issue in Spark 2.1 that you can ignore; Spark is
attempting to remove temporary files without first making sure they exist.
6. Exit the scala shell using :q.

<h4><span style="color:red;">Lesson 3 Answer Key</span></h4>

Note: Answers can be found in files which were downloaded at the beginning of the course,
to `/home/jovyan/work/spark-dev3600/Answers`.
