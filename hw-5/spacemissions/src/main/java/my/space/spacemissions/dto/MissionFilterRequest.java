package my.space.spacemissions.dto;

public class MissionFilterRequest {
    private String planetName;
    private Integer missionYear;
    private int page;
    private int size;

    // Getters and setters

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public Integer getMissionYear() {
        return missionYear;
    }

    public void setMissionYear(Integer missionYear) {
        this.missionYear = missionYear;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
