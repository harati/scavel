package ru.harati.scavel.d3

import ru.harati.scavel.BasicTypes.{isMultiplicable, isSubtractive}
import ru.harati.scavel.Operations.{CollectionFlatTranslation, CollectionTranslation, hasPerimeter, hasPlainDimension, hasSurface, hasVolume}
import ru.harati.scavel.Shape.{hasIntersection, isContain, isIntersects}
import ru.harati.scavel.d2.Point2
import ru.harati.scavel.{BasicTypes, SelfPointed, Shape}
import ru.harati.scavel.d3.Shape3.hasCenter

import scala.math.Ordering.Implicits

/**
 * Created by loki on 06.04.2017.
 */
object AABB extends SelfPointed with isIntersects[AABB, AABB]
  with hasIntersection[AABB, AABB, AABB] with CollectionTranslation[AABB, Vec3] with hasCenter[AABB] with hasPerimeter[AABB] with hasSurface[AABB] with hasVolume[AABB]{

  import BasicTypes._

  @inline def apply[T](a: Point3[T], b: Point3[T])(implicit ord: Ordering[T]): AABB[T] = {
    val min = a min b
    val max = a max b
    new AABB[T](min, max)
  }

  implicit object ContainPoint3 extends isContain[AABB, Point3] {
    override def contain[T](self: AABB[T], obj: Point3[T])(implicit cmp: Ordering[T]): Boolean =
      cmp.lt(obj.x, self.max.x) && cmp.lt(obj.y, self.max.y) && cmp.lt(obj.z, self.max.z) &&
        cmp.gt(obj.x, self.min.x) && cmp.gt(obj.y, self.min.y) && cmp.gt(obj.z, self.min.z)
  }

  implicit object ContainPoint2 extends isContain[AABB, Point2] {
    override def contain[T](self: AABB[T], obj: Point2[T])(implicit cmp: Ordering[T]): Boolean =
      cmp.lt(obj.x, self.max.x) && cmp.lt(obj.y, self.max.y) &&
        cmp.gt(obj.x, self.min.x) && cmp.gt(obj.y, self.min.y)
  }

  override def intersects[T](shape: AABB[T], sec: AABB[T])(implicit cmp: Ordering[T]): Boolean = cmp.lt(cmp.max(shape.min.x, sec.min.x), cmp.min(shape.max.x, sec.max.x)) &&
    cmp.lt(cmp.max(shape.min.y, sec.min.y), cmp.min(shape.max.y, sec.max.y)) &&
    cmp.lt(cmp.max(shape.min.z, sec.min.z), cmp.min(shape.max.z, sec.max.z))

  override def intersection[T](self: AABB[T], that: AABB[T])(implicit cmp: Ordering[T]): AABB[T] =
    AABB(self.min max that.min, self.max min that.max)

  override def drive[T](self: AABB[T], other: Vec3[T])(implicit sub: isAdditive[T]): AABB[T] =
    new AABB[T](self.min +> other, self.max +> other)

  override def center[T](data: AABB[T])(implicit ha: hasAverage[T]): Point3[T] =
    Point3(
      ha.average(data.min.x, data.max.x),
      ha.average(data.min.y, data.max.y),
      ha.average(data.min.z, data.max.z)
    )

  override def perimeter[T](data: AABB[T])(implicit sub: isSubtractive[T], add: isAdditive[T]): T = {
    val half = sub.minus(data.max.x, data.min.x) + sub.minus(data.max.y, data.min.y) + sub.minus(data.max.z, data.min.z)
    add.plus(half, half)
  }

  override def surface[T](data: AABB[T])(implicit sub: isSubtractive[T], add: isAdditive[T], mul: isMultiplicable[T]): T = {
    val xy = (data.max.x - data.min.x) * (data.max.y - data.min.y)
    val zx = (data.max.x - data.min.x) * (data.max.z - data.min.z)
    val yz = (data.max.z - data.min.z) * (data.max.y - data.min.y)
    val half = xy + zx + yz
    add.plus(half, half)
  }

  override def volume[T](data: AABB[T])(implicit sub: isSubtractive[T], neg: hasNegative[T], mul: isMultiplicable[T]): T = {
    neg.abs((data.max.x - data.min.x) * (data.max.y - data.min.y) * (data.max.z - data.min.z))
  }
}

class AABB[@specialized(Int, Long, Float, Double) T] private (val min: Point3[T], val max: Point3[T]) extends Shape3 {

  override def hashCode(): Int = 1488 + 31 * (31 * min.hashCode() + max.hashCode())
  override def equals(obj: scala.Any): Boolean = obj match {
    case f: AABB[_] => f.min == min && f.max == max
    case _ => false
  }

  override def toString: String = s"AABB(min=(${min.x}, ${min.y}, ${min.z}), max=(${max.x}, ${max.y}, ${max.z}))"
}
