package ru.harati.scavel.d3

import ru.harati.scavel.Axis.{AxisProjection, X, Y, Z}
import ru.harati.scavel.{Axis, VectorFactory, Vector}
import ru.harati.scavel.d2.{Vec2, Vec2d}

import scala.math.Numeric.Implicits._

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Vec3 extends VectorFactory[Vec3]{

  @inline def apply[T: Numeric](c: Point3[T]): Vec3[T] = new Vec3[T](c)
  @inline def apply[T: Numeric](x: T, y: T, z: T): Vec3[T] = Vec3(Point3(x, y, z))

  @inline def zero[T: Numeric] = {
    val space = implicitly[Numeric[T]]
    Vec3(space.zero, space.zero, space.zero)
  }

  @inline def one[T: Numeric] = {
    val space = implicitly[Numeric[T]]
    Vec3(space.one, space.one, space.one)
  }

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

  override def toString: String = s"Vec(${carrier.x}, ${carrier.y}, ${carrier.z})"
}
