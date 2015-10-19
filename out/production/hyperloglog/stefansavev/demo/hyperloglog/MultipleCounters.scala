package stefansavev.demo.hyperloglog

import stefansavev.demo.hyperloglog.counters.ApproximateCounter

class MultipleCounters(counters: Array[ApproximateCounter]){
  def add(obj: Long): Unit = {
    for(counter <- counters){
      counter.add(obj)
    }
  }

  def distinctCount(cntrId: Int): Double = {
    counters(cntrId).distinctCount()
  }
}
