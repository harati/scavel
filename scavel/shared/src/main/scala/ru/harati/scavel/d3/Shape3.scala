package ru.harati.scavel.d3

import ru.harati.scavel.BasicTypes.{hasAverage, hasNegative, isMultiplicable, isSubtractive}
import ru.harati.scavel.Shape

import scala.language.higherKinds

/**
 * Created by loki on 07.04.2017.
 */
object Shape3 {

  trait hasCenter[Q[_]] { def center[T](data: Q[T])(implicit ha: hasAverage[T]): Point3[T] }
  implicit class UniversalCenter[Q[_], T](val data: Q[T]) extends AnyVal {
    def center(implicit hc: hasCenter[Q], ha: hasAverage[T]): Point3[T] = hc.center(data)
  }

}

trait Shape3 extends Shape {

}
