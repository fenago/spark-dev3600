// Databricks notebook source
val lines = sc.textFile("dbfs:/FileStore/shared_uploads/ernesto@ernesto.net/ErnestoSparkBook-1.txt")

// COMMAND ----------

lines.take(10).mkString("\n")

// COMMAND ----------

val numPartitions = lines.partitions.length
println(s"Number of partitions (workers) storing the datest = $numPartitions")

// COMMAND ----------

val words = lines.flatMap(x => x.split(' '))
words.take(10).mkString("\n")
val stopWords=Seq("a","*","and","is","of","the")
val filteredWords = words.filter(x => !stopWords.contains(x.toLowerCase()))
filteredWords.take(10).mkString("\n")

// COMMAND ----------

filteredWords.cache()

// COMMAND ----------

filteredWords.count()

// COMMAND ----------

val word1Tuples = filteredWords.map(x => (x,1))
word1Tuples.take(50).mkString("\n")

// COMMAND ----------

val wordCountTuples = word1Tuples.reduceByKey{case(x,y) => x + y}
wordCountTuples.take(10).mkString("\n")

// COMMAND ----------

val sortedWordCountTuples = wordCountTuples.top(10)(Ordering.by(tuple => tuple._2)).mkString("\n")
