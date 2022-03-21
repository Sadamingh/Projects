package edu.gatech.seclass.txted;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;


public class Main {

    // Empty Main class for compiling Individual Project.
    // During Deliverable 1 and Deliverable 2, DO NOT ALTER THIS CLASS or implement it

    public static void main(String[] args) {
        /* Local Variables */
        Hashtable<Character, String> optionsDictionary = new Hashtable<Character, String>();
        String filename = null;
        List<String> list = new ArrayList<String>();
        int begin_size;
        int endin_size;

        /* Make sure we have at least file name as argument */
        try {
            filename = args[args.length - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            usage();
            return;
        }

        /* Get the input file, store content in an array list */
        File inputFile = new File(filename);
        Scanner input = getFileContent(inputFile);

        if (input == null) {
            usage();
            return;
        }

        while (input.hasNextLine()) {
            list.add(input.nextLine());
        }

        begin_size = list.size();

//        for (String s : list) {
//            System.out.println(s);
//        }

        /* Options Scanner */
        for (int i = 0; i < args.length - 1; i++) {
            String arg = args[i];

            if (arg.isEmpty()) {
                continue;

            } else if (arg.charAt(0) == '-') {
                switch (arg.charAt(1)) {
                    case 'f':
                        optionsDictionary.put('f', "TRUE");
                        break;
                    case 'i':
                        optionsDictionary.put('i', "TRUE");
                        break;
                    case 's':
                        i++;
                        if (i < args.length - 1) {
                            optionsDictionary.put('s', args[i]);
                        } else {
                            usage();
                            return;
                        }
                        break;
                    case 'e':
                        i++;
                        if (i < args.length - 1) {
                            optionsDictionary.put('e', args[i]);
                        } else {
                            usage();
                            return;
                        }
                        break;
                    case 'r':
                        optionsDictionary.put('r', "TRUE");
                        break;
                    case 'x':
                        i++;
                        if (i < args.length - 1) {
                            optionsDictionary.put('x', args[i]);
                        } else {
                            usage();
                            return;
                        }
                        break;
                    case 'n':
                        i++;
                        if (i < args.length - 1) {
                            optionsDictionary.put('n', args[i]);
                        } else {
                            usage();
                            return;
                        }
                        break;
                    default:
                        usage();
                        return;
                }
            } else {
                usage();
                return;
            }
        }
        // System.out.println(optionsDictionary);      // DEBUG

        /* Check the parameters are existed */
        boolean exist = false;
        if (optionsDictionary.get('e') != null) {
            exist = optionsDictionary.get('e').isEmpty();
        }
        if (optionsDictionary.get('s') != null) {
            exist = exist || optionsDictionary.get('s').isEmpty();
        }
        if (optionsDictionary.get('x') != null) {
            exist = exist || optionsDictionary.get('x').isEmpty();
        }
        if (optionsDictionary.get('n') != null) {
            exist = exist || optionsDictionary.get('n').isEmpty();
        }
        if (exist) {
            usage();
            return;
        }

        /* Content operator */

        // Step 0: dependency errors
        // 0-1. -i must pairs with -e
        if (optionsDictionary.get('i') != null && optionsDictionary.get('e') == null) {
            usage();
            return;
        }

        // Step 1: filtered by -s
        if (optionsDictionary.get('s') != null) {
            try {
                int sValue = Integer.parseInt(optionsDictionary.get('s'));

                // check if we have a invalid skip value other then 0 and 1
                if (sValue != 0 && sValue != 1) {
                    usage();
                    return;

                } else if (sValue == 0) {
                    // if the skip value is 0, then skip all the even lines
                    for (int i = list.size() - 1; i >= 0; i--) {
                        if (i % 2 == 1) {
                            list.remove(i);
                        }
                    }

                } else {
                    // if the skip value is 1, then skip all the odd lines
                    for (int i = list.size() - 1; i >= 0; i--) {
                        if (i % 2 == 0) {
                            list.remove(i);
                        }
                    }
                }

            } catch (NumberFormatException e) {
                // make sure we have a skip value of integer
                usage();
                return;
            }
        }

        // Step 2-1: check insensitive by -i
        boolean INSENSITIVE = false;
        if (optionsDictionary.get('i') != null) {
            INSENSITIVE = true;
        }

        // Step 2-2: exclude by -e
        if (optionsDictionary.get('e') != null) {

            String eValue = optionsDictionary.get('e');
            boolean match;
            String currentLine;

            // Go through all the elements in the array list
            for (int i = list.size() - 1; i >= 0; i--) {
                currentLine = list.get(i);

                // Check if the current line march the eliminated value
                if (!INSENSITIVE) {
                    match = currentLine.contains(eValue);
                } else {
                    match = currentLine.toLowerCase().contains(eValue.toLowerCase());
                }
                if (match) {
                    list.remove(i);
                }
            }
        }

        // Step 3: add suffix by -x
        if (optionsDictionary.get('x') != null) {
            String xValue = optionsDictionary.get('x');
            for (int i = list.size() - 1; i >= 0; i--) {
                String currentLine = list.get(i) + xValue;
                list.set(i, currentLine);
            }
        }

        // Step 4: reverse by -r
        if (optionsDictionary.get('r') != null) {
            Collections.reverse(list);
        }

        // Step 5: padding number by -n
        if (optionsDictionary.get('n') != null) {
            try {
                int nValue = Integer.parseInt(optionsDictionary.get('n'));
                String currentLine = null;

                if (nValue < 0) {
                    usage();
                    return;

                } else if (nValue == 0) {
                    for (int i = list.size() - 1; i >= 0; i--) {
                        currentLine = list.get(i);
                        list.set(i, " " + currentLine);
                    }

                } else {
                    DecimalFormat df = new DecimalFormat("0".repeat(nValue));
                    for (int i = list.size() - 1; i >= 0; i--) {
                        currentLine = list.get(i);
                        list.set(i, df.format((i + 1) % Math.pow(10, nValue)) + " " + currentLine);
                    }
                }

            } catch (NumberFormatException e) {
                // make sure we have a skip value of integer
                usage();
                return;
            }

        }


        // Step 6: output file by -f
        if (optionsDictionary.get('f') != null) {
            File file = new File(filename);
            try {
                OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
                for (String s : list) {
                    fileWriter.write(s + System.lineSeparator());
                }

                // Handle Test 19 edge case
                if (begin_size > 0 && list.isEmpty()) {
                    fileWriter.write(System.lineSeparator());
                }

                fileWriter.close();
            } catch (IOException e) {
                usage();
                return;
            }

        } else {
            // Step 7: Normally output
            for (String s : list) {
                System.out.print(s + System.lineSeparator());
            }
        }

        // Step 8: Empty output handler
        endin_size = list.size();
        if (begin_size > 0 && endin_size == 0) {
            System.out.print(System.lineSeparator());
        }
    }

    private static Scanner getFileContent(File filename) {
        try {
            return new Scanner(filename, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }

    private static void usage() {
        System.err.println("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
    }
}
