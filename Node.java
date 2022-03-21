import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Node
{
    Node parent;
    List<Node> children;
    static int nextId = 0;
    int id;
    ReadWriteLock lock;
  
    Node(Node parent){
        this.parent = parent;
        this.children = new ArrayList<>();
        this.id = nextId++;
        if (parent != null) {
            parent.children.add(this);
        }
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public String toString() {
        return String.format("Node: parent id = %d id = %d", parent == null ? -1 : parent.id, id);
    }

    Lock takeRead() {
        return lock.readLock();
    }

    Lock takeWrite() {
        return lock.writeLock();
    }
}