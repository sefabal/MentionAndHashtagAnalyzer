package edu.anadolu;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwitterAnalysis {

    private static HashMap<String, Integer> numberOfEntities = new HashMap<>();

    private static Pattern emojiPattern = Pattern.compile(":[)(/|DSP*O3V]|<3|:'\\(|>:O|☺|☻|♥|♦|♣|♠|•|○|◘|◙|♂|♀|♪|♫|☼|►|◄|↕");

    private static Pattern hashtagPattern = Pattern.compile("#(\\w+)", Pattern.UNICODE_CHARACTER_CLASS);

    private static Pattern mentionPattern = Pattern.compile("@(\\w+)");


    public static void main(String[] args) {

		System.setProperty("file.encoding", "ISO-8859-9");

        Params params;
        try {
            params = CliFactory.parseArguments(Params.class, args);

        } catch (ArgumentValidationException e) {
            System.out.println(e.getMessage());
            return;
        }

        final boolean isReverse = params.getReverse();
        final int numOfEntity = params.getNumber();
        final String searchFor = params.searchFor();
        final boolean isIgnoredCase = params.getIgnoreCase();
        final String fileName = params.getFile();

        try (FileInputStream inputStream = new FileInputStream(fileName);

             Scanner sc = new Scanner(inputStream, "ISO-8859-9")) {

            while (sc.hasNextLine()) {

                String line = sc.nextLine();

                String[] splittedLine = line.split(" ");

                checkFor(splittedLine, searchFor, isIgnoredCase);
//
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        printTopN(isReverse, numOfEntity, searchFor);

    }

    private static void checkFor(String[] splittedLine, String parameter, boolean isIgnoredCase) {

        switch (parameter) {
            case "emoji":
                for (String word : splittedLine) {
//                    word = word.toUpperCase(Locale.forLanguageTag("tr-TR"));

                    Matcher matcher = TwitterAnalysis.emojiPattern.matcher(word);

                    if (matcher.matches())
                        putToMap(word, isIgnoredCase);

                }

                break;
            case "hashtag":

                for (String word : splittedLine) {
                    Matcher matcher = TwitterAnalysis.hashtagPattern.matcher(word);

                    if (matcher.matches())
                        putToMap(word, isIgnoredCase);
                }
                break;
            case "mention":
                ArrayList<String> tempList = new ArrayList<>(); //we need to make sure that eliminate the writer

                for (String word : splittedLine) {
                    Matcher matcher = TwitterAnalysis.mentionPattern.matcher(word);

                    if (matcher.matches())
                        tempList.add(word);
                }
                if (tempList.size() > 1) {
                    for (int i = 1; i < tempList.size(); i++) {
                        putToMap(tempList.get(i), isIgnoredCase);
                    }
                }
                break;

            default:
                throw new IllegalArgumentException("Wrong argument yo! Check the entity. It should be hashtag,mention or emoji");
        }

    }

    private static void putToMap(String word, boolean isIgnoredCase) {

        if (isIgnoredCase) {
            word = word.toLowerCase(Locale.forLanguageTag("tr-TR"));
            if (numberOfEntities.containsKey(word)) {
                numberOfEntities.put(word, numberOfEntities.get(word) + 1);
            } else
                numberOfEntities.put(word, 1);

        } else {
            if (numberOfEntities.containsKey(word)) {
                numberOfEntities.put(word, numberOfEntities.get(word) + 1);
            } else
                numberOfEntities.put(word, 1);

        }
    }

    private static void printTopN(boolean isReverse, int number, String searchFor) {

        //this comparator sort values to ascending order (küçükten büyüğe)

        List<Map.Entry<String, Integer>> list = new LinkedList<>(numberOfEntities.entrySet());
        list.sort(Comparator.comparing(Map.Entry::getValue));

        //if we want descending order we reverse the list with -r flag
        if (!isReverse) {
            Collections.reverse(list);
        }

        for (int i = 0; i < number; i++) {
            if (searchFor.equals("emoji"))
                System.out.println(list.get(i).getKey() + "\t" + list.get(i).getValue());
            else
                System.out.println(list.get(i).getKey().substring(1) + "\t" + list.get(i).getValue());
        }

    }

}






