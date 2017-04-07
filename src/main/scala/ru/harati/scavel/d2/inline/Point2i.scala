package ru.harati.scavel.d2.inline

import ru.harati.scavel.d2.Point2

/**
 * Created by loki on 07.04.2017.
 */
object Point2i {
  def apply(x: Int, y: Int) = new Point2i(x, y)
}

final class Point2i(x: Int, y: Int) extends Point2[Int](x, y)
