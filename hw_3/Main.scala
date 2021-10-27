package linearRegression


import breeze.linalg._
import breeze.numerics._
import java.io.File

object Main {
  def main(args: Array[String]): Unit = {
    val trainFilename = args(0)
    val testFilename = args(1)
    val trainFile = new File(trainFilename)
    val testFile = new File(testFilename)
    val trainData = csvread(trainFile, ',', skipLines = 1)
    val testData = csvread(testFile, ',', skipLines = 1)
    val trainX = trainData(::, 0)
    val trainY = trainData(::, 1)
    val testX = testData(::, 0)
    val linreg = new LinReg(trainX, trainY, testX)
    val pred = linreg.pred()
    val outputFile = new File("C:/Users/user/IdeaProjects/made_scala_lesson/pred.csv")
    csvwrite(outputFile, pred.toDenseMatrix, separator = ',')
  }

  class LinReg(var trainX: DenseVector[Double], var trainY: DenseVector[Double], var testX: DenseVector[Double]){
    def pred(): DenseVector[Double] = {
      var b = pinv(trainX * trainX.t) * trainX * trainY
      return testX * b
    }
  }
}
