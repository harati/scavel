package ru.harati.scavel.d2

import ru.harati.scavel.d3.Point3

/**
 * Creation date: 29.08.2016
 * Copyright (c) harati
 */
object Point3i {

  def apply(x: Int, y: Int, z: Int) = new Point3i(x, y, z)

}

class Point3i(x: Int, y: Int, z: Int) extends Point3[Int](x, y, z)
