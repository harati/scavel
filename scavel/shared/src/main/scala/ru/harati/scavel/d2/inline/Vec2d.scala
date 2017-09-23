package ru.harati.scavel.d2.inline

import ru.harati.scavel.Point.Point2d
import ru.harati.scavel.Vec.Vec2d
import ru.harati.scavel.d2.Vec2

/**
 * Created by loki on 07.04.2017.
 */
object Vec2d {
  def apply(carrier: Point2d): Vec2d = new Vec2dImp(carrier)
  def apply(x: Double, y: Double): Vec2d = new Vec2dImp(Point2d(x, y))
}

final class Vec2dImp(carrier: Point2d) extends Vec2[Double](carrier)
