package ru.harati.scavel.d3.inline

import ru.harati.scavel.Point.Point3d
import ru.harati.scavel.Vec.Vec3d
import ru.harati.scavel.d3.Vec3

/**
 * Created by loki on 07.04.2017.
 */
object Vec3d {
  def apply(carrier: Point3d): Vec3d = new Vec3dImp(carrier)
  def apply(x: Double, y: Double, z: Double): Vec3d = Vec3d(Point3d(x, y, z))
}

final class Vec3dImp(carrier: Point3d) extends Vec3[Double](carrier)
