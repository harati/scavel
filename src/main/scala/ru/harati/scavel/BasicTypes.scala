package ru.harati.scavel

import ru.harati.scavel.Operations.MappableCollection


/**
 * Created by loki on 06.04.2017.
 */
object BasicTypes {

  trait hasZero[T] { def zero: T }
  trait hasOne[T] { def one: T }

  trait isAdditive[T] { def plus(a: T, b: T): T }
  implicit class UniversalAdditive[T](val data: T) extends AnyVal {
    def +(other: T)(implicit additive: isAdditive[T]) = additive.plus(data, other)
  }

  trait isSubtractive[T] { def minus(a: T, b: T): T }
  implicit class UniversalSubtractive[T](val data: T) extends AnyVal {
    def -(other: T)(implicit subtractive: isSubtractive[T]) = subtractive.minus(data, other)
  }

  trait isMultiplicable[T] { def multiply(a: T, b: T): T }
  implicit class UniversalMultiplicable[T](val data: T) extends AnyVal {
    def *(other: T)(implicit mul: isMultiplicable[T]) = mul.multiply(data, other)
  }

  trait hasNegative[T] {
    def negate(value: T): T
    def abs(value: T): T
  }
  implicit class UniversalNegative[T](val data: T) extends AnyVal {
    def unary_-(other: T)(implicit additive: isAdditive[T], negate: hasNegative[T]) = additive.plus(data, negate.negate(other))
  }

  implicit object DoubleField extends hasZero[Double] with hasOne[Double] with hasNegative[Double]
    with isAdditive[Double] with isSubtractive[Double] with isMultiplicable[Double] with Ordering[Double]{
    override def zero: Double = 0
    override def one: Double = 1
    override def plus(a: Double, b: Double): Double = a + b
    override def negate(value: Double): Double = -value
    override def multiply(a: Double, b: Double): Double = a * b
    override def minus(a: Double, b: Double): Double = a - b
    override def abs(value: Double): Double = Math.abs(value)
    override def compare(x: Double, y: Double): Int = scala.math.Ordering.Double.compare(x, y)
  }

  trait SafeCast[A, B] { def cast(from: A): B }
  implicit object IntToDoubleCast extends SafeCast[Int, Double] { override def cast(from: Int): Double = from }
  implicit object ByteToDoubleCast extends SafeCast[Byte, Double] { override def cast(from: Byte): Double = from }
  implicit object FloatToDoubleCast extends SafeCast[Float, Double] { override def cast(from: Float): Double = from }
  implicit object IntToLongCast extends SafeCast[Int, Long] { override def cast(from: Int): Long = from }
  implicit object ByteToLongCast extends SafeCast[Byte, Long] { override def cast(from: Byte): Long = from }

  implicit def autoSafeCast[A, B](a: A)(implicit cast: SafeCast[A, B]) = cast.cast(a)
}

trait BasicTypes
