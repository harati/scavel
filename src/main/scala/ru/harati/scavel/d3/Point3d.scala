package ru.harati.scavel.d3

import ru.harati.scavel.{AbstractPoint}
import ru.harati.scavel.d2.{Point2, Point2d}

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Point3d {

  def apply(x: Double, y: Double, z: Double) = new Point3d(x, y, z)

}

final class Point3d(x: Double, y: Double, z: Double) extends Point3[Double](x, y, z) {
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

  /**
    * Find min/max foreach coordinate of points
    */
  override def min(o: Point3[Double]): Point3d = Point3d(Math.min(x, o.x), Math.min(y, o.y), Math.min(z, o.z))
  override def max(o: Point3[Double]): Point3d = Point3d(Math.max(x, o.x), Math.max(y, o.y), Math.max(z, o.z))
  override def +(driver: Vec3[Double]): Point3d = Point3d(x + driver.x, y + driver.y, z + driver.z)
  override def -(driver: Vec3[Double]): Point3d = Point3d(x - driver.x, y - driver.y, z - driver.z)
  override def +(driver: Double): Point3d = Point3d(x + driver, y + driver, z + driver)
  override def -(driver: Double): Point3d = Point3d(x - driver, y - driver, z - driver)
}
