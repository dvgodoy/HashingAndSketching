package stefansavev.demo.hyperloglog

import java.io.PrintWriter

import stefansavev.demo.hyperloglog.hashing.{FakeHasher, Murmur3Hasher}

object TestHashers {
  //I generate a lot of hashses and look at the distribution in R
  //It should be uniform
  def main(args: Array[String]) {
    val h = Murmur3Hasher
    val writer = new PrintWriter("D:/tmp/random-nums.txt")
    var i = 0
    while(i < 1000000){
      val hashCode = h.hash(i.toLong)
      writer.write((hashCode >> 8).toByte + "\n")
      i += 1
    }
    writer.close()
  }
}
