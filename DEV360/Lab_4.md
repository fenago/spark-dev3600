<img align="right" src="../logo-small.png">

# Lab : Build a Simple Spark Application

#### Pre-reqs:
- Google Chrome (Recommended)

#### Lab Environment
Notebooks are ready to run. All packages have been installed. There is no requirement for any setup.

**Note:** Elev8ed Notebooks (powered by Jupyter) will be accessible at the port given to you by your instructor. Password for jupyterLab : `1234`

All Notebooks are present in `work/spark-dev3600` folder. To copy and paste: use **Control-C** and to paste inside of a terminal, use **Control-V**

You can access jupyter lab at `<host-ip>:<port>/lab/workspaces/lab`

<h4><span style="color:red;">Lab Overview </span></h4>

In this activity, you will build a standalone Spark application. You will use the same scenario described in
Lab 2. Instead of using the Interactive Shell, you will run a python script.

### Python Application
Since you wrote your application in Python, then you can pass the .py file directly to spark-submit.

`spark-submit filename.py --master local[2]`

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
