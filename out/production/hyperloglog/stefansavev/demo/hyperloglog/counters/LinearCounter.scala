package stefansavev.demo.hyperloglog.counters

import stefansavev.demo.hyperloglog.hashing.Hasher

class LinearCounter(hasher: Hasher) extends ApproximateCounter{
  final val p = 14 //number of bits used to represent the buckets
  final val m = 1 << p
  val counters = Array.ofDim[Int](m)

  override def add(obj: Long): Unit = {
    val h = hasher.hash(obj)
    val index = (h >>> (64 - p)).toInt
    counters(index) = 1
  }

  override def distinctCount(): Double = {
    val zeros = m - counters.sum
    if (zeros != 0){
      m.toDouble*Math.log(m.toDouble/zeros.toDouble)
    }
    else{
      //it's hard to say anything except that the unique elements are more than
      //typically linear counting should not be used in this situation
      m.toDouble*Math.log(m.toDouble)
    }
  }
}
