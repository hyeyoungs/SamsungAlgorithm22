package SamsungAlgorithm22.Practice.SoldierManagement;

import java.util.*;

class UserSolution {

    TreeSet<Soldier>[] team2Soldier; //team 별로 병사들 매핑
    TreeMap<Integer, Integer> id2Team; //id로 팀 매핑
    TreeMap<Integer, Integer>[] teamScoreMap; // 기본 점수 업데이트 매핑 (원래 점수 : 새로운 점수로 관리)
    TreeMap<Integer, Integer>[] interruptScoreMap; // 중간에 끼어든 병사 점수 업데이트 누적 매핑 (ID : 점수로 관리)
    
    public void init() {
        team2Soldier = new TreeSet[5];
        id2Team = new TreeMap<>();
        teamScoreMap = new TreeMap[5];
        interruptScoreMap = new TreeMap[5];

        for (int i = 0; i < 5; i++) {
            teamScoreMap[i] = new TreeMap<>();
            team2Soldier[i] = new TreeSet<Soldier>();
            interruptScoreMap[i] = new TreeMap<>();
            //teamScoreMap 초기화
            // 각 팀마다 (1 : 1), (2 : 2), ... (5 : 5)
            for (int j = 1; j < 6; j += 1) {
                teamScoreMap[i].put(j, j);
            }
        }
    }

    public void hire(int mID, int mTeam, int mScore) {
        //끼어든 병사 점수 업데이트 누적 매핑에 데이터 추가
        interruptScoreMap[mTeam - 1].put(mID, mScore);
        //팀 : 병사 맵에 추가
        team2Soldier[mTeam - 1].add(new Soldier(mID, mScore));
        //id : 팀 맵에 추가
        id2Team.put(mID, mTeam);
    }

    public void fire(int mID) {
        //찾는 병사 팀 인덱스
        int teamIndex = id2Team.get(mID) - 1;
        TreeSet<Soldier> currentTeam = team2Soldier[teamIndex];
        // 찾고 있는 ID의 lower bound 병사 오브젝트 확인
        Soldier findSoldier = currentTeam.floor(new Soldier(mID, 0));
        //끼어든 병사 점수 업데이트 누적 매핑에서 데이터 삭제
        interruptScoreMap[teamIndex].remove(mID);
        //팀에서 병사 객체 삭제
        currentTeam.remove(findSoldier);
        // id : team 맵에서 데이터 삭제
        id2Team.remove(mID);
    }

    public void updateSoldier(int mID, int mScore) {
        //찾는 병사 팀 인덱스
        int teamIndex = id2Team.get(mID) - 1;
        //끼어든 병사 점수 업데이트 누적 매핑에서 데이터 추가
        interruptScoreMap[teamIndex].put(mID, mScore);
        // 찾고 있는 ID의 lower bound 병사 오브젝트 확인
        Soldier findSoldier = team2Soldier[teamIndex].floor(new Soldier(mID, 0));
        //데이터 수정
        findSoldier.mScore = mScore;
    }

    public void updateTeam(int mTeam, int mChangeScore) {
        int dxScore;
        ArrayList<Integer> removeList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //현재까지 누적된 점수 확인
            dxScore = teamScoreMap[mTeam - 1].get(i + 1);
            if (dxScore + mChangeScore > 5) dxScore = 5;
            else if (dxScore + mChangeScore < 1)    dxScore = 1;
            else    dxScore += mChangeScore;
            // 새로운 누적 점수 저장
            teamScoreMap[mTeam - 1].put(i + 1, dxScore);
        }

        for (Map.Entry<Integer, Integer> map : interruptScoreMap[mTeam - 1].entrySet()) {
            // 끼어든 누적 점수
            dxScore = map.getValue();
            if (dxScore + mChangeScore > 5) dxScore = 5;
            else if (dxScore + mChangeScore < 1)    dxScore = 1;
            else    dxScore += mChangeScore;

            // 같은 이전 점수에 대하여, 만약 끼어든 누적 점수와 일반 누적 점수가 같다면
            if (teamScoreMap[mTeam - 1].get(map.getValue()) == dxScore)
                // 끼어든 누적 점수를 더 이상 따로 관리하지 않아도 되므로 제거하기 위한 리스트에 추가
                removeList.add(map.getKey());
            else
                // 새로운 누적 점수 저장
                interruptScoreMap[mTeam - 1].put(map.getKey(), dxScore);

        }

        // 제거리스트에 값이 존재한다면
        for (int i = 0; i < removeList.size(); i += 1) {
            // 중간에 들어온 누적 점수를 삭제
            interruptScoreMap[mTeam - 1].remove(removeList.get(i));
        }
    }

    public int bestSoldier(int mTeam) {
        int bestID = 0;
        int bestScore = Integer.MIN_VALUE;

        //일반 누적 점수로 점수 업데이트
        for (Soldier soldier : team2Soldier[mTeam - 1]) {
            soldier.mScore = teamScoreMap[mTeam - 1].get(soldier.mScore);
        }

        // 끼어든 값들은 일반 누적 점수로 안되기 때문에 별도 처리
        for (Map.Entry<Integer, Integer> map : interruptScoreMap[mTeam - 1].entrySet()) {
            // 관리한 값으로 업데이트
            Soldier findSoldier = team2Soldier[mTeam - 1].floor(new Soldier(map.getKey(), 0));
            findSoldier.mScore = map.getValue();
        }

        // 최고 점수 갱신
        for (Soldier soldier : team2Soldier[mTeam - 1]) {
            if (bestScore == soldier.mScore) {
                if (bestID < soldier.mID) {
                    bestID = soldier.mID;
                }
            }
            if (bestScore < soldier.mScore) {
                bestID = soldier.mID;
                bestScore = soldier.mScore;
            }
        }

        // 끼어든 누적 점수 초기화
        interruptScoreMap[mTeam - 1] = new TreeMap<>();
        // 일반 누적 점수 초기화
        for (int i = 1; i < 6; i += 1)
            // 각 팀마다 (1 : 1), (2 : 2), ... (5 : 5)
            teamScoreMap[mTeam - 1].put(i, i);

        return bestID;
    }

}

class Soldier implements Comparable<Soldier> {
    int mID;
    int mScore;

    public Soldier(int mID, int mScore) {
        this.mID = mID;
        this.mScore = mScore;
    }

    public int compareTo(Soldier soldier) {
        if (this.mID > soldier.mID)
            return 1;
        else if (this.mID == soldier.mID)
            return 0;
        return -1;
    }

    public boolean equals(Soldier soldier) {
        return this.mID == soldier.mID;
    }
}
