package com.johnsnowlabs

import org.apache.spark.ml.Transformer
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.param.shared.{HasInputCol, HasOutputCol}
import org.apache.spark.ml.util.Identifiable
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Dataset}
import org.apache.spark.sql.types.{IntegerType, StructField, StructType}

import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

class ImageWidthTransformer(override val uid: String) extends Transformer with HasInputCol with HasOutputCol {

  def this() = this(Identifiable.randomUID("ImageWidthTransformer"))

  def setInputCol(value: String): this.type = set(inputCol, value)

  def setOutputCol(value: String): this.type = set(outputCol, value)

  override def transform(dataset: Dataset[_]): DataFrame = {
    val getImageWidth = udf { image: Array[Byte] => {
      ImageIO.read(new ByteArrayInputStream(image)).getWidth
    }}

    dataset
      .withColumn($(outputCol),
                  getImageWidth(dataset.col($(inputCol))).as($(outputCol)))
      .drop($(inputCol))
  }

  override def copy(extra: ParamMap): ImageWidthTransformer = defaultCopy(extra)

  override def transformSchema(schema: StructType): StructType = StructType(Seq(StructField($(outputCol), IntegerType)))
}
