package stefansavev.demo.hyperloglog.counters

import java.lang.{Long => JLong}

import stefansavev.demo.hyperloglog.hashing.Hasher

import scala.collection.mutable.ArrayBuffer

class HyperLogLog(hasher: Hasher) extends ApproximateCounter{
  final val p = 10 //14 //number of bits used to represent the buckets
  final val m = 1 << p //number of counters
  val mask: Long = (1L << (64 - p)) - 1L //max value within the buckets, contains only 1's
  val counters = Array.ofDim[Int](m) //TODO: we can replace the ints by a byte or less

  override def add(obj: Long): Unit = {
    val hashCode = hasher.hash(obj)
    val bucketIndex = (hashCode >>> (64 - p)).toInt
    val bucketHashCode = hashCode & mask
    val sigma = (1 + JLong.numberOfTrailingZeros(bucketHashCode))
    counters(bucketIndex) = Math.max(counters(bucketIndex), sigma)
  }

  def distinctCount(): Double = {
    //this approximation is only good when the number of distinct elements is large
    var inverseSum = 0.0

    for(maxZeroSuffix <- counters){
      val estimatedValuesInBucket = (1 << maxZeroSuffix).toDouble
      inverseSum += 1.0/estimatedValuesInBucket
    }

    val harmonicMean = m.toDouble/inverseSum

    val corr = 0.7213/(1 + 1.079/m)
    corr*m*harmonicMean //multiply by m because we have m buckets
  }

  def exportStatistics(): Array[Double] = {
    val statistics = scala.collection.mutable.HashMap[Int, Int]()
    for(maxZeroSuffix <- counters){
      val truncatedSuffix = Math.min(maxZeroSuffix, 32)
      if (statistics.contains(truncatedSuffix)){
        statistics(truncatedSuffix) += 1
      }
      else{
        statistics(truncatedSuffix) = 1
      }
    }

    val statisticsList = new ArrayBuffer[Double]()
    for(k <- 0 until 32){
      val count = if (statistics.contains(k)){statistics(k)} else 0
      statisticsList += count.toDouble
    }
    statisticsList.toArray
  }
}
