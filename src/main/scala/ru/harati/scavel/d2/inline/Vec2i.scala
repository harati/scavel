package ru.harati.scavel.d2.inline

import ru.harati.scavel.d2.Vec2

/**
 * Created by loki on 07.04.2017.
 */
object Vec2i {
  def apply(carrier: Point2i): Vec2i = new Vec2i(carrier)
  def apply(x: Int, y: Int): Vec2i = new Vec2i(Point2i(x, y))
}

final class Vec2i(carrier: Point2i) extends Vec2[Int](carrier)
