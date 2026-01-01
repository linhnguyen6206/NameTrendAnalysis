import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DriverHW02 {
    public static void main(String[] args) throws FileNotFoundException {
        NameLL female = new NameLL();
        NameLL male = new NameLL();

        String femaleName = null;
        String maleName = null;
        
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-f") && i + 1 < args.length) {
                femaleName = args[i + 1];
                i++;
            } else if (args[i].equals("-m") && i + 1 < args.length) {
                maleName = args[i + 1];
                i++; 
            }
        }
        // Data loading
        for (String filename : args) {
            if (!filename.endsWith(".csv")) {
                continue;
            }
            
            Scanner sc = new Scanner(new File(filename));
            int year = Integer.parseInt(filename.replaceAll("[^0-9]", ""));

            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                int rank = Integer.parseInt(parts[0].replace("\"", "").trim());
                String mname = parts[1].replace("\"", "").trim();
                int mnum = Integer.parseInt(parts[2].replace("\"", "").trim());
                String fname = parts[3].replace("\"", "").trim();
                int fnum = Integer.parseInt(parts[4].replace("\"", "").trim());

                // Load male data
                Name maleObj = male.findName(mname);
                if (maleObj == null) {
                    maleObj = new Name(mname); 
                    male.insertSortedAlpha(maleObj);
                }
                maleObj.setRank(year, rank);
                maleObj.setCount(year, mnum);

                // Load female data
                Name femaleObj = female.findName(fname);
                if (femaleObj == null) {
                    femaleObj = new Name(fname); 
                    female.insertSortedAlpha(femaleObj);
                }
                femaleObj.setRank(year, rank);
                femaleObj.setCount(year, fnum);
            }
            sc.close();
        }

        // Data reporting
        boolean femaleReportPrinted = false;
        boolean maleReportPrinted = false;

        // Loop through the arguments again to determine the print order.
        for (String arg : args) {
            // Check for the female flag
            if (arg.equals("-f") && femaleName != null && !femaleReportPrinted) {
                int idx = female.index(femaleName);
                if (idx == -1) {
                    System.out.println("That name isn't in our data.");
                    System.out.println();
                } else {
                    System.out.println(idx);
                    System.out.println();
                    // Loop through args to find files and print yearly stats
                    for (String filename : args) {
                        if (!filename.endsWith(".csv")) continue;
                        int year = Integer.parseInt(filename.replaceAll("[^0-9]", ""));
                        if (female.findName(femaleName).getRank(year) > 0) {
                            double[] stats = female.yearStats(femaleName, year);
                            System.out.println(year);
                            System.out.printf("%s: %d, %d, %f%n%n", femaleName, (int)stats[0], (int)stats[1], stats[2]);
                        }
                    }
                    double[] totalStats = female.totalStats(femaleName);
                    System.out.println("Total");
                    System.out.printf("%s: %d, %d, %f%n", femaleName, (int)Math.round(totalStats[0]), (int)totalStats[1], totalStats[2]);
                }
                femaleReportPrinted = true; 
            }
           
            // Check for the male flag
            else if (arg.equals("-m") && maleName != null && !maleReportPrinted) {
                int idx = male.index(maleName);
                if (idx == -1) {
                    System.out.println("That name isn't in our data.");
                    System.out.println();
                } else {
                    System.out.println(idx);
                    System.out.println();
                    // Loop through args to find files and print yearly stats
                    for (String filename : args) {
                        if (!filename.endsWith(".csv")) continue;
                        int year = Integer.parseInt(filename.replaceAll("[^0-9]", ""));
                        if (male.findName(maleName).getRank(year) > 0) {
                            double[] stats = male.yearStats(maleName, year);
                            System.out.println(year);
                            System.out.printf("%s: %d, %d, %f%n%n", maleName, (int) stats[0], (int) stats[1], stats[2]);
                        }
                    }
                    double[] totalStats = male.totalStats(maleName);
                    System.out.println("Total");
                    System.out.printf("%s: %d, %d, %f%n", maleName, (int)Math.round(totalStats[0]), (int)totalStats[1], totalStats[2]);
                }
                maleReportPrinted = true; 
            }
        }
    }
}