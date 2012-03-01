package com.marczych.robocode;

import robocode.Droid;
import robocode.MessageEvent;
import robocode.TeamRobot;

public class Crushinator extends TeamRobot implements Droid {
   public void run() {
   }

   public void onMessageReceived(MessageEvent e) {
      Object obj = e.getMessage();

      if (obj instanceof EnemyScannedEvent) {
         EnemyScannedEvent event = (EnemyScannedEvent)obj;
         double x = getX(), y = getY();
         double enX = event.x, enY = event.y;
         double deltaX = x - enX, deltaY = y - enY;
         double bearing = Math.atan(deltaY / deltaX);

         goTo(bearing);
      }
   }

   private void goTo(double bearing) {
      setTurnLeft(7);
      setAhead(10);

      execute();
   }
}
