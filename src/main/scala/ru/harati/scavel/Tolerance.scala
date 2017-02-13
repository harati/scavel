package ru.harati.scavel

import ru.harati.scavel.d2.Vec2
import ru.harati.scavel.d3.Vec3

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Tolerance {

  implicit class DTDTol(val i: Double) extends AnyVal {
    def ~=(o: Double, e: Double = 10E-4) = (i - o).abs < e
  }

  implicit class V3DTol(val a: Vec3[Double]) extends AnyVal {
    def ~=(b: Vec3[Double]) = (a.x ~= b.x) && (a.y ~= b.y) && (a.z ~= b.z)
  }

  implicit class V2DTol(val a: Vec2[Double]) extends AnyVal {
    def ~=(b: Vec2[Double]) = (a.x ~= b.x) && (a.y ~= b.y)
  }

}