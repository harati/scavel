package ru.harati.scavel

/**
 * Created by loki on 05.04.2017.
 */
trait VectorFactory[C[R]] {
  def zero[T](implicit space: Numeric[T]): C[T]
  def one[T](implicit space: Numeric[T]): C[T]
}
