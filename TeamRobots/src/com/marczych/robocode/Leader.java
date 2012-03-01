package com.marczych.robocode;

import java.awt.Color;

import java.io.IOException;

import robocode.BulletHitEvent;
import robocode.HitWallEvent;
import robocode.RoundEndedEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

public class Leader extends TeamRobot {
   double width, height;
   int dist = 200;
   int fired, hit;

   public void run() {
      setColors(Color.black, Color.yellow, Color.yellow);
      setBulletColor(Color.yellow);
      turnGunLeft(90);
      fired = hit = 0;
      width = getBattleFieldWidth();
      height = getBattleFieldHeight();
      
      while (true) {
         turnRadarLeft(360);
      }
   }

   public void onScannedRobot(ScannedRobotEvent e) {
      double x = getX(), y = getY();

      try {
         double distance = e.getDistance();
         double bearing = e.getBearing();
         double enX = x + Math.cos(bearing) * distance;
         double enY = y + Math.sin(bearing) * distance;

         EnemyScannedEvent event = new EnemyScannedEvent(enX, enY);
         broadcastMessage(event);
      } catch (IOException exc) {

      }

      if (e.getBearing() + 90 < 180) {
         turnRight(e.getBearing() + 90);
      } else {
         turnLeft(360 - e.getBearing() - 90);
      }
      
      dist = (int)(200 + (Math.random()*100 - 50));
      
      fired ++;
      if (hit/(double)fired > 0.8) {
         fire(3);
      } else if (hit/(double)fired * 3 * 300/e.getDistance() < .5) {
         fire(1);
      } else {
         fire(hit/(double)fired * 3 * 300/e.getDistance());
      }

      if (getHeading() > 225 && getHeading() < 315 || getHeading() > 45 &&
       getHeading() < 135) { //horizontal pointing
         if (x/width > 0.5 && getHeading() > 180 || x/width < 0.5 &&
          getHeading() < 180) {
            ahead(dist);
         } else {
            back(dist);
         }
      } else if (y/height < 0.5 && (getHeading() > 270 || getHeading() < 90) ||
       y/height > 0.5 && !(getHeading() > 270 || getHeading() < 90)) {
         ahead(dist);
      } else {
         back(dist);
      }
   }
   
   public void onHitWall(HitWallEvent e) {
      turnLeft(e.getBearing() - 90);
      if (Math.abs(e.getBearing()) < 90) {
         back(dist);
      } else {
         ahead(dist);
      }
   }
   
   public void onBulletHit(BulletHitEvent e) {
      hit++;
   }
   
   public void onRoundEnded(RoundEndedEvent e) {
      for(;;) {
         turnLeft(30);
         setColors(Color.yellow, Color.black, Color.black);
         turnLeft(30);
         setColors(Color.black, Color.yellow, Color.yellow);
      }
   }
}                                   
