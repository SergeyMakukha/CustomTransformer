package com.johnsnowlabs

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.SparkConf
import org.apache.spark.ml.Pipeline
import org.apache.spark.sql.types.{BinaryType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec

class ImageWidthTransformerTest extends AnyFlatSpec {

  private val master = "local"
  private val appName = "example-spark"

  private val conf = new SparkConf()
    .setMaster(master)
    .setAppName(appName)

  private val spark = SparkSession.builder()
    .config(conf)
    .getOrCreate()

  private val binaryData = spark.sparkContext
    .binaryFiles(getClass.getResource("/images").getPath)
    .map{ case (fileName, pds) => Row(pds.open().readAllBytes()) }

  private val schema = StructType(Seq(StructField("data", BinaryType)))

  private val imageDataFrame = spark.createDataFrame(binaryData, schema)

  private val imageWidthTransformer = new ImageWidthTransformer()
    .setInputCol("data")
    .setOutputCol("imageWidth")

  private val pipeline = new Pipeline()

  pipeline.setStages(Array(imageWidthTransformer))

  private val modelPipeline = pipeline.fit(spark.emptyDataFrame)

  private val imageWidthDataFrame = modelPipeline.transform(imageDataFrame)

  private val imageCount = imageWidthDataFrame.count()
  "Image count " should "be 2." in {
    assert(imageCount == 2)
  }

  private val wrongImageWidthCount = imageWidthDataFrame.filter(v => {v.getInt(0) != 730}).count()
  "Image width of all images " should "be 730." in {
      assert(wrongImageWidthCount == 0)
    }

  if (spark != null)
    spark.stop()
}
