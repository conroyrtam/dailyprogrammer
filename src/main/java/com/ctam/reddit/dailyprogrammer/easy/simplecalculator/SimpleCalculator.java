package com.ctam.reddit.dailyprogrammer.easy.simplecalculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Source: http://www.reddit.com/r/dailyprogrammer/comments/2h5b2k/09222014_challenge_181_easy_basic_equations/
 * Title: [09/22/2014] Challenge #181 [Easy] Basic Equations
 * Date: Oct 04 2014
 *
 * Description: This class takes in two linear functions and outputs a point of intersection.
 *
 * Input: Format is in slope-intercept form. y = ax+b, where a and b are constants.
 *
 * Future enhancements:
 *      Could expand to solve more complicated functions.
 *      Print double output at a specified precision.
 *
 * Resources used:
 * Regex tutorial: http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
 */
public class SimpleCalculator
{

    //This method finds a point of intersection given two equations.
    public static Point findPoint(String equationOne, String equationTwo) throws Exception
    {
        //Pattern pattern = Pattern.compile("=([\\+\\-]?)([\\d+]x?)([\\+\\-]?)([\\d+]?)");

        //FIXME: Pattern does not work with both integer coefficients and double coefficients.
        Pattern pattern = Pattern.compile("=([\\+\\-]?)(\\d+\\.\\d+x?)([\\+\\-]?)(\\d+\\.\\d+?)");
        Matcher matcher = pattern.matcher(equationOne);

        Double xOne = null;
        Double xTwo = null;
        Double bOne = null;
        Double bTwo = null;

        //Match patterns for equation one.
        if(matcher.find())
        {
            Character xOneSign = getSign(matcher.group(1));
            xOne = getDoubleValue(matcher.group(2), xOneSign);
            Character bOneSign = getSign(matcher.group(3));
            bOne = getDoubleValue(matcher.group(4), bOneSign);
        }
        else
        {
            throw new Exception("Unable to parse equation one");
        }

        //Match patterns for equations two.
        matcher = pattern.matcher(equationTwo);
        if(matcher.find())
        {
            Character xTwoSign = getSign(matcher.group(1));
            xTwo = getDoubleValue(matcher.group(2), xTwoSign);
            Character bTwoSign = getSign(matcher.group(3));
            bTwo = getDoubleValue(matcher.group(4), bTwoSign);
        }
        else
        {
            throw new Exception ("Unable to parse equation two");
        }

        Point point = null;

        //Special case: If the slope is the same, points will never intersect.
        if(xOne.intValue() == xTwo.intValue())
        {
            System.out.println("Slopes are the same, so the points do not intersect");
        }
        else {
            double b = (bOne - bTwo) * -1;
            double x = b / (xOne - xTwo);
            double y = xOne * x + bOne;
            point = new Point(x, y);
        }
        return point;
    }

    //Extracts the sign of a coefficient.
    private static Character getSign(String group) {
        if(group.equals(""))
            return '+';
        return group.charAt(0);
    }

    //Parse the value of the group and apply the sign to the returned result.
    private static Double getDoubleValue(String group, Character sign)
    {
        group = group.replaceAll("x", "");
        if(group.equals(""))
            return 0.0;
        Double value = Double.parseDouble(group);
        if(sign == '-')
        {
            value *= -1;
        }
        return value;
    }


    //RESEARCH: Using keyword 'static'.
    //This class represents a point on a graph.
    static class Point
    {
        double xCoordinate;
        double yCoordinate;

        public Point(double x, double y)
        {
            this.xCoordinate = x;
            this.yCoordinate = y;
        }

        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("(").append(xCoordinate).append(" , ").append(yCoordinate).append(")");
            return builder.toString();
        }
    }
}
