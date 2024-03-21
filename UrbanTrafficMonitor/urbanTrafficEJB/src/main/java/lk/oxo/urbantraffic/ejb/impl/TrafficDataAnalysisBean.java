package lk.oxo.urbantraffic.ejb.impl;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lk.oxo.urbantraffic.ejb.remote.TrafficDataAnalysis;
import lk.oxo.urbantraffic.ejb.remote.TrafficDataStorage;
import lk.oxo.urbantraffic.model.TrafficData;

@Stateless
public class TrafficDataAnalysisBean implements TrafficDataAnalysis {
    @EJB
    TrafficDataStorage dataStorage;

    @Override
    public double calculateAverageSpeed() {
        if(dataStorage.getTrafficDataList().isEmpty())
            return 0;

        long totalSpeed = 0;
        int totalCount = 0;

        for (TrafficData trafficData : dataStorage.getTrafficDataList()) {
            totalSpeed += trafficData.getVehicleSpeed();
            totalCount++;
        }

        return (double) totalSpeed / totalCount;
    }
}
