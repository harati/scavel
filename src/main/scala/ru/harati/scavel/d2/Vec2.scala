package ru.harati.scavel.d2

import ru.harati.scavel.Axis.{AxisProjection, X, Y, Z}
import ru.harati.scavel.{Axis, Point, VectorFactory, Vector}

import scala.math.Numeric.Implicits._
import ru.harati.scavel.Tolerance._
import ru.harati.scavel.d3.Point3

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Vec2 extends VectorFactory[Vec2]{

  def apply[T: Numeric](c: Point2[T]): Vec2[T] = new Vec2[T](c)
  def apply[T: Numeric](x: T, y: T): Vec2[T] = Vec2(Point2(x, y))

  @inline def zero[T: Numeric] = {
    val space = implicitly[Numeric[T]]
    Vec2(space.zero, space.zero)
  }

  @inline def one[T: Numeric] = {
    val space = implicitly[Numeric[T]]
    Vec2(space.one, space.one)
  }

}

class Vec2[@specialized(Int, Long, Float, Double) T: Numeric](val carrier: Point2[T]) extends Vector[T] {

  @inline def x = carrier.x
  @inline def y = carrier.y

  override def length: Double = Math.hypot(x.toDouble, y.toDouble)

  def +(c: Vec2[T]) = Vec2(x + c.x, y + c.y)
  def -(c: Vec2[T]) = Vec2(x - c.x, y - c.y)

  def *(f: T) = Vec2(f * x, f * y)
  def unary_- = Vec2(-x, -y)

  /**
   * Scalar product
   */
  @inline def *(f: Vec2[T]): T = x * f.x + y * f.y

  /**
   * Angle between vectors
   */
  def angle(another: Vec2[T]): Double = Math.acos((this * another).toDouble() / (length * another.length))

  /**
   * Project this vector to vector @f
   */
  def apply(f: Vec2[T]): Vec2d = {
    val scalar = (this * f).toDouble()
    val norm = scalar / Math.pow(f.length, 2)
    Vec2d(x.toDouble() * norm, y.toDouble() * norm)
  }

  /**
   * Project vector to axis
   */
  def apply(f: Axis): Vec2[T] with AxisProjection[T] = {
    val zero = implicitly[Numeric[T]].zero
    f match {
      case X => new Vec2[T](Point2[T](carrier.x, zero)) with AxisProjection[T] { def value = carrier.x }
      case Y => new Vec2[T](Point2[T](zero, carrier.y)) with AxisProjection[T] { def value = carrier.y }
      case Z => new Vec2[T](Point2[T](zero, zero)) with AxisProjection[T] { def value = zero }
    }
  }

  override def toString: String = s"Vec(${carrier.x}, ${carrier.y})"
}
