package com.marczych.robocode;

import java.io.Serializable;

public class EnemyScannedEvent implements Serializable {
   private static final long serialVersionUID = 1L;

   protected String name;
   protected double x;
   protected double y;

   public EnemyScannedEvent(String name, double x, double y) {
      this.name = name;
      this.x = x;
      this.y = y;
   }
}
