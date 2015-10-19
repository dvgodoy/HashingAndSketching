package stefansavev.demo.hyperloglog.counters

import org.apache.lucene.util.LongBitSet
import stefansavev.demo.hyperloglog.hashing.Hasher

class EfficientLinearCounter(hasher: Hasher) extends ApproximateCounter{
  final val p = 14 //number of bits used to represent the buckets
  final val m = 1 << p
  val bitSetCounters = new LongBitSet(m)
  var numSetBits: Int = 0

  override def add(obj: Long): Unit = {
    val h = hasher.hash(obj)
    val index = (h >>> (64 - p)).toInt
    if (!bitSetCounters.get(index)){
      bitSetCounters.set(index)
      numSetBits += 1
    }
  }

  override def distinctCount(): Double = {
    val zeros = m - numSetBits
    if (zeros != 0){
      m.toDouble*Math.log(m.toDouble/zeros.toDouble)
    }
    else{
      //it's hard to say anything except that the unique elements are more than
      m.toDouble*Math.log(m.toDouble)
    }
  }
}
