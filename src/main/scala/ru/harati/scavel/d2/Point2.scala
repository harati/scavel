package ru.harati.scavel.d2

import ru.harati.scavel.BasicTypes.{hasZero, isAdditive, isSubtractive}
import ru.harati.scavel.Point.OutboundSubtractive
import ru.harati.scavel.d3.{Point3, Vec3}
import ru.harati.scavel.{Operations, Point, SelfPointed}
import ru.harati.scavel.Operations.{AdditiveCollection, CollectionTranslation, MappableCollection, hasDistanceCC, hasPlainDimension, isComparableCollection, isFoldableCollection}

/**
 * Created by loki on 06.04.2017.
 */
object Point2 extends SelfPointed with hasPlainDimension[Point2] with MappableCollection[Point2] with isFoldableCollection[Point2] with OutboundSubtractive[Point2, Vec2]
  with CollectionTranslation[Point2, Vec2] with isComparableCollection[Point2] {

  def apply[T](a: T, b: T) = new Point2[T](a, b)

  override def dimension[T](data: Point2[T])(implicit field: hasZero[T]): Int = {
    val zero = implicitly[hasZero[T]].zero
    var acc = 0
    if (zero != data.x) acc += 1
    if (zero != data.y) acc += 1
    acc
  }

  override def map[T, R](data: Point2[T], function: (T) => R): Point2[R] = Point2(function(data.x), function(data.y))
  override def fold[T, R](data: Point2[T], initial: R, trans: (R, T) => R): R =
    trans(trans(initial, data.x), data.y)

  override def subtract[T](self: Point2[T], other: Point2[T])(implicit sub: isSubtractive[T]): Vec2[T] =
    Vec2(sub.minus(self.x, other.x), sub.minus(self.y, other.y))

  override def drive[T](self: Point2[T], other: Vec2[T])(implicit sub: isAdditive[T]): Point2[T] =
    Point2(sub.plus(self.x, other.x), sub.plus(self.y, other.y))

  override def min[T](self: Point2[T], that: Point2[T])(implicit ord: Ordering[T]): Point2[T] = Point2(ord.min(self.x, that.x), ord.min(self.y, that.y))
  override def max[T](self: Point2[T], that: Point2[T])(implicit ord: Ordering[T]): Point2[T] = Point2(ord.max(self.x, that.x), ord.max(self.y, that.y))

  implicit object toPoint2DDistance extends hasDistanceCC[Point2, Double, Point2, Double, Double] {
    override def distance(self: Point2[Double], that: Point2[Double]): Double = Math.sqrt(Math.pow(self.x - that.x, 2) + Math.pow(self.y - that.y, 2))
  }
}

class Point2[@specialized(Int, Long, Float, Double) T](val x: T, val y: T) extends Point[T] {

  override def hashCode(): Int = (31 + x.hashCode()) + y.hashCode()

  override def equals(obj: scala.Any): Boolean = obj match {
    case other: Point2[_] => other.x == x && other.y == y
    case _ => false
  }

  override def toString = s"Point2($x, $y)"
}
