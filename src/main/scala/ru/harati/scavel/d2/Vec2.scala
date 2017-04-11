package ru.harati.scavel.d2

import ru.harati.scavel.BasicTypes.{hasZero, isAdditive}
import ru.harati.scavel.Operations.{AdditiveCollection, MappableCollection, hasLength, hasPlainDimension, isComparableCollection, isFoldableCollection}
import ru.harati.scavel.d3.Vec3
import ru.harati.scavel._

/**
 * Created by loki on 06.04.2017.
 */
object Vec2 extends SelfPointed with MappableCollection[Vec2] with hasLength[Vec2] with AdditiveCollection[Vec2]
  with hasPlainDimension[Vec2] with isComparableCollection[Vec2] with isFoldableCollection[Vec3] with PrimitiveFactory[Vec2] {

  def apply[T](carrier: Point2[T]): Vec2[T] = new Vec2[T](carrier)
  def apply[T](a: T, b: T): Vec2[T] = Vec2(Point2(a, b))

  override def map[T, R](data: Vec2[T], function: (T) => R): Vec2[R] = Vec2(function(data.x), function(data.y))
  override def plus[T](a: Vec2[T], b: Vec2[T])(implicit additive: isAdditive[T]): Vec2[T] = Vec2(additive.plus(a.x, b.x), additive.plus(a.y, b.y))
  override def dimension[T](data: Vec2[T])(implicit field: hasZero[T]): Int = data.carrier.dimension
  override def length[T](data: Vec2[T])(implicit fractional: Fractional[T]): Double = {
    val a = fractional.toDouble(data.x)
    val b = fractional.toDouble(data.y)
    Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2))
  }

  override def min[T](self: Vec2[T], that: Vec2[T])(implicit ord: Ordering[T]): Vec2[T] =
    Vec2(ord.min(self.x, that.x), ord.min(self.y, that.y))
  override def max[T](self: Vec2[T], that: Vec2[T])(implicit ord: Ordering[T]): Vec2[T] =
    Vec2(ord.max(self.x, that.x), ord.max(self.y, that.y))

  override def fold[T, R](data: Vec3[T], initial: R, trans: (R, T) => R): R = trans(trans(initial, data.x), data.y)
  implicit def box[T](value: T): Vec2[T] = Vec2(value, value)
  override def fill[T](data: T): Vec2[T] = Vec2(data, data)
}

class Vec2[@specialized(Int, Long, Float, Double) T](coordinates: Point2[T]) extends Vec[T] {
  @inline override def carrier: Point2[T] = coordinates
  @inline def x: T = carrier.x
  @inline def y: T = carrier.y

  override def toString = s"Vec2($x, $y)"
}
