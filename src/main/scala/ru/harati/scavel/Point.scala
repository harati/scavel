package ru.harati.scavel

import ru.harati.scavel.BasicTypes.{isAdditive, isSubtractive}

/**
 * Created by loki on 06.04.2017.
 */
object Point {

  trait OutboundSubtractive[Q[_], R[_] <: Vec[_]] { def subtract[T](self: Q[T], other: Q[T])(implicit sub: isSubtractive[T]): R[T] }
  implicit class UniversalOutboundSubtractive[T, Q[_]](val data: Q[T]) extends AnyVal {
    def -[R[_] <: Vec[_]](other: Q[T])(implicit out: OutboundSubtractive[Q, R], sub: isSubtractive[T]): R[T] = out.subtract(data, other)
  }



}

abstract class Point[T] extends Operations
