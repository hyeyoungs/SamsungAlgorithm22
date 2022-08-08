package SamsungAlgorithm22.Practice.ContactDatabase;

import java.util.*;

class UserSolution {
    static class Contact {
        String name;
        String number;
        String birthday;
        String email;
        String memo;

        public Contact(String name, String number, String birthday, String email, String memo) {
            this.name = name;
            this.number = number;
            this.birthday = birthday;
            this.email = email;
            this.memo = memo;
        }
    }

    static final int MAX_SIZE = 50000;

    static final int NAME = 0;
    static final int NUMBER = 1;
    static final int BIRTHDAY = 2;
    static final int EMAIL = 3;
    static final int MEMO = 4;

    static Contact[] contactPool;
    static int nextContact;
    static List<Map<String, Map<Contact, Contact>>> fields;

    void InitDB() {
        contactPool = new Contact[MAX_SIZE];
        for (int i = 0; i < MAX_SIZE; i++) {
            contactPool[i] = new Contact(null, null, null, null, null);
        }

        nextContact = 0;

        fields = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fields.add(new HashMap<>());
        }
    }

    Contact createContact(String name, String number, String birthday, String email, String memo) {
        Contact contact = contactPool[nextContact++];
        contact.name = name;
        contact.number = number;
        contact.birthday = birthday;
        contact.email = email;
        contact.memo = memo;

        return contact;
    }

    void Add(String name, String number, String birthday, String email, String memo) {
        Contact contact = createContact(name, number, birthday, email, memo);

        if (!fields.get(NAME).containsKey(contact.name)) {
            fields.get(NAME).put(contact.name, new HashMap<>());
        }
        fields.get(NAME).get(contact.name).put(contact, null);

        if (!fields.get(NUMBER).containsKey(contact.number)) {
            fields.get(NUMBER).put(contact.number, new HashMap<>());
        }
        fields.get(NUMBER).get(contact.number).put(contact, null);

        if (!fields.get(BIRTHDAY).containsKey(contact.birthday)) {
            fields.get(BIRTHDAY).put(contact.birthday, new HashMap<>());
        }
        fields.get(BIRTHDAY).get(contact.birthday).put(contact, null);

        if (!fields.get(EMAIL).containsKey(contact.email)) {
            fields.get(EMAIL).put(contact.email, new HashMap<>());
        }
        fields.get(EMAIL).get(contact.email).put(contact, null);

        if (!fields.get(MEMO).containsKey(contact.memo)) {
            fields.get(MEMO).put(contact.memo, new HashMap<>());
        }
        fields.get(MEMO).get(contact.memo).put(contact, null);
    }

    int Delete(int field, String str) {
        if (!fields.get(field).containsKey(str) || fields.get(field).get(str).size() == 0) {
            return 0;
        }

        List<Contact> list = new ArrayList<>(fields.get(field).get(str).keySet());
        for (int i = 0; i < list.size(); i++) {
            Contact contact = list.get(i);

            fields.get(NAME).get(contact.name).remove(contact);
            fields.get(NUMBER).get(contact.number).remove(contact);
            fields.get(BIRTHDAY).get(contact.birthday).remove(contact);
            fields.get(EMAIL).get(contact.email).remove(contact);
            fields.get(MEMO).get(contact.memo).remove(contact);
        }
        return list.size();
    }

    int Change(int field, String str, int changefield, String changestr) {
        if (!fields.get(field).containsKey(str) || fields.get(field).get(str).size() == 0) {
            return 0;
        }

        List<Contact> list = new ArrayList<>(fields.get(field).get(str).keySet());
        for (int i = 0; i < list.size(); i++) {
            Contact contact = list.get(i);

            if (changefield == NAME) {
                fields.get(changefield).get(contact.name).remove(contact);
                contact.name = changestr;
            } else if (changefield == NUMBER) {
                fields.get(changefield).get(contact.number).remove(contact);
                contact.number = changestr;
            } else if (changefield == BIRTHDAY) {
                fields.get(changefield).get(contact.birthday).remove(contact);
                contact.birthday = changestr;
            } else if (changefield == EMAIL) {
                fields.get(changefield).get(contact.email).remove(contact);
                contact.email = changestr;
            } else {
                fields.get(changefield).get(contact.memo).remove(contact);
                contact.memo = changestr;
            }

            if (!fields.get(changefield).containsKey(changestr)) {
                fields.get(changefield).put(changestr, new HashMap<>());
            }
            fields.get(changefield).get(changestr).put(contact, null);
        }

        return list.size();
    }

    Solution.Result Search(int field, String str, int returnfield) {
        Solution.Result result = new Solution.Result();
        result.count = 0;
        result.str = "";

        if (!fields.get(field).containsKey(str) || fields.get(field).get(str).size() == 0) {
            return result;
        }

        List<Contact> contacts = new ArrayList<>(fields.get(field).get(str).keySet());
        result.count = contacts.size();

        if (contacts.size() == 1) {
            if (returnfield == NAME)    result.str = contacts.get(0).name;
            else if (returnfield == NUMBER) result.str = contacts.get(0).number;
            else if (returnfield == BIRTHDAY)   result.str = contacts.get(0).birthday;
            else if (returnfield == EMAIL)  result.str = contacts.get(0).email;
            else    result.str = contacts.get(0).memo;
        }

        return result;
    }
}
