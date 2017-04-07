package ru.harati.scavel.d3.inline

import ru.harati.scavel.Point.Point3i
import ru.harati.scavel.Vec.Vec3i
import ru.harati.scavel.d3.Vec3

/**
 * Created by loki on 07.04.2017.
 */
object Vec3i {
  def apply(carrier: Point3i): Vec3i = new Vec3iImp(carrier)
  def apply(x: Int, y: Int, z: Int): Vec3i = Vec3i(Point3i(x, y, z))
}

final class Vec3iImp(carrier: Point3i) extends Vec3[Int](carrier)
