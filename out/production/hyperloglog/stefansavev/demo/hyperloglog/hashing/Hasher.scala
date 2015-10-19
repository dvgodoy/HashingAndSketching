package stefansavev.demo.hyperloglog.hashing

trait Hasher {
  def hash(obj: Long): Long
}
