package edu.austral.ingsis;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

public class StringSimplifier {

    public static List<Line> removeEnters(String s){
        List<Line> result = new ArrayList<>();
        List<String> stringDelimiters = getStringDelimiters();
        AtomicInteger row = new AtomicInteger(1);
        String nonChecked = s;
        while (stringDelimiters.stream().anyMatch(nonChecked::contains)) {
            String between = getFirstStringInText(nonChecked);
            String[] aux = split(nonChecked, between).toArray(String[]::new);
            nonChecked = aux.length > 1 ? aux[1] : "";

            if(!result.isEmpty()) addNewAndMerge(result, aux[0], row);
            else result = Arrays.stream(aux[0].split("\n")).map(text -> new Line(text, row.getAndIncrement())).collect(toList());

            if(aux[0].charAt(aux[0].length()-1) == '\n') result.add(new Line(between, row.getAndIncrement()));
            else result.set(result.size()-1, result.get(result.size()-1).concatText(between));
        }
        if (nonChecked.equals(s)) result.addAll(Arrays.stream(s.split("\n")).map(text -> new Line(text, row.getAndIncrement())).collect(toList()));
        else addNewAndMerge(result, nonChecked, row);
        return result;
    }

    public static List<String> removeSpaces(String s){
        List<String> split = new ArrayList<>();
        List<String> stringDelimiters = getStringDelimiters();
        String nonChecked = s;
        while (stringDelimiters.stream().anyMatch(nonChecked::contains)) {
            String between = getFirstStringInText(nonChecked);
            String[] aux = split(nonChecked, between).toArray(String[]::new);
            nonChecked = aux.length > 1 ? aux[1] : "";
            split.addAll(Arrays.asList(aux[0].strip().split(" ")));
            split.add(between);
        }
        if (nonChecked.equals(s)) split.addAll(Arrays.asList(s.split(" ")));
        else split.addAll(Arrays.asList(nonChecked.strip().split(" ")));
        return split;
    }

    private static List<String> getStringDelimiters(){
        return StringUtils.substringBetween(KeyWord.STRING.getRegex(), "[", "]")
                .chars().boxed().map(Character::toString).collect(toList());
    }

    private static void addNewAndMerge(List<Line> linesSoFar, String toAdd, AtomicInteger row){
        row.decrementAndGet();
        List<Line> newLines = Arrays.stream(toAdd.split("\n")).map(text -> new Line(text, row.getAndIncrement())).collect(toList());
        mergeLists(linesSoFar, newLines);
    }

    private static void mergeLists(List<Line> oldLines,List<Line> newLines){
        oldLines.set(oldLines.size()-1, oldLines.get(oldLines.size()-1).concatText(newLines.get(0).getText()));
        oldLines.addAll(newLines.subList(1, newLines.size()));
    }

    private static List<String> split(String txt, String value) {
        char reference = value.charAt(0);
        List<String> array = new ArrayList<>();
        if (txt.contains(value)) {
            array.add(txt.substring(0, txt.indexOf(reference)));
            String s = txt.substring(txt.indexOf(reference)+1);
            String next = s.substring(s.indexOf(reference)+1);
            if(!next.isEmpty()) array.add(next);
        } else array.add(value);
        return array;
    }

    private static String getFirstStringInText(String text){
        String colons = whichCharacterAppearsFirst(text);
        String subBetween =  StringUtils.substringBetween(text, colons);
        if (subBetween == null) throw new RuntimeException("Missing one " + colons + " in " + text);
        return colons + subBetween + colons;
    }

    private static String whichCharacterAppearsFirst(String text){
        char[] chars = StringUtils.substringBetween(KeyWord.STRING.getRegex(), "[", "]").toCharArray();
        List<Integer> indexes = String.copyValueOf(chars).chars().map(text::indexOf)
                .map(i -> i == -1 ? Integer.MAX_VALUE : i).boxed().collect(toList());
        int indexOfNearest = indexes.indexOf(Collections.min(indexes));
        return String.valueOf(chars[indexOfNearest]);
    }
}