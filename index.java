import java.util.*;

class MetroStop {
    private String stopName;
    private MetroStop nextStop;
    private MetroStop prevStop;
    private MetroLine line;
    private int fare;

    public MetroStop(String name, MetroLine metroLine, int fare) {
        this.stopName = name;
        this.nextStop = null;
        this.prevStop = null;
        this.line = metroLine;
        this.fare = fare;
    }

    public String getStopName() {
        return stopName;
    }

    public MetroStop getNextStop() {
        return nextStop;
    }

    public MetroStop getPrevStop() {
        return prevStop;
    }

    public MetroLine getLine() {
        return line;
    }

    public int getFare() {
        return fare;
    }

    public void setNextStop(MetroStop next) {
        this.nextStop = next;
    }

    public void setPrevStop(MetroStop prev) {
        this.prevStop = prev;
    }
}

class MetroLine {
    private String lineName;
    private MetroStop node;

    public MetroLine(String name) {
        this.lineName = name;
        this.node = null;
    }

    public String getLineName() {
        return lineName;
    }

    public MetroStop getNode() {
        return node;
    }

    public void setNode(MetroStop node) {
        this.node = node;
    }

    public void printLine() {
        MetroStop stop = node;
        while (stop != null) {
            System.out.println(stop.getStopName());
            stop = stop.getNextStop();
        }
    }

    public int getTotalStops() {
        MetroStop stop = node;
        int c = 0;
        while (stop != null) {
            c++;
            stop = stop.getNextStop();
        }
        return c;
    }

    public void populateLine(String filename) {
        MetroStop stop;
        String line = "";
        String lineName = filename.split("\\.")[0];
        this.lineName = lineName;
        try (Scanner inputfile = new Scanner(new File(filename))) {
            while (inputfile.hasNextLine()) {
                line = inputfile.nextLine();
                String[] parts = line.split(", ");
                String stopName = parts[0];
                int fare = Integer.parseInt(parts[1]);
                stop = new MetroStop(stopName, this, fare);
                if (this.node == null) {
                    this.setNode(stop);
                } else {
                    MetroStop lastStop = this.node;
                    while (lastStop.getNextStop() != null) {
                        lastStop = lastStop.getNextStop();
                    }
                    lastStop.setNextStop(stop);
                    stop.setPrevStop(lastStop);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class AVLNode {
    private String stopName;
    private List<MetroStop> stops;
    private AVLNode left;
    private AVLNode right;
    private AVLNode parent;

    public AVLNode(String name) {
        this.stopName = name;
        this.stops = new ArrayList<>();
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    public String getStopName() {
        return stopName;
    }

    public List<MetroStop> getStops() {
        return stops;
    }

    public AVLNode getLeft() {
        return left;
    }

    public AVLNode getRight() {
        return right;
    }

    public AVLNode getParent() {
        return parent;
    }

    public void addMetroStop(MetroStop metroStop) {
        stops.add(metroStop);
    }

    public void setLeft(AVLNode left) {
        this.left = left;
    }

    public void setRight(AVLNode right) {
        this.right = right;
    }

    public void setParent(AVLNode parent) {
        this.parent = parent;
    }
}

class AVLTree {
    private AVLNode root;

    public AVLTree() {
        this.root = null;
    }

    public AVLNode getRoot() {
        return root;
    }

    public void setRoot(AVLNode root) {
        this.root = root;
    }

    public int height(AVLNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    public int balanceFactor(AVLNode node) {
        if (node == null) return 0;
        return height(node.getLeft()) - height(node.getRight());
    }

    public void rotateLeft(AVLNode x) {
        AVLNode y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != null) y.getLeft().setParent(x);
        y.setParent(x.getParent());
        if (x.getParent() == null) root = y;
        else if (x == x.getParent().getLeft()) x.getParent().setLeft(y);
        else x.getParent().setRight(y);
        y.setLeft(x);
        x.setParent(y);
    }

    public void rotateRight(AVLNode x) {
        AVLNode y = x.getLeft();
        x.setLeft(y.getRight());
        if (y.getRight() != null) y.getRight().setParent(x);
        y.setParent(x.getParent());
        if (x.getParent() == null) root = y;
        else if (x == x.getParent().getRight()) x.getParent().setRight(y);
        else x.getParent().setLeft(y);
        y.setRight(x);
        x.setParent(y);
    }

    public void balance(AVLNode node) {
        int bal_factor = balanceFactor(node);
        if (bal_factor > 1) {
            if (balanceFactor(node.getLeft()) > 0) rotateRight(node);
            else {
                rotateLeft(node.getLeft());
                rotateRight(node);
            }
        } else if (bal_factor < -1) {
            if (balanceFactor(node.getRight()) > 0) {
                rotateRight(node.getRight());
                rotateLeft(node);
            } else rotateLeft(node);
        }
    }

    public void insert(AVLNode r, MetroStop metroStop) {
        AVLNode temp = root;
        while (temp != null) {
            if (temp.getStopName().compareTo(r.getStopName()) > 0) temp = temp.getLeft();
            else if (temp.getStopName().compareTo(r.getStopName()) < 0) temp = temp.getRight();
            else {
                temp.addMetroStop(metroStop);
                return;
            }
        }
        AVLNode parent = root;
        AVLNode parentDad = root;
        while (parent != null) {
            if (parent.getStopName().compareTo(r.getStopName()) > 0) {
                if (parent.getParent() != null) parentDad = parent;
                parent = parent.getLeft();
            } else if (parent.getStopName().compareTo(r.getStopName()) < 0) {
                if (parent.getParent() != null) parentDad = parent;
                parent = parent.getRight();
            }
        }
        if (parentDad.getStopName().compareTo(r.getStopName()) < 0) {
            r.setParent(parentDad);
            r.addMetroStop(metroStop);
            parentDad.setRight(r);
            AVLNode tempNode = r;
            while (tempNode.getParent().getParent() != null) {
                if (Math.abs(balanceFactor(tempNode.getParent().getParent())) > 1) {
                    balance(tempNode.getParent().getParent());
                    break;
                }
                tempNode = tempNode.getParent();
            }
        } else {
            r.setParent(parentDad);
            r.addMetroStop(metroStop);
            parentDad.setLeft(r);
            AVLNode tempNode = r;
            while (tempNode.getParent().getParent() != null) {
                if (Math.abs(balanceFactor(tempNode.getParent().getParent())) > 1) {
                    balance(tempNode.getParent().getParent());
                    break;
                }
                tempNode = tempNode.getParent();
            }
        }
    }

    public void populateTree(MetroLine metroLine) {
        MetroStop currentStop = metroLine.getNode();
        while (currentStop != null) {
            if (root == null) {
                AVLNode newNode = new AVLNode(currentStop.getStopName());
                setRoot(newNode);
                root.addMetroStop(currentStop);
            } else {
                AVLNode newNode = new AVLNode(currentStop.getStopName());
                insert(newNode, currentStop);
            }
            currentStop = currentStop.getNextStop();
        }
    }

    public void inOrderTraversal(AVLNode node) {
        if (node == null) return;
        inOrderTraversal(node.getLeft());
        System.out.println(node.getStopName());
        inOrderTraversal(node.getRight());
    }

    public int getTotalNodes(AVLNode node) {
        if (node == null) return 0;
        return 1 + getTotalNodes(node.getLeft()) + getTotalNodes(node.getRight());
    }

    public AVLNode searchStop(String stopName) {
        AVLNode temp = root;
        while (temp != null) {
            if (temp.getStopName().compareTo(stopName) > 0) temp = temp.getLeft();
            else if (temp.getStopName().compareTo(stopName) < 0) temp = temp.getRight();
            else return temp;
        }
        return null;
    }
}

class Trip {
    private MetroStop node;
    private int fare;
    private Trip prev;

    public Trip(MetroStop metroStop, Trip previousTrip) {
        this.node = metroStop;
        this.prev = previousTrip;
        if (previousTrip == null) this.fare = 0;
        else this.fare = Math.abs(metroStop.getFare() - previousTrip.getNode().getFare());
    }

    public MetroStop getNode() {
        return node;
    }

    public Trip getPrev() {
        return prev;
    }

    public int getFare() {
        return fare;
    }
}

class Exploration {
    private Queue<Trip> trips;

    public Exploration() {
        trips = new LinkedList<>();
    }

    public Queue<Trip> getTrips() {
        return trips;
    }

    public void enqueue(Trip trip) {
        trips.add(trip);
    }
}

public class MetroSystem {
    public static void main(String[] args) {
        // Example usage
        MetroLine line = new MetroLine("Red Line");
        line.populateLine("RedLine.txt");
        line.printLine();
        AVLTree tree = new AVLTree();
        tree.populateTree(line);
        tree.inOrderTraversal(tree.getRoot());
        AVLNode searchResult = tree.searchStop("Central");
        if (searchResult != null) {
            System.out.println("Found stop: " + searchResult.getStopName());
        } else {
            System.out.println("Stop not found.");
        }
    }
}
