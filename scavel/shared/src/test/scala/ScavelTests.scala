package scala

import ru.harati.scavel.d2.inline.Vec2d
import ru.harati.scavel.d2.{Point2, Vec2}
import ru.harati.scavel.d3.inline.Vec3d
import ru.harati.scavel.d3.{AABB, Point3, Vec3}
import utest._

/**
  * Creation date: 17.08.2016
  * Copyright (c) harati
  */
object ScavelTests extends TestSuite {

  val tests = Tests {
    "Vec and Point 2 should have correct elementary operations" - {
      assert(
        Vec2(0D, 0D).length == 0,
        (Point2(0D, 0D) - Point2(-2, -2)) == Vec2(2D, 2D),
        Vec2(0D, 1D) * 4D == Vec2(0D, 4D),
        Vec2(0D, -4D).dimension == 1,
        (Vec2(-4D, 2) max Vec2(5, 3)) == Vec2(5, 3),
        (Vec2d(4, 4) max Vec2(6D, 3D)) == Vec2(6, 4),
        (Point3[Double](1, 2, 3) <+ 4D) == Point3(-3, -2, -1),
        (Point3[Double](1, 2, 3) +> 1) == Point3(2, 3, 4),
        (Vec2d(3, -3) - Vec2d(1, 0)) == Vec2d(2, -3)
      )
    }

    "Vec and Point 3 should have correct elementary operations" - {
      assert(
        Vec3(0D, 0D, 0D).length == 0,
        (Point3(0D, 0D, 0D) - Point3(-2, -2, 2)) == Vec3(2D, 2D, -2D),
        Vec3(0D, 1D, 0D) * 4D == Vec3(0D, 4D, 0D),
        Vec3(0D, -4D, 0).dimension == 1,
        (Vec3(-4D, 2, 6) max Vec3(5, 0, 1)) == Vec3(5, 2, 6),
        (Point3(0D, -4D, 1D) +> Vec3[Double](4D, 1D, 1D)) == Point3(4D, -3, 2D),
        (Point3[Int](1, 1, 1) distance Point3[Double](1, 1, 1)) == 0,
        Vec3(0D, 0D, 0D).isZero,
        Point2(13, 4) +> 3 == Point2(16, 7),
        Vec3d(3, 3, 3) ++ Vec3d(3D, 2D, 2D) == Vec3d(6, 5, 5),
        Vec3d(3, 3, 3) - Vec3d(3D, 2D, 2D) == Vec3d(0, 1, 1),
        Vec3d(4, 4, 4) / 2D == Vec3d(2, 2, 2)
      )
    }

    "Autoconversion should work" - {
      val p3: Point3[Double] = Point3[Int](1, 1, 1)
    }

    "AABB should work" - {
      val box = AABB(Point3[Double](1, 1, 1), Point3(0.5, 4, 4))
      assert(
        box contain Point3(0.75, 2, 2),
        !(box contain Point2[Double](10, 10)),
        box intersects AABB(Point3[Double](-1, -1, -1), Point3[Double](5, 5, 5)),
        !(box intersects AABB(Point3[Double](5, 5, 5), Point3[Double](10, 12, 17))),
        box +> Vec3[Double](1, 1, 1) == AABB[Double](Point3(2, 2, 2), Point3(1.5, 5, 5)),
        (box center) == Point3(0.75, 2.5, 2.5),
        AABB(Point3[Double](0, 0, 0), Point3[Double](0, 0, 0)).isPoint,
        !AABB(Point3[Double](0, 0, 4), Point3[Double](0, 0, 0)).isPoint,
        AABB(Point3[Double](4, 5, 0), Point3[Double](1, 3, 0)).isPlane,
        !AABB(Point3[Double](4, 5, 0), Point3[Double](1, 3, 1)).isPlane,
        AABB(Point3[Double](4, 0, 0), Point3[Double](1, 0, 0)).isSegment,
        !AABB(Point3(4, 0, 0), Point3(1, 3, 0)).isSegment
      )
    }
  }

}
