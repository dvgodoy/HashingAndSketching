package com.dvgodoy.demo

import scala.util.Random

/**
 * Created by Godoy on 19/10/2015.
 */
object Test2{
  val numDarts = 1000000
  val rnd = new Random(4811)
  case class Point2D(x: Double, y: Double)
  val circleRadius = 0.5
  def throwDart(): Point2D = Point2D(rnd.nextDouble()-0.5, rnd.nextDouble()-0.5)
  def inCircle(point: Point2D) = (point.x * point.x + point.y * point.y) <= (circleRadius * circleRadius)
  val numDartsInCircle = (1 to numDarts).map(_ => throwDart()).count(inCircle)
  def estimate(numDartsInCircle: Int, numTotalDarts: Int): Double = 4 * numDartsInCircle.toFloat / numTotalDarts.toFloat
  println("pi estimate: " + estimate(numDartsInCircle, numDarts))
}

object Test {
  def main(args: Array[String]): Unit = {
    val naive = new NaiveCounter
    val hll = new StreamLibHLL
    val eshll = new ElasticSearchHLL
    val my = new MyHashSet(10)
    for (i <- (0 until 1000)) {
      /*naive.add(i)
      hll.add(i)
      eshll.add(i)*/
      //println(naive.distinctCount()
      my.add(i)
    }
    /*println("naive: " + naive.distinctCount())
    println(hll.distinctCount())
    println(eshll.distinctCount())*/
    println(my.contains(100))
    println(my.contains(1001))
  }
}