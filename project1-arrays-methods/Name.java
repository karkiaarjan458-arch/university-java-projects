import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

public class Name {
    public static void main(String[] args) {
        ArrayList<String> names = getNames();
        sortNames(names);
        displayNames(names);
    }

    public static ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();

        while (true) {
            String n = JOptionPane.showInputDialog("Enter a name");
            names.add(n);
            if (JOptionPane.showConfirmDialog(null,
                    "Read more names?", "Continue Dialog",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                break;
            }
        }
        return names;
    }

    public static void sortNames(ArrayList<String> names) {
        Collections.sort(names); // Sorting  the names alphabetically
    }

    public static void displayNames(ArrayList<String> names) {
        sortNames(names); //Ensuring names are displayed after sorting
        for (String aName : names) {
            System.out.println(aName);
        }
    }
}
