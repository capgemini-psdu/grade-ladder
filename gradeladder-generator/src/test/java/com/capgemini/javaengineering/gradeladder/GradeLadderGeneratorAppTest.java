package com.capgemini.javaengineering.gradeladder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GradeLadderGeneratorAppTest {

    @Test
    public void createFileNameShouldFormatG1AsExpected() throws Exception {

        String expectedFileName = "test/flat/SE_Ladder-A1-O1-ApprenticeSoftwareEngineer.md";
        Grade g = Grade.ONE;
        assertEquals(expectedFileName, GradeLadderGeneratorApp.createFileName("test", g));

    }

    @Test
    public void createFileNameShouldFormatG8AsExpected() throws Exception {

        String expectedFileName = "test/flat/SE_Ladder-A8-O8-LeadSoftwareEngineer.md";
        Grade g = Grade.EIGHT;
        assertEquals(expectedFileName, GradeLadderGeneratorApp.createFileName("test", g));

    }

}
