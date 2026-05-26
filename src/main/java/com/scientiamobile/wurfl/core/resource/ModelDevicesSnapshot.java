package com.scientiamobile.wurfl.core.resource;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

final class ModelDevicesSnapshot implements Serializable, Comparable<ModelDevicesSnapshot> {
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
         StringBuilder builder = (new StringBuilder()).append(this.patch ? "Patch" : "Root").append(":").append(this.info);
         if (StringUtils.isNotBlank(this.version)) {
            builder.append(":").append(this.version);
         }

         this.cachedKey = builder.toString();
      }

      return this.cachedKey;
   }

   public final ModelDevices copyDevices() {
      return new ModelDevices(this.devices);
   }

   @Override
   public final int hashCode() {
      HashCodeBuilder hashCodeBuilder;
      (hashCodeBuilder = new HashCodeBuilder(33, 55)).append(this.info).append(this.version);
      return hashCodeBuilder.toHashCode();
   }

   @Override
   public final boolean equals(Object other) {
      if (!this.getClass().isInstance(other)) {
         return false;
      }

      ModelDevicesSnapshot o = (ModelDevicesSnapshot)other;
      EqualsBuilder equalsBuilder;
      (equalsBuilder = new EqualsBuilder()).append(this.getSnapshotKey(), o.getSnapshotKey());
      return equalsBuilder.isEquals();
   }

   @Override
   public final String toString() {
      ToStringBuilder toStringBuilder;
      (toStringBuilder = new ToStringBuilder(this)).append(this.info).append(this.version);
      return toStringBuilder.toString();
   }

   final String getSmid() {
      return this.smid;
   }

   public final int compareTo(ModelDevicesSnapshot other) {
      CompareToBuilder compareToBuilder;
      (compareToBuilder = new CompareToBuilder()).append(this.info, other.info).append(this.version, other.version);
      return compareToBuilder.toComparison();
   }
}
