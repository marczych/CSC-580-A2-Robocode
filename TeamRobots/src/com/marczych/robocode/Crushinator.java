package com.marczych.robocode;

import robocode.Droid;
import robocode.HitRobotEvent;
import robocode.MessageEvent;
import robocode.TeamRobot;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class Crushinator extends TeamRobot implements Droid {
   public void run() {
   }

   public void onMessageReceived(MessageEvent e) {
      Object obj = e.getMessage();

      if (obj instanceof EnemyScannedEvent) {
         EnemyScannedEvent event = (EnemyScannedEvent)obj;
         double x = getX(), y = getY();
         double enX = event.x, enY = event.y;
         double diffX = enX - x, diffY = enY - y;
         double distance = Math.hypot(diffX, diffY);
         double theta = Math.toDegrees(Math.atan2(diffX, diffY));
         double turnAngle = theta - getHeading();
         double normAngle = normalize(turnAngle);

         goTo(normAngle, distance);
      }
   }

   private void goTo(double bearing, double distance) {
      turnRight(normalRelativeAngleDegrees(bearing));

      ahead(distance);
   }

   public void onHitRobot(HitRobotEvent event) {
      if (isTeammate(event.getName()) && event.isMyFault()) {
         // Don't run into your teammates!
         turnRight(90);
         ahead(50);
      } else {
         setAhead(100);
         setFire(3);
         execute();
      }
   }

   private double normalize(double angle) {
      if (angle < -180) {
         angle = angle + 360;
      } else if (angle > 180) {
         angle = angle - 360;
      }

      return angle;
   }
}
