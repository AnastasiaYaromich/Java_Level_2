package Marathon.course;

import Marathon.competitor.Competitor;
import Marathon.competitor.Team;

public class Course {
    private Obstacle[] obstacles;

    public Course(Obstacle... obstacles) {
        this.obstacles = obstacles;

    }

    public void doIt(Team team) {
        for (Competitor comp: team.getCompetitors()) {
            for (Obstacle obstacle: obstacles) {
                obstacle.doIt(comp);
                if(!comp.isOnDistance()) break;
            }
        }
    }
}
