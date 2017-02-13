package scala
import ru.harati.scavel.Axis.{AxisProjection, X, Y, Z}
import org.scalatest._
import ru.harati.scavel.d2.Vec2
import ru.harati.scavel.d3.{Point3, Vec3}
import ru.harati.scavel.Tolerance._

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
class Vectors extends FlatSpec with Matchers {

  "Vectors" should "have correct creation and comparison" in {
    Vec3(3, 2, 1) shouldBe Vec3(Point3(3, 2, 1))
    Vec2(1, 5) should be(Vec3(1, 5, 0))
    Vec3(1, 2, 3) should be(Vec3(1D, 2D, 3D))
  }

  "Vectors" should "be able to do simple algebra" in {
    val a = Vec3(3, 4, 5)
    val b = Vec3(1, 2, 3)
    (a + b) + (a - b) should be(2 * a)
  }

  "Vectors" should "determine correct dimension after mathematics" in {
    val a = Vec3(0, -7, 5)
    val b = Vec2(1, 2)
    a.dimension should be(2)
    b.dimension should be(2)
    (a + Vec3(1, 2, 3)).dimension should be(3)
  }


}
