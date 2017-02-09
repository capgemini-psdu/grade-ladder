package com.capgemini.javaengineering.gradeladder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

abstract class ExpectationRenderer {


    final ExpectationRepository repository;
    final Function<Expectation, String> expectationToDescription;
    List<String> output = new ArrayList<>();

    ExpectationRenderer(ExpectationRepository repository) {
        this.repository = repository;
        expectationToDescription = expectation -> expectation.getDescription()+"\n";
    }

    void addHeaderToOutput() {
        output.add("# Software Engineering Ladder");
    }

    void addTraitHeaderToOutput(Trait t) {
        output.add(t.getHeader());
    }

    void addGradeSubHeadingToOutput(int grade) {
        if (grade < 3 ) {
            output.add("### _Apprentice Software Engineer - Grade A" + grade + " and O" + grade + "_");
        } else if (grade < 6 ) {
            output.add("### _Software Engineer - Grade A" + grade + " and O" + grade + "_");
        } else if (grade < 8 ) {
            output.add("### _Senior Software Engineer - Grade A" + grade + " and O" + grade + "_");
        } else {
            output.add("### _Lead Software Engineer - Grade A" + grade + " and O" + grade + "_");
        }
    }

    void addSubTraitSubHeadingToOutput(String subTrait) {
        output.add("### " + subTrait);
    }
}
