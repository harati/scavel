package ru.harati.scavel

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 *
 * Implemented in Vec2, Vec3 directly for increasing performance
 */
object Axis {

  case object X extends Axis
  case object Y extends Axis
  case object Z extends Axis

  trait AxisProjection[T] { def value: T }

  implicit def value[T](f: AxisProjection[T]): T = f.value

}

sealed abstract class Axis {

}
