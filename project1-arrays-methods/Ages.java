import javax.swing.JOptionPane;

public class Ages {
    public static void main(String[] args) {
        int[] ages = getAges(); // Get ages from the user
        displayAges(ages); // Display all entered ages

        int minAge = findMinimumAge(ages); // Find the minimum age
        System.out.println("The minimum age is: " + minAge);
    }

    public static int[] getAges() {
        int count = Integer.parseInt(JOptionPane.showInputDialog("How many ages do you want to enter?"));
        int[] ages = new int[count];

        for (int i = 0; i < count; i++) {
            String ageInput = JOptionPane.showInputDialog("Enter age #" + (i + 1) + ":");
            ages[i] = Integer.parseInt(ageInput);
        }
        return ages;
    }

    public static void displayAges(int[] ages) {
        System.out.println("Ages entered:");
        for (int age : ages) {
            System.out.println(age);
        }
    }

    public static int findMinimumAge(int[] ages) {
        int min = ages[0];
        for (int age : ages) {
            if (age < min) {
                min = age;
            }
        }
        return min;
    }
}

