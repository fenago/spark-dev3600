// Databricks notebook source
val lines = sc.textFile("dbfs:/FileStore/shared_uploads/ernesto@ernesto.net/ErnestoSparkBook-1.txt")

// COMMAND ----------

lines.take(10).mkString("\n")

// COMMAND ----------

val numPartitions = lines.partitions.length
println(s"Number of partitions (workers) storing the datest = $numPartitions")

// COMMAND ----------

val words = lines.flatMap(x => x.split(' '))
