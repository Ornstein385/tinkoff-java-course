package edu.java.service;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkTypeDeterminant {
    private LinkTypeDeterminant() {
    }

    public static boolean isGitHubCorrectLink(URI uri) {
        Pattern pattern = Pattern.compile("^https?://github\\.com/[^/]+/[^/]+/?");
        Matcher matcher = pattern.matcher(uri.toString());
        return matcher.find();
    }

    public static boolean isStackOverflowCorrectLink(URI uri) {
        Pattern pattern = Pattern.compile("^https?://stackoverflow\\.com/questions/(\\d+)/?.*");
        Matcher matcher = pattern.matcher(uri.toString());
        return matcher.find();
    }

    public static String getGitHubOwner(URI uri) {
        Pattern pattern = Pattern.compile("^https?://github\\.com/([^/]+)/");
        Matcher matcher = pattern.matcher(uri.toString());
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String getGitHubRepo(URI uri) {
        Pattern pattern = Pattern.compile("^https?://github\\.com/[^/]+/([^/]+)/?");
        Matcher matcher = pattern.matcher(uri.toString());
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String getStackOverflowQuestionId(URI uri) {
        Pattern pattern = Pattern.compile("^https?://stackoverflow\\.com/questions/(\\d+)/?");
        Matcher matcher = pattern.matcher(uri.toString());
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
