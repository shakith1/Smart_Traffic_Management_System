package lk.oxo.urbantraffic.ejb.impl;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import lk.oxo.urbantraffic.ejb.remote.TrafficDataStorage;
import lk.oxo.urbantraffic.model.TrafficData;

import java.util.ArrayList;
import java.util.List;

@Startup
@Singleton
public class TrafficDataStorageBean implements TrafficDataStorage {
    private List<TrafficData> trafficDataList;

    @PostConstruct
    public void init() {
        trafficDataList = new ArrayList<>();
    }

    @Override
    public void storeTrafficData(List<TrafficData> trafficData) {
        trafficDataList.addAll(trafficData);
    }

    @Override
    public int listSize() {
        return trafficDataList.size();
    }

    @Override
    public List<TrafficData> getTrafficDataList() {
        return trafficDataList;
    }
}
