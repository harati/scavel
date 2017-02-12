package ru.harati.scavel.d3

import ru.harati.d2.{Point2, Point2d}
import ru.harati.Point
import ru.harati.scavel.AbstractPoint
import ru.harati.scavel.d2.Point2d

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Point3d {

  def apply(x: Double, y: Double, z: Double) = new Point3d(x, y, z)

}

class Point3d(x: Double, y: Double, z: Double) extends Point3[Double](x, y, z) {
  /**
   * Required for distance measurement/etc
   */
  override def toDoublePoint: Point3d = this

  /**
   * Consider always double
   */
  override def distance(f: AbstractPoint): Double = f match {
    case f: Point2d => Math.hypot(x - f.x, y - f.y)
    case f: Point3d => Math.sqrt(Math.pow(x - f.x, 2) + Math.pow(y - f.y, 2) + Math.pow(z - f.z, 2))
    case p          => p.distance(this)
  }

  /**
   * If zero coordinate is zero - Some(_), otherwise None
   */
  override def asPoint2: Option[Point2d] = if (0D == z) Some(Point2d(x, y)) else None

  override def equals(obj: Any): Boolean = obj match {
    case f: Point2d => asPoint2 match {
      case None    => false
      case Some(p) => f == p
    }
    case f: Point3d => x == f.x && y == f.y && z == f.z
    case p          => super.equals(p)
  }
}
