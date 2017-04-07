package ru.harati.scavel.d3

import ru.harati.scavel.BasicTypes.{hasZero, isAdditive}
import ru.harati.scavel.{Operations, SelfPointed, Vec}
import ru.harati.scavel.d2.{Point2, Vec2}
import ru.harati.scavel.Operations.{AdditiveCollection, hasPlainDimension, MappableCollection, hasLength, isComparableCollection}

/**
  * Created by loki on 06.04.2017.
  */
object Vec3 extends SelfPointed with MappableCollection[Vec3] with hasLength[Vec3] with AdditiveCollection[Vec3] with hasPlainDimension[Vec3] with isComparableCollection[Vec3]{

  def apply[T](carrier: Point3[T]): Vec3[T] = new Vec3[T](carrier)
  def apply[T](a: T, b: T, c : T): Vec3[T] = Vec3(Point3(a, b, c))

  override def map[T, R](data: Vec3[T], function: (T) => R): Vec3[R] = Vec3(function(data.x), function(data.y), function(data.z))
  override def plus[T](a: Vec3[T], b: Vec3[T])(implicit additive: isAdditive[T]): Vec3[T] =
    Vec3(additive.plus(a.x, b.x), additive.plus(a.y, b.y), additive.plus(a.z, b.z))
  override def dimension[T](data: Vec3[T])(implicit field: hasZero[T]): Int = data.carrier.dimension
  override def length[T](data: Vec3[T])(implicit fractional: Fractional[T]): Double = {
    val x = fractional.toDouble(data.x)
    val y = fractional.toDouble(data.y)
    val z = fractional.toDouble(data.z)
    Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2))
  }

  override def min[T](self: Vec3[T], that: Vec3[T])(implicit ord: Ordering[T]): Vec3[T] =
    Vec3(ord.min(self.x, that.x), ord.min(self.y, that.y), ord.min(self.z, that.z))
  override def max[T](self: Vec3[T], that: Vec3[T])(implicit ord: Ordering[T]): Vec3[T] =
    Vec3(ord.max(self.x, that.x), ord.max(self.y, that.y), ord.max(self.z, that.z))
}

class Vec3[@specialized(Int, Long, Float, Double) T](coordinates: Point3[T]) extends Vec[T] {
  @inline override def carrier: Point3[T] = coordinates
  @inline def x: T = carrier.x
  @inline def y: T = carrier.y
  @inline def z: T = carrier.z

  override def toString = s"Vec3($x, $y, $z)"
}
