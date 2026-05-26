package com.scientiamobile.wurfl.core.resource;

public interface WURFLResource {
   ModelDevicesSnapshot getData(String... params);

   String getInfo();

   String getVersion();

   void release();

   String getOriginalPath();
}
