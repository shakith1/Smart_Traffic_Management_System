package lk.oxo.urbantraffic.ejb.remote;

import lk.oxo.urbantraffic.model.TrafficData;

import java.util.List;

public interface TrafficDataStorage {
    void storeTrafficData(List<TrafficData> trafficData);
    int listSize();
    List<TrafficData> getTrafficDataList();
}
