package ru.harati.scavel.d3.inline

import ru.harati.scavel.d3.Point3

/**
 * Created by loki on 07.04.2017.
 */
object Point3d {
  def apply(x: Double, y: Double, z: Double) = new Point3d(x, y, z)
}

final class Point3d(x: Double, y: Double, z: Double) extends Point3[Double](x, y, z)
