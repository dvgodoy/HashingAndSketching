package stefansavev.demo.hyperloglog.counters

import com.clearspring.analytics.stream.cardinality.{HyperLogLog => HLL}

class StreamLibHLL extends ApproximateCounter{
  val hLL = new HLL(0.01)

  override def add(obj: Long): Unit = {
    hLL.offer(obj)
  }

  override def distinctCount(): Double = {
    hLL.cardinality()
  }

}
