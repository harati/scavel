package ru.harati.scavel

import scala.math.Numeric.Implicits._

/**
 * Creation date: 18.08.2016
 * Copyright (c) harati
 *
 * Various utils
 */
object Misc {

  /**
   * Average between two Numeric, fuck yea : D
   */
  @inline def average[T](a: T, b: T)(implicit space: Numeric[T]) = {
    val min = space.min(a, b)
    val max = space.max(a, b)
    var buf = min
    do {
      buf = buf + buf
      if (space.gt(buf, max)) buf = buf - max
      else buf - space.one
    } while (space.gt((buf + buf - min - max).abs, space.one))
    buf
  }

}
