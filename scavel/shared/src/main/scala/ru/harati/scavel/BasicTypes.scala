package ru.harati.scavel

import ru.harati.scavel.Operations.MappableCollection

/**
 * Created by loki on 06.04.2017.
 */
object BasicTypes {

  trait hasZero[T] { def zero: T }
  trait hasOne[T] { def one: T }
  trait hasInvert[T] {def invert(value: T): T}

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

  trait hasAverage[T] { def average(a: T, b: T): T }
  implicit class UniversalAverage[Q](val data: Q) extends AnyVal {
    def average(other: Q)(implicit ha: hasAverage[Q]) = ha.average(data, other)
  }

  trait SafeCast[A, B] { def cast(from: A): B }
  implicit object IntToDoubleCast extends SafeCast[Int, Double] { override def cast(from: Int): Double = from }
  implicit object ByteToDoubleCast extends SafeCast[Byte, Double] { override def cast(from: Byte): Double = from }
  implicit object FloatToDoubleCast extends SafeCast[Float, Double] { override def cast(from: Float): Double = from }
  implicit object IntToLongCast extends SafeCast[Int, Long] { override def cast(from: Int): Long = from }
  implicit object ByteToLongCast extends SafeCast[Byte, Long] { override def cast(from: Byte): Long = from }

  implicit def autoSafeCast[A, B](a: A)(implicit cast: SafeCast[A, B]) = cast.cast(a)

  implicit object DoubleField extends hasZero[Double] with hasOne[Double] with hasNegative[Double]
    with isAdditive[Double] with isSubtractive[Double] with isMultiplicable[Double] with Ordering[Double] with hasAverage[Double] with hasInvert[Double]{
    override def zero: Double = 0
    override def one: Double = 1
    override def plus(a: Double, b: Double): Double = a + b
    override def negate(value: Double): Double = -value
    override def multiply(a: Double, b: Double): Double = a * b
    override def minus(a: Double, b: Double): Double = a - b
    override def abs(value: Double): Double = Math.abs(value)
    override def compare(x: Double, y: Double): Int = scala.math.Ordering.Double.compare(x, y)
    override def average(a: Double, b: Double): Double = (a + b) / 2
    override def invert(value: Double): Double = 1 / value
  }

  implicit object FloatField extends hasZero[Float] with hasOne[Float] with hasNegative[Float]
    with isAdditive[Float] with isSubtractive[Float] with isMultiplicable[Float] with Ordering[Float] with hasAverage[Float] with hasInvert[Float]{
    override def zero: Float = 0
    override def one: Float = 1
    override def plus(a: Float, b: Float): Float = a + b
    override def negate(value: Float): Float = -value
    override def multiply(a: Float, b: Float): Float = a * b
    override def minus(a: Float, b: Float): Float = a - b
    override def abs(value: Float): Float = Math.abs(value)
    override def compare(x: Float, y: Float): Int = scala.math.Ordering.Float.compare(x, y)
    override def average(a: Float, b: Float): Float = (a + b) / 2
    override def invert(value: Float): Float = 1 / value
  }

  implicit object IntField extends hasZero[Int] with hasOne[Int] with hasNegative[Int]
    with isAdditive[Int] with isSubtractive[Int] with isMultiplicable[Int] with Ordering[Int] {
    override def zero: Int = 0
    override def one: Int = 1
    override def plus(a: Int, b: Int): Int = a + b
    override def negate(value: Int): Int = -value
    override def multiply(a: Int, b: Int): Int = a * b
    override def minus(a: Int, b: Int): Int = a - b
    override def abs(value: Int): Int = Math.abs(value)
    override def compare(x: Int, y: Int): Int = scala.math.Ordering.Int.compare(x, y)   
  }

  implicit object LongField extends hasZero[Long] with hasOne[Long] with hasNegative[Long]
    with isAdditive[Long] with isSubtractive[Long] with isMultiplicable[Long] with Ordering[Long] {
    override def zero: Long = 0
    override def one: Long = 1
    override def plus(a: Long, b: Long): Long = a + b
    override def negate(value: Long): Long = -value
    override def multiply(a: Long, b: Long): Long = a * b
    override def minus(a: Long, b: Long): Long = a - b
    override def abs(value: Long): Long = Math.abs(value)
    override def compare(x: Long, y: Long): Int = scala.math.Ordering.Long.compare(x, y)
  }


}

trait BasicTypes
