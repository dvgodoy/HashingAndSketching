package stefansavev.demo.hyperloglog.counters

trait ApproximateCounter {
  def add(obj: Long): Unit
  def distinctCount(): Double
}


