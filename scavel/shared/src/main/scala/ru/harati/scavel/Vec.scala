package ru.harati.scavel

import ru.harati.scavel.d2.Vec2
import ru.harati.scavel.d3.Vec3

/**
 * Created by loki on 06.04.2017.
 */
object Vec {
  type Vec2i = Vec2[Int]
  type Vec2d = Vec2[Double]
  type Vec3d = Vec3[Double]
  type Vec3i = Vec3[Int]
}

abstract class Vec[T] extends Operations {
  def carrier: Point[T]

  override def hashCode(): Int = (31 * carrier.hashCode()) + 31
  override def equals(obj: scala.Any): Boolean = obj match {
    case other: Vec[_] => other.carrier == carrier
    case _ => false
  }
}
