package org.example;
public class NearStations {
    private String[] nearestStation;

    public NearStations(String[] nearestStation) {
        this.nearestStation = nearestStation;
    }

    public String[] getNearestStation() {
        return nearestStation;
    }

    public void setNearestStation(String[] nearestStation) {
        this.nearestStation = nearestStation;
    }
}
