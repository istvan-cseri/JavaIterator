import java.util.Stack;
import java.util.concurrent.locks.Lock;

public class Iterator implements Enumerator {

    Node root;
    Node lastNode;
    Node nextNode;
    Stack<Integer> path = new Stack<>();

    Iterator(Node root){
        this.root = root;
        this.nextNode = root;
    }

    public boolean hasMoreElements() {
        return nextNode != null;
    }
            
    public Object nextElement() {
        Node next = nextNode;
        Lock lock = next.takeRead();
        System.out.println(String.format("Locking node %d", next.id));
        lock.lock();
        if (nextNode.children.size() > 0) {
            nextNode = nextNode.children.get(0);
            path.push(0);
            return next;
        }
        else {
            System.out.println(String.format("Un-locking node %d", next.id));
            lock.unlock();
        }

        while (!path.empty()) {
            int ordinal = path.pop();
            nextNode = nextNode.parent;
            if (ordinal + 1 < nextNode.children.size()) {
                path.push(ordinal + 1);
                nextNode = nextNode.children.get(ordinal + 1);
                return next;
            }
            else {
                System.out.println(String.format("Un-locking node %d", nextNode.id));
                nextNode.takeRead().unlock();
            }
        }
        nextNode = null;
        return next;   
    }   

    /**
     * Delete a node from the tree
     * @param n the Node to delete
     */
    public void delete(Node n) {
//        Lock lock = n.parent.takeWrite();
        n.parent.children.remove(n);
        n.parent = null;
//        lock.unlock();
    }
}