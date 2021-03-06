/*
 * Copyright (C) 2015, 2020 Yoshiki Shibata. All rights reserved.
 */
package ch06.ex10;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Write a program that asks the user for a URL, then reads the web page at that URL, and then
 * displays all the links. Use a CompletableFuture for each stage. Don’t call get. To prevent your
 * program from terminating prematurely, call
 *
 * <pre>
 * ForkJoinPool.commonPool().awaitQuiescence(10, TimeUnit.SECONDS);
 * </pre>
 */
public final class ShowLinks {

  public static void main(String[] args) {
    validateArgs(args);

    try {
      URL url = new URL(args[0]);
      CompletableFuture.supplyAsync(() -> blockingReadPage(url))
          .thenApply(ShowLinks::getLinks)
          .thenAccept(
              (lists) -> {
                for (String u : lists) {
                  System.out.println(u);
                }
              });
    } catch (MalformedURLException ex) {
      Logger.getLogger(ShowLinks.class.getName()).log(Level.SEVERE, null, ex);
      showUsage();
      System.exit(1);
    }

    ForkJoinPool.commonPool().awaitQuiescence(10, TimeUnit.SECONDS);
  }

  private static void validateArgs(String[] args) {
    if (args.length != 1) {
      showUsage();
      System.exit(1);
    }
    if (!args[0].startsWith("http://") && !args[0].startsWith("https://")) {
      showUsage();
      System.exit(1);
    }
  }

  private static void showUsage() {
    System.out.printf("ShowLinks <URL>%n  URL: must start with http://%n");
  }

  private static String blockingReadPage(URL url) {
    try {
      Scanner in = new Scanner(url.openStream());
      StringBuilder builder = new StringBuilder();
      while (in.hasNextLine()) {
        builder.append(in.nextLine());
        builder.append("\n");
      }
      return builder.toString();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  private static List<String> getLinks(String page) {
    String hrefPattern = "<a.*?href\\s*?=\\s*?[\"\']([^\"\']*)[\"\'].*?[/]*>";
    Pattern pattern = Pattern.compile(hrefPattern, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(page);
    List<String> result = new ArrayList<>();

    while (matcher.find()) {
      result.add(matcher.group(1));
    }
    return result;
  }
}
