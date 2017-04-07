package ru.harati.scavel.d2.inline

import ru.harati.scavel.Point.Point2i
import ru.harati.scavel.d2.Point2

/**
 * Created by loki on 07.04.2017.
 */
object Point2i {
  def apply(x: Int, y: Int): Point2i = new Point2iImp(x, y)
}

final class Point2iImp(x: Int, y: Int) extends Point2[Int](x, y)
