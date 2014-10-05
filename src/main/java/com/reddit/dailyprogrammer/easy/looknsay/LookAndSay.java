package com.reddit.dailyprogrammer.easy.looknsay;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Source: http://www.reddit.com/r/dailyprogrammer/comments/2ggy30/9152014_challenge180_easy_looknsay/
 * Title: [9/15/2014] Challenge#180 [Easy] Look'n'Say
 * Date: Oct 05 2014
 *
 * Description:
 *  The Look and Say sequence is an interesting sequence of numbers where each term is given by describing the makeup of the previous term.
 *  The 1st term is given as 1. The 2nd term is 11 ('one one') because the first term (1) consisted of a single 1. The 3rd term is then 21 ('two one') because the second term consisted of two 1s. The first 6 terms are:
 *  1
 *  11
 *  21
 *  1211
 *  111221
 *  312211
 *
 * Input: Two integers: One for the Nth Look and say sequence to compute and one for the seed number.
 *
 * Future enhancements:
 *      Expand to accommodate negative integers in the sequence.
 *
 * */
public class LookAndSay
{
    public static void main(String [] args)
    {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the Nth Look and Say sequence to output: ");
            int sequenceNumber = Integer.parseInt(bufferedReader.readLine());

            if(sequenceNumber > 0)
            {
                //Bonus: Allow user to enter a different seed number.
                System.out.print("Enter the seed number to use: ");
                int seedNumber = Integer.parseInt(bufferedReader.readLine());

                computeLookAndSaySequence(sequenceNumber, seedNumber);
            }
            else
            {
                System.out.println("Sequence number must be a positive integer.");
            }
        }
        catch (Exception e)
        {
            System.out.println("Failed to read input. Input must be an integer.");
            e.printStackTrace();
        }
    }

    private static void computeLookAndSaySequence(int sequenceNumber, int seedNumber)
    {
        String lookAndSaySequence = String.valueOf(seedNumber);
        System.out.println(lookAndSaySequence);

        for(int i = 1; i < sequenceNumber; i++)
        {
            //Re-initialize nextLookAndSaySequence.
            StringBuilder nextLookAndSaySequence = new StringBuilder();
            int characterCount;

            for(int j = 0; j < lookAndSaySequence.length(); j++)
            {
                //Reset character count to 1 and get the next character in the sequence to match.
                characterCount = 1;
                char character = lookAndSaySequence.charAt(j);

                //Increment characterCount for each matching character we find in the lookAndSaySequence.
                while(j + 1 < lookAndSaySequence.length() && lookAndSaySequence.charAt(j+1) == character)
                {
                    characterCount++;
                    j++;
                }
                nextLookAndSaySequence.append(characterCount).append(character);
            }

            //DEBUG: Print out the sequences as they are computed.
            System.out.println(nextLookAndSaySequence.toString());
            //Update the lookAndSaySequence.
            lookAndSaySequence = nextLookAndSaySequence.toString();
        }
    }
}
