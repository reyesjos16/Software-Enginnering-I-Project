import android.support.annotation.NonNull;
import com.chat.secure.reilly.securechat.FriendlyMessage;
import java.util.*;

public class Conversation {

    private String user1;
    private String user2;

    public Conversation() {
    }

    public Conversation(String user1, String user2){
        this.user1 = user1;
        this.user2 = user2;
    }

    List<FriendlyMessage> Message = new List<FriendlyMessage>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<FriendlyMessage> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] ts) {
            return null;
        }

        @Override
        public boolean add(FriendlyMessage friendlyMessage) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends FriendlyMessage> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, @NonNull Collection<? extends FriendlyMessage> collection) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public FriendlyMessage get(int i) {
            return null;
        }

        @Override
        public FriendlyMessage set(int i, FriendlyMessage friendlyMessage) {
            return null;
        }

        @Override
        public void add(int i, FriendlyMessage friendlyMessage) {

        }

        @Override
        public FriendlyMessage remove(int i) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<FriendlyMessage> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<FriendlyMessage> listIterator(int i) {
            return null;
        }

        @NonNull
        @Override
        public List<FriendlyMessage> subList(int i, int i1) {
            return null;
        }
    };

    public boolean isMember(String username) {
        if (username.equals(user1)) {
            return true;
        } else if (username.equals(user2)) {
            return true;
        } else
            return false;
    }

    public void setUser1(String user) {
        user1 = user;
    }

    public void setUser2(String user) {
        user2 = user;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }
}

