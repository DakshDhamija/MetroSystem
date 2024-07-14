import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<MetroLine> lines = new ArrayList<>();

    public static void main(String[] args) {
        testPopulateLine();
        testPopulateTree();
        testFindPath();
    }

    static List<String> getFileNames() {
        List<String> filenames = new ArrayList<>();
        filenames.add("blue.txt");
        filenames.add("green.txt");
        filenames.add("magenta.txt");
        filenames.add("orange.txt");
        filenames.add("red.txt");
        filenames.add("violet.txt");
        filenames.add("yellow.txt");
        return filenames;
    }

    static void testPopulateLine() {
        System.out.println("Testing populateLine()");
        List<String> filenames = getFileNames();
        int[] expectedTotalStops = {44, 21, 25, 6, 29, 38, 62};
        for (int i = 0; i < filenames.size(); i++) {
            String lineName = filenames.get(i).substring(0, filenames.get(i).length() - 4);
            MetroLine metroLine = new MetroLine(lineName);
            metroLine.populateLine(filenames.get(i));
            lines.add(metroLine);
            System.out.println("Line name: " + metroLine.getLineName());
            System.out.println("Total stops: " + metroLine.getTotalStops());
            System.out.println();
            assert metroLine.getTotalStops() == expectedTotalStops[i];
            metroLine.printLine();
        }
    }

    static void testPopulateTree() {
        System.out.println("Testing populateTree()");
        List<String> filenames = getFileNames();
        AVLTree tree = new AVLTree();
        tree.setRoot(null);
        for (MetroLine line : lines) {
            if (tree.getRoot() == null) {
                tree.setRoot(new AVLNode(line.getNode().getStopName()));
            }
            tree.populateTree(line);
        }
        System.out.println("Height of tree: " + tree.height(tree.getRoot()));
        System.out.println("Total nodes in tree: " + tree.getTotalNodes(tree.getRoot()));
        assert tree.height(tree.getRoot()) == 9;
        assert tree.getTotalNodes(tree.getRoot()) == 211;
    }

    static List<String> getExpectedPath() {
        List<String> expectedPath = new ArrayList<>();
        expectedPath.add("Pul Bangash");
        expectedPath.add("Pratap Nagar");
        expectedPath.add("Shastri Nagar");
        expectedPath.add("Inder Lok");
        expectedPath.add("Kanhaiya Nagar");
        expectedPath.add("Keshav Puram");
        expectedPath.add("Netaji Subhash Place");
        expectedPath.add("Kohat Enclave");
        expectedPath.add("Pitampura");

        return expectedPath;
    }

    static void testFindPath() {
        System.out.println("Testing findPath()");
        PathFinder pathFinder = new PathFinder(new AVLTree(), lines);
        pathFinder.createAVLTree();
        Path path = pathFinder.findPath("Pitampura", "Pul Bangash");
        assert path != null;
        System.out.println("Total fare: " + path.getTotalFare());
        path.printPath();
        List<String> expectedPath = getExpectedPath();
        for (int i = 0; i < expectedPath.size(); i++) {
            assert path.getStops().get(i).getStopName().equals(expectedPath.get(i));
        }
    }
}
