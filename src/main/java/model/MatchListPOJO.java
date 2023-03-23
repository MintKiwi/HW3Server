package model;

import java.util.List;

public class MatchListPOJO {
    private List<Integer> matchList;

    public MatchListPOJO(List<Integer> matchList) {
        this.matchList = matchList;
    }

    public List<Integer> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<Integer> matchList) {
        this.matchList = matchList;
    }
}
