import java.util.HashMap;
import java.util.Map;
class LRUCache {
    // Define the doubly linked list node
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        Node() {} // empty constructor for dummy head and tail
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private Map<Integer, Node> cache;
    private int capacity;
    private Node head;
    private Node tail;

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // dummy haid and tail nodes to avoid edge cases
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) return -1;
        
        // Mark as recently used by moving to the front of the list
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);

        if (node == null) {
            // if Key doesn't exist, create a new node
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addNode(newNode);
            
            // Check if capacity is exceeded
            if (cache.size() > capacity) {
                // remove the least recently used item (from the tail)
                Node tailNode = popTail();
                cache.remove(tailNode.key);
            }
        } 
        else {
            // Key exists, update the value and mark as recently used
            node.value = value;
            moveToHead(node);
        }
    }

    private void addNode(Node node) {
        // Always add the new node right after the dummy head
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        // Remove an existing node from the linked list
        Node prevNode = node.prev;
        Node nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }

    private void moveToHead(Node node) {
        // Move an accessed node to the front
        removeNode(node);
        addNode(node);
    }

    private Node popTail() {
        // Pop the node right before the dummy tail
        Node res = tail.prev;
        removeNode(res);
        return res;
    }
}


