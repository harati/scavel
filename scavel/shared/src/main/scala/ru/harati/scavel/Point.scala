package ru.harati.scavel

import ru.harati.scavel.BasicTypes.{isAdditive, isSubtractive}
import ru.harati.scavel.d2.{Point2, Vec2}
import ru.harati.scavel.d3.{Point3, Vec3}

import scala.language.higherKinds

/**
 * Created by loki on 06.04.2017.
 */
object Point {

  trait OutboundSubtractive[Q[_], R[_] <: Vec[_]] { def subtract[T](self: Q[T], other: Q[T])(implicit sub: isSubtractive[T]): R[T] }
  implicit class UniversalOutboundSubtractive[T, Q[_]](val data: Q[T]) extends AnyVal {
    def -[R[_] <: Vec[_]](other: Q[T])(implicit out: OutboundSubtractive[Q, R], sub: isSubtractive[T]): R[T] = out.subtract(data, other)
  }

  type Point2i = Point2[Int]
  type Point2d = Point2[Double]
  type Point3d = Point3[Double]
  type Point3i = Point3[Int]


}

abstract class Point[T] extends Operations
