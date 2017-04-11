package ru.harati.scavel

import ru.harati.scavel.BasicTypes.{hasOne, hasZero}

/**
 * Created by loki on 11.04.2017.
 */
trait PrimitiveFactory[Q[_]] {
  def fill[T](data: T): Q[T]

  def zero[T](implicit hz: hasZero[T]) = fill(hz.zero)
  def one[T](implicit ho: hasOne[T]) = fill(ho.one)
}
