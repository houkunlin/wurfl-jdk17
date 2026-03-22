package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.BadCapabilityGroupException;
import com.scientiamobile.wurfl.core.resource.exc.CircularHierarchyException;
import com.scientiamobile.wurfl.core.resource.exc.GenericNotDefinedException;
import com.scientiamobile.wurfl.core.resource.exc.InexistentCapabilityException;
import com.scientiamobile.wurfl.core.resource.exc.InexistentGroupException;
import com.scientiamobile.wurfl.core.resource.exc.OrphanHierarchyException;
import com.scientiamobile.wurfl.core.resource.exc.RedefinedDeviceException;
import com.scientiamobile.wurfl.core.resource.exc.UserAgentNotUniqueException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

final class d {
  public static void a(ModelDevices paramModelDevices) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    HashSet<String> hashSet = new HashSet<>();
    ModelDevices modelDevices = paramModelDevices;
    if (modelDevices == null)
      throw new AssertionError("devices is null");
    if (!modelDevices.containsId("generic"))
      throw new GenericNotDefinedException();
    Iterator<ModelDevice> iterator = paramModelDevices.iterator();
    while (iterator.hasNext()) {
      ModelDevice modelDevice1;
      HashMap<Object, Object> hashMap1 = hashMap;
      ModelDevice modelDevice2 = iterator.next();
      ModelDevice modelDevice3 = modelDevice2;
      if (modelDevice3 == null)
        throw new AssertionError("device is null");
      if (hashMap1.containsKey(modelDevice3.getUserAgent())) {
        modelDevice1 = (ModelDevice)hashMap1.get(modelDevice3.getUserAgent());
        throw new UserAgentNotUniqueException(modelDevice3, modelDevice3.getUserAgent(), modelDevice1);
      }
      hashMap.put(modelDevice2.getUserAgent(), modelDevice2);
      a(modelDevice2, (ModelDevices)modelDevice1, hashSet);
      hashSet.add(modelDevice2.getID());
      a(modelDevice2, (ModelDevices)modelDevice1);
      b(modelDevice2, (ModelDevices)modelDevice1);
    }
  }

  private static void a(ModelDevice paramModelDevice, ModelDevices paramModelDevices, Set paramSet) {
    if (paramModelDevice == null)
      throw new AssertionError("device is null");
    if (paramModelDevices == null)
      throw new AssertionError("devices is null");
    if (paramModelDevice.getID().equals("my_dev_id"))
      System.out.println("VERIFYING device with ID: " + paramModelDevice.getID());
    ArrayList<ModelDevice> arrayList = new ArrayList<>(10);
    String str = paramModelDevice.getID();
    if (StringUtils.isEmpty(str))
      throw new AssertionError();
    arrayList.add(paramModelDevices.getById(str));
    while (!"generic".equals(str)) {
      LinkedList linkedList;
      str = paramModelDevices.getById(str).getFallBack();
      if (StringUtils.isEmpty(str))
        throw new AssertionError();
      if (paramSet.contains(str))
        return;
      if (!paramModelDevices.containsId(str))
        throw new OrphanHierarchyException(arrayList);
      int i;
      if ((i = arrayList.indexOf(paramModelDevices.getById(str))) != -1) {
        linkedList = new LinkedList(arrayList.subList(i, arrayList.size()));
        throw new CircularHierarchyException(linkedList);
      }
      arrayList.add(paramModelDevices.getById((String)linkedList));
    }
  }

  private static void a(ModelDevice paramModelDevice, ModelDevices paramModelDevices) {
    if (paramModelDevice == null)
      throw new AssertionError("device is null");
    if (paramModelDevices == null)
      throw new AssertionError("devices is null");
    ModelDevice modelDevice = paramModelDevices.getById("generic");
    Set set2 = paramModelDevice.getGroups();
    Set set1 = modelDevice.getGroups();
    for (String str : set2) {
      if (!set1.contains(str))
        throw new InexistentGroupException(paramModelDevice, str);
    }
  }

  private static void b(ModelDevice paramModelDevice, ModelDevices paramModelDevices) {
    if (paramModelDevice == null)
      throw new AssertionError("device is null");
    if (paramModelDevices == null)
      throw new AssertionError("devices is null");
    if (!paramModelDevices.containsId("generic"))
      throw new AssertionError("device do not containing generic");
    ModelDevice modelDevice;
    Map map = (modelDevice = paramModelDevices.getById("generic")).getCapabilities();
    for (String str1 : paramModelDevice.getCapabilities().keySet()) {
      if (!map.containsKey(str1))
        throw new InexistentCapabilityException(paramModelDevice, str1);
      String str2 = paramModelDevice.getGroupForCapability(str1);
      String str3 = modelDevice.getGroupForCapability(str1);
      if (!str2.equals(str3))
        throw new BadCapabilityGroupException(paramModelDevice, str1, str2, str3);
    }
  }

  public static void a(ModelDevices paramModelDevices1, ModelDevices paramModelDevices2) {
    Iterator<ModelDevice> iterator = paramModelDevices1.getDevices().iterator();
    while (iterator.hasNext()) {
      ModelDevice modelDevice1;
      String str1 = (modelDevice1 = iterator.next()).getUserAgent();
      String str2 = modelDevice1.getID();
      String str3 = modelDevice1.getFallBack();
      ModelDevice modelDevice2;
      if ((modelDevice2 = paramModelDevices2.getById(str2)) == null)
        return;
      if (StringUtils.isEmpty(str3))
        throw new RedefinedDeviceException("Patched device with id " + str2 + "does not provide a value for fall_back");
      if (!str1.equals(modelDevice2.getUserAgent()))
        throw new RedefinedDeviceException(modelDevice1, modelDevice2, "user agent");
      if (!str3.equals(modelDevice2.getFallBack()))
        throw new RedefinedDeviceException(modelDevice1, modelDevice2, "fall_back");
    }
  }

  static {
    LoggerFactory.getLogger(d.class);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
