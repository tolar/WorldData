package cz.vaclavtolar.world_data.dto;

public class IntervalLimits {

    public static final float ALMOST_ZERO = 0.001f;

    private long populationMin = Long.MAX_VALUE;
    private long populationMax = 0;
    private long filterPopulationMin = 0;
    private long filterPopulationMax = 100;
    private long areaMin = Long.MAX_VALUE;
    private long areaMax = 0;
    private long densityMin = Long.MAX_VALUE;
    private long densityMax = 0;

    public long getPopulationMin() {
        return populationMin;
    }

    public void setPopulationMin(long populationMin) {
        this.populationMin = populationMin;
    }

    public long getPopulationMax() {
        return populationMax;
    }

    public void setPopulationMax(long populationMax) {
        this.populationMax = populationMax;
    }

    public long getFilterPopulationMin() {
        return filterPopulationMin;
    }

    public void setFilterPopulationMin(long filterPopulationMin) {
        this.filterPopulationMin = filterPopulationMin;
    }

    public long getFilterPopulationMax() {
        return filterPopulationMax;
    }

    public void setFilterPopulationMax(long filterPopulationMax) {
        this.filterPopulationMax = filterPopulationMax;
    }

    public long getAreaMin() {
        return areaMin;
    }

    public void setAreaMin(long areaMin) {
        this.areaMin = areaMin;
    }

    public long getAreaMax() {
        return areaMax;
    }

    public void setAreaMax(long areaMax) {
        this.areaMax = areaMax;
    }

    public long getDensityMin() {
        return densityMin;
    }

    public void setDensityMin(long densityMin) {
        this.densityMin = densityMin;
    }

    public long getDensityMax() {
        return densityMax;
    }

    public void setDensityMax(long densityMax) {
        this.densityMax = densityMax;
    }
}
