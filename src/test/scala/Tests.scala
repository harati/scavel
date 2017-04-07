package scala
import org.scalatest._
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
  }

  "Vec and Point 3" should "have correct elementary operations" in {
    Vec3(0D, 0D, 0D).length shouldBe 0
    (Point3(0D, 0D, 0D) - Point3(-2, -2, 2)) shouldBe Vec3(2D, 2D, -2D)
    Vec3(0D, 1D, 0D) * 4D shouldBe Vec3(0D, 4D, 0D)
    Vec3(0D, -4D, 0).dimension shouldBe 1
    Vec3(-4D, 2, 6) max Vec3(5, 0, 1) shouldBe Vec3(5, 2, 6)
    Point3(0D, -4D, 1D) +> Vec3[Double](4D, 1D, 1D) shouldBe Point3(4D, -3, 2D)
    (Point3[Int](1, 1, 1) distance Point3[Double](1, 1, 1)) shouldBe 0
    Vec3(0D, 0D, 0D).isZero
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
  }

  /*"Vectors" should "have correct creation and comparison" in {
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

  "AABB" should "have correct center" in {
    AABB(Point3d(4, 6, 1), Point3d(0, -2, -1)).center should be(Point3d(2, 2, 0))
  }*/

}