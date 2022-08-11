package SamsungAlgorithm22.Practice.SocialMedia;

import java.util.*;

class UserSolution {
    class Post implements Comparable<Post> {
        int pID;
        int timestamp;
        int like;

        public Post(int pID, int timestamp, int like) {
            this.pID = pID;
            this.timestamp = timestamp;
            this.like = like;
        }

        @Override
        public int compareTo(Post post) {
            if (currentTimeStamp - this.timestamp > 1000)   return post.timestamp - this.timestamp;
            else {
                if (this.like == post.like) return post.timestamp - this.timestamp;
                return post.like - this.like;
            }
        }
    }

    static int currentTimeStamp;
    static ArrayList<Integer>[] userFollow;
    static ArrayList<Integer>[] userPost;
    static HashMap<Integer, Post> postMap;

    public void init(int N) {
        currentTimeStamp = 0;
        userFollow = new ArrayList[N + 1];
        userPost =  new ArrayList[N + 1];
        postMap = new HashMap<>();

        for (int i = 1; i < N + 1; i++) {
            userFollow[i] = new ArrayList<>();
            userPost[i] = new ArrayList<>();
        }
    }

    public void follow(int uID1, int uID2, int timestamp) {
        userFollow[uID1].add(uID2);
    }

    public void makePost(int uID, int pID, int timestamp) {
        Post post = new Post(pID, timestamp, 0);
        postMap.put(pID, post);
        userPost[uID].add(pID);
    }

    public void like(int pID, int timestamp) {
        Post post = postMap.get(pID);
        post.like++;
    }

    public void getFeed(int uID, int timestamp, int pIDList[]) {
        currentTimeStamp = timestamp;
        PriorityQueue<Post> inTime = new PriorityQueue<>();
        PriorityQueue<Post> timeOver = new PriorityQueue<>();

        //uID 구독 중인 사용자 탐색
        ArrayList<Integer> followerIDs = userFollow[uID];
        //구독중인 사용자의 게시글 탐색
        for (int followerID : followerIDs) {
            ArrayList<Integer> followerPostIDs = userPost[followerID];
            for (int postID : followerPostIDs) {
                Post post = postMap.get(postID);
                //1000초 이내 Post (like, timestamp) 큐에 넣기
                if (calculateTime(timestamp, post.timestamp))   inTime.offer(post);
                //1000초 이후 Post (timestamp) 큐에 넣기
                else    timeOver.offer(post);
            }
        }

        //자기가 작성한 게시글 탐색
        ArrayList<Integer> myPostIDs = userPost[uID];
        for (int postID: myPostIDs) {
            Post post = postMap.get(postID);
            //1000초 이내 Post (like, timestamp) 큐에 넣기
            if (calculateTime(timestamp, post.timestamp))   inTime.offer(post);
            //1000초 이후 Post (timestamp) 큐에 넣기
            else    timeOver.offer(post);
        }

        int count = 0;
        //1000초 이내 큐에서 꺼내기 (비어있지 않거나, 10번 미만이면 꺼낸다.)
        while(!inTime.isEmpty() && count < 10) {
            Post currentPost = inTime.poll();
            pIDList[count++] = currentPost.pID;
        }
        //나머지 큐에서 꺼내기 (비어있지 않거나, count가 10번 미만이면 꺼낸다.)
        while(!timeOver.isEmpty() && count < 10) {
            Post currentPost = timeOver.poll();
            pIDList[count++] = currentPost.pID;
        }
    }

    public boolean calculateTime(int currentTimeStamp, int postTimeStamp) {
        return currentTimeStamp - postTimeStamp <= 1000;
    }
}
