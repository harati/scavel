package ru.harati.scavel

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 *
 * Any geometrical shape
 */
trait Shape {

  /**
   * Contains given point, border excluded
   */
  def contains(f: AbstractPoint): Boolean

  /**
   * Real dimension of object
   */
  def dimension: Int

}
