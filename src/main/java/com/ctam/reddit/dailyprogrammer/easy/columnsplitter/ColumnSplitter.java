package com.ctam.reddit.dailyprogrammer.easy.columnsplitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Source: http://www.reddit.com/r/dailyprogrammer/comments/2hssx6/29092014_challenge_182_easy_the_column_conundrum/
 * Title: [29/09/2014] Challenge #182 [Easy] The Column Conundrum
 * Date: Oct 04 2014
 *
 * Description: This class takes a txt file as input and splits the given text into columns.
 *
 * Input: In the first line, three numbers are given: <number of columns><column width><space width>
 *     Number of columns: The number of columns to split the txt into.
 *     Column width: The width, in characters, of each column.
 *     Space width: The width, in spaces, between each column.
 *
 * Future enhancements:
 *      Instead of printing the output to the console, write output to a file using FileOutputStream.
 *
 */
public class ColumnSplitter
{
    public static void main (String [] args)
    {
        //Hardcoded values.
        final String INPUT_FILE_NAME = "src/main/resources/c182e-sample-input.txt";

        FileInputStream inputStream = null;
        BufferedReader reader = null;

        try
        {
            //RESEARCH: Scanner vs. BufferedReader.

            inputStream = new FileInputStream(INPUT_FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(inputStream));

            //Step 1: Read in the first line of the document.
            String[] firstLine = getFirstLine(reader);

            //Assumption: First line must have a length of 3 and be in <int> <int> <int> format.
            if(firstLine.length != 3)
            {
                throw new Exception("Failed to parse the first line of the file. Line must use the format: <int> <int> <int>");
            }

            //Step 2: Get the remaining text from the input file.
            String fileText = getFileText(reader);

            formatAndOutputFileTxt(firstLine, fileText);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(inputStream != null)
            {
                try { inputStream.close(); } catch (Exception e) {};
            }
            if(reader != null)
            {
                try { reader.close(); }
                catch (IOException e) {}
            }
        }
    }

    //This method processes the data read from firstLine and fileText then passes the information
    //to create and output the formatted lines.
    private static void formatAndOutputFileTxt(String[] firstLine, String fileText) {

        //RESEARCH: How to use FileOutputBuffer.

        final String OUTPUT_FILE_NAME = "c182e-sample-output.txt";
        FileOutputStream outputStream = null;

        int numberOfColumns = Integer.parseInt(firstLine[0]);
        int columnWidth = Integer.parseInt(firstLine[1]);
        int spaceWidth = Integer.parseInt(firstLine[2]);

        try
        {
            outputStream = new FileOutputStream(OUTPUT_FILE_NAME);

            //Step 3: Split the file into individual words, then create each line in the output file.
            String[] fileWords = fileText.replaceAll("\n", " ").split(" ");
            String lines = createFormattedLines(columnWidth, fileWords);
            String[] outputLines = lines.split("\n");

            //Step 4: Print the output.
            printOutput(outputLines, numberOfColumns, spaceWidth);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally
        {
            if(outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (IOException e) {
                }
            }
        }

    }

    //This method prints out the output. The index is computed by using the number of lines per column and number of columns.
    private static void printOutput(String[] outputLines, int numberOfColumns, int spaceWidth) {

        String columnSpacing = getSpaces(spaceWidth);

        int linesPerColumn = (int) Math.ceil((double) outputLines.length / (double) numberOfColumns);
        int indexToPrint = 0;

        //Step 4: Output the result.
        for(int i = 0; i < linesPerColumn; i++)
        {
            //DEBUG: Keep track of how many lines per column are in the output. Also, it makes the output look nicer.
            System.out.print(i + " ");
            for(int j = 0; j < numberOfColumns; j++)
            {
                indexToPrint = i+(j*linesPerColumn);
                if(indexToPrint < outputLines.length) {
                    System.out.print(outputLines[indexToPrint]);
                    if (j != numberOfColumns - 1) {
                        System.out.print(columnSpacing);
                    }
                    else {
                        System.out.println();
                    }
                }
            }
        }
    }

    //This method returns the specified number of spaces for format "padding".
    private static String getSpaces(int spaceWidth)
    {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < spaceWidth; i++)
        {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    //This method creates the formatted lines used in the output using the words and the column width.
    private static String createFormattedLines(int columnWidth, String[] fileWords)
    {
        StringBuilder stringBuilder = new StringBuilder();

        int widthRemaining = columnWidth;
        boolean firstWord = true;

        for(String word: fileWords)
        {
            if(word.length() >= widthRemaining)
            {
                stringBuilder.append(getSpaces(widthRemaining));
                stringBuilder.append("\n");
                widthRemaining = columnWidth;
            }
            else
            {
                if(!firstWord)
                {
                    stringBuilder.append(" ");
                    widthRemaining--;
                }
                else
                {
                    firstWord = false;
                }
            }
            stringBuilder.append(word);
            widthRemaining -= word.length();
        }
        return stringBuilder.toString();
    }

    //This method gets the remaining text from the input file.
    private static String getFileText(BufferedReader reader) {

        //RESEARCH: More efficient to read input into String[], split by String, then format
        //RESEARCH: more efficient to read one word at a time (Scanner) and format at the same time?

        try {

            //RESEARCH: StringBuffer vs. StringBuilder.

            StringBuilder stringBuilder = new StringBuilder();

            String line = reader.readLine();
            while(line != null)
            {
                stringBuilder.append(line);
                line = reader.readLine();
            }

            return stringBuilder.toString();
        }
        catch (IOException e)
        {
            System.out.println("Failed to read the first line in the file.");
            e.printStackTrace();
        }
        return "";
    }

    //This method gets the first line from the input file.
    //First line is assumed to be three Integers in the format <int> <int> <int>
    private static String[] getFirstLine(BufferedReader reader)
    {
        try {
            return reader.readLine().split(" ");
        }
        catch (IOException e)
        {
            System.out.println("Filed to read the first line in the file.");
            e.printStackTrace();
        }
        return new String[0];
    }
}
