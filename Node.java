import java.util.ArrayList;
import java.util.List;

class Node
{
    Node parent;
    List<Node> children;
    static int nextId = 0;
    int id;
  
    Node(Node parent){
        this.parent = parent;
        this.children = new ArrayList<>();
        this.id = nextId++;
        if (parent != null) {
            parent.children.add(this);
        }
    }

    @Override
    public String toString() {
        return String.format("Node: parent id = %d id = %d", parent == null ? -1 : parent.id, id);
    }
}