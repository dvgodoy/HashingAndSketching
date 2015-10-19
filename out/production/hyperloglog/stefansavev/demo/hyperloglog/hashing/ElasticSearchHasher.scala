package stefansavev.demo.hyperloglog.hashing

import com.carrotsearch.hppc.BitMixer

object ElasticSearchHasher extends Hasher{
  override def hash(obj: Long): Long = {
    BitMixer.mix64(obj)
  }
}
