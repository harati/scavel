package ru.harati.scavel.d2

import ru.harati.scavel.{AbstractPoint}
import ru.harati.scavel.d3.Point3d
import ru.harati.scavel.Tolerance._

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Point2d {
  def apply(x: Double, y: Double) = new Point2d(x, y)
  implicit def shrink(f: Point2[Double]): Point2d = f.toDoublePoint
}

final class Point2d(x: Double, y: Double) extends Point2[Double](x, y) {
  /**
   * Required for distance measurement/etc
   */
  override def toDoublePoint: Point2d = this

  /**
   * Consider always double
   */
  override def distance(f: AbstractPoint): Double = f match {
    case f: Point2d => Math.hypot(x - f.x, y - f.y)
    case f: Point3d => Math.sqrt(Math.pow(x - f.x, 2) + Math.pow(y - f.y, 2) + f.z * f.z)
    case p          => p.distance(this)
  }

  override def equals(obj: Any): Boolean = obj match {
    case f: Point2d => (x ~= f.x) && (y ~= f.y)
    case f: Point3d => f.asPoint2 match {
      case None    => false
      case Some(p) => p == this
    }
    case f: Point2[_] => f.toDoublePoint == this
    case p            => super.equals(p)
  }
}
