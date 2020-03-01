<img align="right" src="../logo-small.png">

# Lab :

#### Pre-reqs:
- Google Chrome (Recommended)

#### Lab Environment
Notebooks are ready to run. All packages have been installed. There is no requirement for any setup.

**Note:** Elev8ed Notebooks (powered by Jupyter) will be accessible at the port given to you by your instructor. Password for jupyterLab : `1234`

All Notebooks are present in `work/spark-dev3600` folder. To copy and paste: use **Control-C** and to paste inside of a terminal, use **Control-V**

You can access jupyter lab at `<host-ip>:<port>/lab/workspaces/lab`


<h4><span style="color:red;">Gradient descent learning with multiple inputs </span></h4>

##### Run Notebook
Click notebook `.ipynb` in jupterLab UI and run jupyter notebook.



Lab 3: Build a Simple Spark Application
Lab Overview
In this activity, you will build a standalone Spark application. You will use the same scenario described in
Lab 2. Instead of using the Interactive Shell, you will create a new project in your IDE, import the Scala
code, and then build the project with Maven. After the code is built and packaged, you will run the code
using spark-submit.

Lab 3.1: Import and Configure Application Files
Estimated time to complete: 5 minutes

Choosing an IDE
You can use your choice of Netbeans, IntelliJ, Eclipse, or just a text editor with Maven on the command
line. You need to install your IDE in the MapR lab environment, or alternatively install Maven on the
Sandbox and use the command line. The guide will provide a detailed overview of using Eclipse. For
IntelliJ, the Scala plugin is required. For Eclipse, you can download scala-ide, the Scala Eclipse tool,
which comes with Maven and Scala prepackaged. Netbeans is pre-installed on the MapR lab
environment. If you wish to install it locally, or want to use a different IDE, links are available below.
IDE

Link

Eclipse (Scala-IDE)

http://scala-ide.org/download/sdk.html

IntelliJ

https://www.jetbrains.com/idea/download/

Netbeans

https://netbeans.org/downloads/

Maven / CLI

https://maven.apache.org/download.cgi

Open/Import the Project into Eclipse
There is an exercises package with stubbed code for you to finish, and a solutions package with the
complete solution. Open/Import the project into your IDE following the instructions below. Optionally, you
can just edit the Scala files and use Maven on the command line. If you just want to use the prebuilt
solution, you can copy the solution jar file from the target directory.

Lesson 3: Build a Simple Spark Application

1. Start off by installing the Scala-IDE using the link above.
2. Unzip and launch Eclipse.
3. Select your workspace directory.

4. Unzip and import lab 3 by selecting File > Import > Maven > Existing Maven Project. Then,
select your lab 3 folder, and click Finish.
5. Change Scala version to scala 2.10. Right-click on the project, then select Properties > Scala
Compiler. Tick Use Project Settings, then select Fixed Scala Installation 2.10.6 (built-in).
Click OK.

L3-2

Lesson 3: Build a Simple Spark Application

A message will show that the project needs to be rebuilt. Click OK and allow time for Eclipse to
rebuild the project.
6. Verify java configuration. In case you have an exception that a JRE isn’t configured for the
project, use the following steps: Right-Click project > Properties > Java Build Path > Libraries
> JRE System Library > Edit > Installed JREs > Add > Standard JVM. Then, find the path to
the installed JRE (typically C:\Program Files\Java\jdk_version).

7. Update Maven dependencies, the project should now be error-free. Right-click project > Maven
> Update Project > OK.

Complete the Code
If you wish, you can complete the TODO sections in the code under the exercise package or run the
example code in the solutions package.

If you want complete the exercises open the AuctionsApp.scala file. In this lab, you will finish the
code following the //TODO comments in the code. Your code should do the following:

L3-3

Lesson 3: Build a Simple Spark Application

•

Load the data from the auctionsdata.csv into an RDD and split on the comma separator.

•

Cache the RDD.

•

Print the following with values to the console.
o
o
o
o
o

Total number of bids
Total number of distinct auctions
Highest number (max) number of bids
Lowest number of bids
Average number of bids

Note: The instructions provided here are for Scala. If using Python, look at the file in
DEV3600_LAB_FILES /LESSON3/AuctionsApp.py.
You can test your Scala or Python code using the Spark Interactive shell or PySpark interactive
shell respectively. The solutions are provided at the end of Lab 3.

1. Add import statements to import SparkContext, all subclasses in SparkContext and SparkConf:
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
2. Define the class and the main method.
object AuctionsApp {
def main(args: Array[String]) {
...
}
}

Caution! The name of the object should exactly match the name of the file. In this example, if
your file is called AuctionsApp.scala, then the object should also be name AuctionsApp.

3. In the main() method, create a new SparkConf object and set the application name.
object AuctionsApp {
def main(args: Array[String]) {
val conf = new SparkConf().setAppName("AuctionsApp")
val sc = new SparkContext(conf)
}
}



Lesson 3: Build a Simple Spark Application

4. You can define a variable that points to the full path of the data file and then use that when you define
the RDD or you can point to the data file directly.
5. Now add the necessary code to the main method to create the RDD, cache it, and find the values for
total bids, total auctions, max bids, min bids and average. Refer to the previous Lab (Lab 2.1).
Note: If you are using Math.max or Math.min, then you also need to include the import
statement: import java.lang.Math

Lab 3.2: Package the Spark Application
Estimated time to complete: 10 minutes
This step applies to applications written in Scala. If you wrote your application in Python, skip this step
and move on to Lab 3.3.

Objectives
•

Build and package the application

Building the Project with Eclipse (Scala)
Right-click the project > Run As > Maven Install

L3-5

Lesson 3: Build a Simple Spark Application

The output will display the path to the JAR which we need to submit to Spark. Now copy the created
auctionsapp-1.0.jar to the Sandbox or cluster node to run your Spark application.

Building the Project with other IDEs
IDE

Build Process

IntelliJ

Select Build Menu > Rebuild Project > Finish

Netbeans

Right-click on the project and select Build

Building will create the auctionsapp-1.0.jar in the target folder. You copy this JAR file to your
Sandbox or cluster node to run your application.

Lab 3.3: Launch the Spark Application
Estimated time to complete: 5 minutes

Objectives
•

Launch the application using spark-submit

1. Copy the jar file to you sandbox as explained in the connecting to the Sandbox document. Replace
user01 with your username:
Using SCP:
$ scp file.jar <username>@ipaddres:/user/<username>/.
For example:
$ scp auctapp-1.0.jar user01@192.168.100.128:/user/user01/.

L3-6

Lesson 3: Build a Simple Spark Application

You can also use WinSCP for Windows.

Drag and drop the JAR to the user location (/user/user01/):

2. Once you have copied the application jar, you can launch it using spark-submit. From the working
directory /user/<username>, run the following:
/opt/mapr/spark/spark-<version>/bin/spark-submit
--class "name of class"
--master <mode> <path to the jar file>
For example:
spark-submit --class solutions.AuctionsApp --master local[2]
auctionsapp-1.0.jar

L3-7

Lesson 3: Build a Simple Spark Application

Once the applications finishes running, the below output should be present in the console.
total bids across all auctions: 10654
total number of distinct items: 627
Max bids across all auctions: 75
Min bids across all auctions: 1
Avg bids across all auctions: 16

Python Application
If you wrote your application in Python, then you can pass the .py file directly to spark-submit.
/opt/mapr/spark/spark-<version>/bin/spark-submit
filename.py --master local[2]
Note: The filename is the name of the Python file (for example: AuctionsApp.py).
--master refers to the master URL. It points to the URL of the cluster. You can also specify
the mode in which to run, such as local, yarn-cluster, or yarn-client.

Solutions
Lab 3.1: Create the Application file (Scala solution)
/* Simple App to inspect Auction data */
/* The following import statements are importing SparkContext, all subclasses
and SparkConf*/
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
//Will use max, min - import java.Lang.Math
import java.lang.Math
object AuctionsApp {
def main(args: Array[String]) {
val conf = new SparkConf().setAppName("AuctionsApp")

L3-8

Lesson 3: Build a Simple Spark Application
val sc = new SparkContext(conf)
// Add location of input file
val aucFile = "/user/user01/data/auctiondata.csv"
//map input variables
val auctionid = 0
val bid = 1
val bidtime = 2
val bidder = 3
val bidderrate = 4
val openbid = 5
val price = 6
val itemtype = 7
val daystolive = 8
//build the inputRDD
val auctionRDD=sc.textFile(aucFile).map(line=>line.split(",")).cache()
//total number of bids across all auctions
val totalbids=auctionRDD.count()
//total number of items (auctions)
val
totalitems=auctionRDD.map(line=>line(auctionid)).distinct().count()
//RDD containing ordered pairs of auctionid,number
val bids_auctionRDD=auctionRDD.map(line=>(line(auctionid),1))
.reduceByKey((x,y)=>x+y)
//max, min and avg number of bids
val maxbids=bids_auctionRDD.map(x=>x._2).reduce((x,y)=>Math.max(x,y))
val minbids=bids_auctionRDD.map(x=>x._2).reduce((x,y)=>Math.min(x,y))

L3-9

Lesson 3: Build a Simple Spark Application
val avgbids=totalbids/totalitems
println("total bids across all auctions: %s .format(totalbids))
println("total number of distinct items: %s" .format(totalitems))
println("Max bids across all auctions: %s ".format(maxbids))
println("Min bids across all auctions: %s ".format(minbids))
println("Avg bids across all auctions: %s ".format(avgbids))
}
}

Lab 3.1: Create the Application file (Python solution)
# Simple App to inspect Auction data
# The following import statements import SparkContext, SparkConf
from pyspark import SparkContext,SparkConf

conf = SparkConf().setAppName("AuctionsApp")
sc = SparkContext(conf=conf)
# MAKE SURE THAT PATH TO DATA FILE IS CORRECT
aucFile ="/user/user01/data/auctiondata.csv"
#map input variables
auctionid = 0
bid = 1
bidtime = 2
bidder = 3
bidderrate = 4
openbid = 5

L3-10

Lesson 3: Build a Simple Spark Application
price = 6
itemtype = 7
daystolive = 8

#build the inputRDD
auctionRDD = sc.textFile(aucFile).map(lambda line:line.split(",")).cache()
#total number of bids across all auctions
totalbids = auctionRDD.count()
#total number of items (auctions)
totalitems = auctionRDD.map(lambda x:x[auctionid]).distinct().count()
#RDD containing ordered pairs of auctionid,number
bids_auctionRDD = auctionRDD.map(lambda x:
(x[auctionid],1)).reduceByKey(lambda x,y:x+y)
#max, min and avg number of bids
maxbids = bids_auctionRDD.map(lambda x:x[bid]).reduce(max)
minbids = bids_auctionRDD.map(lambda x:x[bid]).reduce(min)
avgbids = totalbids/totalitems
print "total bids across all auctions: %d " %(totalbids)
print "total number of distinct items: %d " %(totalitems)
print "Max bids across all auctions: %d " %(maxbids)
print "Min bids across all auctions: %d " %(minbids)
print "Avg bids across all auctions: %d " %(avgbids)
print "DONE"

L3-11