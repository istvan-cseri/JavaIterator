
public class Iterator implements Enumerator {

    Node root;
    Node lastNode;
    Node nextNode;

    Iterator(Node root){
        this.root = root;
        this.nextNode = root;
    }

    public boolean hasMoreElements() {
        return nextNode != null;
    }
            
    public Object nextElement() {
        Node next = nextNode;
        if (nextNode.children.size() > 0) {
            nextNode = nextNode.children.get(0);
            return next;
        }

        Node node = nextNode;
        while(node.parent != null)
        {
            Node last = node;
            node = node.parent;  
            
            for(int i = 0; i < node.children.size() - 1; i++)
            {
                if(last == node.children.get(i)){
                    nextNode = node.children.get(i + 1);
                    return next;
                }   
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
        n.parent.children.remove(n);
        n.parent = null;
    }
}