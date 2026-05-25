package com.scientiamobile.wurfl.core.resource;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.text.StrBuilder;

final class ModelDevicesSnapshot implements Serializable, Comparable {
   private static final long serialVersionUID = 1L;
   private String info;
   private String version;
   private boolean patch;
   private ModelDevices devices;
   private transient String cachedKey;
   private String smid;

   public ModelDevicesSnapshot(String info, String version, boolean patch, ModelDevices devices, String smid) {
      this.info = info;
      this.version = version;
      this.patch = patch;
      this.devices = devices;
      this.smid = smid;
   }

   public final String getSnapshotKey() {
      if (this.cachedKey == null) {
         StrBuilder var1;
         (var1 = new StrBuilder()).append(this.patch ? "Patch" : "Root").append(":").append(this.info);
         if (StringUtils.isNotBlank(this.version)) {
            var1.append(":").append(this.version);
         }

         this.cachedKey = var1.toString();
      }

      return this.cachedKey;
   }

   public final ModelDevices copyDevices() {
      return new ModelDevices(this.devices);
   }

   public final int hashCode() {
      HashCodeBuilder var1;
      (var1 = new HashCodeBuilder(33, 55)).append(this.info).append(this.version);
      return var1.toHashCode();
   }

   public final boolean equals(Object other) {
      if (!this.getClass().isInstance(other)) {
         return false;
      }

      ModelDevicesSnapshot o = (ModelDevicesSnapshot)other;
      EqualsBuilder var2;
      (var2 = new EqualsBuilder()).append(this.getSnapshotKey(), o.getSnapshotKey());
      return var2.isEquals();
   }

   public final String toString() {
      ToStringBuilder var1;
      (var1 = new ToStringBuilder(this)).append(this.info).append(this.version);
      return var1.toString();
   }

   final String getSmid() {
      return this.smid;
   }

   public final int compareTo(Object other) {
      ModelDevicesSnapshot var2 = (ModelDevicesSnapshot)other;
      CompareToBuilder var3;
      (var3 = new CompareToBuilder()).append(this.info, var2.info).append(this.version, var2.version);
      return var3.toComparison();
   }
}

