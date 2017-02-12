package ru.harati.scavel.d3

import ru.harati.d2.{Point2, Vec2}
import ru.harati.scavel.{AbstractPoint, Misc, Point}
import ru.harati.scavel.d2.Point2
import ru.harati.{AbstractPoint, Misc, Point, Vector}

import scala.math.Numeric.Implicits._

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Point3 {

  def apply[T: Numeric](x: T, y: T, z: T) = new Point3[T](x, y, z)

  implicit def shrink(f: Point3[Double]): Point3d = f.toDoublePoint

}

class Point3[@specialized(Int, Long, Float, Double) T: Numeric](val x: T, val y: T, val z: T) extends Point[T] {

  /**
   * Assuming always double
   */
  override def distance(f: AbstractPoint): Double = f match {
    case f: Point2[_] => f.toDoublePoint.distance(f)
    case f: Point3[_] => f.toDoublePoint.distance(f)
    case p            => p.distance(this)
  }

  /**
   * Required for distance measurement/etc
   */
  override def toDoublePoint: Point3d = Point3d(x.toDouble, y.toDouble, z.toDouble)

  /**
   * If zero coordinate is zero - Some(_), otherwise None
   */
  def asPoint2: Option[Point2[T]] = if (implicitly[Numeric[T]].zero == z) Some(Point2[T](x, y)) else None

  /**
   * If all point coordinates satisfy predicate
   */
  override def is(f: (T) => Boolean): Boolean = f(x) && f(y) && f(z)

  /**
   * Real dimension of object
   */
  @inline override def dimension: Int = {
    val zero = implicitly[Numeric[T]].zero
    var sum = 0
    if (x != zero) sum += 1
    if (y != zero) sum += 1
    if (z != zero) sum += 1
    sum
  }

  /**
   * Find min/max foreach coordinate of points
   */
  def min(o: Point3[T]) = Point3[T](space.min(x, o.x), space.min(y, o.y), space.min(z, o.z))
  def max(o: Point3[T]) = Point3[T](space.max(x, o.x), space.max(y, o.y), space.max(z, o.z))

  def average(o: Point3[T]): Point3[T] = Point3[T](Misc.average(o.x, x), Misc.average(o.y, y), Misc.average(o.z, z))

  /**
   * Some translation
   */
  def +(driver: Vec3[T]) = Point3[T](x + driver.x, y + driver.y, z + driver.z)
  def -(driver: Vec3[T]) = Point3[T](x - driver.x, y - driver.y, z - driver.z)
  def +(driver: T) = Point3[T](x + driver, y + driver, z + driver)
  def -(driver: T) = Point3[T](x - driver, y - driver, z - driver)

  override def hashCode(): Int = (x.hashCode() + 31 * y.hashCode()) * 31 + z.hashCode()

  override def toString: String = s"Point($x, $y, $z)"

  /**
   * Build vector by two points
   */
  override def -(other: Point[T]): Vec3[T] = other match {
    case p: Point2[T] => Vec3[T](Point3[T](x - p.x, y - p.y, z))
    case p: Point3[T] => Vec3[T](Point3[T](x - p.x, y - p.y, z - p.z))
  }

  override def toRadiusVector: Vec3[T] = Vec3[T](x, y, z)
}
