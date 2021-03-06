package com.capgemini.javaengineering.gradeladder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GradeLadderGeneratorApp
{
    public static void main( String[] args )
    {

        checkArgs(args);

        String inutFile = args[0];
        String outPutDir = args[1];

        try {
            Path path = Paths.get(inutFile);
            Path folder = Paths.get(outPutDir);
            Stream<String> stream = Files.lines(path, Charset.forName("UTF-8"));
            ExpectationRepository repository = new ExpectationRepository();
            repository.loadLinesStream(stream);

            createGradeExpectations(folder, repository);

            createSingleConsistencyExpectationsList(folder, repository);

            createGradeExpectationsBook(folder, repository);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkArgs(String[] args) {
        if (args.length != 2) {
            showUsageAndQuit();
        }
    }

    private static void showUsageAndQuit() {
        System.out.println("Usage:\n" +
                "GradeLadderGeneratorApp input_file output_directory\n"+
                "Where:\n"+
                "input_file = path to the csv file of the form: Expectation Text,1|2,WIS,Sub Trait\n"+
                "output_directory = location where grade ladder mark down documents created.");
        System.exit(0);
    }

    private static void createSingleConsistencyExpectationsList(Path folder, ExpectationRepository repository) throws IOException {
        FileWriter writer = new FileWriter(folder.toString()+"/flat/SE_Ladder-FullLadder.md");
        ConsistencyExpectationRenderer consistencyExpectationRenderer = new ConsistencyExpectationRenderer(repository);
        writer.write(consistencyExpectationRenderer.render());
        writer.close();
    }

    private static void createGradeExpectations(Path folder, ExpectationRepository repository) throws IOException {

        Collection<Grade> grades = Arrays.asList(Grade.values()).stream().sorted((g1, g2) -> Integer.compare(g1.getGrade(), g2.getGrade())).collect(Collectors.toList());
        for (Grade grade : grades) {

            FileWriter writer = new FileWriter(createFileName(folder.toString(), grade));
            ByGradeExpectationRenderer byGradeExpectationRenderer = new ByGradeExpectationRenderer(repository, grade.getGrade());

            writer.write(byGradeExpectationRenderer.render());
            writer.close();

        }
    }

    private static void createGradeExpectationsBook(Path folder, ExpectationRepository repository) throws IOException {
        // Each Grade is a chapter
        Collection<Grade> grades = Arrays.asList(Grade.values()).stream().sorted((g1, g2) -> Integer.compare(g1.getGrade(), g2.getGrade())).collect(Collectors.toList());
        for (Grade grade : grades) {

            String gradeDirStr = createBookDir(folder.toString(), grade);
            File gradeDir = new File(gradeDirStr);
            gradeDir.mkdirs();

            FileWriter readmeWriter = new FileWriter(createBookDir(folder.toString(), grade) + "/README.md");
            ByGradeExpectationRenderer byGradeExpectationRenderer = new ByGradeExpectationRenderer(repository, grade.getGrade());
            readmeWriter.write(byGradeExpectationRenderer.render());
            readmeWriter.close();

        }

        // Write Summary
        BookSummaryRenderer bookSummaryRenderer = new BookSummaryRenderer(repository);
        FileWriter summaryWriter = new FileWriter(folder.toString() + "/book/SUMMARY.md");
        summaryWriter.write(bookSummaryRenderer.render());
        summaryWriter.close();
    }

    static String createFileName(String folderName, Grade grade) {
        String formatString = "/flat/SE_Ladder-A%d-O%d-%s.md";
        return folderName  + String.format(formatString, grade.getGrade(), grade.getGrade(), grade.getTitle());
    }


    static String createBookDir(String folderName, Grade grade) {
        String formatString = "/book/A and O Grades/A%d-O%d-%s";
        return folderName + String.format(formatString, grade.getGrade(), grade.getGrade(), grade.getTitle());
    }

}
