package scala
import org.scalatest._
import ru.harati.scavel.Point
import ru.harati.scavel.d2.inline.Vec2d
import ru.harati.scavel.d2.{Point2, Vec2}
import ru.harati.scavel.d3.{AABB, Point3, Vec3}

import scala.Predef.{any2stringadd => _}

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
class Tests extends FlatSpec with Matchers {

  "Vec and Point 2" should "have correct elementary operations" in {
    Vec2(0D, 0D).length shouldBe 0
    (Point2(0D, 0D) - Point2(-2, -2)) shouldBe Vec2(2D, 2D)
    Vec2(0D, 1D) * 4D shouldBe Vec2(0D, 4D)
    Vec2(0D, -4D).dimension shouldBe 1
    Vec2(-4D, 2) max Vec2(5, 3) shouldBe Vec2(5, 3)
    Vec2d(4, 4) max Vec2(6D, 3D) shouldBe Vec2(6, 4)
    Point3[Double](1, 2, 3) <+ 4D shouldBe Point3(-3, -2, -1)
    Point3[Double](1, 2, 3) +> 1 shouldBe Point3(2, 3, 4)
  }

  "Vec and Point 3" should "have correct elementary operations" in {
    Vec3(0D, 0D, 0D).length shouldBe 0
    (Point3(0D, 0D, 0D) - Point3(-2, -2, 2)) shouldBe Vec3(2D, 2D, -2D)
    Vec3(0D, 1D, 0D) * 4D shouldBe Vec3(0D, 4D, 0D)
    Vec3(0D, -4D, 0).dimension shouldBe 1
    Vec3(-4D, 2, 6) max Vec3(5, 0, 1) shouldBe Vec3(5, 2, 6)
    (Point3(0D, -4D, 1D) +> Vec3[Double](4D, 1D, 1D)) shouldBe Point3(4D, -3, 2D)
    (Point3[Int](1, 1, 1) distance Point3[Double](1, 1, 1)) shouldBe 0
    Vec3(0D, 0D, 0D).isZero
    Point2(13, 4) +> 3 shouldBe Point2(16, 7)
  }

  "Autoconversion" should "work" in {
    val p3: Point3[Double] = Point3[Int](1, 1, 1)
  }

  "AABB" should "work" in {
    val box = AABB(Point3[Double](1, 1, 1), Point3(0.5, 4, 4))
    box contain Point3[Double](0.75, 2, 2) shouldBe true //If erase Point3 type - compiler error will appear (2.12.1, 07.04.17)
    box contain Point2[Double](10, 10) shouldBe false
    box intersects AABB(Point3[Double](-1, -1, -1), Point3[Double](5, 5, 5)) shouldBe true
    box intersects AABB(Point3[Double](5, 5, 5), Point3[Double](10, 12, 17)) shouldBe false
    box +> Vec3[Double](1, 1, 1) shouldBe AABB[Double](Point3(2, 2, 2), Point3(1.5, 5, 5))
    (box center) shouldBe Point3(0.75, 2.5, 2.5)
    AABB(Point3[Double](0, 0, 0), Point3[Double](0, 0, 0)).isPoint shouldBe true
    AABB(Point3[Double](0, 0, 4), Point3[Double](0, 0, 0)).isPoint shouldBe false
    AABB(Point3[Double](4, 5, 0), Point3[Double](1, 3, 0)).isPlane shouldBe true
    AABB(Point3[Double](4, 5, 0), Point3[Double](1, 3, 1)).isPlane shouldBe false
    AABB(Point3[Double](4, 0, 0), Point3[Double](1, 0, 0)).isSegment shouldBe true
    AABB(Point3[Double](4, 0, 0), Point3[Double](1, 3, 0)).isSegment shouldBe false
  }

}
