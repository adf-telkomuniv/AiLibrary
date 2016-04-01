/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsCategorization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author dee
 */
public class Preprocessing {

    private HashMap<String, Double> preprocessedData;

    public Preprocessing() {
        preprocessedData = new HashMap<>();
    }

    public String removeUrl(String article) {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(article);
        int i = 0;
        while (m.find()) {
            article = article.replaceAll(m.group(i), "").trim();
            i++;
        }
        return article;
    }

    public String[] stopwordRemoval(String[] tokens) {
        String[] stopword = loadFile("stopword.txt");
        List<String> stp = Arrays.asList(stopword);
        List<String> tkn = Arrays.asList(tokens);
        List<String> res = tkn.stream()
                .filter(o -> !(stp.contains(o)))
                .collect(Collectors.toList());
        return res.toArray(new String[0]);
    }

    public String[] preprocess(String article) {
        String[] result;
        article = article.toLowerCase();
        article = removeUrl(article);
        article = article.replaceAll("[^a-zA-Z|^ ]", "");
        String[] tokens = article.split(" ");

        String[] rootword = new String[1];

        Set<String> rootwordSet = new HashSet(Arrays.asList(rootword));
        Set<String> tokenSet = new HashSet(Arrays.asList(tokens));
        rootwordSet.retainAll(tokenSet);

        String[] nonKataDasar = rootwordSet.toArray(new String[rootwordSet.size()]);

        result = stopwordRemoval(tokens);

        return result;
    }

    public void preprocessInput(String[] input, int[] cls) {
        if (input.length != cls.length) {
            throw new IllegalStateException("Wrong Input size");
        }

        for (int i = 0; i < input.length; i++) {

        }

    }

    public HashMap<String, Double> getPreprocessedData() {
        return preprocessedData;
    }

    public String[] loadFile(String filename) {
        String s = "";
        try (BufferedReader b = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = b.readLine()) != null) {
                s = s + line + "\n";
            }

        } catch (Exception e) {

        }
        return s.split("\n");
    }

}
