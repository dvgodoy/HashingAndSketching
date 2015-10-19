package stefansavev.demo.hyperloglog.counters

import java.lang.{Long => JLong}
import stefansavev.demo.hyperloglog.hashing.Hasher

//just as a baseline, does not really work
class MinCounter(hasher: Hasher) extends ApproximateCounter{
  val maxHashCode: Long = JLong.MAX_VALUE
  var minHashCode: Long = maxHashCode

  def getHashCode(obj: Long): Long = {
    hasher.hash(obj) & maxHashCode //remove the first bit
  }

  override def add(obj: Long): Unit = {
    val hashCode = getHashCode(obj)
    minHashCode = Math.min(minHashCode, hashCode)
  }

  override def distinctCount(): Double = {
    maxHashCode.toDouble/minHashCode.toDouble - 1
  }
}

