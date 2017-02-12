package ru.harati.scavel.d3

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Vec3d {

  def apply(p: Point3d): Vec3d = new Vec3d(p)
  def apply(x: Double, y: Double, z: Double): Vec3d = Vec3d(Point3d(x, y, z))

  implicit class extendDouble(val f: Double) extends AnyVal {
    def *(c: Vec3d) = c * f
  }

}

class Vec3d(carrier: Point3d) extends Vec3[Double](carrier) {

  override def +(c: Vec3[Double]): Vec3d = Vec3d(x + c.x, y + c.y, z + c.z)
  override def -(c: Vec3[Double]): Vec3d = Vec3d(x - c.x, y - c.y, z - c.z)
  override def *(f: Double): Vec3d = Vec3d(x * f, y * f, z * f)
  override def unary_- : Vec3d = -1 * this

}
