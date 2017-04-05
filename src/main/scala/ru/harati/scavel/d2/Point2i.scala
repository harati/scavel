package ru.harati.scavel.d2

import ru.harati.scavel.d3.Point3d

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Point2i {

  def apply(x: Int, y: Int) = new Point2i(x, y)

}

final class Point2i(x: Int, y: Int) extends Point2[Int](x, y) {

}
