package stefansavev.demo.hyperloglog.hashing

import java.nio.ByteBuffer

import YonikMurmurHash3.LongPair

object Murmur3Hasher extends Hasher{
  override def hash(obj: Long): Long = {
    val buffer = ByteBuffer.allocate(8)
    buffer.putLong(obj)
    buffer.rewind()
    val data = buffer.array()
    val output = new LongPair()
    YonikMurmurHash3.murmurhash3_x64_128(data, 0 , 8, 1868, output)
    output.val1
  }
}

