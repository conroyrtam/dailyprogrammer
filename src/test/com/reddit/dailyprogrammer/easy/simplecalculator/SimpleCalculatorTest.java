package com.reddit.dailyprogrammer.easy.simplecalculator;

/**
 * Test harness for SimpleCalculator.java
 */
public class SimpleCalculatorTest
{
    public static void main(String [] args) throws Exception
    {
        //String equationOne = "y=2x+2";
        //String equationTwo = "y=5x-4";

        //String equationOne = "y=-5x";
        //String equationTwo = "y=-4x+1";

        String equationOne = "y=0.5x+1.3";
        String equationTwo = "y=-1.4x-0.2";

        System.out.println(SimpleCalculator.findPoint(equationOne, equationTwo));
    }
}
