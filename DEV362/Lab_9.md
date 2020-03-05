<img align="right" src="../logo-small.png">

# Lab : Apache Spark MLlib

#### Pre-reqs:
- Google Chrome (Recommended)

#### Lab Environment
Notebooks are ready to run. All packages have been installed. There is no requirement for any setup.

**Note:** Elev8ed Notebooks (powered by Jupyter) will be accessible at the port given to you by your instructor. Password for jupyterLab : `1234`

All Notebooks are present in `work/spark-dev3600` folder. To copy and paste: use **Control-C** and to paste inside of a terminal, use **Control-V**

You can access jupyter lab at `<host-ip>:<port>/lab/workspaces/lab`

<h4><span style="color:red;">Lab Overview </span></h4>

In this activity, you will use Spark to make movie recommendations.

Set up for the Lab
If you have not already downloaded the files DEV3600_LAB_DATA.zip and DEV3600_LAB_FILES.zip
to your machine, and copied the data to your Sandbox or cluster, do so now.
You can use the Scala commands in the spark-shell-commands.txt file or you can copy from the
code boxes below.

A Typical Machine Learning Workflow

![](..\images\105-285.png)

In this tutorial we will perform the following steps:
1. Load the sample data.
2. Parse the data into the input format for the ALS algorithm.
3. Split the data into two parts, one for building the model and one for testing the model.
4. Run the ALS algorithm to build/train a user product matrix model.
5. Make predictions with the training data and observe the results.
6. Test the model with the test data.

## Lab 10.1: Load and Inspect Data using Spark Shell
Estimated time to complete: 10 minutes
Log in to your Sandbox or cluster, as explained in the Connection Guide.
$ ssh –p port user01@<ipaddress>


To launch the Interactive Shell, at the command line, run the following command:
spark-shell --master local[2]

The Sample Data Set

![](..\images\101.png)

First we will explore the data using Spark DataFrames with questions like:
-Count the maximum and minimum ratings, and the number of users who have rated a movie

-Display the title of movies with ratings greater than four

Load Data into Spark DataFrames
First we will import some packages and instantiate a sqlContext, which is the entry point for working
with structured data (rows and columns) in Spark and allows the creation of DataFrame objects.
In the code boxes, comments are in green and output is in purple.
// SQLContext entry point for working with structured data
val sqlContext = new org.apache.spark.sql.SQLContext(sc)

// This is used to implicitly convert an RDD to a DataFrame.
import sqlContext.implicits._

// Import Spark SQL data types
import org.apache.spark.sql._

// Import mllib recommendation data types

import org.apache.spark.mllib.recommendation.{ALS,
MatrixFactorizationModel, Rating}




Below we use Scala case classes to define the Movie and User schemas corresponding to the
movies.dat and users.dat files.
// input format MovieID::Title::Genres

case class Movie(movieId: Int, title: String)
// input format is UserID::Gender::Age::Occupation::Zip-code

case class User(userId: Int, gender: String, age: Int, occupation:
Int, zip: String)
The functions below parse a line from the movie.dat, user.dat, and rating.dat files into the corresponding
Movie and User classes.
// function to parse input into Movie class

def parseMovie(str: String): Movie = {
val fields = str.split("::")

}

Movie(fields(0).toInt, fields(1))

// function to parse input into User class
def parseUser(str: String): User = {
val fields = str.split("::")
assert(fields.size == 5)

User(fields(0).toInt, fields(1).toString,
fields(2).toInt,fields(3).toInt, fields(4).toString)
}

Below we load the data from the ratings.dat file into a Resilient Distributed Dataset (RDD). RDDs can
have transformations and actions. The first() action returns the first element in the RDD, which is the
String “1::1193::5::978300760”
// load the data into an RDD

val ratingText = sc.textFile("/home/jovyan/work/spark-dev3600/moviemed/ratings.dat")
// MapPartitionsRDD[1] at textFile

// Return the first element in this RDD
ratingText.first()

// String = 1::1193::5::978300760
We use the org.apache.spark.mllib.recommendation.Rating class for parsing the ratings.dat
file. Later we will use the Rating class as input for the ALS run method.




Then we use the map transformation on ratingText, which will apply the parseRating function to
each element in ratingText and return a new RDD of Rating objects. We cache the ratings data, since
we will use this data to build the matrix model. Then we get the counts for the number of ratings, movies
and users.
// function to parse input UserID::MovieID::Rating

// into org.apache.spark.mllib.recommendation.Rating class

def parseRating(str: String): Rating= {
val fields = str.split("::")

}

Rating(fields(0).toInt, fields(1).toInt, fields(2).toDouble)

// create an RDD of Ratings objects

val ratingsRDD = ratingText.map(parseRating).cache()

// ratingsRDD: org.apache.spark.mllib.recommendation.Rating] =
MapPartitionsRDD
// count number of total ratings

val numRatings = ratingsRDD.count()
// numRatings: Long = 1000209

// count number of movies rated

val numMovies = ratingsRDD.map(_.product).distinct().count()
// numMovies: Long = 3706

// count number of users who rated a movie

val numUsers = ratingsRDD.map(_.user).distinct().count()
// numUsers: Long = 6040

Explore and Query the Movie Lens data with Spark DataFrames
Spark SQL provides a programming abstraction called DataFrames. A DataFrame is a distributed
collection of data organized into named columns. Spark supports automatically converting an RDD
containing case classes to a DataFrame with the method toDF(). The case class defines the schema of
the table.
Below we load the data from the users and movies data files into an RDD, use the map transformation
with the parse functions, and then call toDF() which returns a DataFrame for the RDD. Then we register
the DataFrames as temporary tables so that we can use the tables in SQL statements.




// load the data into DataFrames

val usersDF =
sc.textFile("/home/jovyan/work/spark-dev3600/moviemed/users.dat").map(parseUser).toDF()

val moviesDF =
sc.textFile("/home/jovyan/work/spark-dev3600/moviemed/movies.dat").map(parseMovie).toDF()
// create a DataFrame from the ratingsRDD
val ratingsDF = ratingsRDD.toDF()

// register the DataFrames as a temp table
ratingsDF.registerTempTable("ratings")
moviesDF.registerTempTable("movies")
usersDF.registerTempTable("users")

DataFrame printSchema() prints the schema to the console in a tree format:
// Return the schema of this DataFrame
usersDF.printSchema()

root

|-- userId: integer (nullable = false)
|-- gender: string (nullable = true)
|-- age: integer (nullable = false)

|-- occupation: integer (nullable = false)
|-- zip: string (nullable = true)

moviesDF.printSchema()
root

|-- movieId: integer (nullable = false)
|-- title: string (nullable = true)

ratingsDF.printSchema()
root

|-- user: integer (nullable = false)

|-- product: integer (nullable = false)

|-- rating: double (nullable = false) |-- zip: string (nullable =
true)




Here are some example queries using Spark SQL with DataFrames on the Movie Lens data. The first
query gets the maximum and minimum ratings along with the count of users who have rated a movie.
// Get the max, min ratings along with the count of users who have
rated a movie.
val results = sqlContext.sql("select movies.title,
movierates.maxr, movierates.minr, movierates.cntu
from(SELECT ratings.product, max(ratings.rating)
as maxr, min(ratings.rating) as minr,count(distinct user)
as cntu FROM ratings group by ratings.product ) movierates
join movies on movierates.product=movies.movieId
order by movierates.cntu desc ")
// DataFrame show() displays the top 20 rows in
results.show()
title

tabular form

maxr minr cntu

American Beauty (... 5.0

1.0

3428

Star Wars: Episod... 5.0

1.0

2990

Star Wars: Episod... 5.0
Star Wars: Episod... 5.0
Jurassic Park (1993) 5.0
Saving Private Ry... 5.0

1.0
1.0
1.0
1.0

2991
2883
2672
2653

The query below finds the users who rated the most movies, then finds which movies the most active user
rated higher than four. We will get recommendations for this user later.
// Show the top 10 most-active users and how many times they rated a
movie
val mostActiveUsersSchemaRDD = sqlContext.sql("SELECT ratings.user,
count(*) as ct from ratings group by ratings.user order by ct desc
limit 10")
println(mostActiveUsersSchemaRDD.collect().mkString("\n"))

[4169,2314]
[1680,1850]
[4277,1743]
. . .




// Find the movies that user 4169 rated higher than 4

val results =sqlContext.sql("SELECT ratings.user,
ratings.product, ratings.rating, movies.title
FROM ratings JOIN movies ON movies.movieId=ratings.product
where ratings.user=4169 and ratings.rating > 4")
results.show

user product rating title
4169 1231

5.0

Right Stuff, The ...

4169 3632

5.0

Monsieur Verdoux ...

4169 232

4169 2434

4169 1834

5.0
5.0

5.0

Eat Drink Man Wom...
Down in the Delta...

Spanish Prisoner,... …

## Lab 10.2: Use Spark to Make Movie Recommendations
Estimated time to complete: 20 minutes

Using ALS to Build a Matrix Factorization Model
Now we will use the MLlib ALS algorithm to learn the latent factors that can be used to predict missing
entries in the user-item association matrix. First we separate the ratings data into training data (80%) and
test data (20%). We will get recommendations for the training data, then we will evaluate the predictions
with the test data. This process of taking a subset of the data to build the model and then verifying the
model with the remaining data is known as cross validation, the goal is to estimate how accurately a
predictive model will perform in practice. To improve the model this process is often done multiple times
with different subsets, we will only do it once.

![](..\images\111-296.png)


We run ALS on the input trainingRDD of Rating (user, product, rating) objects with the rank
and Iterations parameters:
-Rank is the number of latent factors in the model.

-Iterations is the number of iterations to run.

The ALS run(trainingRDD) method will build and return a MatrixFactorizationModel, which can
be used to make product predictions for users.
// Randomly split ratings RDD into training data RDD (80%) and test
data RDD (20%)
val splits = ratingsRDD.randomSplit(Array(0.8, 0.2), 0L)
val trainingRatingsRDD = splits(0).cache()

val testRatingsRDD = splits(1).cache()

val numTraining = trainingRatingsRDD.count()
val numTest = testRatingsRDD.count()

println(s"Training: $numTraining, test: $numTest.")
//Training: 800702, test: 199507.

// build a ALS user product matrix model with rank=20, iterations=10

val model = (new
ALS().setRank(20).setIterations(10).run(trainingRatingsRDD))

Making Predictions with the MatrixFactorizationModel
Now we can use the MatrixFactorizationModel to make predictions. First we will get movie
predictions for the most active user, 4169, with the recommendProducts() method , which takes as
input the user ID and the number of products to recommend. Then we print out the recommended movie
titles.
// Get the top 4 movie predictions for user 4169

val topRecsForUser = model.recommendProducts(4169, 5)

// get movie titles to show with recommendations

val movieTitles=moviesDF.map(array => (array(0),
array(1))).collectAsMap()

// print out top recommendations for user 4169 with titles

topRecsForUser.map(rating => (movieTitles(rating.product),
rating.rating)).foreach(println)



(Other Side of Sunday) (1996),5.481923568209796)
(Shall We Dance? (1937),5.435728723311838)
(42 Up (1998),5.3596886655841995)

(American Dream (1990),5.291663089739282)

Evaluating the Model
Next we will compare predictions from the model with actual ratings in the testRatingsRDD. First we
get the user product pairs from the testRatingsRDD to pass to the MatrixFactorizationModel
predict(user:Int,product:Int) method , which will return predictions as Rating (user,
product, rating) objects.
// get user product pair from testRatings

val testUserProductRDD = testRatingsRDD.map {
}

case Rating(user, product, rating) => (user, product)

// get predicted ratings to compare to test ratings
val predictionsForTestRDD

= model.predict(testUserProductRDD)

predictionsForTestRDD.take(10).mkString("\n")

Rating(5874,1084,4.096802264684769)
Rating(6002,1084,4.884270180173981)

Now we will compare the test predictions to the actual test ratings. First we put the predictions and the
test RDDs in this key, value pair format for joining: ((user, product), rating). Then we print out
the (user, product), (test rating, predicted rating) for comparison.
// prepare predictions for comparison

val predictionsKeyedByUserProductRDD = predictionsForTestRDD.map{
}

case Rating(user, product, rating) => ((user, product), rating)

// prepare test for comparison

val testKeyedByUserProductRDD = testRatingsRDD.map{
}

case Rating(user, product, rating) => ((user, product), rating)

//Join the test with predictions

val testAndPredictionsJoinedRDD =
testKeyedByUserProductRDD.join(predictionsKeyedByUserProductRDD)



// print the (user, product),( test rating, predicted rating)
testAndPredictionsJoinedRDD.take(3).mkString("\n")
((455,1254),(4.0,4.48399986469759))

((2119,1101),(4.0,3.83955683816239))

((1308,1042),(4.0,2.616444598335322))
The example below finds false positives by finding predicted ratings which were >= 4 when the actual
test rating was <= 1. There were 557 false positives out of 199,507 test ratings.
val falsePositives =(testAndPredictionsJoinedRDD.filter{

case ((user, product), (ratingT, ratingP)) => (ratingT <= 1 &&
ratingP >=4)
})

falsePositives.take(2)

Array[((Int, Int), (Double, Double))] =
((3842,2858),(1.0,4.106488210964762)),
((6031,3194),(1.0,4.790778049100913))
falsePositives.count
res23: Long = 557

Next we evaluate the model using Mean Absolute Error (MAE). MAE is the absolute differences between
the predicted and actual targets.
//Evaluate the model using Mean Absolute Error (MAE) between test
and predictions
val meanAbsoluteError = testAndPredictionsJoinedRDD.map {
case ((user, product), (testRating, predRating)) =>
val err = (testRating - predRating)
Math.abs(err)

}.mean()

meanAbsoluteError: Double = 0.7244940545944053




Make Predictions for Yourself
There are no movie recommendations for userid 0. You can use this user to add your own ratings and
get recommendations.
// set rating data for user 0

val data = Array(Rating(0,260,4),Rating(0,1,3),Rating(0,16,3),
Rating(0,25,4),Rating(0,32,4),Rating(0,335,1),Rating(0,379,1),
Rating(0,296,3),Rating(0,858,5),Rating(0,50,4))
// put in a RDD

val newRatingsRDD=sc.parallelize(data)
// combine user 0 ratings with total ratings

val unionRatingsRDD = ratingsRDD.union(newRatingsRDD)
val model = (new
ALS().setRank(20).setIterations(10).run(unionRatingsRDD))

Now we can use the MatrixFactorizationModel to make predictions. First we will get movie
predictions for the most active user, 4169, with the recommendProducts() method, which takes as
input the userid and the number of products to recommend. Then we print the recommended movies.
// Get the top 5 movie predictions for user 0

val topRecsForUser = model.recommendProducts(0, 5)
// get movie titles to show with recommendations
val movieTitles=moviesDF.map(array => (array(0),
array(1))).collectAsMap()

// print out top recommendations for user 0 with titles

topRecsForUser.map(rating => (movieTitles(rating.product),
rating.rating)).foreach(println)
((A Chef in Love (1996),7.63995409048131)

(All the Vermeers in New York (1990),7.580395148572494)
(Kika (1993),7.339997933272976)

(Clean Slate (Coup de Torchon) (1981),7.3236912666017195)

(Love and Other Catastrophes (1996),7.2672409490233045)




## Lab 10.3: Analyze a simple flight example with decision trees
Estimated time to complete: 15 minutes

Our data is from http://www.transtats.bts.gov/DL_SelectFields.asp?Table_ID=236&DB_Short_Name=OnTime. We are using flight information for January 2014. For each flight, we have the following information:

![](..\images\102.png)

In this scenario, we will build a tree to predict the label / classification of delayed or not based on the
following features:
-Label → delayed and not delayed
o

-delayed if delay > 40 minutes

Features → {day_of_month, weekday, crsdeptime, crsarrtime, carrier,
crselapsedtime, origin, dest, delayed}

![](..\images\103.png)

# Load and Parse the Data from a CSV File
First, we will import the machine learning packages.
In the code boxes, comments are in green and output is in blue.
import org.apache.spark._

import org.apache.spark.rdd.RDD
// Import classes for MLLib

import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.Vectors

import org.apache.spark.mllib.tree.DecisionTree

import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.mllib.util.MLUtils

In our example, each flight is an item, and we use a Scala case class to define the Flight schema
corresponding to a line in the CSV data file.
// define the Flight Schema

case class Flight(dofM: String, dofW: String, carrier: String,
tailnum: String, flnum: Int, org_id: String, origin: String,
dest_id: String, dest: String, crsdeptime: Double, deptime: Double,
depdelaymins: Double, crsarrtime: Double, arrtime: Double, arrdelay:
Double, crselapsedtime: Double, dist: Int)




The function below parses a line from the data file into the Flight class.
// function to parse input into Flight class
def parseFlight(str: String): Flight = {
val line = str.split(",")

Flight(line(0), line(1), line(2), line(3), line(4).toInt, line(5),
line(6), line(7), line(8), line(9).toDouble, line(10).toDouble,
line(11).toDouble, line(12).toDouble, line(13).toDouble,
line(14).toDouble, line(15).toDouble, line(16).toInt)
}

We use the flight data for January 2014. Below we load the data from the CSV file into an RDD.
// load the data into an RDD

val textRDD = sc.textFile("/home/jovyan/work/spark-dev3600/data/rita2014jan.csv")
// MapPartitionsRDD[1] at textFile

// parse the RDD of csv lines into an RDD of flight classes
val flightsRDD = textRDD.map(parseFlight).cache()
flightsRDD.first()

//Array(Flight(1,3,AA,N338AA,1,12478,JFK,12892,LAX,900.0,914.0,14.0,
1225.0,1238.0,13.0,385.0,2475),

Extract Features
To build a classifier model, first extract the features that most contribute to the classification.
We are defining two classes or labels – Yes (delayed) and No (not delayed). A flight is considered to be
delayed if it is more than 40 minutes late.
The features for each item consists of the fields shown below:
-Label → delayed and not delayed
o

-delayed if delay > 40 minutes

Features → {day_of_month, weekday, crsdeptime, crsarrtime, carrier,
crselapsedtime, origin, dest, delayed}




Below we transform the non-numeric features into numeric values. For example, the carrier AA is the
number 6. The originating airport ATL is 273.
// create airports RDD with ID and Name

var carrierMap: Map[String, Int] = Map()
var index: Int = 0

flightsRDD.map(flight => flight.carrier).distinct.collect.foreach(x
=> { carrierMap += (x -> index); index += 1 })
carrierMap.toString

//res2: String = Map(DL -> 5, F9 -> 10, US -> 9, OO -> 2, B6 -> 0,
AA -> 6, EV -> 12, FL -> 1, UA -> 4, MQ -> 8, WN -> 13, AS -> 3, VX
-> 7, HA -> 11)
// Defining a default vertex called nowhere
var originMap: Map[String, Int] = Map()
var index1: Int = 0

flightsRDD.map(flight => flight.origin).distinct.collect.foreach(x
=> { originMap += (x -> index1); index1 += 1 })
originMap.toString

//res4: String = Map(JFK -> 214,
...

LAX -> 294,

ATL -> 273,MIA -> 175

// Map airport ID to the 3-letter code to use for printlns
var destMap: Map[String, Int] = Map()
var index2: Int = 0

flightsRDD.map(flight => flight.dest).distinct.collect.foreach(x =>
{ destMap += (x -> index2); index2 += 1 })
```

![](..\images\120-315.png)

## Define Features Array

image reference: O’Reilly Learning Spark
The features are transformed and put into Feature Vectors, which are vectors of numbers representing
the value for each feature.
Next, we create an RDD containing feature arrays consisting of the label and the features in numeric
format. An example is shown in this table:

![](..\images\104.png)

```
// Defining the features array

val mlprep = flightsRDD.map(flight => {

val monthday = flight.dofM.toInt - 1 // category
val weekday = flight.dofW.toInt - 1 // category
val crsdeptime1 = flight.crsdeptime.toInt
val crsarrtime1 = flight.crsarrtime.toInt

val carrier1 = carrierMap(flight.carrier) // category
val crselapsedtime1 = flight.crselapsedtime.toDouble
val origin1 = originMap(flight.origin) // category
val dest1 = destMap(flight.dest) // category

val delayed = if (flight.depdelaymins.toDouble > 40) 1.0 else 0.0




Array(delayed.toDouble, monthday.toDouble, weekday.toDouble,
crsdeptime1.toDouble, crsarrtime1.toDouble, carrier1.toDouble,
crselapsedtime1.toDouble, origin1.toDouble, dest1.toDouble)
})

mlprep.take(1)

//res6: Array[Array[Double]] = Array(Array(0.0, 0.0, 2.0, 900.0,
1225.0, 6.0, 385.0, 214.0, 294.0))

## Create Labeled Points
From the RDD containing feature arrays, we create an RDD containing arrays of LabeledPoints.
A labeled point is a class that represents the feature vector and label of a data point.
//Making LabeledPoint of features – this is the training data for
the model

val mldata = mlprep.map(x => LabeledPoint(x(0), Vectors.dense(x(1),
x(2), x(3), x(4), x(5), x(6), x(7), x(8))))
mldata.take(1)

//res7: Array[org.apache.spark.mllib.regression.LabeledPoint] =
Array((0.0,[0.0,2.0,900.0,1225.0,6.0,385.0,214.0,294.0]))
Next the data is split to get a good percentage of delayed and not delayed flights. Then it is split into a
training data set and a test data set
// mldata0 is %85 not delayed flights

val mldata0 = mldata.filter(x => x.label ==
0).randomSplit(Array(0.85, 0.15))(1)
// mldata1 is %100 delayed flights

val mldata1 = mldata.filter(x => x.label != 0)
// mldata2 is delayed and not delayed
val mldata2 = mldata0 ++ mldata1

// split mldata2 into training and test data

val splits = mldata2.randomSplit(Array(0.7, 0.3))

val (trainingData, testData) = (splits(0), splits(1))
testData.take(1)

//res21: Array[org.apache.spark.mllib.regression.LabeledPoint] =
Array((0.0,[18.0,6.0,900.0,1225.0,6.0,385.0,214.0,294.0]))



## Train the Model

![](..\images\122-320.png)

Next, we prepare the values for the parameters that are required for the Decision Tree:
-categoricalFeaturesInfo: Specifies which features are categorical and how many
categorical values each of those features can take. The first item here represents the day of the
month and can take the values from zero through to 31. The second one represents day of the
week and can take the values from one though seven. The carrier value can go from four to the
number of distinct carriers and so on.

-maxDepth: Maximum depth of a tree.

-maxBins: Number of bins used when discretizing continuous features.

-impurity: Impurity measure of the homogeneity of the labels at the node.

The model is trained by making associations between the input features and the labeled output
associated with those features. We train the model using the DecisionTree.trainClassifier
method which returns a DecisionTreeModel.

```
// set ranges for 0=dofM 1=dofW 4=carrier 6=origin 7=dest
var categoricalFeaturesInfo = Map[Int, Int]()
categoricalFeaturesInfo += (0 -> 31)
categoricalFeaturesInfo += (1 -> 7)

categoricalFeaturesInfo += (4 -> carrierMap.size)
categoricalFeaturesInfo += (6 -> originMap.size)
categoricalFeaturesInfo += (7 -> destMap.size)
val numClasses = 2

// Defning values for the other parameters
val impurity = "gini"
val maxDepth = 9

val maxBins = 7000
// call DecisionTree trainClassifier with the trainingData , which
returns the model
val model = DecisionTree.trainClassifier(trainingData, numClasses,
categoricalFeaturesInfo,
impurity, maxDepth, maxBins)

// print out the decision tree
model.toDebugString

// 0=dofM 4=carrier 3=crsarrtime1
res20: String =

6=origin

DecisionTreeModel classifier of depth 9 with 919 nodes

If (feature 0 in
{11.0,12.0,13.0,14.0,15.0,16.0,17.0,18.0,19.0,20.0,21.0,22.0,23.0,24.0,25
.0,26.0,27.0,30.0})
If (feature 4 in
{0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,10.0,11.0,13.0})
If (feature 3 <= 1603.0)

If (feature 0 in {11.0,12.0,13.0,14.0,15.0,16.0,17.0,18.0,19.0})

If (feature 6 in
{0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,10.0,11.0,12.0,13.0...

```

Model.toDebugString prints out the decision tree, which asks the following questions to determine if
the flight was delayed or not:

![](..\images\124-325.png)

**Test the Model**

Next we use the test data to get predictions. Then we compare the predictions of a flight delay to the
actual flight delay value, the label. The wrong prediction ratio is the count of wrong predictions divided by
the count of test data values, which is 31%.

```
// Evaluate model on test instances and compute test error
val labelAndPreds = testData.map { point =>

val prediction = model.predict(point.features)
(point.label, prediction)}

labelAndPreds.take(3)

res33: Array[(Double, Double)] = Array((0.0,0.0), (0.0,0.0), (0.0,0.0))
val wrongPrediction =(labelAndPreds.filter{

case (label, prediction) => ( label !=prediction)
})

wrongPrediction.count()
res35: Long = 11040

val ratioWrong=wrongPrediction.count().toDouble/testData.count()
ratioWrong: Double = 0.3157443157443157
```



