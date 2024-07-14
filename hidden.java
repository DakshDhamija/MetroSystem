import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    static List<Pair<String, String>> getExpectedPathPairs() {
        List<Pair<String, String>> expectedPathPairs = new ArrayList<>();
        expectedPathPairs.add(new Pair<>("Pitampura", "Pul Bangash"));
        expectedPathPairs.add(new Pair<>("Brigadier Hoshiar Singh", "Inderlok"));

        return expectedPathPairs;
    }

    static Map<Integer, List<String>> expectedPathMapping() {
        Map<Integer, List<String>> m = new HashMap<>();
        m.put(0, List.of("Pul Bangash", "Pratap Nagar", "Shastri Nagar", "Inder Lok", "Kanhaiya Nagar", "Keshav Puram", "Netaji Subhash Place", "Kohat Enclave", "Pitampura"));
        m.put(1, List.of("Inderlok", "Ashok Park Main", "Punjabi Bagh", "Shivaji Park", "Madi Pur", "Paschim Vihar East", "Paschim Vihar West", "Peera Garhi", "Udyog Nagar", "Surajmal Stadium", "Nangloi", "Nangloi Railway Station", "Rajdhani Park", "Mundka", "Mundka Industrial Area", "Ghevra Metro Station", "Tikri Kalan", "Tikri Border", "Pandit Shree Ram Sharma", "Bahadurgarh City", "Brigadier Hoshiar Singh"));

        return m;
    }

    static void testFindPath() {
        PathFinder pathFinder = new PathFinder(new AVLTree(), lines);
        pathFinder.createAVLTree();
        List<Pair<String, String>> expectedPathPairs = getExpectedPathPairs();
        for (int i = 0; i < expectedPathPairs.size(); i++) {
            Path path = pathFinder.findPath(expectedPathPairs.get(i).getKey(), expectedPathPairs.get(i).getValue());
            assert path != null;
            System.out.println("Total fare: " + path.getTotalFare());
            path.printPath();
            List<String> expectedPath = expectedPathMapping().get(i);
            for (int j = 0; j < expectedPath.size(); j++) {
                assert path.getStops().get(j).getStopName().equals(expectedPath.get(j));
            }
        }
    }
}
