package ru.harati.scavel.d3

import ru.harati.scavel.BasicTypes.{isMultiplicable, isSubtractive}
import ru.harati.scavel.Operations.{CollectionTranslation, hasPlainDimension}
import ru.harati.scavel.Shape.{hasIntersection, isContain, isIntersects}
import ru.harati.scavel.d2.Point2
import ru.harati.scavel.{BasicTypes, SelfPointed, Shape}
import ru.harati.scavel.d3.Shape3.{hasCenter, hasVolume}

import scala.math.Ordering.Implicits

/**
 * Created by loki on 06.04.2017.
 */
object AABB extends SelfPointed with hasVolume[AABB] with isIntersects[AABB, AABB]
  with hasIntersection[AABB, AABB, AABB] with CollectionTranslation[AABB, Vec3] with hasCenter[AABB] {

  import BasicTypes._

  @inline def apply[T](a: Point3[T], b: Point3[T])(implicit ord: Ordering[T]): AABB[T] = {
    val min = a min b
    val max = a max b
    new AABB(min, max)
  }

  override def volume[T](data: AABB[T])(implicit sub: isSubtractive[T], mul: isMultiplicable[T], neg: hasNegative[T]): T =
    neg.abs((data.max.x - data.min.x) * (data.max.y - data.min.y) * (data.max.z - data.min.z))

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
    AABB[T](self.min +> other, self.max +> other)(self.ord)

  override def center[T](data: AABB[T])(implicit ha: hasAverage[T]): Point3[T] =
    Point3(
      ha.average(data.min.x, data.max.x),
      ha.average(data.min.y, data.max.y),
      ha.average(data.min.z, data.max.z)
    )
}

class AABB[@specialized(Int, Long, Float, Double) T] private (val min: Point3[T], val max: Point3[T])(implicit val ord: Ordering[T]) extends Shape3 {

  override def hashCode(): Int = 1488 + 31 * (31 * min.hashCode() + max.hashCode())
  override def equals(obj: scala.Any): Boolean = obj match {
    case f: AABB[_] => f.min == min && f.max == max
    case _ => false
  }

  override def toString: String = s"AABB(min=(${min.x}, ${min.y}, ${min.z}), max=(${max.x}, ${max.y}, ${max.z}))"
}
