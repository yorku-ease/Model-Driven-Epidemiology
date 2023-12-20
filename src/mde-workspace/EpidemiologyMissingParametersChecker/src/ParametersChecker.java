
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParametersChecker {
	public static void main(String[] args) {

		String equationsFile = "src/equations.txt";
		String compartmentsFile = "src/compartments.txt";
		String parametersFile = "src/parameters.txt";
		String outputFile = "src/missing_parameters.txt";

		try {
			ArrayList<String> parameterStrings = extractParameterStrings(equationsFile);
			parameterStrings.addAll(extractContactParametersFromEquations(equationsFile, compartmentsFile));
			ArrayList<String> missingParameters = findMissingParameters(parameterStrings, parametersFile);
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
				// Print missing parameters
				if (!missingParameters.isEmpty()) {
					writer.write(missingParameters.size()+ " Missing Parameters:");
					writer.newLine();
					// write it out to a report file.
					for (String missingParameter : missingParameters) {
						writer.write(missingParameter);
						writer.newLine();
					}
				} else {
					writer.write("All parameters are present in the parameters file :)");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static ArrayList<String> extractParameterStrings(String fileName) throws IOException {
		ArrayList<String> parameterStrings = new ArrayList<>();
		String line;

		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			while ((line = reader.readLine()) != null) {
				String regex = "\\(parameter\\s+([^\\s]+)\\s+([^\\s]+)\\s*\\)";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(line);

				while (matcher.find()) {
					// Group 1 contains the first name, and Group 2 contains the second name
					String parameterString = matcher.group(0);
					parameterStrings.add(parameterString);
				}
			}
		}

		return parameterStrings;
	}
	
	private static ArrayList<String> extractContactParametersFromEquations(String equationsFileName, String compartmentsFileName)
	        throws IOException {
	    ArrayList<String> newParameters = new ArrayList<>();
	    ArrayList<String> compartments = loadCompartments(compartmentsFileName);

	    try (BufferedReader reader = new BufferedReader(new FileReader(equationsFileName))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String regex = "sumproduct[^()]*\\(parameter\\s+([^\\s]+)\\s+([^\\s]+)\\s+\\[([^\\]]+)\\]\\)";
	            Pattern pattern = Pattern.compile(regex);
	            Matcher matcher = pattern.matcher(line);

	            while (matcher.find()) {
	            	String wordWithinBrackets = matcher.group(3);
	                List<List<String>> matchingCompartments = findAllCompartments(compartmentsFileName, wordWithinBrackets);
	                
	                for (List<String> innerList : matchingCompartments) {
	                    String parameterString = String.format("(parameter %s %s %s)", matcher.group(1), matcher.group(2), innerList.toString());
	                    newParameters.add(parameterString);
	                }
	                
	            }
	        }
	    }
	    return newParameters;
	}
	
	private static List<List<String>> findAllCompartments(String filename, String wordToExtract) {
		List<List<String>> matchingRows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
            	ArrayList<String> row = convertRowToList(line);
            	if (row.contains(wordToExtract)) {
                    matchingRows.add(row);
                    
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matchingRows;
    }
	
	private static ArrayList<String> convertRowToList(String row) {
        // Assuming the row is enclosed in square brackets and elements are separated by commas
        row = row.substring(1, row.length() - 1); // Remove square brackets
        String[] elements = row.split(", ");
        ArrayList<String> result = new ArrayList<String>(Arrays.asList(elements));
        return result;
    }


	private static ArrayList<String> findMissingParameters(ArrayList<String> parameterStrings, String parametersFileName) throws IOException {
		ArrayList<String> parametersInFile = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(parametersFileName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				// Use regex to find parameter names in the format (parameter name1 name2)
				String regex = "\\(parameter\\s*([^)]+\\s*)\\)";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(line);

				while (matcher.find()) {
					// Group 1 contains the entire parameter expression, and Group 2 contains the parameter value
					String parameterExpression = matcher.group(0);
					parametersInFile.add(parameterExpression);
				}
			}
		}

		// Find missing parameters
		ArrayList<String> missingParameters = new ArrayList<>();
		for (String parameter : parameterStrings) {
			if (!parametersInFile.contains(parameter) && !missingParameters.contains(parameter)) {
				missingParameters.add(parameter);
			}
		}

		return missingParameters;
	}
	
	private static ArrayList<String> loadCompartments(String compartmentsFileName) throws IOException {
	    ArrayList<String> compartments = new ArrayList<>();

	    try (BufferedReader reader = new BufferedReader(new FileReader(compartmentsFileName))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            // Add entries to the compartments list
	            compartments.add(line);
	        }
	    }

	    return compartments;
	}

}
