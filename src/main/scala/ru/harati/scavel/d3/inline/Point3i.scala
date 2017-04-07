package ru.harati.scavel.d3.inline

import ru.harati.scavel.Point.Point3i
import ru.harati.scavel.d3.Point3

/**
 * Created by loki on 07.04.2017.
 */
object Point3i {
  def apply(x: Int, y: Int, z: Int): Point3i = new Point3iImp(x, y, z)
}

final class Point3iImp(x: Int, y: Int, z: Int) extends Point3[Int](x, y, z)
