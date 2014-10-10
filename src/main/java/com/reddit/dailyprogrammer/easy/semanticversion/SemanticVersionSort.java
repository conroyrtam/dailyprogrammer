package com.reddit.dailyprogrammer.easy.semanticversion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Source: http://www.reddit.com/r/dailyprogrammer/comments/2igfj9/10062014_challenge_183_easy_semantic_version_sort/
 * Title: [10/06/2014] Challenge #183 [Easy] Semantic Version Sort
 * Date: Oct 06 2014
 *
 * Description:
 *      For the purpose of this challenge, you will be sorting a list of Semantic Versions into chronological order, and you will do it like so:
 *      First, compare the major version.
 *      If they are the same, compare the minor version.
 *      If they are the same, compare the patch version.
 *      If those are all the same, check if the version has a label - ignore the content of the label. A version with a label (prerelease) comes before one without a label (final release.)
 *      Ignore the build metadata.
 *
 * Input: A number N, then N lines of semantic versions to sort.
 *
 * Future enhancements:
 *      Parse the label and consider the label when sorting.
 *      Consider meta data when sorting.
 *      Instead of using Collections.sort, can write own sorting algorithm.
 *      Check if semantic versions are valid before proceeding with the program.
 *
 * */
public class SemanticVersionSort
{
    //Assumptions: Major, minor, and patch must be present.
    //Break down of the groups.
    //1.) (\\d+) - the major version.
    //2.) (\\d+) - the minor version.
    //3.) (\\d+) - the patch version.
    //4.) (\-[^\+]+)? - the '-' and all characters after the '-' that isn't a '+', if it exists.
    //5.) (\+.+)? - the '+' and all characters after the '+'.
    static Pattern semanticVersionPattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(\\-[^\\+]+)?(\\+.+)?");

    public static void main(String [] args) throws Exception
    {
        System.out.print("Enter the number of semantic versions to sort: ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int numberOfVersions = Integer.parseInt(bufferedReader.readLine());

            if(numberOfVersions <= 0)
            {
                throw new Exception("Number must be a positive integer.");
            }

            String[] semanticVersionStrings = new String[numberOfVersions];
            ArrayList<SemanticVersion> semanticVersions;
            int count = 0;

            while(count < numberOfVersions)
            {
                try
                {
                    System.out.print("Enter a semantic version to sort (" + (numberOfVersions - count) + " remaining): ");
                    semanticVersionStrings[count++] = bufferedReader.readLine();
                }
                catch (IOException e)
                {
                    System.out.println("Could not read in semantic version. Please try again.");
                }
            }

            semanticVersions = createSemanticVersions(semanticVersionStrings);
            printAndSortList(semanticVersions);
        }
        catch (IOException e) {
            System.out.println("Could not read in the number of semantic versions to sort.");
        }

    }

    private static void printAndSortList(ArrayList<SemanticVersion> semanticVersions)
    {
        Collections.sort(semanticVersions);
        for(SemanticVersion semanticVersion : semanticVersions)
        {
            System.out.println(semanticVersion);
        }
    }

    protected static ArrayList<SemanticVersion> createSemanticVersions(String[] semanticVersionStrings)
    {
        ArrayList<SemanticVersion> semanticVersions = new ArrayList<>(semanticVersionStrings.length);
        Matcher matcher;
        for(String semanticVersionString: semanticVersionStrings)
        {
            matcher = semanticVersionPattern.matcher(semanticVersionString);
            if(matcher.matches())
            {
                semanticVersions.add(new SemanticVersion(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)),
                    //Call substring to remove the sign in front.
                    //RESEARCH: Is there a way to handle this in the regex?
                    matcher.group(4),
                    matcher.group(5)));
            }
            else
            {
                System.out.println("Could not match " + semanticVersionString + " to pattern.");
            }
        }
        return semanticVersions;
    }

    static class SemanticVersion implements Comparable<SemanticVersion>
    {
        int major;
        int minor;
        int patch;
        String label;
        String metaData;

        public SemanticVersion(int major, int minor, int patch, String label, String metaData)
        {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
            parseLabel(label);
            parseMetaData(metaData);
        }

        private void parseLabel(String label)
        {
            if(label != null)
            {
                this.label = label.substring(1);
            }
        }

        private void parseMetaData(String metaData)
        {
            if(metaData != null)
            {
                this.metaData = metaData.substring(1);
            }
        }

        //RESEARCH: Tradeoffs of implementing a comparator vs. comparable.
        @Override
        public int compareTo(SemanticVersion s) {

            //1.) Compare major versions.
            if(major > s.major)
            {
                return 1;
            }
            else if(major < s.major)
            {
                return -1;
            }

            //2.) Compare minor versions.
            if(minor > s.minor)
            {
                return 1;
            }
            else if(minor < s.minor)
            {
                return -1;
            }

            //3.) Compare the patch version.
            if(patch > s.patch)
            {
                return 1;
            }
            else if(patch < s.patch)
            {
                return -1;
            }

            //4.) Check the label. Contents of label are not compared, but if one
            //    semantic version has a label and the other one doesn't, the one with the label is before (less than).
            if(label.equals("") && !s.label.equals(""))
            {
                return -1;
            }
            else if(!label.equals("") && s.label.equals(""))
            {
                return 1;
            }
            else
            {
                //Either both labels are empty or both labels have a value.
                return 0;
            }
        }

        @Override
        public String toString()
        {
            StringBuilder stringBuilder = new StringBuilder().append(major).append(".")
                                      .append(minor).append(".")
                                      .append(patch);

            if(label != null) {
                stringBuilder.append("-").append(label);
            }
            if(metaData != null)
            {
                stringBuilder.append("+").append(metaData);
            }
            return stringBuilder.toString();
        }
    }
}
