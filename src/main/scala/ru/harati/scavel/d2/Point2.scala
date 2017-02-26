package ru.harati.scavel.d2

import ru.harati.scavel.{AbstractPoint,Point}
import ru.harati.scavel.d3.Point3
import scala.math.Numeric.Implicits._
import scala.util.hashing.MurmurHash3

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 *
 * Point in 2d space
 */
object Point2 {

  def apply[T: Numeric](a: T, b: T) = new Point2(a, b)

}

class Point2[@specialized(Int, Long, Float, Double) T: Numeric](val x: T, val y: T) extends Point[T] {

  /**
   * Consider always double
   */
  override def distance(f: AbstractPoint): Double = f match {
    case f: Point2[_] => f.toDoublePoint.distance(f)
    case f: Point3[_] => f.toDoublePoint.distance(f)
    case p            => p.distance(this)
  }

  /**
   * Required for distance measurement/etc
   */
  def toDoublePoint: Point2d = Point2d(x.toDouble, y.toDouble)

  /**
   * If all point coordinates satisfy predicate
   */
  override def is(f: (T) => Boolean): Boolean = f(x) && f(y)

  /**
   * Real dimension of object
   */
  @inline override def dimension: Int = {
    val zero = implicitly[Numeric[T]].zero
    var sum = 0
    if (x != zero) sum += 1
    if (y != zero) sum += 1
    sum
  }

  /**
   * Find min/max foreach coordinate of points
   */
  def min(o: Point2[T]) = Point2[T](space.min(x, o.x), space.min(y, o.y))
  def max(o: Point2[T]) = Point2[T](space.max(x, o.x), space.max(y, o.y))

  /**
   * Some translation
   */
  def +(driver: Vec2[T]) = Point2[T](x + driver.x, y + driver.y)
  def -(driver: Vec2[T]) = Point2[T](x - driver.x, y - driver.y)
  def +(driver: T) = Point2[T](x + driver, y + driver)
  def -(driver: T) = Point2[T](x - driver, y - driver)

  override def hashCode(): Int = x.hashCode + 31 * y.hashCode

  override def toString: String = s"Point($x, $y)"

  /**
   * Build vector by two points
   */
  override def -(other: Point[T]): Vec2[T] = other match {
    case p: Point2[T] => Vec2[T](Point2[T](x - p.x, y - p.y))
    case p: Point3[T] => Vec2[T](Point2[T](x - p.x, y - p.y))
  }

  override def toRadiusVector: Vec2[T] = Vec2[T](x, y)
}
