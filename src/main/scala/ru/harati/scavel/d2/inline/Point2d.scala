package ru.harati.scavel.d2.inline

import ru.harati.scavel.d2.Point2

/**
 * Created by loki on 07.04.2017.
 */
object Point2d {
  def apply(x: Double, y: Double) = new Point2d(x, y)
}

final class Point2d(x: Double, y: Double) extends Point2[Double](x, y)
