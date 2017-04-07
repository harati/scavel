package ru.harati.scavel.d3

import ru.harati.scavel.BasicTypes.{hasZero, isAdditive, isSubtractive}
import ru.harati.scavel.{Operations, Point, SelfPointed}
import ru.harati.scavel.Point.OutboundSubtractive
import ru.harati.scavel.d2.{Point2, Vec2}
import ru.harati.scavel.Operations.{CollectionTranslation, MappableCollection, hasDistanceCC, hasPlainDimension, isComparableCollection, isFoldableCollection}

/**
 * Created by loki on 06.04.2017.
 */
object Point3 extends hasPlainDimension[Point3] with MappableCollection[Point3] with isFoldableCollection[Point3]
  with OutboundSubtractive[Point3, Vec3] with CollectionTranslation[Point3, Vec3] with isComparableCollection[Point3] with SelfPointed {
  def apply[T](a: T, b: T, c: T) = new Point3(a, b, c)

  override def dimension[T](data: Point3[T])(implicit field: hasZero[T]): Int = {
    val zero = implicitly[hasZero[T]].zero
    var acc = 0
    if (zero != data.x) acc += 1
    if (zero != data.y) acc += 1
    if (zero != data.z) acc += 1
    acc
  }
  override def map[T, R](data: Point3[T], function: (T) => R): Point3[R] = Point3(function(data.x), function(data.y), function(data.z))
  override def fold[T, R](data: Point3[T], initial: R, trans: (R, T) => R): R = trans(trans(trans(initial, data.x), data.y), data.z)
  override def subtract[T](self: Point3[T], other: Point3[T])(implicit sub: isSubtractive[T]): Vec3[T] =
    Vec3(sub.minus(self.x, other.x), sub.minus(self.y, other.y), sub.minus(self.z, other.z))

  override def drive[T](self: Point3[T], other: Vec3[T])(implicit sub: isAdditive[T]): Point3[T] =
    Point3(sub.plus(self.x, other.x), sub.plus(self.y, other.y), sub.plus(self.z, other.z))

  override def min[T](self: Point3[T], that: Point3[T])(implicit ord: Ordering[T]): Point3[T] =
    Point3(ord.min(self.x, that.x), ord.min(self.y, that.y), ord.min(self.z, that.z))
  override def max[T](self: Point3[T], that: Point3[T])(implicit ord: Ordering[T]): Point3[T] =
    Point3(ord.max(self.x, that.x), ord.max(self.y, that.y), ord.max(self.z, that.z))

  implicit object toPoint3DDistance extends hasDistanceCC[Point3, Double, Point3, Double, Double] {
    override def distance(self: Point3[Double], that: Point3[Double]): Double =
      Math.sqrt(Math.pow(self.x - that.x, 2) + Math.pow(self.y - that.y, 2) + Math.pow(self.z - that.z, 2))
  }
}

class Point3[@specialized(Int, Long, Float, Double) T](val x: T, val y: T, val z: T) extends Point[T] {

  override def hashCode(): Int = 31 * ((31 + x.hashCode()) + y.hashCode()) + z.hashCode()

  override def equals(obj: scala.Any): Boolean = obj match {
    case other: Point3[_] => other.x == x && other.y == y && other.z == z
    case _ => false
  }

  override def toString = s"Point3($x, $y, $z)"

}
