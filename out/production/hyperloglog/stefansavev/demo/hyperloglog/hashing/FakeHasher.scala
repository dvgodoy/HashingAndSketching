package stefansavev.demo.hyperloglog.hashing

import java.util.Random
import scala.collection._

//uses the java random number generator to simulate hashes
//however, it needs a hash table in order to generate the same hash code for the same object id
//useful for testing
class FakeHasher(seed: Int = 6717676) extends Hasher{
  val rnd = new Random(seed)
  val objToHashCode = mutable.HashMap[Long, Long]()

  override def hash(obj: Long): Long = {
    if (objToHashCode.contains(obj)){
      objToHashCode(obj)
    }
    else{
      val code = rnd.nextLong()
      objToHashCode += ((obj, code))
      code
    }
  }
}
