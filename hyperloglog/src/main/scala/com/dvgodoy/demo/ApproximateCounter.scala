package com.dvgodoy.demo

import com.clearspring.analytics.stream.cardinality.HyperLogLog
import com.dvgodoy.demo.hyperloglog.hashing.Murmur3Hasher
import org.elasticsearch.common.util.BigArrays
import org.elasticsearch.search.aggregations.metrics.cardinality.HyperLogLogPlusPlus
import scala.collection.mutable.ArrayBuffer

import scala.util.Random

/**
 * Created by Godoy on 19/10/2015.
 */
trait ApproximateCounter{
  def add(obj: Long): Unit
  def distinctCount(): Double
}

class NaiveCounter extends ApproximateCounter{
  val ids = scala.collection.mutable.HashSet.empty[Long]

  override def add(obj: Long): Unit = {
    ids.add(obj)
  }

  override def distinctCount(): Double = {
    ids.size
  }
}

class StreamLibHLL extends ApproximateCounter {
  val HLL = new HyperLogLog(23)

  override def add(obj: Long): Unit = {
    HLL.offer(obj)
  }

  override def distinctCount(): Double = {
    HLL.cardinality()
  }
}

class ElasticSearchHLL extends ApproximateCounter {
  val ESHLL = new HyperLogLogPlusPlus(14, BigArrays.NON_RECYCLING_INSTANCE, 1)

  override def add(obj: Long): Unit = {
    //ESHLL.collect(0, BitMixer.mix64(13 * BitMixer.mix64(obj)))
    ESHLL.collect(0, Murmur3Hasher.hash(Murmur3Hasher.hash(17 * obj)))
  }

  override def distinctCount(): Double = {
    ESHLL.cardinality(0)
  }
}

class ItemGenerator(objectCounts: Array[Int], rnd: Random) {
  def onProcessItem(processor: Int=> Unit): Unit = {

  }
}

class MyHashSet(p: Int) {
  val m = 1 << p
  val table = Array.ofDim[ArrayBuffer[Long]](m)

  def calcIndex(element: Long): Long = scala.math.abs(Murmur3Hasher.hash(element)) & (m-1)

  def add(element: Long): Unit = {
    val index = calcIndex(element)
    if (table(index.toInt) == null) {
      table(index.toInt) = new ArrayBuffer[Long]
    }
    val list = table(index.toInt)
    if (!list.contains(element)){
      list += element
    }
  }

  def contains(element: Long): Boolean = {
    val index = calcIndex(element)
    table(index.toInt).contains(element)
  }
}