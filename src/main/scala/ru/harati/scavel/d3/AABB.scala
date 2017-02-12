package ru.harati.scavel.d3

import ru.harati.d2.{Point2, Point2d}
import ru.harati.scavel.{AbstractPoint, AttachedSpace, Shape}
import ru.harati.scavel.d2.{Point2, Point2d}
import ru.harati.{AttachedSpace, Shape}

import scala.math.Numeric.Implicits._

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object AABB {

  def apply[T: Numeric](a: Point3[T], b: Point3[T]) = new AABB[T](a, b)

}

class AABB[@specialized(Int, Long, Float, Double) T: Numeric](a: Point3[T], b: Point3[T]) extends Shape with AttachedSpace[T] {

  override protected def space: Numeric[T] = implicitly[Numeric[T]]

  val min = a min b
  val max = a max b

  def center = min average max

  /**
   * Contains given point, border excluded
   */
  override def contains(f: AbstractPoint): Boolean = f match {
    case p: Point2[_] => contains(p.toDoublePoint)
    case p: Point3[_] => contains(p.toDoublePoint)
    case _            => false
  }

  def contains(f: Point2d): Boolean =
    if (f.dimension != dimension) false
    else contains(Point3d(f.x, f.y, 0))

  /**
   * Inclusive contains
   */
  def contains(f: Point3d): Boolean =
    (min.x.toDouble <= f.x) && (min.y.toDouble <= f.y) && (min.z.toDouble <= f.z) &&
      (f.x <= max.x.toDouble) && (f.y <= max.y.toDouble) && (f.y <= max.y.toDouble)

  def intersection(other: AABB[T]) = AABB(min max other.min, max min other.max)
  def intersects(other: AABB[T]) =
    space.lt(space.max(min.x, other.min.x), space.min(max.x, other.max.x)) &&
      space.lt(space.max(min.y, other.min.y), space.min(max.y, other.max.y)) &&
      space.lt(space.max(min.z, other.min.z), space.min(max.z, other.max.z))

  /**
   * Extend AABB for given value to all directions
   */
  def extend(offset: T) = {
    val driver = Vec3[T](offset, offset, offset)
    AABB(min - driver, max + driver)
  }

  def +(driver: Vec3[T]) = AABB(min + driver, max + driver)

  /**
   * AABB is point
   */
  def isEmpty = min == max

  /**
   * Actual volume of AABB, for empty AABB should be zero
   */
  def volume = (max.x - min.x) * (max.y - min.y) * (max.z - min.z)

  /**
   * Real dimension of object
   */
  override def dimension: Int = Math.max(min.dimension, max.dimension)
}
