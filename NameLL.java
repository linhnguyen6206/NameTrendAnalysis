public class NameLL {
    private Node head;
    private Node tail;
    private int size;

    public NameLL() {
        head = new Node(null);
        tail = new Node(null);
        size = 0;

        head.next = tail;
        tail.prev = head;
    }
    
    private class Node {
        public Name data;
        public Node next;
        public Node prev;

        public Node(Name data) {
            this.next = null;
            this.prev = null;
            this.data = data;
        }

        public Node(Name data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (size == 0)
            return true;
        else
            return false;
    }

    public Name first() {
        if (isEmpty()) return null;
        return head.next.data;
    }

    public Name last() {
        if (isEmpty()) return null;
        Node current = head.next;
        while (current.next != tail) {
            current = current.next;
        }
        return current.data;
    }
    
    public int index(String name) {
        Node current = head.next;
        int index = 1;
        while (current != tail) {
            if (current.data.getName().equals(name)) {
                return index;
            }
            current = current.next;
            index++;
    }
    return -1;
    }
    
    public void insertFirst(Name name) {
        Node n = new Node(name);
        n.next = head.next;
        n.prev = head;
        head.next.prev = n;
        head.next = n;
        size++;
    }

    public void insertBack(Name name) {
        Node n = new Node(name);
        Node last = tail.prev;
        n.next = tail;
        n.prev = last;
        last.next = n;
        tail.prev = n;
        size++;
    }

    public void insertBefore(Name name, Node n) {
        Node newData = new Node(name);
        Node oldPrev = n.prev;

        newData.prev = oldPrev;
        newData.next = n;

        oldPrev.next = newData;
        n.prev = newData;
        size++;
    }

    public void insertSortedAlpha(Name name) {
        Node current = head.next;

        while (current != tail) {
            if (name.getName().compareTo(current.data.getName()) < 0) {
                insertBefore(name, current);
                return;
            }
            if (name.getName().compareTo(current.data.getName()) == 0) {
                return;
            }
            current = current.next;
        }
        insertBack(name);
    }
       
    public int getTotalCount(int year) { // calculate total count for a given year by summing all names
        int total = 0;
        Node current = head.next;
        while (current != tail) {
            Name n = current.data;
            if (n != null) {
                if (year >= 0 && year < n.getYearCount()) {
                    total += n.getCount(year);
                }
            }
            current = current.next;
        }
        return total;
    }

    public Name findName(String name) {
        Node current = head.next;
        while (current != tail) {
            if (current.data.getName().equals(name)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public int getCount(String name, int year) {
        Node current = head.next;

        while (current != tail) {
            if(current.data.getName().equals(name)) {
                return current.data.getCount(year);
            }
            current = current.next;
        }
    return 0;
    }

    public int getTotal() { // calculates the total count over all years for all names in the list
        int total = 0;
        Node current = head.next;
        
        while (current != tail) {
            Name n = current.data;
            for (int year = 0; year < n.getYearCount(); year++) {
                total += n.getCount(year);
            }
            current = current.next;
        }
        return total;
    }

    // get percentage of a specific name in a specific year by dividing the count of that name by the total of that year
    public double getPercentage(String name, int year) {
        Node current = head.next;
        int count = 0;
        int total = getTotalCount(year);

        while (current != tail) {
            if (current.data.getName().equals(name)) {
                count = current.data.getCount(year);
                break;
            }
            current = current.next;
        }
        if (total == 0) return 0.0;

        return ((double) count/total);
    }

    public double[] yearStats(String name, int year) {
        Node current = head.next;

        while (current != tail) {

            if (current.data.getName().equals(name)) {
                double[] stats = new double[3];

                stats[0] = (double) current.data.getRank(year);
                stats[1] = (double) current.data.getCount(year);
                // stats[2] calculation depends on the corrected total count below
                stats[2] = (double) getPercentage(name, year);

                return stats;
            }
            current = current.next;
        }
        return null;
    }

    public String toString() {
        Node current = head.next;
        String string = "";
        while (current != tail) {
            string += current.data.getName();
            if (current.next != tail) {
                string += ", ";
            }
            current = current.next;
        }
        return string;
    }

    public int getTotalName(String name) { // calculates the total count over all years for a specific name
        int count = 0;
        Node current = head.next;

        while (current != tail) {
            Name n = current.data;
            for (int year = 0; year < n.getYearCount(); year++) {
                if(n.getName().equals(name)) {
                    count += n.getCount(year);
                }
            }
            current = current.next;
        }
        return count;
    }

    public double getTotalPercentage(String name) {
        int count = getTotalName(name); // total count of the specific name over all years
        Node current = head.next;
        int total = getTotal(); // total count of all names over all years
        return ((double) count/total);
    }

    private double totalRank(String name) {
        int totalName = getTotalName(name);
        int rank = 1;

        Node current = head.next;
        while (current != tail) {
            String otherName = current.data.getName();

            if (!name.equals(otherName)) {
                int otherNameTotal = getTotalName(otherName);

                if (otherNameTotal > totalName) {
                    rank++;
                }
            }
            current = current.next;
        }
        return rank;
    }
    

    public double[] totalStats(String name) {
        Node current = head.next;

        while (current != tail) {

            if (current.data.getName().equals(name)) {
                double[] stats = new double[3];

                stats[0] = (double) totalRank(name);
                stats[1] = (double) getTotalName(name);
                stats[2] = (double) getTotalPercentage(name);

                return stats;
            }
        current = current.next;
        }
        return null;
    }
}