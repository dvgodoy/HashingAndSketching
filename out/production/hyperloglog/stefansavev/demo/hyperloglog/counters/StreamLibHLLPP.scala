package stefansavev.demo.hyperloglog.counters

import com.clearspring.analytics.stream.cardinality.{HyperLogLogPlus => HLLPP}

class StreamLibHLLPP extends ApproximateCounter {
  val hLL = new HLLPP(14, 14)

  override def add(obj: Long): Unit = {
    hLL.offer(obj)
  }

  override def distinctCount(): Double = {
    hLL.cardinality()
  }
}

