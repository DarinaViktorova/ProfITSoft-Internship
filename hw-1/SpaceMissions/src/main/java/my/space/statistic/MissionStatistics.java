package my.space.statistic;

import javax.xml.bind.annotation.*;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MissionStatistics {

    @XmlElement
    private int totalMissions;

    @XmlElement
    private int totalSpaceships;

    @XmlElement
    private Map<String, Integer> attributeStatistics;

    public MissionStatistics() {}

    public void setAttributeStatistics(Map<String, Integer> attributeStatistics) {
        this.attributeStatistics = attributeStatistics;
    }

    public int getTotalMissions() {
        return totalMissions;
    }

    public void setTotalMissions(int totalMissions) {
        this.totalMissions = totalMissions;
    }

    public int getTotalSpaceships() {
        return totalSpaceships;
    }

    public void setTotalSpaceships(int totalSpaceships) {
        this.totalSpaceships = totalSpaceships;
    }

    public Map<String, Integer> getAttributeStatistics() {
        return attributeStatistics;
    }
}
