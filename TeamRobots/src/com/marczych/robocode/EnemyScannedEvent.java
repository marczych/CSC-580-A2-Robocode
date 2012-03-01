package com.marczych.robocode;

import java.io.Serializable;

public class EnemyScannedEvent implements Serializable {
   private static final long serialVersionUID = 1L;
   protected double x;
   protected double y;

   public EnemyScannedEvent(double x, double y) {
      this.x = x;
      this.y = y;
   }
}
