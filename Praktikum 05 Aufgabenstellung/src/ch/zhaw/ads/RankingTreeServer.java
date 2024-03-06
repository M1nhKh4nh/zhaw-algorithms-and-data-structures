package ch.zhaw.ads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RankingTreeServer implements CommandExecutor {

    public Tree<Competitor> createTree(String rankingText) {
        List<Competitor> competitors = createList(rankingText);
        Tree<Competitor> sortedBinaryTree = new SortedBinaryTree<>();
        for(Competitor competitor : competitors){
            sortedBinaryTree.add(competitor);
        }
        return sortedBinaryTree;
    }

    public List<Competitor> createList(String rankingText) {
        List<Competitor> competitors = new ArrayList<>();
        String[] rows = rankingText.split("\n");
        for (String row : rows) {
            String[] data = row.split(";");
            competitors.add(new Competitor(0, data[0], data[1]));
        }
        return competitors;
    }

    public String createSortedText(Tree<Competitor> competitorTree) {
        AtomicInteger rank = new AtomicInteger(1);
        StringBuilder sb = new StringBuilder();
        Traversal<Competitor> traversal = competitorTree.traversal();
        traversal.inorder(competitor -> {
            sb.append(String.format("%d %s %s\n",rank.getAndIncrement(), competitor.getName(), competitor.getTime()));
        });
        return sb.toString();
    }

    public String execute(String rankingList) {
        Tree<Competitor> competitorTree = createTree(rankingList);
        return "Rangliste (Tree)\n" + createSortedText(competitorTree);
    }

    public static void main(String[] args) {
        String rangliste = "Mueller Stefan;02:31:14\n" +
                "Marti Adrian;02:30:09\n" +
                "Kiptum Daniel;02:11:31\n" +
                "Ancay Tarcis;02:20:02\n" +
                "Kreibuhl Christian;02:21:47\n" +
                "Ott Michael;02:33:48\n" +
                "Menzi Christoph;02:27:26\n" +
                "Oliver Ruben;02:32:12\n" +
                "Elmer Beat;02:33:53\n" +
                "Kuehni Martin;02:33:36\n";
        RankingTreeServer server = new RankingTreeServer();
        System.out.println(server.execute(rangliste));
    }
}
