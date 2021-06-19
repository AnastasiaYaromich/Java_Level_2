package Marathon.competitor;

import Marathon.competitor.Competitor;

public class Team {
    private String name;
    private Competitor competitors[];

    public Competitor[] getCompetitors() {
        return competitors;
    }


    public Team(String name, Competitor... competitors) {
        this.name = name;
        this.competitors = competitors;
    }

    public void showSuccesfulResult() {
        System.out.println("Успешный результат: ");
        for (Competitor o: competitors) {
            if(o.isOnDistance()) {
                o.info();
            }
        }
    }
}
