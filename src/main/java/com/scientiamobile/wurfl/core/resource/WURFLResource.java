package com.scientiamobile.wurfl.core.resource;

public interface WURFLResource {
   ModelDevicesSnapshot getData(String... var1);

   String getInfo();

   String getVersion();

   void release();

   String getOriginalPath();
}
