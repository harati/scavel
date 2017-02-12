package ru.harati.scavel.d3

import ru.harati.Axis.{AxisProjection, X, Y, Z}
import ru.harati.scavel.{Axis, Vector}
import ru.harati.scavel.d2.Vec2d

import scala.math.Numeric.Implicits._
import ru.harati.{Axis, Vector}

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Vec3 {

  def apply[T: Numeric](c: Point3[T]): Vec3[T] = new Vec3[T](c)
  def apply[T: Numeric](x: T, y: T, z: T): Vec3[T] = Vec3(Point3(x, y, z))

  implicit def incrementInt(f: Vec3[Int]): Vec3[Double] = Vec3d(f.x, f.y, f.z)
  implicit def incrementFloat(f: Vec3[Float]): Vec3[Double] = Vec3d(f.x, f.y, f.z)
  implicit def incrementIntL(f: Vec3[Int]): Vec3[Long] = Vec3[Long](f.x, f.y, f.z)

}

class Vec3[@specialized(Int, Long, Float, Double) T: Numeric](val carrier: Point3[T]) extends Vector[T] {

  @inline def x = carrier.x
  @inline def y = carrier.y
  @inline def z = carrier.z

  override def length: Double = Math.sqrt(Math.pow(x.toDouble(), 2) + Math.pow(x.toDouble(), 2) + Math.pow(z.toDouble(), 2))

  def +(c: Vec3[T]) = Vec3(x + c.x, y + c.y, z + c.z)
  def -(c: Vec3[T]) = Vec3(x - c.x, y - c.y, z - c.z)

  def *(f: T) = Vec3(f * x, f * y, f * z)
  def *(f: Double) = Vec3d(x.toDouble() * f, y.toDouble() * f, z.toDouble * f)
  def unary_- = Vec3(-x, -y, -z)

  /**
   * Scalar product
   */
  @inline def *(f: Vec3[T]): T = x * f.x + y * f.y + z * f.z

  /**
   * Angle between vectors
   */
  def angle(another: Vec3[T]): Double = Math.acos((this * another).toDouble() / (length * another.length))

  /**
   * Project this vector to vector @f
   */
  def apply(f: Vec3[T]): Vec3d = {
    val scalar = (this * f).toDouble()
    val norm = scalar / Math.pow(f.length, 2)
    f * norm
  }

  /**
   * Project vector to axis
   */
  def apply(f: Axis): Vec3[T] with AxisProjection[T] = {
    val zero = implicitly[Numeric[T]].zero
    f match {
      case X => new Vec3[T](Point3[T](carrier.x, zero, zero)) with AxisProjection[T] { def value = carrier.x }
      case Y => new Vec3[T](Point3[T](zero, carrier.y, zero)) with AxisProjection[T] { def value = carrier.y }
      case Z => new Vec3[T](Point3[T](zero, zero, carrier.z)) with AxisProjection[T] { def value = carrier.z }
    }
  }
}
