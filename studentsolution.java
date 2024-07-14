import java.io.*;
import java.util.*;

class MetroStop {
    private String stopName;
    private MetroStop nextStop;
    private MetroStop prevStop;
    private MetroLine line;
    private int fare;

    public MetroStop(String name, MetroLine metroLine, int fare) {
        stopName = name;
        nextStop = null;
        prevStop = null;
        line = metroLine;
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
        nextStop = next;
    }

    public void setPrevStop(MetroStop prev) {
        prevStop = prev;
    }
}

// MetroLine class
class MetroLine {
    private String lineName;
    private MetroStop node;

    public MetroLine(String name) {
        lineName = name;
        node = null;
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

    public void populateLine(String filename) {
        MetroStop stop;
        String faltu3 = "";
        String faltu = "";
        String faltu1 = "";
        String faltu2 = "";
        String line = "";
        String lo = filename;
        StringTokenizer lon = new StringTokenizer(lo, ".");
        if (lon.hasMoreTokens()) {
            faltu3 = lon.nextToken();
        }
        try (BufferedReader inputfile = new BufferedReader(new FileReader(filename))) {
            while ((line = inputfile.readLine()) != null) {
                StringTokenizer input = new StringTokenizer(line, ",");
                if (input.hasMoreTokens()) {
                    faltu = input.nextToken();
                    for (int j = faltu.length() - 1; j > 0; j--) {
                        if (faltu.charAt(j) == ' ') {
                            faltu2 = faltu.substring(j + 1);
                            faltu1 = faltu.substring(0, j);
                            break;
                        }
                    }
                    int k = Integer.parseInt(faltu2);
                    stop = new MetroStop(faltu1, this, k);
                    if (getNode() == null)
                        setNode(stop);
                    else {
                        MetroStop stop1 = getNode();
                        MetroStop stop2 = stop;
                        while (stop1.getNextStop() != null) {
                            stop1 = stop1.getNextStop();
                        }
                        stop1.setNextStop(stop2);
                        stop2.setPrevStop(stop1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}

// AVLNode class
class AVLNode {
    private String stopName;
    private List<MetroStop> stops;
    private AVLNode left;
    private AVLNode right;
    private AVLNode parent;

    public AVLNode(String name) {
        stopName = name;
        stops = new ArrayList<>();
        left = null;
        right = null;
        parent = null;
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

// AVLTree class
class AVLTree {
    private AVLNode root;

    public AVLTree() {
        root = null;
    }

    public AVLNode getRoot() {
        return root;
    }

    public void setRoot(AVLNode root) {
        this.root = root;
    }

    public int height(AVLNode node) {
        if (node == null)
            return 0;
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        return 1 + Math.max(leftHeight, rightHeight);
    }

    public int balanceFactor(AVLNode node) {
        if (node == null)
            return 0;
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        return leftHeight - rightHeight;
    }

    public void rotateLeft(AVLNode x) {
        AVLNode y = x.getRight();
        AVLNode z = y.getRight();
        y.setParent(x.getParent());
        if (x.getParent() != null) {
            if (x.getParent().getRight() == x)
                x.getParent().setRight(y);
            else
                x.getParent().setLeft(y);
        } else
            root = y;
        x.setParent(y);
        x.setRight(y.getLeft());
        if (x.getRight() != null)
            x.getRight().setParent(x);
        y.setLeft(x);
    }

    public void rotateRight(AVLNode x) {
        AVLNode y = x.getLeft();
        AVLNode z = y.getLeft();
        y.setParent(x.getParent());
        if (x.getParent() != null) {
            if (x.getParent().getLeft() == x)
                x.getParent().setLeft(y);
            else
                x.getParent().setRight(y);
        } else
            root = y;
        x.setParent(y);
        x.setLeft(y.getRight());
        if (x.getLeft() != null)
            x.getLeft().setParent(x);
        y.setRight(x);
    }

    public void balance(AVLNode t) {
        int bal_factor = balanceFactor(t);
        if (bal_factor > 1) {
            if (balanceFactor(t.getLeft()) > 0)
                rotateRight(t);
            else {
                rotateLeft(t.getLeft());
                rotateRight(t);
            }
        } else if (bal_factor < -1) {
            if (balanceFactor(t.getRight()) > 0) {
                rotateRight(t.getRight());
                rotateLeft(t);
            } else
                rotateLeft(t);
        }
    }

    public void insert(AVLNode r, MetroStop metroStop) {
        AVLNode tempo = getRoot();
        AVLNode jadu = r;
        int k = 0;
        while (tempo != null) {
            if (tempo.getStopName().compareTo(r.getStopName()) > 0)
                tempo = tempo.getLeft();
            else if (tempo.getStopName().compareTo(r.getStopName()) < 0)
                tempo = tempo.getRight();
            else {
                k = 1;
                tempo.addMetroStop(metroStop);
                break;
            }
        }
        if (k == 0) {
            AVLNode tempparent = getRoot();
            AVLNode tempparentdad = getRoot();
            while (tempparent != null) {
                if (tempparent.getStopName().compareTo(r.getStopName()) > 0) {
                    if (tempparent.getParent() != null)
                        tempparentdad = tempparent;
                    tempparent = tempparent.getLeft();
                } else if (tempparent.getStopName().compareTo(r.getStopName()) < 0) {
                    if (tempparent.getParent() != null)
                        tempparentdad = tempparent;
                    tempparent = tempparent.getRight();
                }
            }
            if (tempparentdad.getStopName().compareTo(r.getStopName()) < 0) {
                jadu.setParent(tempparentdad);
                jadu.addMetroStop(metroStop);
                tempparentdad.setRight(jadu);
                AVLNode temp = jadu;
                while (temp.getParent().getParent() != null) {
                    if (balanceFactor(temp.getParent().getParent()) < -1 || balanceFactor(temp.getParent().getParent()) > 1) {
                        balance(temp.getParent().getParent());
                        break;
                    }
                    temp = temp.getParent();
                }
            } else {
                jadu.setParent(tempparentdad);
                jadu.addMetroStop(metroStop);
                tempparentdad.setLeft(jadu);
                AVLNode temp = jadu;
                while (temp.getParent().getParent() != null) {
                    if (balanceFactor(temp.getParent().getParent()) < -1 || balanceFactor(temp.getParent()) > 1) {
                        balance(temp.getParent().getParent());
                        break;
                    }
                    temp = temp.getParent();
                }
            }
        }
    }

    public void populateTree(MetroLine metroLine) {
        MetroStop newt = metroLine.getNode();
        while (newt != null) {
            AVLNode newp = new AVLNode(newt.getStopName());
            insert(newp, newt);
            newt = newt.getNextStop();
        }
    }

    public void printTree(AVLNode node) {
        if (node == null)
            return;
        printTree(node.getLeft());
        System.out.println("Stop Name: " + node.getStopName());
        List<MetroStop> stops = node.getStops();
        for (MetroStop stop : stops) {
            System.out.println("\tMetro Stop: " + stop.getStopName() + ", Fare: " + stop.getFare() + ", Line: " + stop.getLine().getLineName());
        }
        printTree(node.getRight());
    }
}

// Trip class
class Trip {
    private MetroStop start;
    private MetroStop end;
    private int totalFare;

    public Trip(MetroStop start, MetroStop end) {
        this.start = start;
        this.end = end;
        totalFare = 0;
    }

    public MetroStop getStart() {
        return start;
    }

    public MetroStop getEnd() {
        return end;
    }

    public int getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(int totalFare) {
        this.totalFare = totalFare;
    }
}

// Exploration class
class Exploration {
    private AVLTree tree;

    public Exploration() {
        tree = new AVLTree();
    }

    public AVLTree getTree() {
        return tree;
    }

    public void setTree(AVLTree tree) {
        this.tree = tree;
    }

    public void findPaths(MetroStop start, MetroStop end) {
        Trip trip = new Trip(start, end);
        findPathsUtil(start, end, trip);
        System.out.println("Total Fare: " + trip.getTotalFare());
    }

    private void findPathsUtil(MetroStop currentStop, MetroStop endStop, Trip trip) {
        if (currentStop == endStop) {
            System.out.println("Path found:");
            System.out.println("\t" + currentStop.getStopName() + " (Line: " + currentStop.getLine().getLineName() + ")");
            trip.setTotalFare(trip.getTotalFare() + currentStop.getFare());
            return;
        }

        System.out.println("Current Stop: " + currentStop.getStopName());

        AVLNode node = tree.getRoot();
        while (node != null) {
            if (node.getStopName().compareTo(currentStop.getStopName()) > 0)
                node = node.getLeft();
            else if (node.getStopName().compareTo(currentStop.getStopName()) < 0)
                node = node.getRight();
            else {
                List<MetroStop> stops = node.getStops();
                for (MetroStop stop : stops) {
                    if (stop.getStopName().compareTo(currentStop.getStopName()) == 0) {
                        trip.setTotalFare(trip.getTotalFare() + stop.getFare());
                        break;
                    }
                }
            }
        }

        MetroStop nextStop = currentStop.getNextStop();
        if (nextStop != null)
            findPathsUtil(nextStop, endStop, trip);
    }
}

// PathFinder class
class PathFinder {
    private AVLTree tree;

    public PathFinder() {
        tree = new AVLTree();
    }

    public AVLTree getTree() {
        return tree;
    }

    public void setTree(AVLTree tree) {
        this.tree = tree;
    }

    public void findShortestPath(MetroStop start, MetroStop end) {
        Trip trip = new Trip(start, end);
        findShortestPathUtil(start, end, trip);
        System.out.println("Shortest Path found:");
        System.out.println("\t" + start.getStopName() + " (Line: " + start.getLine().getLineName() + ")");
        System.out.println("\t" + end.getStopName() + " (Line: " + end.getLine().getLineName() + ")");
        System.out.println("Total Fare: " + trip.getTotalFare());
    }

    private void findShortestPathUtil(MetroStop currentStop, MetroStop endStop, Trip trip) {
        if (currentStop == endStop) {
            trip.setTotalFare(trip.getTotalFare() + currentStop.getFare());
            return;
        }

        AVLNode node = tree.getRoot();
        while (node != null) {
            if (node.getStopName().compareTo(currentStop.getStopName()) > 0)
                node = node.getLeft();
            else if (node.getStopName().compareTo(currentStop.getStopName()) < 0)
                node = node.getRight();
            else {
                List<MetroStop> stops = node.getStops();
                for (MetroStop stop : stops) {
                    if (stop.getStopName().compareTo(currentStop.getStopName()) == 0) {
                        trip.setTotalFare(trip.getTotalFare() + stop.getFare());
                        break;
                    }
                }
            }
        }

        MetroStop nextStop = currentStop.getNextStop();
        if (nextStop != null)
            findShortestPathUtil(nextStop, endStop, trip);
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        MetroLine blueLine = new MetroLine("Blue Line");
        blueLine.populateLine("D:\\blue.txt");  

        MetroLine redLine = new MetroLine("Red Line");
        redLine.populateLine("D:\\red.txt");  

        AVLTree tree = new AVLTree();
        tree.populateTree(blueLine);
        tree.populateTree(redLine);

        Exploration explorer = new Exploration();
        explorer.setTree(tree);

        MetroStop start = blueLine.getNode();
        MetroStop end = redLine.getNode();

        explorer.findPaths(start, end);

        PathFinder pathFinder = new PathFinder();
        pathFinder.setTree(tree);
        pathFinder.findShortestPath(start, end);
    }
}
