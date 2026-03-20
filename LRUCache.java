import java.util.HashMap;
import java.util.Map;

class LRUCache {

    // Define the doubly linked list node
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        Node() {}
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private Map<Integer, Node> cache;
    private int capacity;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // Dummy head and tail nodes to avoid edge-case null checks
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1;
        }
        
        // Mark as recently used by moving to the front of the list
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);

        if (node == null) {
            // Key doesn't exist, create a new node
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addNode(newNode);
            
            // Check if capacity is exceeded
            if (cache.size() > capacity) {
                // Evict the least recently used item (from the tail)
                Node tailNode = popTail();
                cache.remove(tailNode.key);
            }
        } else {
            // Key exists, update the value and mark as recently used
            node.value = value;
            moveToHead(node);
        }
    }

    // --- Helper Methods for $O(1)$ Doubly Linked List Operations ---

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

    public static void main(String[] args) {
        // Initialize the cache with a capacity of 2
        System.out.println("Initializing LRU Cache with capacity 2...");
        LRUCache lru = new LRUCache(2);

        System.out.println("\nAction: put(1, 1)");
        lru.put(1, 1); 
        // Current state: [1] (1 is most recently used)

        System.out.println("Action: put(2, 2)");
        lru.put(2, 2); 
        // Current state: [2, 1] (2 is most recently used)

        System.out.println("Action: get(1) -> Returned: " + lru.get(1)); 
        // Expected: 1. Current state: [1, 2] (1 moves to the front)

        System.out.println("\nAction: put(3, 3) -> Cache is full, evicting least recently used (2)");
        lru.put(3, 3); 
        // Expected eviction of 2. Current state: [3, 1] 

        System.out.println("Action: get(2) -> Returned: " + lru.get(2)); 
        // Expected: -1 (Since 2 was evicted)

        System.out.println("\nAction: put(4, 4) -> Cache is full, evicting least recently used (1)");
        lru.put(4, 4); 
        // Expected eviction of 1. Current state: [4, 3]

        System.out.println("Action: get(1) -> Returned: " + lru.get(1)); 
        // Expected: -1 (Since 1 was evicted)

        System.out.println("Action: get(3) -> Returned: " + lru.get(3)); 
        // Expected: 3. Current state: [3, 4]

        System.out.println("Action: get(4) -> Returned: " + lru.get(4)); 
        // Expected: 4. Current state: [4, 3]
    }
}


