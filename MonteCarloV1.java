import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class MonteCarloV1 {

    /** LET'S COME UP WIT SOME PSEUDOCODE
     * 1) Initialize the creation of a new file and use a while loop with Random.nextInt to simulate 1000 (or more trials) - use the PrintWriter.prinln(method)
     * to record the number
     * 2) Close the file and open it again, this time run through it with another while loop, counting the average number of 'incorrect numbers' before we reach
     * the desired one.
     * 3) Print out the results
     */


    /** AND NOW A PLAN
     * 0) Declare variables - int nOfTrials, String pathName, nOfLines, average
     * 1) I'll need to create the file (use PrintWriter)
     * 2) Use the random object's method nextInt(bound will be say 20, so there's 5 % chance)
     * 3) Simulate 1 000 trials / the user input using the System.in Scanner
     * 4) CLOSE THE FILE!
     * 5) Open the file, skim through it using the while loop, count nOfLines and nOfZeros (Zero will be our "fox squirrel")
     * 6) CLOSE THE FILE!
     * 7) Count the average
     * 8) Write the print out statements for output to the user
     * 9) Add the user input for the number of trials (use a boolean flag || boolean "wrongInputs <= 5" while loop to check, if the number is greater than 1000.
     * If not, ask the user to enter it once again. If he fails to do so, wrongInputs++. If the user fails to enter correct number 5 times, print a statement
     * and run it automatically with 1 000.
     * 10) That's enough for now, let's stop writing and start coding!
     *
     */
    public static void main(String args[]) throws IOException {

        /* 0TH BLOCK - DECLARE VARIABLES, OBJECTS AND WELCOME THE USER TO THE PROGRAM */
        int probability = 20;
        int wrongInputsAllowed = 5;
        int wrongInputs = 0;
        int nOfTrials;
        int minTrials = 1000;
        int space = 0;
        int totalSpace = 0;
        int desiredNumber = 0;

        double average;


        String directory = "";
        String fileName  = "marcoPoloTrials.txt";
        String filePath;

        Random random = new Random();




        System.out.println("Welcome to the Marco Polo testing program.\n" +
                            "Today, we'll be trying to spot the brown bear.");
        System.out.println();
        System.out.println("In case you were wondering, the probability of spotting one\n" +
                "is about 1:" + probability + " for each animal you see.");
        System.out.println();

        Scanner input = new Scanner(System.in);
        /* END OF 0TH BLOCK */



        /* 1ST BLOCK - DECLARING DIRECTORY TO THE FILE BASED ON THE USER INPUT */

        System.out.println("Would you like to enter a specific directory\n" +
                "for saving the records of the trials? (Y/N)");
        String answer = input.nextLine();
        if(answer.equalsIgnoreCase("y")) {
            System.out.println("Please, enter desired directory for saving trial records:");
            directory = input.nextLine();

            directory = directory.replace("\\", "/"); // avoid escape sequences

            /* the following lines of code will remove the double quotes "" from the directory
               that are included when the user uses the "Copy a File Path" in windows file explorer */
            char firstChar = directory.charAt(0);
            char lastChar = directory.charAt(directory.length()-1);

            if(firstChar == '"') {
                directory = directory.substring(1);
            }
            if(lastChar == '"') {
                directory = directory.substring(0, directory.length() - 1);
            }

            /* add a front-slash to the end if it's missing there */
            lastChar = directory.charAt(directory.length()-1);
            if(lastChar != '/') {
                directory += "/";
            }
//            System.out.println(directory);
        } else if(answer.equalsIgnoreCase("n")) {

        } else {
            System.out.println("Neither 'n' nor 'y' have been entered, running with the " +
                    "default 'n'");
        }
        filePath = directory + fileName; /* concatenate the directory with the name of the file to get the final path*/
//        System.out.println("The file path is: " + filePath);
        /* END OF THE 1ST BLOCK*/




        /* 2ND BLOCK - CREATING NEW FILE AND RUNNING THE TRIALS */
        PrintWriter newFile = new PrintWriter(new File(filePath));

        System.out.println();
        System.out.println("How many trials would you like to run? \n" +
                "(please enter a number of " + minTrials + " or higher) ");
        nOfTrials = Integer.parseInt(input.nextLine());
        if(nOfTrials < minTrials) {
            wrongInputs++;
            while(nOfTrials < minTrials && wrongInputs < wrongInputsAllowed) {
                int leftAttempts = wrongInputsAllowed-wrongInputs;
                System.out.println("It seems like a number below " + minTrials + " has been entered.");
                System.out.print(leftAttempts + " attempt");
                if(leftAttempts > 1) {
                    System.out.print("s");
                }
                System.out.println(" left.");
                wrongInputs++;
                nOfTrials = Integer.parseInt(input.nextLine());
            }
            if(nOfTrials < minTrials) {
                System.out.println("Wrong input entered " + wrongInputs + " times.");
                System.out.println("Running with the default: " + minTrials + " trials.");
            }
            nOfTrials = minTrials;
        }
//        System.out.println(nOfTrials);

        System.out.println();
        System.out.println("Wait a moment please (or two, if you entered a really big number.");
        System.out.println();

        for(int i = 0; i < nOfTrials; i++) {
            space = 0;
            int n = random.nextInt(probability);
            while(n != desiredNumber) {
                space++;
                n = random.nextInt(probability);
            }
//            System.out.println("The space for trial " + (i+1) + " is " + space);
            newFile.println(space);
//            System.out.println(n);
        }

//        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        newFile.close();

        /* END OF THE 2ND BLOCK */




        /* 3RD BLOCK - READING AND PROCESSING THE DATA FROM THE NEWLY CREATED FILE */
        Scanner file = new Scanner(new File(filePath));

        while(file.hasNextLine()) {
            totalSpace += Integer.parseInt(file.nextLine());
        }

        file.close();
        /* END OF THE 3RD BLOCK */





        /*4TH BLOCK - Output to the user */
//        System.out.println("totalSpace = " + totalSpace);

        average = (double)totalSpace/nOfTrials;

        // NOTE - the average will generally gravitate towards the value of (probability - 1)
        // which is because if there is a probability 1:20 that you'll see a bear,
        // then you should see 19 animals and then the bear as the 20th, another 19 animals
        // and then the bear as the 40th and so on --> that implies the average space between
        // two bears (chosen animals) spotted is the probability ratio - 1

        System.out.println("Aaaaaand the result:");
        System.out.println("The average number of other animals observed \n" +
                "between seeing two bears is: " + average);

        System.out.println();
        System.out.println("We hope you enjoyed this program.");


        /* JUST A RANDOM BLOCK OF CODE TO PREPARE A SURPRISE TO SOMEONE WHO'D OPEN THE RECORDS FILE */
        FileWriter reopen = new FileWriter(filePath, true);
        reopen.write("Wow, have you really scrolled this far? ");
        reopen.write("Not sure if that's commitment or insanity, but let's give you the benefit of doubt and got with the former.");
        for(int i = 0; i < 400; i++) {
            reopen.write(" ");
        }
        reopen.write("Really?! Well I guess you won't stop that easily. ");
        reopen.write("I guess just go and play around with it yourself: https://github.com/microhyd/AP-CSA-05.06-MarcoPoloMethod");
        for(int i = 0; i < 1000; i++) {
            reopen.write("     ");
        }
        reopen.close();

        
    }
}
