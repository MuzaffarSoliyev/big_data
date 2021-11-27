package ru.made

import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object TfIdf {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("tf-idf")
      .getOrCreate()

    var df = spark.read
      .option("header", "true")
      .option("interschema", "true")
      .csv("tripadvisor_hotel_reviews.csv")
      .withColumn("Review", lower(col("Review")))
      .withColumn("Review", regexp_replace(col("Review"), "[^a-z ]", ""))
      .withColumn("Review", split(col("Review"), " "))

    df = df.withColumn("doc_id", monotonically_increasing_id())

    val columns = df.columns.map(col):+(explode(col("Review")) as "token")
    var termFrequencies = df.select(columns: _*)

    termFrequencies = termFrequencies
      .groupBy("doc_id", "token")
      .agg(count("Review") as "term_frequencies")

    val totalReviewCount = df.count()

    val docFrequencies = termFrequencies
      .groupBy("token")
      .agg(countDistinct("doc_id") as "doc_frequencies")
      .orderBy(desc("doc_frequencies"))
      .limit(10)
      .withColumn("idf", log(lit(totalReviewCount) / (col("doc_frequencies") + 1) + 1))

    val result = termFrequencies
      .join(docFrequencies, "token")
      .withColumn("tf-idf", col("idf") * col("term_frequencies"))
      .groupBy("doc_id")
      .pivot("token")
      .mean("tf-idf")
      .orderBy("doc_id")
      .show
  }
}
