package com.ctam.reddit.dailyprogrammer.easy.semanticversion;

import java.util.ArrayList;

import junit.framework.Assert;
import org.junit.Test;
import com.ctam.reddit.dailyprogrammer.easy.semanticversion.SemanticVersionSort.SemanticVersion;


public class SemanticVersionSortTest {

    @Test
    public void testCreateSemanticVersionsWithNoLabelAndNoMetadata()
    {
        String[] semanticVersionStrings = new String[]{"1.2.34"};
        ArrayList<SemanticVersion> semanticVersions = SemanticVersionSort.createSemanticVersions(semanticVersionStrings);

        Assert.assertEquals(semanticVersions.get(0).major, 1);
        Assert.assertEquals(semanticVersions.get(0).minor, 2);
        Assert.assertEquals(semanticVersions.get(0).patch, 34);
        Assert.assertEquals(semanticVersions.get(0).label, null);
        Assert.assertEquals(semanticVersions.get(0).metaData, null);
    }

    @Test
    public void testCreateSemanticVersionsWithLabel()
    {
        String[] semanticVersionStrings = new String[]{"2.0.11-alpha"};
        ArrayList<SemanticVersion> semanticVersions = SemanticVersionSort.createSemanticVersions(semanticVersionStrings);

        Assert.assertEquals(semanticVersions.get(0).major, 2);
        Assert.assertEquals(semanticVersions.get(0).minor, 0);
        Assert.assertEquals(semanticVersions.get(0).patch, 11);
        Assert.assertEquals(semanticVersions.get(0).label, "alpha");
        Assert.assertEquals(semanticVersions.get(0).metaData, null);
    }

    @Test
    public void testCreateSemanticVersionsWithMetaData()
    {
        String[] semanticVersionStrings = new String[]{"0.1.7+amd64"};
        ArrayList<SemanticVersion> semanticVersions = SemanticVersionSort.createSemanticVersions(semanticVersionStrings);

        Assert.assertEquals(semanticVersions.get(0).major, 0);
        Assert.assertEquals(semanticVersions.get(0).minor, 1);
        Assert.assertEquals(semanticVersions.get(0).patch, 7);
        Assert.assertEquals(semanticVersions.get(0).label, null);
        Assert.assertEquals(semanticVersions.get(0).metaData, "amd64");
    }

    @Test
    public void testCreateSemanticVersionsWithLabelAndMetaData()
    {
        String[] semanticVersionStrings = new String[]{"0.1.7-alpha+amd64"};
        ArrayList<SemanticVersion> semanticVersions = SemanticVersionSort.createSemanticVersions(semanticVersionStrings);

        Assert.assertEquals(semanticVersions.get(0).major, 0);
        Assert.assertEquals(semanticVersions.get(0).minor, 1);
        Assert.assertEquals(semanticVersions.get(0).patch, 7);
        Assert.assertEquals(semanticVersions.get(0).label, "alpha");
        Assert.assertEquals(semanticVersions.get(0).metaData, "amd64");
    }
}