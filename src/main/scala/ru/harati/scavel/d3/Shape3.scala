package ru.harati.scavel.d3

import ru.harati.scavel.BasicTypes.{hasNegative, isMultiplicable, isSubtractive}
import ru.harati.scavel.Shape

/**
  * Created by loki on 07.04.2017.
  */
object Shape3 {
  trait hasVolume[Q[_]] { def volume[T](data: Q[T])(implicit sub: isSubtractive[T], mul: isMultiplicable[T], abs: hasNegative[T]): T }
  implicit class UniversalVolume[Q[_], T](val data: Q[T]) extends AnyVal {
    def volume(implicit vol: hasVolume[Q], sub: isSubtractive[T], mul: isMultiplicable[T], abs: hasNegative[T]): T = vol.volume(data)
  }

}

trait Shape3 extends Shape {

}
