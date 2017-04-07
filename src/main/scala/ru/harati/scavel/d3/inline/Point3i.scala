package ru.harati.scavel.d3.inline

import ru.harati.scavel.d3.Point3

/**
 * Created by loki on 07.04.2017.
 */
object Point3i {
  def apply(x: Int, y: Int, z: Int) = new Point3i(x, y, z)
}

final class Point3i(x: Int, y: Int, z: Int) extends Point3[Int](x, y, z)
