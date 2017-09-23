package ru.harati.scavel

import ru.harati.scavel.BasicTypes.{SafeCast, hasInvert, hasNegative, hasOne, hasZero, isAdditive, isMultiplicable, isSubtractive}
import ru.harati.scavel.Operations.MappableCollection

import scala.language.higherKinds

/**
 * Created by loki on 06.04.2017.
 */
object Operations {

  implicit class UniversalCollection[T, Q[_]](val data: Q[T]) extends AnyVal {

    def +(other: Q[T])(implicit additive: isAdditive[T], collection: AdditiveCollection[Q]): Q[T] = collection.plus(data, other)
    def ++(other: Q[T])(implicit additive: isAdditive[T], collection: AdditiveCollection[Q]): Q[T] = data + other
    def -(other: Q[T])(implicit additive: isAdditive[T],
                       collection: AdditiveCollection[Q],
                       map: MappableCollection[Q],
                       negative: hasNegative[T]) = collection.plus(data, - other)
    def *(f: T)(implicit mul: isMultiplicable[T], map: MappableCollection[Q]): Q[T] = map.map[T, T](data, mul.multiply(_, f))
    def /(f: T)(implicit mul: isMultiplicable[T], map: MappableCollection[Q], inv: hasInvert[T]) = data * inv.invert(f)

    def min(other: Q[T])(implicit com: isComparableCollection[Q], ord: Ordering[T]) = com.min[T](data, other)
    def max(other: Q[T])(implicit com: isComparableCollection[Q], ord: Ordering[T]) = com.max[T](data, other)

    def perimeter(implicit p: hasPerimeter[Q], sub: isSubtractive[T], add: isAdditive[T]) = p.perimeter(data)
    def surface[R](implicit p: hasSurface[Q], sub: isSubtractive[T], add: isAdditive[T], mul: isMultiplicable[T]) = p.surface(data)
    def volume[R](implicit p: hasVolume[Q], sub: isSubtractive[T], neg: hasNegative[T], mul: isMultiplicable[T]) = p.volume(data)
    def length(implicit len: hasLength[Q], fractional: Fractional[T]) = len.length[T](data)
    def dimension(implicit spatial: hasPlainDimension[Q], field: hasZero[T]): Int = spatial.dimension(data)
    def unary_-(implicit mapper: MappableCollection[Q], negative: hasNegative[T]) = mapper.map(data, negative.negate)

    def fold[R](initial: R, trans: (R, T) => R)(implicit foldable: isFoldableCollection[Q]) = foldable.fold[T, R](data, initial, trans)
    def is(predicate: T => Boolean)(implicit foldable: isFoldableCollection[Q]) = fold[Boolean](true, (acc, value) => acc && predicate(value))
    def isZero(implicit foldable: isFoldableCollection[Q], hz: hasZero[T]) = is(_ == hz.zero)
    def isOne(implicit foldable: isFoldableCollection[Q], ho: hasOne[T]) = is(_ == ho.one)

    def isPoint(implicit p: hasPerimeter[Q], z: hasZero[T], sub: isSubtractive[T], add: isAdditive[T]) = p.perimeter(data) == z.zero
    def isSegment(implicit p: hasSurface[Q], z: hasZero[T], sub: isSubtractive[T], add: isAdditive[T], mul: isMultiplicable[T]) = p.surface(data) == z.zero
    def isPlane(implicit p: hasVolume[Q], z: hasZero[T], sub: isSubtractive[T], neg: hasNegative[T], mul: isMultiplicable[T]) = p.volume(data) == z.zero

    def +>[R[_] <: Vec[_]](other: R[T])(implicit tr: CollectionTranslation[Q, R], sub: isAdditive[T]): Q[T] = tr.drive[T](data, other)
    def +>(other: T)(implicit tr: CollectionFlatTranslation[Q], sub: isAdditive[T]) = tr.drive(data, other)
    def <+[R[_] <: Vec[_]](other: R[T])(implicit tr: CollectionTranslation[Q, R], sub: isAdditive[T],
      mapper: MappableCollection[R], negative: hasNegative[T]): Q[T] = tr.drive[T](data, -other)
    def <+(other: T)(implicit tr: CollectionFlatTranslation[Q], neg: hasNegative[T], add: isAdditive[T]) =
      tr.drive[T](data, neg.negate(other))

  }

  trait hasPlainDimension[Q[_]] { def dimension[T](data: Q[T])(implicit field: hasZero[T]): Int }
  trait hasLength[Q[_]] { def length[T](data: Q[T])(implicit fractional: Fractional[T]): Double }
  trait hasPerimeter[Q[_]] { def perimeter[T](data: Q[T])(implicit sub: isSubtractive[T], add: isAdditive[T]): T }
  trait hasSurface[Q[_]] { def surface[T](data: Q[T])(implicit sub: isSubtractive[T], add: isAdditive[T], mul: isMultiplicable[T]): T }
  trait hasVolume[Q[_]] { def volume[T](data: Q[T])(implicit sub: isSubtractive[T], neg: hasNegative[T], mul: isMultiplicable[T]): T }

  trait AdditiveCollection[Q[_]] { def plus[T](a: Q[T], b: Q[T])(implicit additive: isAdditive[T]): Q[T] }
  trait MappableCollection[Q[_]] { def map[T, R](data: Q[T], function: T => R): Q[R] }
  trait isFoldableCollection[Q[_]] { def fold[T, R](data: Q[T], initial: R, trans: (R, T) => R): R }

  trait CollectionTranslation[Q[_], R[_]] { def drive[T](self: Q[T], other: R[T])(implicit sub: isAdditive[T]): Q[T] }
  trait CollectionFlatTranslation[Q[_]] { def drive[T](self: Q[T], other: T)(implicit sub: isAdditive[T]): Q[T] }

  trait isComparableCollection[Q[_]] {
    def min[T](self: Q[T], that: Q[T])(implicit ord: Ordering[T]): Q[T]
    def max[T](self: Q[T], that: Q[T])(implicit ord: Ordering[T]): Q[T]
  }

  trait hasDistanceOO[Q, R, T] { def distance(self: Q, that: R): T }
  trait hasDistanceOC[Q, R[_], C, T] { def distance(self: Q, that: R[C]): T }
  trait hasDistanceCC[Q[_], A, R[_], C, T] { def distance(self: Q[A], that: R[C]): T }

  //Warning - NOT implicit class, so will be produced additional object for each call

  trait LowPriorityDistanceO[Q] {
    def data: Q
    def distance[R, T](to: R)(implicit dis: hasDistanceOO[Q, R, T]) = dis.distance(data, to)
    def distance[F[_], R, T](to: F[R])(implicit dis: hasDistanceOC[Q, F, R, T]) = dis.distance(data, to)
  }

  implicit class UniversalDistanceO[Q](val data: Q) extends LowPriorityDistanceO[Q] {
    def distance[R, T, A](to: R)(implicit dis: hasDistanceOO[A, R, T], safe: SafeCast[Q, A]) = dis.distance(safe.cast(data), to)
    def distance[F[_], R, T, A](to: F[R])(implicit dis: hasDistanceOC[Q, F, A, T], safe: SafeCast[R, A], map: MappableCollection[F]) = dis.distance(data, to)
  }

  trait LowPriorityDistanceC[Q[_], T] {
    def data: Q[T]
    def distance[F, R](to: F)(implicit dis: hasDistanceOC[F, Q, T, R]) = dis.distance(to, data)
    def distance[F[_], R, A](to: F[R])(implicit dis: hasDistanceCC[Q, T, F, R, A]) = dis.distance(data, to)
  }

  implicit class UniversalDistanceC[Q[_], T](val data: Q[T]) extends LowPriorityDistanceC[Q, T] {
    def distance[F, R, A](to: F)(implicit dis: hasDistanceOC[F, Q, A, R], safe: SafeCast[T, A], map: MappableCollection[Q]) = dis.distance(to, data)
    def distance[F[_], R, A, M](to: F[R])(implicit dis: hasDistanceCC[Q, M, F, R, A], safe: SafeCast[T, M], map: MappableCollection[Q]) = dis.distance(data, to)
  }

  //End of danger zone

  implicit def reshape[A, B, Q[_]](in: Q[A])(implicit safe: SafeCast[A, B], map: MappableCollection[Q]): Q[B] = map.map(in, safe.cast)

  implicit class UniversalCompositeDimension[Q[_], T[_], R](val data: Q[T[R]]) extends AnyVal {
    def dimension(implicit f: isFoldableCollection[Q], dim: hasPlainDimension[T], hz: hasZero[R]): Int = f.fold[T[R], Int](data, 0, _ + dim.dimension(_))
  }

}

trait Operations extends BasicTypes
