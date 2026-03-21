package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.exc.DeviceNotDefinedException;
import com.scientiamobile.wurfl.core.exc.GroupNotDefinedException;
import com.scientiamobile.wurfl.core.resource.exc.DeviceNotInModelException;
import com.scientiamobile.wurfl.core.resource.exc.GenericNotDefinedException;
import com.scientiamobile.wurfl.core.resource.exc.OrphanHierarchyException;
import com.scientiamobile.wurfl.core.utils.CollectionFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.collections.iterators.ReverseListIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultWURFLModel implements WURFLModel {
  private Map a;
  
  private List b;
  
  private final Map c = CollectionFactory.createConcurrentHashMap();
  
  private final Set d = new HashSet();
  
  private ModelDevice e;
  
  private String f;
  
  private String g;
  
  private Integer h;
  
  private final Logger i = LoggerFactory.getLogger(getClass());
  
  public DefaultWURFLModel(WURFLResource paramWURFLResource, String... paramVarArgs) {
    this(paramWURFLResource, new WURFLResources(), paramVarArgs);
  }
  
  public DefaultWURFLModel(WURFLResource paramWURFLResource, WURFLResources paramWURFLResources, String... paramVarArgs) {
    a(paramWURFLResource, paramWURFLResources, paramVarArgs);
  }
  
  private final synchronized void a(WURFLResource paramWURFLResource, WURFLResources paramWURFLResources, String... paramVarArgs) {
    Validate.notNull(paramWURFLResource, "The root resource must be not null.");
    if (paramWURFLResources == null)
      paramWURFLResources = new WURFLResources(); 
    this.c.clear();
    this.e = null;
    this.h = null;
    c c = paramWURFLResource.getData(paramVarArgs);
    this.f = c.a();
    this.g = c.c();
    ModelDevices modelDevices1 = c.b();
    ModelDevices modelDevices2 = new ModelDevices(modelDevices1);
    this.b = modelDevices1.getDeviceIdsByInsertionOrder();
    d.a(modelDevices2);
    a(paramWURFLResources, modelDevices2, paramVarArgs);
    if (paramWURFLResources.size() == 0)
      a(modelDevices2); 
  }
  
  private final synchronized void a(WURFLResources paramWURFLResources, ModelDevices paramModelDevices, String... paramVarArgs) {
    byte b1 = 0;
    byte b2 = 0;
    for (byte b3 = 0; paramWURFLResources != null && b3 < paramWURFLResources.size(); b3++) {
      ModelDevices modelDevices;
      c c;
      d.a(modelDevices = (c = paramWURFLResources.get(b3).getData(paramVarArgs)).b(), paramModelDevices);
      StrBuilder strBuilder;
      (strBuilder = new StrBuilder()).append(StringUtils.defaultString(this.f)).append("; ").append(c.a());
      paramModelDevices = e.a(paramModelDevices, modelDevices);
      this.b = paramModelDevices.getDeviceIdsByInsertionOrder();
      d.a(paramModelDevices);
      this.f = strBuilder.toString();
    } 
    d.a(paramModelDevices);
    this.a = CollectionFactory.createConcurrentHashMap();
    this.a.putAll(paramModelDevices.getDevicesById());
    a(paramModelDevices);
    Iterator<ModelDevice> iterator = this.a.values().iterator();
    while (iterator.hasNext()) {
      ModelDevice modelDevice;
      if ((modelDevice = iterator.next()).isActualDeviceRoot()) {
        b2++;
      } else {
        String str;
        if ((str = modelDevice.getFallBack()).equals("generic") || str.equals("generic_mobile"))
          this.d.add(modelDevice.getID()); 
      } 
      if (getDeviceAncestor(modelDevice).getID().equals("generic"))
        b1++; 
    } 
    this.e = (ModelDevice)this.a.get("generic");
    if (this.i.isInfoEnabled())
      this.i.info("WURFLModel version: " + this.f + "; devices: " + this.a.size() + " root devices: " + b2 + "; families: " + this.d.size() + "; generic devices: " + b1); 
  }
  
  private static void a(ModelDevices paramModelDevices) {
    if (paramModelDevices == null)
      return; 
    Iterator<ModelDevice> iterator = paramModelDevices.getDevices().iterator();
    while (iterator.hasNext()) {
      ModelDevice modelDevice;
      if ((modelDevice = iterator.next()).getFallBack() != null && paramModelDevices.containsId(modelDevice.getFallBack()))
        modelDevice.setAncestor(paramModelDevices.getById(modelDevice.getFallBack())); 
    } 
  }
  
  public String getVersion() {
    return this.f;
  }
  
  public ModelDevice getDeviceById(String paramString) {
    Validate.notEmpty(paramString, "The id must be not null");
    ModelDevice modelDevice;
    if ((modelDevice = (ModelDevice)this.a.get(paramString)) == null)
      throw new DeviceNotDefinedException(paramString); 
    return modelDevice;
  }
  
  public Set getDevices(Set paramSet) {
    Validate.notNull(paramSet, "The devicesIds must be not null Set");
    Validate.noNullElements(paramSet, "The devicesIds must not containing null elements");
    Validate.allElementsOfType(paramSet, String.class, "The devicesIds must containing right devicesById id");
    HashSet<ModelDevice> hashSet = new HashSet();
    Iterator<String> iterator = paramSet.iterator();
    while (iterator.hasNext())
      hashSet.add(getDeviceById(iterator.next())); 
    return hashSet;
  }
  
  public Set getAllDevices() {
    TreeSet<?> treeSet;
    (treeSet = new TreeSet(a.a)).addAll(this.a.values());
    return treeSet;
  }
  
  public List getAllDevicesAsList() {
    ArrayList arrayList = new ArrayList(this.b.size());
    Iterator iterator = this.b.iterator();
    while (iterator.hasNext())
      arrayList.add(this.a.get(iterator.next())); 
    return arrayList;
  }
  
  public Set getAllDevicesId() {
    HashSet<?> hashSet;
    (hashSet = new HashSet()).addAll(this.a.keySet());
    return hashSet;
  }
  
  public List getDeviceHierarchy(ModelDevice paramModelDevice) {
    Validate.notNull(paramModelDevice, "The device must be not null");
    LinkedList<ModelDevice> linkedList = new LinkedList();
    while (!"generic".equals(paramModelDevice.getID())) {
      linkedList.addFirst(paramModelDevice);
      paramModelDevice = getDeviceFallback(paramModelDevice);
    } 
    linkedList.addFirst(paramModelDevice);
    return linkedList;
  }
  
  public ModelDevice getDeviceFallback(ModelDevice paramModelDevice) {
    ModelDevice modelDevice;
    Validate.notNull(paramModelDevice, "The device must be not null");
    try {
      modelDevice = getDeviceById(paramModelDevice.getFallBack());
    } catch (DeviceNotDefinedException deviceNotDefinedException) {
      throw new DeviceNotInModelException(paramModelDevice);
    } 
    return modelDevice;
  }
  
  public ModelDevice getDeviceAncestor(ModelDevice paramModelDevice) {
    Validate.notNull(paramModelDevice, "The device must be not null");
    String str1 = paramModelDevice.getID();
    String str3;
    if ((str3 = (String)this.c.get(str1)) != null)
      return getDeviceById(str3); 
    ModelDevice modelDevice2 = paramModelDevice;
    ModelDevice modelDevice1 = a();
    List list = getDeviceHierarchy(paramModelDevice);
    ReverseListIterator reverseListIterator = new ReverseListIterator(list);
    while (reverseListIterator.hasNext() && !modelDevice2.isActualDeviceRoot() && !modelDevice1.equals(modelDevice2))
      modelDevice2 = (ModelDevice)reverseListIterator.next(); 
    if (!modelDevice2.isActualDeviceRoot() && !modelDevice1.equals(modelDevice2))
      throw new RuntimeException("Hierarchy is invalid"); 
    String str2 = modelDevice2.getID();
    this.c.put(str1, str2);
    return modelDevice2;
  }
  
  public boolean isDeviceDefined(String paramString) {
    Validate.notEmpty(paramString, "The deviceId must be not null");
    return this.a.containsKey(paramString);
  }
  
  public int size() {
    return this.a.size();
  }
  
  public Set getAllGroups() {
    return a().getGroups();
  }
  
  public boolean isGroupDefined(String paramString) {
    Validate.notEmpty(paramString, "The groupId must be not null");
    return a().defineGroup(paramString);
  }
  
  public String getGroupByCapability(String paramString) {
    Validate.notEmpty(paramString, "The capabilityName must be not null");
    ModelDevice modelDevice;
    if (!(modelDevice = a()).defineCapability(paramString))
      throw new CapabilityNotDefinedException(paramString); 
    return modelDevice.getGroupForCapability(paramString);
  }
  
  public void reload(WURFLResource paramWURFLResource, WURFLResources paramWURFLResources, String... paramVarArgs) {
    this.i.info("about to reload the WURFL Model");
    a(paramWURFLResource, paramWURFLResources, paramVarArgs);
  }
  
  public void applyPatches(WURFLResources paramWURFLResources, String... paramVarArgs) {
    a(paramWURFLResources, new ModelDevices(this.a), paramVarArgs);
  }
  
  public Set getAllCapabilities() {
    ModelDevice modelDevice = a();
    return new HashSet(modelDevice.getCapabilities().keySet());
  }
  
  public Integer getCapabilityCount() {
    if (this.h == null || this.h.intValue() == 0)
      this.h = Integer.valueOf(getAllCapabilities().size()); 
    return this.h;
  }
  
  public boolean isCapabilityDefined(String paramString) {
    Validate.notEmpty(paramString, "The capability must be not null");
    return a().defineCapability(paramString);
  }
  
  public Set getCapabilitiesForGroup(String paramString) {
    Validate.notEmpty(paramString, "The groupId must be not null");
    ModelDevice modelDevice;
    if (!(modelDevice = a()).defineGroup(paramString))
      throw new GroupNotDefinedException(paramString); 
    return modelDevice.getCapabilitiesNamesForGroup(paramString);
  }
  
  public ModelDevice getDeviceWhereCapabilityIsDefined(ModelDevice paramModelDevice, String paramString) {
    Validate.notNull(paramModelDevice, "The rootDevice must be not null");
    Validate.notEmpty(paramString, "The name must be not null");
    List list = getDeviceHierarchy(paramModelDevice);
    ReverseListIterator<ModelDevice> reverseListIterator = new ReverseListIterator(list);
    while (reverseListIterator.hasNext()) {
      ModelDevice modelDevice;
      if ((modelDevice = reverseListIterator.next()).defineCapability(paramString))
        return modelDevice; 
      if ("generic_mobile".equals(modelDevice.getID()))
        throw new CapabilityNotDefinedException(paramString); 
    } 
    throw new RuntimeException(new OrphanHierarchyException(list));
  }
  
  public Set getRootDevicesIds() {
    HashSet<String> hashSet = new HashSet();
    Iterator<ModelDevice> iterator = this.a.values().iterator();
    while (iterator.hasNext()) {
      ModelDevice modelDevice;
      if ((modelDevice = iterator.next()).isActualDeviceRoot())
        hashSet.add(modelDevice.getID()); 
    } 
    return hashSet;
  }
  
  private ModelDevice a() {
    if (this.e != null)
      return this.e; 
    ModelDevice modelDevice;
    if ((modelDevice = (ModelDevice)this.a.get("generic")) == null && this.a.size() > 0)
      throw new RuntimeException(new GenericNotDefinedException()); 
    this.e = modelDevice;
    return modelDevice;
  }
  
  public String toString() {
    ToStringBuilder toStringBuilder;
    (toStringBuilder = new ToStringBuilder(this)).append(this.f);
    return toStringBuilder.toString();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\DefaultWURFLModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */