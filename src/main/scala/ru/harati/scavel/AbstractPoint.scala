package ru.harati.scavel

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */

object AbstractPoint extends {

}

abstract class AbstractPoint extends Shape {

  /**
   * Points don't contains any other point
   */
  override def contains(f: AbstractPoint): Boolean = false

  /**
   * Consider always double
   */
  def distance(f: AbstractPoint): Double

}
