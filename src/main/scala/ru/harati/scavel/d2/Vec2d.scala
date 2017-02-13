package ru.harati.scavel.d2

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Vec2d {

  def apply(p: Point2d): Vec2d = new Vec2d(p)
  def apply(x: Double, y: Double): Vec2d = Vec2d(Point2d(x, y))

  implicit class extendDouble(val f: Double) extends AnyVal {
    def *(c: Vec2d) = c * f
  }

}

class Vec2d(carrier: Point2d) extends Vec2[Double](carrier) {

  override def +(c: Vec2[Double]): Vec2d = Vec2d(x + c.x, y + c.y)
  override def -(c: Vec2[Double]): Vec2d = Vec2d(x - c.x, y - c.y)
  override def *(f: Double): Vec2d = Vec2d(x * f, y * f)
  override def unary_- : Vec2d = -1 * this

}
