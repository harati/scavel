package ru.harati.scavel

import Tolerance._
import ru.harati.scavel.d2.Vec2
import ru.harati.scavel.d3.Vec3

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Vector {

  def zero[T: Numeric] = new Vector[T] {
    /**
     * Coordinates of vector
     */
    override val carrier: Point[T] = Point.zero[T]

    /**
     * Reverse this vector
     */
    override def unary_-(): Vector[T] = this

    /**
     * Length of current vector
     */
    override def length: Double = 0
  }

  implicit class ExtendedNumber[T: Numeric](f: T) {
    def *(c: Vec2[T]) = c * f
    def *(c: Vec3[T]) = c * f
  }

  implicit def lowerDim[T: Numeric](f: Vec3[T]): Vec2[T] = Vec2[T](f.x, f.y)
}

abstract class Vector[T: Numeric] extends AbstractVector with AttachedSpace[T] {

  override protected def space: Numeric[T] = implicitly[Numeric[T]]

  /**
   * Coordinates of vector
   */
  override def carrier: Point[T]

  /**
   * Contains given point, border excluded
   */
  override def contains(f: AbstractPoint): Boolean = (f.distance(carrier) + f.distance(Point.zero)) ~= length

  override def equals(obj: scala.Any): Boolean = obj match {
    case f: Vector[_] => carrier == f.carrier
    case _            => false
  }

  /**
   * Reverse this vector
   */
  def unary_-(): Vector[T]

  /**
   * Check if vector zero
   */
  @inline def isZero = is(_ == implicitly[Numeric[T]].zero)

  /**
   * Check if this vector ort
   */
  @inline def isUnit = is(_ == implicitly[Numeric[T]].one)

  /**
   * If all vector component satisfy predicate
   */
  @inline def is(f: T => Boolean): Boolean = carrier.is(f)

  /**
   * Real dimension of object
   */
  @inline override def dimension: Int = carrier.dimension

  override def hashCode(): Int = carrier.hashCode() + 1488
}
