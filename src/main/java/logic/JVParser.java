package logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.sql.RowSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

public class JVParser {

	private static ArrayList<RowJCode> rows = new ArrayList<RowJCode>();

	public static void listMethodCalls(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {

			analyzeFileImports(file);
			// Inside a file
			Hashtable<String, String> attributesMap = new Hashtable<>();
			// System.out.println(path);

			try {
				new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(MethodCallExpr n, Object arg) {
						super.visit(n, arg);

						// Assign method name
						String methodName = n.toString();
						// Print if contains assignment
						if (methodName.contains("findViewById")) {
							// System.out.println(Strings.repeat("=", path.length()));
							// System.out.println(" [L] "+ n);
							String parent = n.getParentNode().toString();
							if (parent.contains("=")) {
								parent = parent.replaceAll("\\s+", "");
								parent = parent.replaceAll("Optional\\[", "");
								String[] parts = parent.split("=");

								String attName = parts[0];
								String attRid = parts[1].replaceAll("findViewById\\(", "").replaceAll("\\)]", "");

								// Add attribute to hashmap - Here I have all attributes assigned in class
								attributesMap.put(attName, attRid);

							}

						} else if (methodName.contains("setContentDescription(")) {
							// System.out.println(path);
							// System.out.println(Strings.repeat("=", path.length()));

							String varName = n.toString().split("\\.")[0];
							String rID = "-";
							// System.out.println(" [L] "+ n.toString());
							if (attributesMap.containsKey(varName)) {
								rID = attributesMap.get(varName);
							}
							String descText = n.getArguments().get(0).toString();
							// System.out.println(descText);
							RowJCode row = new RowJCode(rID, n.toString(), "ContentDescription", descText);
							rows.add(row);
						} else if (methodName.contains("setHint(")) {
							// System.out.println(path);
							// System.out.println(Strings.repeat("=", path.length()));

							String varName = n.toString().split("\\.")[0];

							String rID = "-";
							// System.out.println(" [L] "+ n.toString());
							if (attributesMap.containsKey(varName)) {
								rID = attributesMap.get(varName);
							}
							String descText = n.getArguments().get(0).toString();
							// System.out.println(descText);
							RowJCode row = new RowJCode(rID, n.toString(), "Hint", descText);
							rows.add(row);

						}

					}
				}.visit(JavaParser.parse(file), null);
			} catch (IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);
	}

	private static void analyzeFileImports(File file) {

		LineIterator iter = null;
		boolean search = true;
		try {
			iter = FileUtils.lineIterator(file);
			while (iter.hasNext() && search) {
				String line = iter.next().trim();

				if (line.startsWith("import")) {
					String simport = line.split(" ")[1];
					if (simport.equals("android.accessibilityservice")) {
						Statistics.addService(simport);

					}
					if (simport.equals(
							"android.accessibilityservice.AccessibilityService.MagnificationController.OnMagnificationChangedListener;")) {
						Statistics.addService(simport);

					}
					if (simport.equals(
							"android.accessibilityservice.AccessibilityService.SoftKeyboardController.OnShowModeChangedListener;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.AccessibilityButtonController;")) {
						Statistics.addService(simport);

					}
					if (simport.equals(
							"android.accessibilityservice.AccessibilityButtonController.AccessibilityButtonCallback;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.AccessibilityService;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.AccessibilityService.GestureResultCallback;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.AccessibilityService.MagnificationController;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.AccessibilityService.SoftKeyboardController;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.AccessibilityServiceInfo;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.FingerprintGestureController;")) {
						Statistics.addService(simport);

					}
					if (simport.equals(
							"android.accessibilityservice.FingerprintGestureController.FingerprintGestureCallback;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.GestureDescription;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.GestureDescription.Builder;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.GestureDescription.StrokeDescription;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.view.accessibility.AccessibilityEvent;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.*;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.AccessibilityService.*;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.AccessibilityButtonController.*;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.AccessibilityServiceInfo.*;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.FingerprintGestureController.*;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.accessibilityservice.GestureDescription.*;")) {
						Statistics.addService(simport);

					}
					if (simport.equals("android.view.accessibility.*;")) {
						Statistics.addService(simport);

					}
				}
				else if (line.startsWith("public class"))
				{
					search = false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<RowJCode> getJavaArray() {
		return rows;
	}
}
