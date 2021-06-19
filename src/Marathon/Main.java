package Marathon;

import Marathon.competitor.Cat;
import Marathon.competitor.Dog;
import Marathon.competitor.Human;
import Marathon.competitor.Team;
import Marathon.course.Course;
import Marathon.course.Cross;
import Marathon.course.Wall;
import Marathon.course.Water;

public class Main {
    public static void main(String[] args) {

        Team team[] = {
                new Team("Команда А: ", new Human("Вадим"), new Human("Женя"), new Human("Лена")),
                new Team("Команда B: ", new Cat("Пушинка"), new Cat("Дымка"), new Cat("Тимоша")),
                new Team("Команда С: ", new Dog("Малыш"), new Dog("Луна"), new Dog("Тюша"))
        };

        Course course[] = {
                new Course(new Cross(20), new Wall(20), new Water(20)),
                new Course(new Cross(10), new Wall(15), new Wall(20)),
                new Course(new Cross(30), new Wall(0), new Water(0))
        };


        for (int i = 0; i < team.length; i++) {
            course[i].doIt(team[i]);
            team[i].showSuccesfulResult();
        }
    }
}
