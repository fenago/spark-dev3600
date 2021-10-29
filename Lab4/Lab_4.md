<img align="right" src="../logo.png">

# Lab : Build a Simple Spark Application

#### Pre-reqs:
- Google Chrome (Recommended)

#### Lab Environment
There is no requirement for any setup.

**Note:** 
- Solutions are present in `work/spark-dev3600/Lab4` folder. Copy and paste the solution in the terminal(s) after running command `spark-shell` (for Scala) or `pyspark` (for Python).
- Spark Labs (powered by Jupyter) will be accessible at the port given to you by your instructor. Password for jupyterLab : `1234`

All labs are present in `work/spark-dev3600` folder. To copy and paste: use **Control-C** and to paste inside of a terminal, use **Control-V**

You can access jupyter lab at `http://<lab-environment>/lab/workspaces/lab4`

<h4><span style="color:red;">Lab Overview </span></h4>

In this activity, you will build a standalone Spark application. You will use the same scenario described in
Lab 2. Instead of using the Interactive Shell, you will run a python script.

### Python Application
Since you wrote your application in Python, then you can pass the .py file directly to spark-submit.

**Solution:**

`spark-submit work/spark-dev3600/Lab4/auctions_app.py`

**Note:** The filename is the name of the Python file (for example: AuctionsApp.py).
--master refers to the master URL. It points to the URL of the cluster. You can also specify
the mode in which to run, such as local, yarn-cluster, or yarn-client.

# Create the Application file (Python solution)

```
# Simple App to inspect Auction data
# The following import statements import SparkContext, SparkConf
from pyspark import SparkContext,SparkConf

conf = SparkConf().setAppName("AuctionsApp")
sc = SparkContext(conf=conf)
# MAKE SURE THAT PATH TO DATA FILE IS CORRECT
aucFile ="/home/jovyan/work/spark-dev3600/data/auctiondata.csv"
#map input variables
auctionid = 0
bid = 1
bidtime = 2
bidder = 3
bidderrate = 4
openbid = 5
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
```
