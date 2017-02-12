package ru.harati.scavel

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Point {

  def zero[T: Numeric]: ZeroPoint[T] = new ZeroPoint[T] {

    /**
     * Consider always double
     */
    override def distance(f: AbstractPoint): Double = f match {
      case p: ZeroPoint[_] => 0
      case other           => other distance this
    }

    /**
     * Required for distance measurement/etc
     */
    override def toDoublePoint: Point[Double] = this.asInstanceOf[Point[Double]]

    /**
     * If all point coordinates satisfy predicate
     */
    override def is(f: (T) => Boolean): Boolean = f(space.zero)

    /**
     * Real dimension of object
     */
    override def dimension: Int = 0

    /**
     * Build vector by two points
     */
    override def -(other: Point[T]): Vector[T] = other match {
      case f: ZeroPoint[_] => Vector.zero[T]
      case point           => -(point - this)
    }

    override def toRadiusVector: Vector[T] = Vector.zero[T]
  }

  abstract class ZeroPoint[T: Numeric] extends Point[T]
}

abstract class Point[T: Numeric] extends AbstractPoint with AttachedSpace[T] {

  /**
   * Required for distance measurement/etc
   */
  def toDoublePoint: Point[Double]

  def toRadiusVector: Vector[T]

  override def equals(obj: scala.Any): Boolean = obj match {
    case f: Point[_] => f.toDoublePoint == this.toDoublePoint
    case _           => false
  }

  /**
   * Build vector by two points
   */
  def -(other: Point[T]): Vector[T]

  /**
   * If all point coordinates satisfy predicate
   */
  def is(f: T => Boolean): Boolean

  override protected def space: Numeric[T] = implicitly[Numeric[T]]
}
