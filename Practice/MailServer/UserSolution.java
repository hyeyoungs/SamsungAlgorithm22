package SamsungAlgorithm22.Practice.MailServer;

import java.util.*;

public class UserSolution {

    class Mail {
        int uId;
        char[] subject;
    }

    static Mail[] mailPool;
    static int nextMail;
    static HashMap<Integer, ArrayDeque<Mail>> user2MailBox;
    static HashMap<Mail, String[]> mail2Words;
    static int MAX_MAIL_SIZE;

    public void init(int N, int K) {
        // N 명의 유저가 메일서버를 이용한다.
        // 유저 한 명당 받은 메일함에 저장할 수 있는 메일의 개수는 K 개이다.
        mailPool = new Mail[N * K];
        nextMail = 0;
        MAX_MAIL_SIZE = K;
        // uId -> Mail List
        user2MailBox = new HashMap<>();
        // Mail -> Mail subject's words
        mail2Words = new HashMap<>();

        for (int i = 0; i < N; i ++) {
            user2MailBox.put(i, new ArrayDeque<>());
        }
    }

    Mail createMail(int uId, char[] subject) {
        Mail mail = mailPool[nextMail++];
        mail.uId = uId;
        mail.subject = subject;
        return mail;
    }

    public void sendMail(char[] subject, int uId, int cnt, int[] rIds) {
        for (int i = 0; i < cnt; i++) {
            //메일 서버는 subject[] 제목의 메일을 rIDs[] 수신인들의 받은 메일함에 저장한다.
            //수신인들의 받은 메일함에 있는 메일의 개수가 K 개일 경우, 가장 오래된 메일이 삭제되고 subject[] 제목의 메일이 저장된다.
            if (user2MailBox.get(rIds[i]).size() == MAX_MAIL_SIZE) {
                Mail mail = user2MailBox.get(uId).pollLast();
                mail2Words.remove(mail);
            }

            Mail mail = createMail(rIds[i], subject);
            user2MailBox.get(uId).addFirst(mail);
            String[] words = String.valueOf(mail.subject).split(" ");
            mail2Words.put(mail, words);
        }

    }

    public int getCount(int uId) {
        return user2MailBox.get(uId).size();
    }

    public int deleteMail(int uId, char[] subject) {
        //uID 유저의 받은 메일함에서 subject[] 와 일치하는 제목을 가진 메일을 삭제하고, 삭제한 메일의 개수를 리턴한다.
        //subject[] 는 영어 소문자와 빈칸으로 구성되며, ‘\0’ 으로 끝나고 ‘\0’ 을 포함한 전체 길이는 200 이하이다.
        int count = 0;
        ArrayDeque<Mail> mailDeque = user2MailBox.get(uId);
        Iterator<Mail> it = mailDeque.iterator();

        while (it.hasNext()) {
            char[] currentSubject = it.next().subject;
            if(currentSubject.equals(subject)) {
                user2MailBox.get(uId).remove(it.next());
                mail2Words.remove(it.next());
                count += 1;
            }
        }

        return count;
    }

    public int searchMail(int uId, char[] text) {
        //uID 유저의 받은 메일함에서 메일 제목에 text[] 단어가 포함되어 있는 메일을 찾고, 찾은 메일의 개수를 리턴한다.
        //메일 제목에 포함되어 있는 단어 중 하나와 text[] 단어가 일치해야만 검색이 되며, 일부분만 같을 경우 검색이 되지 않아 찾은 메일 개수에 포함되지 않는다.
        int count = 0;
        ArrayDeque<Mail> mailDeque = user2MailBox.get(uId);
        Iterator<Mail> it = mailDeque.iterator();

        while (it.hasNext()) {
            String[] words = mail2Words.get(it.next());
            for (String word : words) {
                if(word.equals(text)) {
                    count += 1;
                    break;
                }
            }
        }
        return count;
    }

}
