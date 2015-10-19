package stefansavev.demo.hyperloglog.counters

class NaiveCounter extends ApproximateCounter{
  val table = new scala.collection.mutable.HashSet[Long]

  override def add(obj: Long): Unit = {
    table.add(obj)
  }

  override def distinctCount(): Double = {
    table.size
  }
}
