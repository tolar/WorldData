package cz.vaclavtolar.world_data.dto;

public class IntervalLimits {

    private long populationMin = Long.MAX_VALUE;
    private long populationMax = 0;
    private long filterPopulationMin = 0;
    private long filterPopulationMax = Long.MAX_VALUE;

    private long areaMin = Long.MAX_VALUE;
    private long areaMax = 0;
    private long filterAreaMin = 0;
    private long filterAreaMax = Long.MAX_VALUE;

    private long densityMin = Long.MAX_VALUE;
    private long densityMax = 0;
    private long filterDensityMin = 0;
    private long filterDensityMax = Long.MAX_VALUE;

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

    public long getFilterAreaMin() {
        return filterAreaMin;
    }

    public void setFilterAreaMin(long filterAreaMin) {
        this.filterAreaMin = filterAreaMin;
    }

    public long getFilterAreaMax() {
        return filterAreaMax;
    }

    public void setFilterAreaMax(long filterAreaMax) {
        this.filterAreaMax = filterAreaMax;
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

    public long getFilterDensityMin() {
        return filterDensityMin;
    }

    public void setFilterDensityMin(long filterDensityMin) {
        this.filterDensityMin = filterDensityMin;
    }

    public long getFilterDensityMax() {
        return filterDensityMax;
    }

    public void setFilterDensityMax(long filterDensityMax) {
        this.filterDensityMax = filterDensityMax;
    }

    public void resetFilter() {
        this.filterPopulationMin = populationMin;
        this.filterPopulationMax = populationMax;
        this.filterAreaMin = areaMin;
        this.filterAreaMax = areaMax;
        this.filterDensityMin = densityMin;
        this.filterDensityMax = densityMax;
    }

    public enum Filter {
        POPULATION,
        AREA,
        DENSITY
    }
}
