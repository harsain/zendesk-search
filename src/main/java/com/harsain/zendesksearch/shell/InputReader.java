package com.harsain.zendesksearch.shell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jline.reader.LineReader;
import org.springframework.util.StringUtils;

/**
 * InputReader class
 */
public class InputReader {

  public static final Character DEFAULT_MASK = '*';

  private Character mask;

  private LineReader lineReader;

  private ShellHelper shellHelper;

  public InputReader(LineReader lineReader, ShellHelper shellHelper) {
    this.lineReader = lineReader;
    this.shellHelper = shellHelper;
  }

  /**
   * @param prompt : String prompt for shell
   * @return String
   */
  public String prompt(String prompt) {
    return prompt(prompt, null, true);
  }


  /**
   * @param prompt : String prompt for the shell
   * @param defaultValue : String default value for the prompt
   * @return String
   */
  public String prompt(String prompt, String defaultValue) {
    return prompt(prompt, defaultValue, true);
  }


  /**
   * @param prompt shell prompt
   * @param defaultValue default value to be used
   * @param echo whether to echo the entered value
   * @return String prompt
   */
  public String prompt(String prompt, String defaultValue, boolean echo) {
    String answer = "";

    if (echo) {
      answer = lineReader.readLine(prompt + ": ");
    } else {
      answer = lineReader.readLine(prompt + ": ", mask);
    }
    if (StringUtils.isEmpty(answer)) {
      return defaultValue;
    }
    return answer;
  }

  /**
   * Loops until one of the `options` is provided. Pressing return is equivalent to returning
   * `defaultValue`. Passing null for defaultValue signifies that there is no default value. Passing
   * "" or null among optionsAsList means that empty answer is allowed, in these cases this method
   * returns empty String "" as the result of its execution.
   *
   * @param prompt String prompt entered
   * @param defaultValue String default value to be used
   * @param optionsAsList String list of options
   * @return String
   */
  public String promptWithOptions(String prompt, String defaultValue, List<String> optionsAsList) {
    String answer;
    List<String> allowedAnswers = new ArrayList<>(optionsAsList);
    if (StringUtils.hasText(defaultValue)) {
      allowedAnswers.add("");
    }
    do {
      answer = lineReader
          .readLine(String.format("%s %s: ", prompt, formatOptions(defaultValue, optionsAsList)));
    } while (!allowedAnswers.contains(answer) && !"".equals(answer));

    if (StringUtils.isEmpty(answer) && allowedAnswers.contains("")) {
      return defaultValue;
    }
    return answer;
  }

  /**
   * @param defaultValue : String default val to be used
   * @param optionsAsList: String list options
   * @return: String list
   */
  private List<String> formatOptions(String defaultValue, List<String> optionsAsList) {
    List<String> result = new ArrayList();
    for (String option : optionsAsList) {
      String val = option;
      if ("".equals(option) || option == null) {
        val = "''";
      }
      if (defaultValue != null) {
        if (defaultValue.equals(option) || (defaultValue.equals("") && option == null)) {
          val = shellHelper.getInfoMessage(val);
        }
      }
      result.add(val);
    }
    return result;
  }

  /**
   * Loops until one value from the list of options is selected, printing each option on its own
   * line.
   *
   * @param headingMessage String headingMessage shown to the user
   * @param promptMessage String prompt message
   * @param options String Map of options
   * @param ignoreCase boolean if to ignore case
   * @param defaultValue String default value to be used
   * @return String
   */
  public String selectFromList(String headingMessage, String promptMessage,
      Map<String, String> options, boolean ignoreCase, String defaultValue) {
    String answer;
    Set<String> allowedAnswers = new HashSet<>(options.keySet());
    if (defaultValue != null && !defaultValue.equals("")) {
      allowedAnswers.add("");
    }
    shellHelper.print(String.format("%s: ", headingMessage));
    do {
      for (Map.Entry<String, String> option : options.entrySet()) {
        String defaultMarker = null;
        if (defaultValue != null) {
          if (option.getKey().equals(defaultValue)) {
            defaultMarker = "*";
          }
        }
        if (defaultMarker != null) {
          shellHelper.printInfo(
              String.format("%s [%s] %s ", defaultMarker, option.getKey(), option.getValue()));
        } else {
          shellHelper.print(String.format("  [%s] %s", option.getKey(), option.getValue()));
        }
      }
      answer = lineReader.readLine(String.format("%s: ", promptMessage));
    } while (!containsString(allowedAnswers, answer, ignoreCase) && "" != answer);

    if (StringUtils.isEmpty(answer) && allowedAnswers.contains("")) {
      return defaultValue;
    }
    return answer;
  }

  /**
   * @param l : String set
   * @param s : string being currently entered
   * @param ignoreCase : boolean whether to ignore the case
   * @return : boolean value if the string being entered exists in l
   */
  private boolean containsString(Set<String> l, String s, boolean ignoreCase) {
    if (!ignoreCase) {
      return l.contains(s);
    }
    Iterator<String> it = l.iterator();
    while (it.hasNext()) {
      if (it.next().equalsIgnoreCase(s)) {
        return true;
      }
    }
    return false;
  }

}
