package ru.harati.scavel

import ru.harati.scavel.d2.Vec2

/**
 * Created by loki on 06.04.2017.
 */
object Vec {

}

abstract class Vec[T] extends Operations {
  def carrier: Point[T]

  override def hashCode(): Int = (31 * carrier.hashCode()) + 31
  override def equals(obj: scala.Any): Boolean = obj match {
    case other: Vec[_] => other.carrier == carrier
    case _ => false
  }
}
