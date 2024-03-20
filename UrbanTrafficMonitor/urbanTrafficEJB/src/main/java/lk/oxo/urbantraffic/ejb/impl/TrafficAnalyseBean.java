package lk.oxo.urbantraffic.ejb.impl;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import lk.oxo.urbantraffic.ejb.remote.TrafficAnalyse;
import lk.oxo.urbantraffic.model.TrafficData;

import java.util.ArrayList;
import java.util.List;

@Startup
@Singleton
public class TrafficAnalyseBean implements TrafficAnalyse {
    private List<TrafficData> trafficDataList;

    @PostConstruct
    public void m() {
        trafficDataList = new ArrayList<>();
    }

    public void storeTrafficData(List<TrafficData> trafficData) {
        trafficDataList.addAll(trafficData);
    }

    @Override
    public int listSize() {
        return trafficDataList.size();
    }
}
