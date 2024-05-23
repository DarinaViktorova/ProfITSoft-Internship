package my.space.spacemissions.dto;

import my.space.spacemissions.entities.Mission;
import java.util.List;

public class MissionListResponse {
    private List<Mission> list;
    private int totalPages;

    // Getters and setters

    public List<Mission> getList() {
        return list;
    }

    public void setList(List<Mission> list) {
        this.list = list;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
