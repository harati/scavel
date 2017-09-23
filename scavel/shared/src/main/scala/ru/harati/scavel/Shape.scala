package ru.harati.scavel

import ru.harati.scavel.BasicTypes.{hasNegative, isMultiplicable, isSubtractive}

/**
 * Created by loki on 06.04.2017.
 */
object Shape {

  trait isContain[Q[_], R[_]] { def contain[T](self: Q[T], obj: R[T])(implicit cmp: Ordering[T]): Boolean }
  implicit class UniversalContain[Q[_], T](val data: Q[T]) extends AnyVal {
    def contain[R[_]](obj: R[T])(implicit c: isContain[Q, R], cmp: Ordering[T]): Boolean = c.contain[T](data, obj)
  }

  trait isIntersects[Q[_], R[_]] { def intersects[T](self: Q[T], that: R[T])(implicit cmp: Ordering[T]): Boolean }
  implicit class UniversalIntersects[Q[_], T](val data: Q[T]) extends AnyVal {
    def intersects[R[_]](that: R[T])(implicit int: isIntersects[Q, R], cmp: Ordering[T]) = int.intersects(data, that)
  }

  trait hasIntersection[Q[_], A[_], R[_]] { def intersection[T](self: Q[T], that: A[T])(implicit cmp: Ordering[T]): R[T] }
  implicit class UniversalIntersection[Q[_], T](val data: Q[T]) extends AnyVal {
    def intersection[A[_], R[_]](that: A[T])(implicit int: hasIntersection[Q, A, R], cmp: Ordering[T]): R[T] = int.intersection(data, that)
  }

}

trait Shape extends Operations {

}
