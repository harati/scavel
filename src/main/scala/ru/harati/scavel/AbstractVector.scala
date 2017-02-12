package ru.harati.scavel

import Tolerance._
import ru.harati.d2.{Vec2, Vec2d}
import ru.harati.scavel.d3.Vec3

import scala.math.Numeric.Implicits

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 *
 * Vector in any space
 */
object AbstractVector {



}

abstract class AbstractVector extends Shape {

  /**
   * Coordinates of vector
   */
  def carrier: AbstractPoint

  /**
   * Length of current vector
   */
  def length: Double

}