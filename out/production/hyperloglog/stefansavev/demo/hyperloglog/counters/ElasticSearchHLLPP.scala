package stefansavev.demo.hyperloglog.counters

import org.elasticsearch.common.util.BigArrays
import org.elasticsearch.search.aggregations.metrics.cardinality.HyperLogLogPlusPlus
import stefansavev.demo.hyperloglog.hashing.Hasher

class ElasticSearchHLLPP(hasher: Hasher) extends ApproximateCounter{
  //using one bucket
  val hllPP = new HyperLogLogPlusPlus(14, BigArrays.NON_RECYCLING_INSTANCE, 1)

  override def add(obj: Long): Unit = {
    val hash = hasher.hash(obj)
    hllPP.collect(0, hash)
  }

  override def distinctCount(): Double = {
    hllPP.cardinality(0)
  }
}
