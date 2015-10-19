package stefansavev.demo.hyperloglog

import scala.util.Random

class ItemGenerator(objectCounts: Array[Int], rnd: Random) {

  private def createRandomData(): Array[Int] = {
    for(value <- objectCounts){
      if (value <= 0){
        throw new IllegalStateException("objectCounts must contain positive numbers")
      }
    }
    val totalCounts = objectCounts.sum
    val buffer = Array.ofDim[Int](totalCounts)
    var k = 0
    var i = 0
    while(i < objectCounts.length){
      val itemId = i
      val count = objectCounts(i)
      var j = 0
      while(j < count){
        buffer(k) = itemId
        k += 1
        j += 1
      }
      i += 1
    }
    rnd.shuffle(buffer.toVector).toArray
  }

  def generateItems(repeat: Int, processor: Long => Unit): Unit = {
    for(k <- 0 until repeat) {
      val permutation = createRandomData()
      for (item <- permutation) {
        processor(item)
      }
    }
  }
}
