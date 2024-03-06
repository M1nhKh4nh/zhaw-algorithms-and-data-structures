package ch.zhaw.ads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingListServer implements CommandExecutor {

    public List<Competitor> createList(String rankingText) {
        List<Competitor> competitors = new ArrayList<>();
        String[] rows = rankingText.split("\n");
        for (String row : rows) {
            String[] data = row.split(";");
            competitors.add(new Competitor(0, data[0], data[1]));
        }
        return competitors;
    }

    public String createSortedText(List<Competitor> competitorList) {
        Collections.sort(competitorList);
        StringBuilder sb = new StringBuilder();
        int rank = 0;
        for(Competitor competitor : competitorList){
            competitor.setRank(rank++);
            sb.append(String.format("%d %s %s \n",rank, competitor.getName(), competitor.getTime()));
        }
        return sb.toString();
    }

    public String createNameList(List<Competitor> competitorList) {
        competitorList.sort(new AlphaComparatorCompetitor());
        //Collections.sort(competitorList, Comparator.comparing(Competitor::getName).thenComparing(Competitor::getTime));
        StringBuilder sb = new StringBuilder();
        for(Competitor competitor : competitorList){
            sb.append(String.format("%d %s %s \n",0, competitor.getName(), competitor.getTime()));
        }

        return sb.toString();
    }

    public String execute(String rankingList) {
        List<Competitor> competitorList = createList(rankingList);
        return "Rangliste\n" + createSortedText(competitorList);
    }
}
